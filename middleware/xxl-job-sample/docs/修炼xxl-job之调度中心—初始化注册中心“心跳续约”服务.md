调度中心启动时主要执行了`XxlJobScheduler.init()`方法，该方法中有六个初始化动作，这篇文章来分析第二个初始化动作：初始化注册中心“心跳续约”服务。其关键调用代码为：`JobRegistryMonitorHelper.getInstance().start();`

## 源码分析
来看`com.xxl.job.admin.core.thread.JobRegistryMonitorHelper`类的源码。

### 成员变量
先看类的成员变量：

![JobRegistryMonitorHelper.memberVariables](https://cdn.jsdelivr.net/gh/sunchaser-lilu/sunchaser-cdn@master/images/xxl-job/JobRegistryMonitorHelper.memberVariables.png)

1. 私有`slf4j`日志对象；
2. 私有静态`JobRegistryMonitorHelper`类实例对象：`instance`；
3. 私有成员变量：`Thread`类对象`registryThread`；
4. 私有布尔变量`toStop`，初始化为`false`。

### 成员方法
1. 实例`instance`的公有静态访问器`getInstance()`；
2. 公有`start()`方法；
3. 公有`toStop()`方法。

其中公有静态访问器`getInstance()`和私有静态`JobRegistryMonitorHelper`类实例对象`instance`共同形成了饿汉式单例模式。

### `start()`方法解析
可以发现该类的主要实现是在`start()`方法中，先折叠一些实现我们来看下整体。

![JobRegistryMonitorHelper.start](https://cdn.jsdelivr.net/gh/sunchaser-lilu/sunchaser-cdn@master/images/xxl-job/JobRegistryMonitorHelper.start.png)

1. 给私有成员变量`Thread`类对象`registryThread`赋值，即创建一个子线程：实现`Runnable`接口并重写其`run`方法；
2. 将其设置为守护线程；
3. 设置线程名为：`xxl-job, admin JobRegistryMonitorHelper`；
4. 调用线程的`start()`方法启动线程。

这其实是一个守护线程。

我们继续来看子线程重写的`run`方法的具体实现：

![JobRegistryMonitorHelper.complete.start](https://cdn.jsdelivr.net/gh/sunchaser-lilu/sunchaser-cdn@master/images/xxl-job/JobRegistryMonitorHelper.complete.start.png)

看起来实现有点长，我们逐渐将其拆解来分析。

```
while (!toStop) {
```

循环，判断的条件为私有布尔变量`toStop`的非，而`toStop`被初始化为了`false`，所以这是一个死循环。

```
List<XxlJobGroup> groupList = XxlJobAdminConfig.getAdminConfig().getXxlJobGroupDao().findByAddressType(0);
```

从配置“容器”`com.xxl.job.admin.core.conf.XxlJobAdminConfig`对象中拿到`com.xxl.job.admin.dao.XxlJobGroupDao`对象，调用`findByAddressType(0)`方法。这是`Mybatis`持久层的方法，来看一下其实现：

```
/* Mapper接口 */
public List<XxlJobGroup> findByAddressType(@Param("addressType") int addressType);

/* xml实现 */
<select id="findByAddressType" parameterType="java.lang.Integer" resultMap="XxlJobGroup">
	SELECT <include refid="Base_Column_List" />
	FROM xxl_job_group AS t
	WHERE t.address_type = #{addressType}
	ORDER BY t.order ASC
</select>
```

传入的参数`addressType`为`0`，`SQL`的含义为：查询`xxl_job_group`表中`address_type`字段为`0`的数据并按`order`字段升序排序。

我们可以去初始化`sql`脚本中寻找该字段的含义：`执行器地址类型：0=自动注册、1=手动录入`。

所以此处代码的作用是：查询地址类型为自动注册的执行器信息列表。

如果查询出的执行器列表不为空则继续往下执行。

```
List<Integer> ids = XxlJobAdminConfig.getAdminConfig()
                                .getXxlJobRegistryDao()
                                .findDead(RegistryConfig.DEAD_TIMEOUT, new Date());
if (ids!=null && ids.size()>0) {
    XxlJobAdminConfig.getAdminConfig().getXxlJobRegistryDao().removeDead(ids);
}
```

调用`XxlJobRegistryDao`的`findDead`方法，传入的参数为：
- `RegistryConfig.DEAD_TIMEOUT`：超时时间，值为`90`；
- `new Date()`：当前时间对象。

来看一下`Mybatis`的实现：

```
public List<Integer> findDead(@Param("timeout") int timeout,
                              @Param("nowTime") Date nowTime);
                                  
<select id="findDead" parameterType="java.util.HashMap" resultType="java.lang.Integer" >
	SELECT t.id
	FROM xxl_job_registry AS t
	WHERE t.update_time <![CDATA[ < ]]> DATE_ADD(#{nowTime},INTERVAL -#{timeout} SECOND)
</select>
```

查询的是主键`id`列表，关键的是`where`条件，`xxl_job_registry`表的`update_time`字段小于传入的当前时间减去传入的超时时间`90`秒。

其含义为：查询出`90`秒内未更新时间的任务`id`集合列表。

>思考：这里查询时为什么选择传入`Java`时间而不是直接用`MySQL`的`NOW()`函数获取当前时间？
>
>解答：防止`MySQL`服务器和调度中心服务器的时钟不同步。

如果查到了记录，则调用`XxlJobRegistryDao`的`removeDead`方法。来看下其`Mybatis`实现：

```
public int removeDead(@Param("ids") List<Integer> ids);
    
<delete id="removeDead" parameterType="java.lang.Integer" >
	DELETE FROM xxl_job_registry
	WHERE id in
	<foreach collection="ids" item="item" open="(" close=")" separator="," >
		#{item}
	</foreach>
</delete>
```

删除任务`id`列表对应的`xxl_job_registry`记录。

看到这里，我们应该理解到这实际上是“心跳”机制。“心跳”的最长时间间隔为`90`秒。

继续往下看，初始化了一个局部`HashMap`对象：

```
HashMap<String, List<String>> appAddressMap = new HashMap<String, List<String>>();
List<XxlJobRegistry> list = XxlJobAdminConfig.getAdminConfig()
                                    .getXxlJobRegistryDao()
                                    .findAll(RegistryConfig.DEAD_TIMEOUT, new Date());
```

随即调用`XxlJobRegistryDao`的`findAll`方法查询出了一个`List<XxlJobRegistry>`集合，我们来看下查询条件：

```
public List<XxlJobRegistry> findAll(@Param("timeout") int timeout,
                                    @Param("nowTime") Date nowTime);
                                        
<select id="findAll" parameterType="java.util.HashMap" resultMap="XxlJobRegistry">
	SELECT <include refid="Base_Column_List" />
	FROM xxl_job_registry AS t
	WHERE t.update_time <![CDATA[ > ]]> DATE_ADD(#{nowTime},INTERVAL -#{timeout} SECOND)
</select>
```

重点是`where`条件：更新时间`update_time`大于当前时间减去“死亡”超时时间`90`秒。

即：查询上次“心跳”时间在`90`秒以内的注册列表。

实际上这是在做执行器“心跳”续约，类似于微服务中的服务续约。

我们继续往下看：

```
if (list != null) {
    for (XxlJobRegistry item: list) {
        if (RegistryConfig.RegistType.EXECUTOR.name().equals(item.getRegistryGroup())) {
            String appName = item.getRegistryKey();
            List<String> registryList = appAddressMap.get(appName);
            if (registryList == null) {
                registryList = new ArrayList<String>();
            }
            if (!registryList.contains(item.getRegistryValue())) {
                registryList.add(item.getRegistryValue());
            }
            appAddressMap.put(appName, registryList);
        }
    }
}
```

如果查询到有未“死亡”的注册列表，则对其进行“续约”：
- 遍历查询出的注册列表。
- 如果注册组为执行器（这里注意到枚举`RegistType`有两个值：`EXECUTOR`（执行器）和`ADMIN`（调度中心））。
- 取出注册表的注册键（`xxl_job_registry`表中的`registry_key`字段）：即执行器名称`appName`。
- 先尝试从局部`map`变量中取出该执行器名称对应注册地址集合，若没有则新建一个空集合（懒加载思想）。
- 判断从`map`中取出的注册地址集合是否包含当前遍历的注册表的注册值（`xxl_job_registry`表中的`registry_value`字段），若不包含则添加至集合（去重）。
- 将执行器名称`appName`和注册地址集合映射至局部`map`变量。

映射至局部`map`变量后，开始执行真正的“续约”动作：

```
for (XxlJobGroup group: groupList) {
    List<String> registryList = appAddressMap.get(group.getAppName());
    String addressListStr = null;
    if (registryList!=null && !registryList.isEmpty()) {
        Collections.sort(registryList);
        addressListStr = "";
        for (String item:registryList) {
            addressListStr += item + ",";
        }
        addressListStr = addressListStr.substring(0, addressListStr.length()-1);
    }
    group.setAddressList(addressListStr);
    XxlJobAdminConfig.getAdminConfig().getXxlJobGroupDao().update(group);
}
```

- 遍历从`xxl_job_group`表中查询出的自动注册执行器的信息列表。
- 从局部`map`变量中取出当前遍历执行器对应的“心跳续约”注册地址集合。
- 组装执行器注册地址列表，多地址逗号分隔；如果“续约”的地址集合为空，则代表当前执行器已“死亡”。
- 更新执行器“续约”的地址信息至`DB`：更新`xxl_job_group`表的`address_list`字段。

至此，任务注册中心“心跳”机制就已完成。

> 到这里我们或许有了一个疑问？`xxl_job_registry`任务注册表的记录是何时存在的？即任务是何时被注册的？
> 我们也许会在后面的文章中有所发现。

最后，阻塞当前子线程`30`秒。
```
try {
    TimeUnit.SECONDS.sleep(RegistryConfig.BEAT_TIMEOUT);
} catch (InterruptedException e) {
    if (!toStop) {
        logger.error(">>>>>>>>>>> xxl-job, job registry monitor thread error:{}", e);
    }
}
```

这里调用`java.util.concurrent`包下的枚举`TimeUnit`类的`sleep`方法进行睡眠，先指定时间单位，再指定时间大小，代码可读性更高；如果直接使用传统`Thread.sleep()`方法，传给`sleep`方法的值的单位是毫秒，即需传入`30*1000`，代码可读性不高。

## 总结
用以下一副图来对注册中心“心跳续约”服务进行归纳总结。
![registry-center-heartbeat-renewal](https://cdn.jsdelivr.net/gh/sunchaser-lilu/sunchaser-cdn@master/images/xxl-job/registry-center-heartbeat-renewal.png)