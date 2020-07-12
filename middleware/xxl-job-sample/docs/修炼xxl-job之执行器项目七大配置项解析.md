这篇文章主要是解析执行器项目七大配置项作用和原理。我们以示例执行器`xxl-job-executor-sample-springboot`项目为例。

## 工程结构介绍
```
├─java
│  └─com
│      └─xxl
│          └─job
│              └─executor
│                  │  XxlJobExecutorApplication.java  ------启动类
│                  │
│                  ├─core
│                  │  └─config
│                  │          XxlJobConfig.java       ------XxlJobSpringExecutor配置类
│                  │
│                  ├─mvc
│                  │  └─controller
│                  │          IndexController.java    ------空文件
│                  │
│                  └─service
│                      └─jobhandler
│                              SampleXxlJob.java      ------示例执行器类
│
└─resources
        application.properties                        ------项目配置文件
        logback.xml                                   ------日志配置文件
```

## `com.xxl.job.executor.core.config.XxlJobConfig`配置类解读
由于是`SpringBoot`项目，该类采用`@Configuration`注解添加配置。

### 成员变量简介
该配置类一共包含八个成员变量。

#### 日志对象
```
private Logger logger = LoggerFactory.getLogger(XxlJobConfig.class);
```
`slf4j`的日志对象，用来打印关键日志。

#### 七大核心属性变量
```
@Value("${xxl.job.admin.addresses}")
private String adminAddresses;

@Value("${xxl.job.executor.appname}")
private String appName;

@Value("${xxl.job.executor.ip}")
private String ip;

@Value("${xxl.job.executor.port}")
private int port;

@Value("${xxl.job.accessToken}")
private String accessToken;

@Value("${xxl.job.executor.logpath}")
private String logPath;

@Value("${xxl.job.executor.logretentiondays}")
private int logRetentionDays;
```

使用`Spring`提供的`@Value`注解来注入配置文件`application.properties`中的配置信息。

我们打开`resources`目录下的`application.properties`文件查看，默认配置信息如下：
```
### xxl-job admin address list, such as "http://address" or "http://address01,http://address02"
xxl.job.admin.addresses=http://127.0.0.1:8080/xxl-job-admin

### xxl-job executor address
xxl.job.executor.appname=xxl-job-executor-sample
xxl.job.executor.ip=
xxl.job.executor.port=9999

### xxl-job, access token
xxl.job.accessToken=

### xxl-job log path
xxl.job.executor.logpath=/data/applogs/xxl-job/jobhandler
### xxl-job log retention days
xxl.job.executor.logretentiondays=30
```
根据配置文件中的相关注释我们来解释七大核心属性的含义：
- `adminAddresses`：
    - 配置项：`xxl.job.admin.addresses`，选填。
    - 含义：调度中心部署根地址。
    - 作用：执行器会使用该地址进行“执行器心跳注册”和任务结果回调。
    - 注意事项：如调度中心集群部署，存在多个根地址，则用逗号分隔。为空不填则关闭自动注册功能。
- `appName`：
    - 配置项：`xxl.job.executor.appname`，选填。
    - 含义：是每个执行器集群的唯一标示AppName。
    - 作用：执行器心跳注册分组依据。
    - 注意事项：为空不填表示关闭自动注册功能。
- `ip`：
    - 配置项：`xxl.job.executor.ip`，选填。
    - 含义：执行器`IP`。
    - 作用：适用于多网卡时手动设置指定`IP`，该`IP`不会绑定`Host`仅作为通讯使用；用于“执行器注册”和“调度中心请求并触发任务”。
    - 注意事项：为空不填表示自动获取`IP`。
- `port`：
    - 配置项：`xxl.job.executor.port`，选填。
    - 含义：执行器端口号。执行器实际是一个内嵌的`Server`，默认端口`9999`。
    - 作用：用于“执行器注册”和“调度中心请求并触发任务”时通讯。
    - 注意事项：小于等于`0`时自动获取。单机部署多个执行器时，不同执行器端口不能相同。
- `accessToken`：
    - 配置项：`xxl.job.accessToken`，选填。
    - 含义：访问令牌。
    - 作用：为提升系统安全性，调度中心和执行器进行安全性校验，双方`accessToken`匹配才允许通讯。
    - 注意事项：正常通讯只有两种设置；
        - 设置一：调度中心和执行器均不设置`accessToken`，关闭访问令牌校验。
        - 设置二：调度中心和执行器设置相同的`accessToken`。
- `logPath`：
    - 配置项：`xxl.job.executor.logpath`，选填。
    - 含义：执行器运行日志文件存储磁盘路径。
    - 作用：设置执行器运行日志文件存储磁盘路径。
    - 注意事项：需要对设置的路径拥有读写权限；为空则使用默认路径（`/data/applogs/xxl-job/jobhandler`）。
- `logRetentionDays`：
    - 配置项：`xxl.job.executor.logretentiondays`，选填。
    - 含义：执行器日志文件保存天数。
    - 作用：设置过期日志自动清理。
    - 注意事项：设置的值大于等于`3`时生效；否则日志自动清理功能关闭。

### `com.xxl.job.executor.core.config.XxlJobConfig#xxlJobExecutor`方法作用
```
@Bean
public XxlJobSpringExecutor xxlJobExecutor() {
    logger.info(">>>>>>>>>>> xxl-job config init.");
    XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
    xxlJobSpringExecutor.setAdminAddresses(adminAddresses);
    xxlJobSpringExecutor.setAppName(appName);
    xxlJobSpringExecutor.setIp(ip);
    xxlJobSpringExecutor.setPort(port);
    xxlJobSpringExecutor.setAccessToken(accessToken);
    xxlJobSpringExecutor.setLogPath(logPath);
    xxlJobSpringExecutor.setLogRetentionDays(logRetentionDays);

    return xxlJobSpringExecutor;
}
```
上一部分列举出的七大属性最终设置给了`com.xxl.job.core.executor.impl.XxlJobSpringExecutor`这个类的对象，并使用`@Bean`注解交由`Spring`进行管理。该方法中打印的日志信息 `>>>>>>>>>>> xxl-job config init.` 我们在启动执行器时控制台有输出。

## 七大属性配置详解
上一部分列举出了每个属性的注意事项等，这一部分我们去源码中验证上一部分的内容。

### 目标
我们要找到上述七大属性在设置给`com.xxl.job.core.executor.impl.XxlJobSpringExecutor`这个类的对象时是如何以及怎样进行条件限制的。

### 思考
从`com.xxl.job.executor.core.config.XxlJobConfig#xxlJobExecutor`方法来看，这些配置从配置文件中读到值后是直接使用变异器（`setter`方法）设置给`xxlJobSpringExecutor`这个对象，然后就把这个对象交给`Spring`进行管理了，所以只有两种可能，第一是在变异器中进行了逻辑处理，第二是在`Spring`加载`bean`的过程中进行了逻辑处理。

### 实践
我们首先看一下`com.xxl.job.core.executor.impl.XxlJobSpringExecutor`这个类的定义：

```
public class XxlJobSpringExecutor extends XxlJobExecutor implements ApplicationContextAware, InitializingBean, DisposableBean {
......
```

该类继承了`com.xxl.job.core.executor.XxlJobExecutor`父类并实现了`Spring`的三个接口；我们在该类中未找属性对应的变异器，所以我们几乎可以断定这些属性是定义在父类中。

我们来看父类的代码

![XxlJobExecutor变异器.png](https://i.loli.net/2020/02/15/Xg4kodaAcUKtLG9.jpg)

变异器只是单纯的把传入的值设置给对象，所以排除了第一种可能，情况只能是第二种：在`Spring`加载`bean`的过程中进行了逻辑处理。

我们继续往下看父类`XxlJobExecutor`的代码，发现`start`方法中用到了这些属性，接下来我们来仔细阅读以下该方法的代码：
```
// ---------------------- start + stop ----------------------
public void start() throws Exception {

    // init logpath
    XxlJobFileAppender.initLogPath(logPath);

    // init invoker, admin-client
    initAdminBizList(adminAddresses, accessToken);


    // init JobLogFileCleanThread
    JobLogFileCleanThread.getInstance().start(logRetentionDays);

    // init TriggerCallbackThread
    TriggerCallbackThread.getInstance().start();

    // init executor-server
    port = port>0?port: NetUtil.findAvailablePort(9999);
    ip = (ip!=null&&ip.trim().length()>0)?ip: IpUtil.getIp();
    initRpcProvider(ip, port, appName, accessToken);
}
```
一共有七行核心代码，我们一行一行的来解读：

1. `XxlJobFileAppender.initLogPath(logPath);`

从方法名来看显然是初始化日志存储路径，我们进入`XxlJobFileAppender`类查看`initLogPath(logPath)`方法，代码如下：

![XxlJobFileAppender_initLogPath.png](https://i.loli.net/2020/02/15/DswcF91RrLxi4Ca.jpg)

我们可以看到静态成员变量`logBasePath`的值为`/data/applogs/xxl-job/jobhandler`，当`logPath`属性未配置时该路径即为日志存盘的默认路径。

该方法的实现思路如下：
- 如果配置的`logPath`不为空则覆盖静态成员变量`logBasePath`的值。
- 以静态成员变量`logBasePath`的值调用`File`类的`mkdirs`方法创建多级文件目录。
- 调用`File`类的`getPath`方法将创建好的文件夹的路径重新赋值给静态成员变量`logBasePath`。

以上就是属性`logPath`的配置方式。

2. `initAdminBizList(adminAddresses, accessToken);`

从方法名来看该方法的作用是初始化调度中心部署根地址集合，其参数是配置项`adminAddresses`和`accessToken`的值。我们来看该方法的具体实现：

![initAdminBizList.png](https://i.loli.net/2020/02/15/KANYOLTaBfeh3mx.jpg)

该方法的实现思路如下：
- 判断传入的配置项`adminAddresses`的值是否为`null`并且去除两端空格后的长度是否大于零。
- 如果不满足第一步的条件则什么也不做，即关闭自动注册功能；如果满足第一步的条件，则去除两端空格后调用`split`方法以英文逗号`,`分割成字符串数组进行遍历，遍历的第一步是限制数组中的单个地址值不为`null`并且去除两端空格后的长度大于零，这一步是为了防止`,http://127.0.0.1:8080/xxl-job-admin,`等类似误配置。
- 一切限制条件通过后开始创建`com.xxl.job.core.biz.client.AdminBizClient`类的对象，并添加至静态成员变量`adminBizList`集合中。这里是在第一次循环时才使用`new`关键字创建`ArrayList`集合对象，其思想是“懒加载”，用时才去创建对象。由于`Spring`的`bean`的默认作用域是单例的，所以保证了该初始化方法只会执行一次。

我们来看一下`com.xxl.job.core.biz.client.AdminBizClient`类的构造方法：

![AdminBizClient_AdminBizClient.png](https://i.loli.net/2020/02/15/on4UPLwMYal8jsm.png)

构造方法中对传入的`addressUrl`进行了简单的`valid`校验，如果不是以`/`结尾则将`/`拼接至末尾。此处可看出我们的`adminAddresses`配置实际会变成类似`http://127.0.0.1:8080/xxl-job-admin/`这样的字符串。至于该类对象什么时候使用我们暂时没有线索，大可先不关注这个。

3. `JobLogFileCleanThread.getInstance().start(logRetentionDays);`

调用`JobLogFileCleanThread`类的`getInstance`方法取得该类对象再调用其`start(final long logRetentionDays)`方法。

从类名来看是清除日志文件的线程。

我们前往`com.xxl.job.core.thread.JobLogFileCleanThread`类看一下`getInstance`方法，会发现有如下两行关键代码：
```
private static JobLogFileCleanThread instance = new JobLogFileCleanThread();
public static JobLogFileCleanThread getInstance() {
    return instance;
}
```
这是“饿汉式”单例模式的写法，创建了单例的`JobLogFileCleanThread`对象。

接下来看一下`start`方法：

![JobLogFileCleanThread_start.png](https://i.loli.net/2020/02/15/V7KL4TDFZdYaqrj.png)

首先第一步检查设置的`logRetentionDays`属性是否小于`3`，小于则直接`return`；这里说明了配置文件中的值设置小于`3`时关闭日志自动清理功能。

该方法中实例化了成员变量`private Thread localThread;`，调用了`setDaemon(true)`方法将该线程设置为守护线程，并调用`setName`方法将线程名设为了`xxl-job, executor JobLogFileCleanThread`。

线程中运行的`run`方法逻辑大致如下：
- 调用`XxlJobFileAppender.getLogPath()`方法获取设置的日志存盘路径
- 遍历该路径下所有文件夹，判断当前时间与文件夹的创建时间之差是否大于等于设置的`logRetentionDays`天数，如果大于则调用工具类`com.xxl.job.core.util.FileUtil`的`deleteRecursively`方法递归删除文件夹下的所有文件。
- 删除逻辑完成后，有这样一行代码：`TimeUnit.DAYS.sleep(1);`，线程睡眠一天。这里调用并发包下的`TimeUnit`类的`sleep`方法让代码可读性更高，如果直接使用传统`Thread.sleep()`方法，传给`sleep`方法的值的单位是毫秒，即需传入`24*60*60*1000`，代码可读性不高。

4. `TriggerCallbackThread.getInstance().start();`

从类名`TriggerCallbackThread`来看，这是执行器回调线程，由于未使用到配置参数，这篇文章不对其进行展开解读。

5. `port = port>0?port: NetUtil.findAvailablePort(9999);`

初始化`port`端口号。

如果配置文件中设置的端口号大于零，则使用配置文件中的值；

否则执行代码`NetUtil.findAvailablePort(9999);`，我们进入`NetUtil`类查看`findAvailablePort`方法，发现这个类属于`com.xxl.rpc.util`包，可见，`xxl-job`依赖了`xxl-rpc`（这是作者许雪里开源的`rpc`框架）。

从方法名来看作用是“寻找可用端口”，我们来看一下`findAvailablePort`方法的具体实现逻辑：

![NetUtil_findAvailablePort.png](https://i.loli.net/2020/02/15/Y6MigAsaEbWwLxv.png)

- 传入的`9999`作为默认端口，首先循环`9999`到`65534`端口，逐个进行`!isPortUsed(portTmp)`判断，如果返回`true`则表示当前端口号未被使用，返回赋值给属性`port`。
- 如果上述循环未找到可使用的端口，则再循环`9999`到`1`端口，同样逐个进行`!isPortUsed(portTmp)`判断，如果还未找到可用端口，则抛出`XxlRpcException`异常，异常信息为`no available port.`。

我们来看一下`isPortUsed`方法是如何判断端口是否被使用的：

![NetUtil_isPortUsed.png](https://i.loli.net/2020/02/15/jy9bL4ZIrwB7shd.png)

实际是尝试去创建一个`ServerSocket`客户端并与传入的端口号进行绑定，如端口被占用则绑定时会抛出`IOException`，由此来确定端口是否被使用，从而在未配置端口号时选出一个可用端口。最终`finally`代码块中调用了`close`方法关闭资源。

6. `ip = (ip!=null&&ip.trim().length()>0)?ip: IpUtil.getIp();`

作用：确定`ip`地址。示例执行器的配置中是未进行配置的，所以会执行`IpUtil.getIp()`方法，该方法中最后会调用`java.net.InetAddress#getHostAddress`方法，该方法返回`null`，所以属性`ip`会被设置成`null`。

7. `initRpcProvider(ip, port, appName, accessToken);`

传入了四个参数：`ip`、`port`、`appName`和`accessToken`用来初始化`Rpc`服务提供者。这部分属于`xxl-rpc`的内容，目前我们可以简单看看，大致内容是创建出`XxlRpcProviderFactory`类的对象，给该对象设置相关属性，添加`com.xxl.job.core.biz.impl.ExecutorBizImpl`服务至`rpc`服务提供者`map`容器中，最后调用`start`方法启动服务提供者（这里实际是一个`NettyServer`）。

现在我们大可不必去关注`xxl-rpc`是怎么实现的，这篇文章的目的是搞清楚七大核心配置的工作原理。


父类`XxlJobExecutor`的`start`方法我们看完了，那么它是什么时机执行的呢？

我们注意到`XxlJobSpringExecutor`类实现了`Spring`的`InitializingBean`接口，该接口提供了`afterPropertiesSet`方法供子类实现，在`bean`加载过程中会执行该方法。

接下来我们来看一下`XxlJobSpringExecutor`类中重写的`afterPropertiesSet`方法：

```
// start
@Override
public void afterPropertiesSet() throws Exception {

    // init JobHandler Repository
    initJobHandlerRepository(applicationContext);

    // init JobHandler Repository (for method)
    initJobHandlerMethodRepository(applicationContext);

    // refresh GlueFactory
    GlueFactory.refreshInstance(1);

    // super start
    super.start();
}
```

前三行代码是初始化一些东西，暂时不去关注；最后一行`super.start()`是我们的关键，调用了父类的`start`方法。

## 总结
至此，我们知道了七大配置项的基本原理，对我们使用`xxl-job`有了一些帮助。例如配置项`logretentiondays`不能小于`3`，否则日志文件不会自动清理等。