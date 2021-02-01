package com.sunchaser.sparrow.springboot.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

/**
 * 多任务并行执行模板
 *
 * 使用CountDownLatch异步转同步
 *
 * code example：
 *
 *         // 结果的引用
 *         AtomicReference<String> result1 = new AtomicReference<>();
 *         AtomicReference<String> result2 = new AtomicReference<>();
 *         AtomicReference<String> result3 = new AtomicReference<>();
 *         // 入参
 *         String param1 = "param1";
 *         String param2 = "param2";
 *         String param3 = "param3";
 *         // 将需要执行的任务包装成CoatInvoker
 *         CoatInvoker coatInvoker1 = () -> {
 *             // do biz
 *             System.out.println(param1);
 *             // set result
 *             result1.set("result1");
 *             System.out.println("coatInvoker1 invoked.");
 *         };
 *         CoatInvoker coatInvoker2 = () -> {
 *             // do biz
 *             System.out.println(param2);
 *             // set result
 *             result2.set("result2");
 *             System.out.println("coatInvoker2 invoked.");
 *         };
 *         CoatInvoker coatInvoker3 = () -> {
 *             // do biz
 *             System.out.println(param3);
 *             // set result
 *             result3.set("result3");
 *             System.out.println("coatInvoker3 invoked.");
 *         };
 *         // 方式一：将CoatInvoker直接传入
 *         CountDownLatchExecutorUtils.execute(executorService, coatInvoker1, coatInvoker2, coatInvoker3);
 *
 *         // 方式二：将需要执行的任务打包成List集合传入
 *         List<CoatInvoker> list = new ArrayList<>();
 *         list.add(coatInvoker1);
 *         list.add(coatInvoker2);
 *         list.add(coatInvoker3);
 *         CountDownLatchExecutorUtils.execute(executorService, list);
 *
 *         // 得到执行结果
 *         String r1 = result1.get();
 *         String r2 = result1.get();
 *         String r3 = result1.get();
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/1/30
 */
@Slf4j
public class CountDownLatchExecutorUtils {
    private CountDownLatchExecutorUtils() {
    }

    /**
     *
     * 建议直接使用这个重载的方法，只关注需要并行执行的任务。
     *
     * 多任务使用子线程并行执行
     *
     * 将需要执行的任务包装成CoatInvoker传入。使用可变参数，数量随意。
     *
     * @param executorService 线程池
     * @param coatInvoker 需要异步并行执行的任务
     */
    public static void execute(ExecutorService executorService, CoatInvoker ... coatInvoker) {
        execute(executorService, Arrays.asList(coatInvoker));
    }

    /**
     * 多任务使用子线程并行执行
     *
     * 需要由调用者将多任务打包成List集合
     *
     * @param executorService 线程池
     * @param coatInvokerList 需要异步执行的任务集合
     */
    public static void execute(ExecutorService executorService,
                               List<CoatInvoker> coatInvokerList) {
        execute(executorService, coatInvokerList, "");
    }

    /**
     * 多任务使用子线程并行执行
     *
     * 支持打印整体执行耗时、单个异步子线程执行耗时。
     * 可传入bizId，在com.wujie.group.leader.mis.biz.async.frame.StopWatchWrapper#close()方法中会进行打印。
     * @param executorService 线程池
     * @param coatInvokerList 需要异步执行的任务集合
     * @param bizId 业务名称
     */
    public static void execute(ExecutorService executorService,
                               List<CoatInvoker> coatInvokerList,
                               String bizId) {
        if (executorService == null
                || CollectionUtils.isEmpty(coatInvokerList)
                || bizId == null) {
            throw new IllegalArgumentException("执行参数错误");
        }
        int size = coatInvokerList.size();
        try (StopWatchWrapper watch = new StopWatchWrapper(bizId)) {
            watch.start();
            CountDownLatch countDownLatch = new CountDownLatch(size);
            final String subBizId = bizId + "-";
            for (int i = 0; i < coatInvokerList.size(); i++) {
                CoatInvoker coatInvoker = coatInvokerList.get(i);
                final String id = subBizId + i;
                executorService.execute(() -> {
                    // log "bizId-i"
                    try (StopWatchWrapper subWatch = new StopWatchWrapper(id, countDownLatch)) {
                        subWatch.start();
                        coatInvoker.invoke();
                    } catch (Exception e) {
                        log.error("CountDownLatchExecutorUtils#CoatInvoker-{}-sub-thread-error", id, e);
                        throw new RuntimeException("error");
                    }
                });
            }
            countDownLatch.await();
        } catch (Exception e) {
            log.error("CountDownLatchExecutorUtils#CoatInvoker-{}-async-error.", bizId, e);
            throw new RuntimeException("error");
        }
    }

    /**
     * 一般来说，用final变量将入参传给CoatInvoker包装的lambda表达式即可。
     *
     * 如有特殊情况，可使用该实现。
     *
     * 多任务使用子线程并行执行
     *
     * 将任务所需的参数通过lambda的入参传入
     *
     * @param executorService 线程池
     * @param consumerList 需要异步执行的任务集合
     * @param request 任务执行所需要的入参，需要将每一个任务所需要的参数封装成一个大实体类
     * @param <REQUEST> 入参的泛型
     */
    public static <REQUEST> void execute(ExecutorService executorService,
                                         List<Consumer<REQUEST>> consumerList,
                                         REQUEST request) {
        if (executorService == null
                || CollectionUtils.isEmpty(consumerList)
                || request == null) {
            throw new IllegalArgumentException("执行参数错误");
        }
        int size = consumerList.size();
        try {
            CountDownLatch countDownLatch = new CountDownLatch(size);
            for (Consumer<REQUEST> consumer : consumerList) {
                executorService.execute(() -> {
                    try (StopWatchWrapper watch = new StopWatchWrapper(countDownLatch)) {
                        watch.start();
                        consumer.accept(request);
                    } catch (Exception e) {
                        log.error("CountDownLatchExecutorUtils#Consumer sub thread async error.", e);
                        throw new RuntimeException("error");
                    }
                });
            }
        } catch (Exception e) {
            log.error("CountDownLatchExecutorUtils#Consumer async error.", e);
            throw new RuntimeException("error");
        }
    }
}
