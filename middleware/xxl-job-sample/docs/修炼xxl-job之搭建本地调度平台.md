## 下载源码
`xxl-job`目前最新`master`分支的代码为`v2.2.0`，官网显示仍在迭代中（该版本目前发布的更新内容主要是调度中心升级`SpringBoot`版本为`2.x`），所以本次修炼我们采用最近稳定版`v2.1.2`，发布日期为`2019-12-12`。

源码地址：[GitHub](https://github.com/xuxueli/xxl-job)/[Gitee](https://gitee.com/xuxueli0323/xxl-job)

## 工程简介
源码结构如下：
```
xxl-job-admin：调度中心
xxl-job-core：公共依赖
xxl-job-executor-samples：执行器Sample示例（选择合适的版本执行器，可直接使用，也可以参考其并将现有项目改造成执行器）
    ：xxl-job-executor-sample-springboot：Springboot版本，通过Springboot管理执行器，推荐这种方式；
    ：xxl-job-executor-sample-spring：Spring版本，通过Spring容器管理执行器，比较通用；
    ：xxl-job-executor-sample-frameless：无框架版本；
    ：xxl-job-executor-sample-jfinal：JFinal版本，通过JFinal管理执行器；
    ：xxl-job-executor-sample-nutz：Nutz版本，通过Nutz管理执行器；
    ：xxl-job-executor-sample-jboot：jboot版本，通过jboot管理执行器；
```

## 环境搭建

基础环境：
- `Maven3+`
- `JDK7+`
- `MySQL5.7+`

### 搭建调度中心
调度中心项目：`xxl-job-admin`。

作用：统一管理任务调度平台上调度任务，负责触发调度执行，并且提供任务管理平台。

#### 初始化“调度数据库”

源码下载后获取初始化数据库`SQL`脚本并在`MySQL`中执行。

调度数据库初始化`SQL`脚本地址为：
```
/xxl-job/doc/db/tables_xxl_job.sql
```

#### 修改调度中心配置
将整个项目工程导入`IDEA`中，等待`maven`依赖下载完毕，修改调度中心配置。

调度中心配置文件地址：
```
/xxl-job/xxl-job-admin/src/main/resources/application.properties
```

需要修改的配置项有以下三个：
```
spring.datasource.url=
spring.datasource.username=
spring.datasource.password=
```
将数据源信息修改为上一步中导入脚本的数据库信息。

#### 启动调度中心
在`IDEA`中运行`com.xxl.job.admin.XxlJobAdminApplication#main`方法，启动调度中心。

如果一切配置正常，可在控制台看到以下三行关键日志信息：
```
13:01:37.678 logback [main] INFO  o.s.b.c.e.t.TomcatEmbeddedServletContainer - Tomcat started on port(s): 8080 (http)
13:01:37.690 logback [main] INFO  c.x.job.admin.XxlJobAdminApplication - Started XxlJobAdminApplication in 6.976 seconds (JVM running for 8.944)
13:01:40.001 logback [xxl-job, admin JobScheduleHelper#scheduleThread] INFO  c.x.j.a.c.thread.JobScheduleHelper - >>>>>>>>> init xxl-job admin scheduler success.
```

`Tomcat`容器监听`8080`端口启动；

`JVM`启动；

`JobScheduleHelper`这个类打印了`>>>>>>>>> init xxl-job admin scheduler success.`这句话。

#### 访问调度中心地址
如已正确进行前述步骤，则可访问调度中心。

调度中心访问地址：`http://localhost:8080/xxl-job-admin`

该地址执行器将会使用到，作为回调地址。

默认管理员账号密码为`admin/123456`。登录成功后可看到如下界面：

![xxl-job-index.png](https://i.loli.net/2020/02/15/oZum5VbtTvH1SeY.png)

至此调度中心搭建完毕。

### 搭建执行器
“执行器”示例项目：`xxl-job-executor-sample`，该模块下包含多个版本的执行器示例，我们选用`xxl-job-executor-sample-springboot`版本。

作用：负责接收“调度中心”的调度并执行；可直接部署执行器项目，也可以将执行器集成到现有业务项目中。

#### 启动执行器
在`IDEA`中运行`com.xxl.job.executor.XxlJobExecutorApplication#main`方法，启动示例执行器。

如果一切运行正常，可在控制台看到以下关键日志信息（这将作为我们后续解析源码的思路）：
```
......
16:47:51.370 logback [main] INFO  c.x.j.e.core.config.XxlJobConfig - >>>>>>>>>>> xxl-job config init.
16:47:51.425 logback [main] INFO  c.x.job.core.executor.XxlJobExecutor - >>>>>>>>>>> xxl-job register jobhandler success, name:httpJobHandler, jobHandler:com.xxl.job.core.handler.impl.MethodJobHandler@1c32886a[class com.xxl.job.executor.service.jobhandler.SampleXxlJob#httpJobHandler]
16:47:51.426 logback [main] INFO  c.x.job.core.executor.XxlJobExecutor - >>>>>>>>>>> xxl-job register jobhandler success, name:demoJobHandler, jobHandler:com.xxl.job.core.handler.impl.MethodJobHandler@3a4b0e5d[class com.xxl.job.executor.service.jobhandler.SampleXxlJob#demoJobHandler]
16:47:51.426 logback [main] INFO  c.x.job.core.executor.XxlJobExecutor - >>>>>>>>>>> xxl-job register jobhandler success, name:demoJobHandler2, jobHandler:com.xxl.job.core.handler.impl.MethodJobHandler@10b892d5[class com.xxl.job.executor.service.jobhandler.SampleXxlJob#demoJobHandler2]
16:47:51.427 logback [main] INFO  c.x.job.core.executor.XxlJobExecutor - >>>>>>>>>>> xxl-job register jobhandler success, name:commandJobHandler, jobHandler:com.xxl.job.core.handler.impl.MethodJobHandler@3d3f761a[class com.xxl.job.executor.service.jobhandler.SampleXxlJob#commandJobHandler]
16:47:51.427 logback [main] INFO  c.x.job.core.executor.XxlJobExecutor - >>>>>>>>>>> xxl-job register jobhandler success, name:shardingJobHandler, jobHandler:com.xxl.job.core.handler.impl.MethodJobHandler@3546d80f[class com.xxl.job.executor.service.jobhandler.SampleXxlJob#shardingJobHandler]
......
16:47:52.172 logback [main] INFO  c.x.r.r.p.XxlRpcProviderFactory - >>>>>>>>>>> xxl-rpc, provider factory add service success. serviceKey = com.xxl.job.core.biz.ExecutorBiz, serviceBean = class com.xxl.job.core.biz.impl.ExecutorBizImpl
......
16:48:02.189 logback [Thread-10] INFO  com.xxl.rpc.remoting.net.Server - >>>>>>>>>>> xxl-rpc remoting server start success, nettype = com.xxl.rpc.remoting.net.impl.netty_http.server.NettyHttpServer, port = 9999
```

启动成功后，点击任务调度中心后台导航-执行器管理菜单，可看到如下图所示：`OnLine`机器地址中已显示我们运行的执行器地址。

![xxl-job-executor-online.png](https://i.loli.net/2020/02/15/2iUXThLzZHMkP9w.png)

点击任务管理菜单可看到示例执行器的测试任务1；

![xxl-job-demo-executor.png](https://i.loli.net/2020/02/15/DjYCsoTmGf3zknV.png)

点击右侧[操作]->[执行一次]，弹出的模态框中无需输入任务参数，直接点击[保存]即可执行任务；

![xxl-job-executor-one-times.png](https://i.loli.net/2020/02/15/tq754auXjG1NP3f.png)

点击[查询日志]，可进入调度日志列表页，点击右侧[执行日志]，可看到任务在执行器一侧运行输出的日志信息。

![xxl-job-executor-log.png](https://i.loli.net/2020/02/15/3WUBxcaE8YZMG6i.png)

![xxl-job-executor-log-page.png](https://i.loli.net/2020/02/15/nVaF4GzW2LsUYxC.jpg)

至此，我们已经完成了任务的调度和执行。