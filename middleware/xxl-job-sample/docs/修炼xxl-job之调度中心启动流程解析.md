这篇文章主要是解析调度中心`xxl-job-admin`启动流程。

## 准备
我们先来看一下调度中心项目的`maven`依赖。依赖了`Spring Boot`的一些`starter`依赖：`web`、`test`、`freemarker`、`mail`、`actuator`和`mybatis`等，最后依赖了`xxl-job-core`包。

还记得我们在 [修炼xxl-job之搭建本地调度平台](/2020/02/13/middleware/xxl-job/build-local-dispatch-platform/) 时启动调度中心过程中控制台输出的一句日志吗？

```
13:01:40.001 logback [xxl-job, admin JobScheduleHelper#scheduleThread] INFO  c.x.j.a.c.thread.JobScheduleHelper - >>>>>>>>> init xxl-job admin scheduler success.
```

`JobScheduleHelper`类打印了`>>>>>>>>> init xxl-job admin scheduler success.`。表示初始化任务调度器成功。

## 启动分析
我们找到`com.xxl.job.admin.core.thread.JobScheduleHelper`类中打印启动日志的代码，发现其在该类的`start`方法中，`start`方法初始化了一个子线程，在子线程中打印了日志。

那么该`start`方法是何时被执行的呢？我们发现该类并未交由`Spring`管理，借助于`IDEA`的快捷键`Ctrl+Alt+H`查看该方法的调用处，发现其在`XxlJobScheduler.init()`方法中被调用了，来看一下`init`方法的代码：
```
    public void init() throws Exception {
        // init i18n
        initI18n();

        // admin registry monitor run
        JobRegistryMonitorHelper.getInstance().start();

        // admin monitor run
        JobFailMonitorHelper.getInstance().start();

        // admin trigger pool start
        JobTriggerPoolHelper.toStart();

        // admin log report start
        JobLogReportHelper.getInstance().start();

        // start-schedule
        JobScheduleHelper.getInstance().start();

        logger.info(">>>>>>>>> init xxl-job admin success.");
    }
```

`JobScheduleHelper.getInstance().start();`这一行代码使用“饿汉式”单例模式得到`JobScheduleHelper`类对象并随之调用了其`start`方法，然后打印了一行日志：`>>>>>>>>> init xxl-job admin success.`。

思考：既然`JobScheduleHelper`类中的子线程中的日志都进行了打印，那么在其之后的日志打印在了何处呢？

我们复制代码中的日志信息，前往调度中心启动的控制台中按下`Ctrl+F`搜索相关信息，发现确实打印了，并且打印的位置非常靠前。

再来看一下`init()`方法的写法，几乎都是调用某个`XxxxxHelper`类的`getInstance()`方法然后再调用其`start()`方法。

猜想：这里所有的`XxxxxHelper`类的`start()`方法都是开启子线程执行相关任务，才导致`init()`方法中的日志打印位置非常靠前。

实际上这是全异步化设计思想的体现。

我们发现`init()`方法所在的类`XxlJobScheduler`也未交由`Spring`管理，继续借助`IDEA`的快捷键`Ctrl+Alt+H`查看`init()`方法的调用处，发现是在`XxlJobAdminConfig.afterPropertiesSet()`方法中被调用的。

![XxlJobAdminConfig.afterPropertiesSet](https://cdn.jsdelivr.net/gh/sunchaser-lilu/sunchaser-cdn@master/images/xxl-job/XxlJobAdminConfig.afterPropertiesSet.png)

`XxlJobAdminConfig`类使用`@Component`注解交由`Spring`进行管理，并实现了`InitializingBean`和`DisposableBean`这两个接口，重写的`afterPropertiesSet()`方法中只有简单的三行代码，第二行使用`new`关键字创建了`XxlJobScheduler`对象赋给私有成员变量`xxlJobScheduler`，随后第三行调用了其`init()`方法。

至此，我们找到了启动的起点，不妨先来分析一下这个“起点类”。

从类名来看，它是一个配置类。其第一个静态成员变量`adminConfig`是它本身，并在`afterPropertiesSet()`方法的第一行将`this`关键字赋给了该变量。

这是什么意思？“自身包含自身”吗？在该静态成员变量下还提供了一个静态方法`getAdminConfig()`用来获取该静态成员变量的引用。那我可以无限链式调用静态方法`getAdminConfig()`了：

```
XxlJobAdminConfig.getAdminConfig().getAdminConfig().getAdminConfig().......getAdminConfig();
```

只有第一次调用是通过类名.静态方法名，第一次调用返回了类的实例对象`adminConfig`，所以第二次以及之后的每次链式调用都是通过实例对象.静态方法名。这是不被建议和认可的，编译器也发出了黄色警告。所以每次使用时建议是只调用一次，调用一次就拿到了`XxlJobAdminConfig`类的对象，多次链式调用并无任何作用。

再来看一下`XxlJobAdminConfig`类的其它成员变量，发现是一些通过`@Value`注解获取的一些配置信息，还有一些是通过`@Resource`注解注入的`XxxxxxDao`类和其它业务类，同时提供了每个成员变量的访问器（`getter`方法）。

为什么这么做呢？为什么要将这些`XxxxxxDao`等类放到这里并提供访问器呢？

简单猜想：实际上`XxlJobAdminConfig`可以看做是一个简单的“容器”，调度中心在启动时将整个系统需要用到的配置信息和`XxxxxxDao`类对象初始化到该“容器”中，在需要使用某个`XxxxxxDao`类时，不使用`Spring`的依赖注入，而是从该“容器”中拿，还记得前面分析过的静态方法`getAdminConfig()`，它返回了`XxlJobAdminConfig`类的对象，我们可以这样来使用：

```
XxlJobAdminConfig.getAdminConfig().getXxxxxxDao()
```

这样做有什么好处呢？

猜想：这样做对系统的配置信息和依赖信息进行了统一管理，有一种全局配置感。

## 总结
这篇文章主要分析了调度中心项目`xxl-job-admin`的启动流程。发现主要执行了`XxlJobScheduler.init()`方法，该方法中有六个初始化动作，接下来我们会逐个进行分析。

- [初始化国际化组件](/2020/02/27/middleware/xxl-job/initI18n/)