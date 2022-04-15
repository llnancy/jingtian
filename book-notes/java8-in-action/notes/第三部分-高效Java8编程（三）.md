## 第十一章：CompletableFuture组合式异步编程
在介绍这部分内容之前，先介绍一下由于我和另外一位开发人员的考虑不周造成的一次线上事故场景（考虑企业隐私，屏蔽了一些关键词）。

### 需求背景
卡劵系统的后台管理系统，用于处理用户投诉补偿以及发错券补偿的场景。

### 需求说明
两种情况会使用到这次我们开发的功能。

1. 可能会出现用户投诉未领取到优惠券的情况，这个时候直接给用户补发一张。避免浪费人力去查线上日志找原因。
2. 运营人员操作失误发错劵，需要手动给用户补发劵。

由于功能急需上线，产品经理想尽量简化开发，设计的轻量化一些，不将补发记录入库。每次补发完成页面就显示成功多少个，失败多少个，失败的UIDs是哪些。

### 业务流程
1. 一个补发劵页面，运营人员首先输入劵ID查询劵信息，包含劵名称和可用库存。
2. 上传需要进行补发的UIDs到后台进行解析（解析的过程就是去数据库中查是否存在该UID对应的用户）。
3. 给有效的UIDs对应的用户进行劵补发操作，调用发劵接口。
4. 等待所有用户全部调用发劵接口完毕，记录失败的UIDs，响应给前端。

### 事故分析
- 事故描述：运营人员上传了250个用户UID进行补发劵，点击补发按钮，等待了约2分钟页面显示失败了215个UID。

- 排查情况：线上环境将应用部署到了2台服务器。我们在A和B两台服务器的日志上都查到了补发请求相关日志。服务器A上的日志显示补发失败35条，服务器B上的日志显示补发失败215条。

- 原因分析：
  - A服务器上的日志显示控制器层接收到请求的时刻是：`2019-09-06 10:51:19.075`，响应请求的时刻是：`2019-09-06 10:52:40.171`；
  - B服务器上的日志显示控制器层接收到请求的时刻是：`2019-09-06 10:52:19.061`，响应请求的时刻是：`2019-09-06 10:52:40.022`。
  - A服务器上记录的失败的35个UIDs在B服务器上发劵成功；B服务器上记录的失败的215个UIDs在A服务器上发劵成功。另外从日志中可看出发劵接口做了幂等校验。

初步排查：由于后端服务器接收到了两个请求，判断是否运营人员点了两次补发按钮？经过对前端页面的测试，点了一次补发按钮后，页面出现loading遮罩层，不能第二次点击补发按钮。排除运营人员操作的问题。

进一步分析：A和B两台服务器接收到请求的时间间隔恰好是1分钟左右，是否是前端Ajax请求的响应超时会自动重试？由于前端页面是使用jQuery发送Ajax请求，并且请求类型是POST，浏览器并不会自动重试。

最终得出结论：在向指导人请教后，推测是线上环境有Nginx进行负载均衡，当ajax请求得到响应的时间超过Nginx默认的60秒时，请求会自动重发到另一台服务器。向部门经理确认系统架构后，线上环境确实存在负载均衡，使用的是阿里的SLB。（由于我们刚接手该项目，对线上环境还不太熟悉）阿里的SLB开启了超时自动重发机制，超时时间是60秒。

### 事故结论
一个补发劵的请求经过SLB负载均衡到后端服务器，后端服务器执行业务代码时间超过了一分钟，过了60秒后，SLB认为该请求超时，触发重试机制，将同样的请求负载到另外一台后端服务器上，两台服务器上的线程开始并发调用发劵接口，由于发劵接口做了接口幂等性校验，所以并未出现发劵重复的情况。最终250个UIDs都成功的完成了补发。

- 解决方案：
  - 运营人员每次上传少量UIDs，保证响应时间小于60秒。
  - 产品经理提出迭代需求，版本升级。

### 值得思考的问题
产品经理提出需求时，说要简化开发，设计轻量化等。但我们作为Java开发工程师，我们不能和产品经理想的一样，将系统想的过于简化。仍然要从一个程序的角度出发来考虑问题。

### 代码升级方案
我们知道，在原生安卓项目开发中，所有的网络请求都必须在子线程中执行。

安卓为什么要这样限制呢？我想，安卓一定是考虑到所有的网络请求都是有可能出现超时的，即使网络请求只是去简单的获取一个资源，但仍可能会出现网络延迟的情况。如果在主线程中执行，一旦出现延迟或者超时，给用户的感觉就是界面卡住。于是安卓进行了异步化设计。限制网络请求只能在子线程中执行。

对于Web应用系统，如果有执行时间较长的请求，我们也要尽量将其放在子线程中执行。避免因为等待远程服务的返回，或者对数据库的查询，而阻塞主线程的执行，浪费宝贵的计算资源，影响用户体验。

这次线上事故的根本原因就是开发经验不足，考虑不周，不了解线上情况，未进行异步化设计。由于一次请求需要补发较多的用户，导致一次HTTP请求迟迟未完成三次握手四次挥手过程，SLB服务器认为请求超时，触发了重试机制，将同样的请求打到另外一台服务器上。

在Java语言中，`Future`接口，尤其是它在Java 8中的新版实现`CompletableFuture`，是进行异步化设计的利器。

### Future接口
`Future`接口在Java 5中被引入，设计初衷是对将来某个时刻会发生的结果进行建模。它建模了一种异步计算，返回一个执行运算结果的引用，当运算结束后，这个引用被返回给调用方。在`Future`中触发那些潜在耗时的操作把调用线程解放出来，让它能及时响应客户端或者继续执行其它有价值的工作，不再需要呆呆的等到耗时的操作完成。

上述补发劵业务最初的同步代码大致如下（考虑企业隐私，屏蔽关键词）：

业务Service层代码：

```
/**
 * 同步 劵补发操作
 * @param uIds 用户UID集合
 * @param couponId 优惠券ID
 * @return 失败的用户UID集合
 */
@Override
public List<String> syncReSupplyCoupon(List<String> uIds, String couponId) {
    List<String> result = new ArrayList<>();
    List<UserInfoModel> userInfoModelList = new ArrayList<>();
    // 循环验证UID有效性
    for (String uId : uIds) {
        // 查询UID对应用户信息
        UserInfoModel userInfoModel = reSupplyCouponService.queryUserInfo(uId);
        if (userInfoModel != null) {
            // UID存在，放入待进行补发用户集合
            userInfoModelList.add(userInfoModel);
        } else {
            // UID不存在，放入返回结果集合
            result.add(uId);
        }
    }
    // 循环进行劵补发
    for (UserInfoModel userInfoModel : userInfoModelList) {
        Boolean flag = false;
        try {
            flag = reSupplyCouponService.reSupplyCoupon(couponId,userInfoModel.getUid());
        } catch (Exception e) {
            // 异常处理
        }
        if (!flag) {
            // 补发劵失败，放入返回结果集合
            result.add(userInfoModel.getUid());
        }
    }
    return result;
}
```
基础Service层代码：

```
/**
 * 查询用户信息
 * @param uId 用户UID
 * @return 用户信息model
 */
@Override
public UserInfoModel queryUserInfo(String uId) {
    return reSupplyCouponIntegration.queryUserInfo(uId);
}

/**
 * 补发劵操作
 * @param couponId 优惠券ID
 * @param uId 用户ID
 * @return 补发结果：成功或失败
 */
@Override
public Boolean reSupplyCoupon(String couponId, String uId) {
    return reSupplyCouponIntegration.reSupplyCoupon(couponId,uId);
}
```
`Integration`防腐层代码：
```
private static List<UserInfoModel> users = new ArrayList<>();

/**
 * 初始化操作，模拟远程用户数据
 */
static {
    for (int i = 0; i < 250; i++) {
        users.add(new UserInfoModel(String.valueOf(i)));
    }
}

/**
 * 模拟查找用户操作，不存在则UID则新增一个。
 * @param uId 用户UID
 * @return 用户信息model
 */
@Override
public UserInfoModel queryUserInfo(String uId) {
    try {
        // 模拟调用远程服务耗时
        Thread.sleep(100);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    return users.get(Integer.valueOf(uId));
}

/**
 * 模拟补发劵操作
 * @param couponId 优惠券ID
 * @param uId 用户id
 * @return 补发劵结果：成功或失败
 */
@Override
public Boolean reSupplyCoupon(String couponId, String uId) {
    try {
        // 模拟调用远程服务耗时
        Thread.sleep(200);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    // 模拟成功或失败概率
    return new Random().nextInt(100) < 90;
}
```

这段同步执行的代码中存在的问题：包含2个for循环中通过RPC调用远程服务提供方进行数据库操作，如果UID集合数据量较大，这个方法的执行时间是非常长的，例如这次事故中运营人员上传了250个UID，执行时间就花了2分钟左右。耗时过长，SLB负载均衡服务器认为请求超时，进行重试。

使用Future接口进行代码异步化改造：将耗时的操作封装到一个`Callable`对象中，再将它提交给ExecutorService线程池。

业务Service层代码：
```
/**
 * 初始化线程池
 */
private static ExecutorService executorService = Executors.newCachedThreadPool();

/**
 * 声明Future
 */
private static Future<List<String>> future;

/**
 * 使用Callable封装耗时操作
 */
class AsyncReSupplyCouponCallable implements Callable<List<String>> {
    // 通过构造函数间接传递参数给call方法
    private List<String> uIds;
    private String couponId;
    public AsyncReSupplyCouponCallable(List<String> uIds, String couponId) {
        this.uIds = uIds;
        this.couponId = couponId;
    }

    @Override
    public List<String> call() throws Exception {
        // 调用同步的补发劵方法
        return syncReSupplyCoupon(uIds,couponId);
    }
}

/**
 * 异步 劵补发操作 基于JDK 5的Future接口
 * @param uIds 用户UID集合
 * @param couponId 优惠券ID
 */
@Override
public void asyncFutureReSupplyCoupon(List<String> uIds, String couponId) {
    future = executorService.submit(new AsyncReSupplyCouponCallable(uIds,couponId));
    executorService.shutdown();
}
    
/**
 * 获取补发劵失败的UIDs在前端显示
 * 由前端控制调用该方法的时机
 * 根据上传的UIDs数量做轮询，时间可以设置久一点。
 * @return 补发失败的UID集合
 */
@Override
public List<String> getFailedUIDs() {
    List<String> result = new ArrayList<>();
    try {
        if (future != null) {
            // 如果调用get方法时，Callable中的任务还未执行完，则线程阻塞在这里。
            // 使用重载的get方法设置超时时间为50秒。如果发生阻塞，则最多等待50秒后退出。
            result = future.get(50, TimeUnit.SECONDS);
        }
    } catch (InterruptedException e) {
        // 线程等待过程中被中断
    } catch (ExecutionException e) {
        // 计算抛出一个异常
    } catch (TimeoutException e) {
        // 在Future对象完成之前超时已过期
    }
    return result;
}
```

异步化改造基本已经完成。以上代码已经能够有效避免这次线上事故再次发生了。

### 接口性能提升
基于`Future`接口的异步改造已经能够避免事故再次发生，但是耗时的补发劵操作在子线程执行仍然是同步的。子线程中验证同步执行验证250个UIDs是否合法，给250个用户补发劵。耗时仍然很长。如何提升接口的性能呢？如果让不同的UID之间的操作并行，则可显著提升性能。

### 方案一：使用Java 8的并行流
利用Java 8的并行流避免每个UID的顺序执行。

业务Service层代码：
```
/**
 * 使用并行流 补发劵
 * @param uIds 用户UID集合
 * @param couponId 优惠券ID
 * @return 补发失败的用户UIDs集合
 */
@Override
public List<String> parallelReSupplyCoupon(List<String> uIds, String couponId) {
    List<String> failUidList = new ArrayList<>();
    // 使用并行流验证UID是否合法，按是否合法进行分区：不存在的为true区
    Map<Boolean, List<UserInfoModel>> userInfoModelMap = uIds.parallelStream()
            .map(uId -> reSupplyCouponService.queryUserInfo(uId))
            .collect(Collectors.partitioningBy(Objects::isNull));
    // 取出不合法的UID加入补发失败的集合中
    userInfoModelMap.get(true)
            .parallelStream()
            .map(userInfoModel -> failUidList.add(userInfoModel.getUid()))
            .collect(Collectors.toList()); // 触发中间操作
    // 取出合法的UID进行补发劵操作
    List<Map<String, Object>> reSupplyCouponResult = userInfoModelMap.get(false)
            .parallelStream()
            .map(userInfoModel -> reSupplyCouponService.reSupplyCouponWithUid(couponId, userInfoModel.getUid()))
            .collect(Collectors.toList());
    // 从补发劵结果中取出补发失败的加入补发失败的集合中
    reSupplyCouponResult.parallelStream()
            .filter(map -> !(Boolean) map.get("result"))
            .map(map -> failUidList.add(String.valueOf(map.get("uId"))))
            .collect(Collectors.toList());
    return failUidList;
}
```
基础Service层中新增接口：
```
/**
 * 补发劵操作
 * @param couponId 优惠券ID
 * @param uId 用户ID
 * @return [UID,"成功或失败"]，返回对应UID。
 */
@Override
public Map<String, Object> reSupplyCouponWithUid(String couponId, String uId) {
    Map<String,Object> map = new HashMap<>();
    map.put("uId",uId);
    Boolean result = reSupplyCouponIntegration.reSupplyCoupon(couponId,uId);
    map.put("result",result);
    return map;
}
```

### 方案二：使用Java 8的CompletableFuture接口
利用Java 8的`CompletableFuture`接口异步化。每一个UID的操作之间都是异步的。

需要对所有的`CompletableFuture`对象执行`join`操作，一个一个等待它们执行完毕。`CompletableFuture`类中的`join`方法和`Future`接口中的`get`方法有相同的含义，并且也声明在`Future`接口中，唯一的不同是`join`方法不会抛出任何检测到的异常。所以不会显得Lambda表达式过于臃肿。

业务Service层代码：
```
/**
 * 异步 劵补发操作 每一个UID之间都是异步的 基于JDK 8的CompletableFuture接口
 * @param uIds
 * @param couponId
 * @return
 */
@Override
public List<String> asyncCompletableFutureReSupplyCoupon(List<String> uIds, String couponId) { 
    List<String> failUidList = new ArrayList<>();
    // 使用CompletableFuture异步操作：验证UID是否存在系统中
    List<CompletableFuture<UserInfoModel>> list = uIds.stream()
            .map(uId -> CompletableFuture.supplyAsync(
                    () -> reSupplyCouponService.queryUserInfo(uId))
            ).collect(Collectors.toList());
    // 等待所有异步操作执行结束，分区筛选出存在的UIDs和不存在的UIDs
    Map<Boolean, List<UserInfoModel>> joinMap = list.stream()
            .map(CompletableFuture::join)
            .collect(Collectors.partitioningBy(Objects::isNull));
    // 将不存在的UIDs加入补发失败的集合中
    joinMap.get(true)
            .stream()
            .map(userInfoModel -> failUidList.add(userInfoModel.getUid()))
            .collect(Collectors.toList());
    // 使用CompletableFuture异步给存在的UIDs补发劵
    List<CompletableFuture<Map<String, Object>>> reSupplyCouponResult = joinMap.get(false)
            .stream()
            .map(userInfoModel -> CompletableFuture.supplyAsync(
                    () -> reSupplyCouponService.reSupplyCouponWithUid(couponId, userInfoModel.getUid()))
            ).collect(Collectors.toList());
    // 等待所有异步操作执行结束，筛选出补发劵失败的UIDs存入返回结果集合中
    reSupplyCouponResult.stream()
            .map(CompletableFuture::join)
            .filter(r -> !(Boolean) r.get("result"))
            .map(r -> failUidList.add(String.valueOf(r.get("uId"))))
            .collect(Collectors.toList());
    return failUidList;
}
```

### 比较并行流和异步接口的快慢
初始化8个UID进行测试。

测试代码：
```
private static List<String> uIds = new ArrayList<>();

/**
 * 初始化8个UIDs，模拟待补发用户
 */
static {
    for (int i = 0; i < 8; i++) {
        uIds.add(String.valueOf(i));
    }
}

/**
 * 测试使用Java 8的并行流进行的补发劵操作
 *
 * 8个UID
 * done in 312msecs
 */
@Test
public void testParallelReSupplyCoupon() {
    long start = System.nanoTime();
    List<String> failedUIDs = reSupplyCouponBizService.parallelReSupplyCoupon(uIds, "1");
    long duration = (System.nanoTime() - start) / 1_000_000;
    System.out.println("done in " + duration + "msecs");
    failedUIDs.stream().forEach(System.out::println);
}

/**
 * 测试 异步 劵补发操作 每一个UID之间都是异步的 基于JDK 8的CompletableFuture接口
 *
 * 8个UID
 * done in 610msecs
 */
@Test
public void testAsyncCompletableFutureReSupplyCoupon() {
    long start = System.nanoTime();
    List<String> failedUIDs = reSupplyCouponBizService.asyncCompletableFutureReSupplyCoupon(uIds, "1");
    long duration = (System.nanoTime() - start) / 1_000_000;
    System.out.println("done in " + duration + "msecs");
    failedUIDs.stream().forEach(System.out::println);
}
```

结果让人相当失望。使用`CompletableFuture`新接口的耗时大约是使用并行流版本的两倍。难道这种场景下使用`CompletableFuture`真的是浪费时间吗？也许我们漏掉了某些很重要的东西？我们运行测试代码的电脑是否足以以并行方式运行8个线程？

并行流的版本运行的足够快，那是因为它能并行的执行的8个线程，它能为每个UID的操作分配一个线程。但是，如果现在我们初始化9个UID进行测试，我们来看看结果：
```
并行流版本
9个UID
done in 617msecs

异步接口版本
9个UID
done in 611msecs
```

并行流版本9个UID的测试结果比之前大概多消耗了3秒，这个时间间隔刚好是一次模拟调用远程服务接口的耗时。因为可以并行运行的8个线程开始都处于工作状态，都在对前8个UID进行补发劵等操作。第9个UID的操作只能等到前面某个操作完成释放出空闲线程才能继续。

异步接口版本的测试结果和并行流版本相差无几。究其原因都一样：它们内部采用的是同样的通用线程池，默认都使用固定数量的线程，具体线程数取决于`Runtime.getRuntime().availableProcessors()`的返回值。然而，`CompletableFuture`具有一定优势，它可以定制执行器，自定义线程池的大小。这是并行流API无法实现的。

### 定制异步接口的执行器
创建一个配有线程池的执行器很容易，但是我们该如何选择合适的线程数目呢？

>《Java并发编程实战》书中介绍到，Brian Goetz和合著者们为线程池大小的优化提供了不少中肯的建议。这非常重要，如果线程池中线程的数量过多，最终它们会竞争稀缺的处理器和内存资源，浪费大量的时间在上下文切换上。反之，如果线程的数目过少，正如你的应用所面临的情况，处理器的一些核可能就无法充分利用。Brian Goetz建议，线程池大小与处理器的利用率之比可以使用下面的公式进行估算：
>**N**<sub>threads</sub> = **N**<sub>CPU</sub> * **U**<sub>CPU</sub> * (1 + **W**/**C**)
>其中：
>
>- **N**<sub>threads</sub>是处理器的核的数目，可以通过`Runtime.getRuntime().availableProcessors()`得到；
>- **U**<sub>CPU</sub>是期望的CPU利用率（该值应该介于0和1之间）
>- **W**/**C**是等待时间与计算时间的比率

补发劵接口99%的时间都在等待远程服务的响应，所以估算出的W/C的比率为100。如果期望的CPU利用率为100%，则需要创建一个拥有800个线程的线程池。但实际上，线程池中的有些线程根本没机会被使用，反而是一种浪费。所以建议将执行器使用的线程数，与实际需要的线程数（UIDs的数量）设定为同样的值。这样每个UID都对应一个服务线程。但是，当UIDs数量过大时，运行代码的机器必然会因超负荷而崩溃，所以最好还是有一个上限。

业务Service层相关代码如下：

```
/**
 * 定制执行器-线程池大小为UIDs的数量：设置为守护线程，当程序退出时，线程也会被回收。
 */
private final Executor executor = Executors.newFixedThreadPool(125, r -> {
    Thread t = new Thread(r);
    t.setDaemon(true);
    return t;
});

/**
 * 异步 劵补发操作 定制CompletableFuture接口的执行器
 * @param uIds 用户UID集合
 * @param couponId 优惠券ID
 * @return 补发失败的用户UID集合
 */
@Override
public List<String> asyncCompletableFutureCustomExecutorReSupplyCoupon(List<String> uIds, String couponId) {
    List<String> failUidList = new ArrayList<>();
    // 使用定制执行器的CompletableFuture异步操作：验证UID是否存在系统中
    List<CompletableFuture<UserInfoModel>> list = uIds.stream()
            .map(uId -> CompletableFuture.supplyAsync(
                    () -> reSupplyCouponService.queryUserInfo(uId),executor)
            ).collect(Collectors.toList());
    // 等待所有异步操作执行结束，分区筛选出存在的UIDs和不存在的UIDs
    Map<Boolean, List<UserInfoModel>> joinMap = list.stream()
            .map(CompletableFuture::join)
            .collect(Collectors.partitioningBy(Objects::isNull));
    // 将不存在的UIDs加入补发失败的集合中
    joinMap.get(true)
            .stream()
            .map(userInfoModel -> failUidList.add(userInfoModel.getUid()))
            .collect(Collectors.toList());
    // 使用定制执行器的CompletableFuture异步给存在的UIDs补发劵
    List<CompletableFuture<Map<String, Object>>> reSupplyCouponResult = joinMap.get(false)
            .stream()
            .map(userInfoModel -> CompletableFuture.supplyAsync(
                    () -> reSupplyCouponService.reSupplyCouponWithUid(couponId, userInfoModel.getUid()),executor)
            ).collect(Collectors.toList());
    // 等待所有异步操作执行结束，筛选出补发劵失败的UIDs存入返回结果集合中
    reSupplyCouponResult.stream()
            .map(CompletableFuture::join)
            .filter(r -> !(Boolean) r.get("result"))
            .map(r -> failUidList.add(String.valueOf(r.get("uId"))))
            .collect(Collectors.toList());
    return failUidList;
}
```

使用125个UID进行测试：

```
private static List<String> uIds = new ArrayList<>();

/**
 * 初始化操作，模拟待补发用户
 */
static {
    for (int i = 0; i < 125; i++) {
        uIds.add(String.valueOf(i));
    }
}

/**
 * 测试 异步 劵补发操作 定制CompletableFuture接口的执行器
 *
 * 125个UID
 * done in 369msecs
 */
@Test
public void testAsyncCompletableFutureCustomExecutorReSupplyCoupon() {
    long start = System.nanoTime();
    List<String> failedUIDs = reSupplyCouponBizService.asyncCompletableFutureCustomExecutorReSupplyCoupon(uIds, "1");
    long duration = (System.nanoTime() - start) / 1_000_000;
    System.out.println("done in " + duration + "msecs");
    failedUIDs.stream().forEach(System.out::println);
}
```

测试结果：`done in 369msecs`，显而易见，耗时和8个UID的并行流版本很接近。性能显著提升。一般而言，随着UID数量继续增多，耗时不会相差太多，直到达到之前计算的阈值800（CPU利用率达到100%）。

### 在并行流和CompletableFuture之间进行选择
并行流底层的Fork/Join框架使用通用的线程池，无法个性化定制。新的`CompletableFuture`接口可以定制执行器，调整线程池大小，能够更加充分的利用CPU资源。

建议如下：
> - 如果你进行的是计算密集型的操作，并且没有I/O，那么推荐使用Stream接口，因为实
现简单，同时效率也可能是最高的（如果所有的线程都是计算密集型的，那就没有必要
创建比处理器核数更多的线程）。
> - 反之，如果你并行的工作单元还涉及等待I/O的操作（包括网络连接等待），那么使用
CompletableFuture灵活性更好，你可以像前文讨论的那样，依据等待/计算，或者
W/C的比率设定需要使用的线程数。这种情况不使用并行流的另一个原因是，处理流的
流水线中如果发生I/O等待，流的延迟特性会让我们很难判断到底什么时候触发了等待。

## 总结
执行比较耗时的操作时，尤其是那些依赖一个或多个远程服务的操作，建议进行异步化设计，使用`CompletableFuture`类提供的特性可轻松实现异步API。

##### 示例代码Git地址：[传送门](https://github.com/tclilu/gold-road-to-Java/tree/master/Book-Notes/Java8%E5%AE%9E%E6%88%98/src/main/java/cn/org/lilu/chapter11)