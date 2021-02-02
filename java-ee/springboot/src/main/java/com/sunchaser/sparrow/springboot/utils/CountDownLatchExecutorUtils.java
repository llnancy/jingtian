package com.sunchaser.sparrow.springboot.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

import static org.apache.commons.lang3.StringUtils.EMPTY;

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
 * 默认会自动打印整体的执行耗时：
 * 如果指定了bizId，则日志格式为："main-{bizId}-执行耗时{}ms"，否则日志格式为："main-执行耗时{}ms"。
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/1/30
 */
@Slf4j
public class CountDownLatchExecutorUtils {
    private static final String SEPARATE = "-";
    private static final String MAIN = "main";
    private static final String SUB = "sub";
    private CountDownLatchExecutorUtils() {
    }

    /**
     *
     * 如果你想自动打印每个子任务的耗时，使用这个重载的方法。
     *
     * 需要你传入一个bizId来标识你的业务。打印的日志格式为：{bizId}-{i}-执行耗时{}ms。
     * 1、第一个占位符是传入的bizId
     * 2、第二个占位符是子任务的索引，即CoatInvoker传入的顺序
     * 3、第三个占位符是执行耗时
     *
     * 多任务使用子线程并行执行
     *
     * 将需要执行的任务包装成CoatInvoker传入。使用可变参数，数量随意。
     *
     * @param executorService 线程池
     * @param bizId 业务唯一标识
     * @param coatInvoker 需要异步并行执行的任务
     */
    public static void execute(ExecutorService executorService, String bizId, CoatInvoker ... coatInvoker) {
        execute(executorService, bizId, Arrays.asList(coatInvoker));
    }

    /**
     *
     * 如果你不需要自动打印每个子任务的耗时，用这个重载的方法。
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
     *
     * 多任务使用子线程并行执行
     *
     * 需要你将多个任务打包成List集合，不会自动打印子线程的执行耗时。
     *
     * @param executorService 线程池
     * @param coatInvokerList 需要异步执行的任务集合
     */
    public static void execute(ExecutorService executorService,
                               List<CoatInvoker> coatInvokerList) {
        execute(executorService, EMPTY, coatInvokerList);
    }

    /**
     * 多任务使用子线程并行执行
     *
     * 支持打印整体执行耗时、单个异步子线程执行耗时。
     *
     * 指定bizId时，在com.wujie.group.leader.mis.biz.async.frame.StopWatchWrapper#close()方法中会打印执行耗时。
     *
     * @param executorService 线程池
     * @param bizId 业务名称 不能为null空指针
     * @param coatInvokerList 需要异步执行的任务集合
     */
    public static void execute(ExecutorService executorService,
                               String bizId,
                               List<CoatInvoker> coatInvokerList) {
        if (executorService == null
                || CollectionUtils.isEmpty(coatInvokerList)
                || bizId == null) {
            throw new IllegalArgumentException("执行参数错误");
        }
        int size = coatInvokerList.size();
        String mainBizId = MAIN;
        String subBizIdPrefix = EMPTY;
        if (StringUtils.isNotBlank(bizId)) {
            mainBizId = mainBizId + SEPARATE + bizId;
            subBizIdPrefix = SUB + SEPARATE + bizId;
        }
        // log "main-bizId-执行耗时{}ms"
        try (StopWatchWrapper watch = new StopWatchWrapper(mainBizId)) {
            watch.start();
            CountDownLatch countDownLatch = new CountDownLatch(size);
            for (int i = 0; i < coatInvokerList.size(); i++) {
                CoatInvoker coatInvoker = coatInvokerList.get(i);
                final String subId = subBizIdPrefix + i;
                executorService.execute(() -> {
                    // log "sub-bizId-i-执行耗时{}ms"
                    try (StopWatchWrapper subWatch = new StopWatchWrapper(subId, countDownLatch)) {
                        subWatch.start();
                        coatInvoker.invoke();
                    } catch (Exception e) {
                        log.error("CountDownLatchExecutorUtils#CoatInvoker-{}-sub-thread-error", subId, e);
                        throw new RuntimeException("error");
                    }
                });
            }
            countDownLatch.await();
        } catch (Exception e) {
            log.error("CountDownLatchExecutorUtils#CoatInvoker-{}-async-error.", mainBizId, e);
            throw new RuntimeException("error");
        }
    }

    /**
     *
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
            countDownLatch.await();
        } catch (Exception e) {
            log.error("CountDownLatchExecutorUtils#Consumer async error.", e);
            throw new RuntimeException("error");
        }
    }

}
