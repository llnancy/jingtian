调度中心启动时主要执行了`XxlJobScheduler.init()`方法，该方法中有六个初始化动作，这篇文章来分析第三个初始化动作：初始化失败任务管理中心。其关键调用代码为：`JobFailMonitorHelper.getInstance().start();`

## 源码分析
来看`com.xxl.job.admin.core.thread.JobFailMonitorHelper`类的源码。

### 成员变量
先看类的主要成员变量：

![JobFailMonitorHelper.memberVariables](https://cdn.jsdelivr.net/gh/sunchaser-lilu/sunchaser-cdn@master/images/xxl-job/JobFailMonitorHelper.memberVariables.png)

1. 私有`slf4j`日志对象；
2. 私有静态`JobFailMonitorHelper`类实例对象：`instance`；
3. 私有成员变量：`Thread`类对象`monitorThread`；
4. 私有布尔变量`toStop`，初始化为`false`。

### 成员方法
1. 实例`instance`的公有静态访问器`getInstance()`；
2. 公有`start()`方法；
3. 公有`toStop()`方法。
4. 私有`failAlarm()`方法，用于失败告警。

其中公有静态访问器`getInstance()`和私有静态`JobFailMonitorHelper`类实例对象`instance`共同形成了饿汉式单例模式。

### `start()`方法解析
可以发现该类的主要实现是在`start()`方法中，先折叠一些实现我们来看下整体。

![JobFailMonitorHelper.start](https://cdn.jsdelivr.net/gh/sunchaser-lilu/sunchaser-cdn@master/images/xxl-job/JobFailMonitorHelper.start.png)

1. 给私有成员变量`Thread`类对象`monitorThread`赋值，即创建一个子线程：实现`Runnable`接口并重写其`run`方法；
2. 将其设置为守护线程；
3. 设置线程名为：`xxl-job, admin JobFailMonitorHelper`；
4. 调用线程的`start()`方法启动线程。

这其实是一个守护线程。

我们继续来看子线程重写的`run`方法的具体实现：

![JobFailMonitorHelper.complete.start](https://cdn.jsdelivr.net/gh/sunchaser-lilu/sunchaser-cdn@master/images/xxl-job/JobFailMonitorHelper.complete.start.png)

看起来实现有点长，我们逐渐将其拆解来分析。

```
while (!toStop) {
```

循环，判断的条件为私有布尔变量`toStop`的非，而`toStop`被初始化为了`false`，所以这是一个死循环。

```
List<Long> failLogIds = XxlJobAdminConfig.getAdminConfig().getXxlJobLogDao().findFailJobLogIds(1000);
```

从配置“容器”`com.xxl.job.admin.core.conf.XxlJobAdminConfig`对象中拿到`com.xxl.job.admin.dao.XxlJobLogDao`对象，调用`findFailJobLogIds(1000)`方法。这是`Mybatis`持久层的方法，来看一下其实现：

```
/* Mapper接口 */
public List<Long> findFailJobLogIds(@Param("pagesize") int pagesize);

/* xml实现 */
<select id="findFailJobLogIds" resultType="long" >
	SELECT id FROM `xxl_job_log`
	WHERE !(
		(trigger_code in (0, 200) and handle_code = 0)
		OR
		(handle_code = 200)
	)
	AND `alarm_status` = 0
	ORDER BY id ASC
	LIMIT #{pagesize}
</select>
```

传入的参数`pagesize`为`1000`，`SQL`的查询条件：非（调度成功 或 执行成功）。

所以此处实际上就是分页查询`1000`条失败任务日志记录的`failLogIds`。

如果存在失败的日志记录则进行遍历处理，我们往下看处理逻辑：

```
int lockRet = XxlJobAdminConfig.getAdminConfig().getXxlJobLogDao().updateAlarmStatus(failLogId, 0, -1);
if (lockRet < 1) {
    continue;
}
XxlJobLog log = XxlJobAdminConfig.getAdminConfig().getXxlJobLogDao().load(failLogId);
XxlJobInfo info = XxlJobAdminConfig.getAdminConfig().getXxlJobInfoDao().loadById(log.getJobId());
```

调用`XxlJobLogDao`的`updateAlarmStatus`方法，来看下其`Mybatis`的实现：

```
public int updateAlarmStatus(@Param("logId") long logId,
							 @Param("oldAlarmStatus") int oldAlarmStatus,
							 @Param("newAlarmStatus") int newAlarmStatus);

<update id="updateAlarmStatus" >
    UPDATE xxl_job_log
    SET
        `alarm_status` = #{newAlarmStatus}
    WHERE `id`= #{logId} AND `alarm_status` = #{oldAlarmStatus}
</update>
```

`CAS`乐观锁的方式将`alarm_status`字段从`0`更新为`-1`。

> 思考：这里为什么使用乐观锁？有什么作用？
>
> 猜想：支持调度集群部署，高可用。

如果乐观锁“加锁”成功，则根据失败任务`failLogId`加载任务日志信息和任务信息。

得到了任务必要信息后就可以进行处理了，包括重试或告警，我们继续往下看：

```
if (log.getExecutorFailRetryCount() > 0) {
    JobTriggerPoolHelper.trigger(log.getJobId(), TriggerTypeEnum.RETRY, (log.getExecutorFailRetryCount()-1), log.getExecutorShardingParam(), log.getExecutorParam());
	String retryMsg = "<br><br><span style=\"color:#F39C12;\" > >>>>>>>>>>>"+ I18nUtil.getString("jobconf_trigger_type_retry") +"<<<<<<<<<<< </span><br>";
	log.setTriggerMsg(log.getTriggerMsg() + retryMsg);
	XxlJobAdminConfig.getAdminConfig().getXxlJobLogDao().updateTriggerInfo(log);
}
```

判断日志信息中执行器失败重试次数是否仍大于零，是则进行重试。

重试即重新进行调度，调用`JobTriggerPoolHelper`类的`trigger()`方法，传入了五个参数，我们来分别解释一下：
- `log.getJobId()`：任务信息表自增主键。
- `TriggerTypeEnum.RETRY`：执行类型：重试。
- `(log.getExecutorFailRetryCount()-1)`：当前重试次数减一，这里传入是为了执行时存入日志表，当再次失败时可确保重试次数。
- `log.getExecutorShardingParam()`：执行器分片参数（分片任务用到）。
- `log.getExecutorParam()`：执行器执行参数。

关于任务调度执行本篇文章不做详细介绍，只需知道这里触发了任务执行，并将减一后的重试次数存入了日志，若这次执行仍然失败，则可被重新查出并根据剩余重试次数进行处理。

重试完成后更新本条记录的调度日志`trigger_msg`字段。

然后进行告警处理，无论是否有重试都会进行告警处理。

我们来看告警的代码：

```
int newAlarmStatus = 0;		// 告警状态：0-默认、-1=锁定状态、1-无需告警、2-告警成功、3-告警失败
if (info!=null && info.getAlarmEmail()!=null && info.getAlarmEmail().trim().length()>0) {
    boolean alarmResult = true;
    try {
        alarmResult = failAlarm(info, log);
    } catch (Exception e) {
        alarmResult = false;
        logger.error(e.getMessage(), e);
    }
    newAlarmStatus = alarmResult?2:3;
} else {
    newAlarmStatus = 1;
}
XxlJobAdminConfig.getAdminConfig().getXxlJobLogDao().updateAlarmStatus(failLogId, -1, newAlarmStatus);
```

只有配置了告警邮箱才进行告警，核心逻辑是调用了私有成员方法`failAlarm`，根据调用情况得到新的告警状态，最后使用`CAS`的方式更新日志记录表，将`alarm_status`字段从`-1`更新为新的告警状态。

最后阻塞当前子线程`10`秒，一次循环就结束了。

而私有成员方法`failAlarm`的主要逻辑是发送邮件，调度中心`xxl-job-admin`项目配置文件中可配置告警发件邮箱，`Web`管理后台界面添加任务时可填写告警收件人邮箱。该方法有一行代码如下：

```
Set<String> emailSet = new HashSet<String>(Arrays.asList(info.getAlarmEmail().split(",")));
```

从而得知在管理后台界面添加任务时可填写多个告警收件人邮箱，用英文逗号分隔即可，会自动去重。

我们可在该方法中实现自定义的告警方式，例如钉钉机器人等。

## 总结
用以下一张图来对失败任务管理中心进行归纳总结。
![job-fail-monitor](https://cdn.jsdelivr.net/gh/sunchaser-lilu/sunchaser-cdn@master/images/xxl-job/job-fail-monitor.png)