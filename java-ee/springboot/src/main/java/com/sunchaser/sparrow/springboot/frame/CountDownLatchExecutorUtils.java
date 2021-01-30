package com.sunchaser.sparrow.springboot.frame;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

/**
 * 多任务并行执行工具类
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/1/30
 */
@Slf4j
public class CountDownLatchExecutorUtils {
    private CountDownLatchExecutorUtils() {
    }

    /**
     * 多任务使用子线程并行执行
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
     * 可传入bizId，在com.wujie.group.leader.mis.biz.async.frame.StopWatchDecorator#close()方法中会进行打印。
     * @param executorService 线程池
     * @param coatInvokerList 需要异步执行的任务集合
     * @param bizId 业务名称
     */
    public static void execute(ExecutorService executorService,
                               List<CoatInvoker> coatInvokerList,
                               String bizId) {
        if (CollectionUtils.isEmpty(coatInvokerList)) {
            throw new IllegalArgumentException("执行参数错误");
        }
        int size = coatInvokerList.size();
        try (StopWatchDecorator watch = new StopWatchDecorator(bizId)) {
            watch.start();
            CountDownLatch countDownLatch = new CountDownLatch(size);
            final String subBizId = bizId + "-";
            for (int i = 0; i < coatInvokerList.size(); i++) {
                CoatInvoker coatInvoker = coatInvokerList.get(i);
                final String id = subBizId + i;
                executorService.execute(() -> {
                    // log "bizId-i"
                    try (StopWatchDecorator subWatch = new StopWatchDecorator(id, countDownLatch)) {
                        subWatch.start();
                        coatInvoker.invoke();
                    } catch (Exception e) {
                        log.error("{}-sub-thread-error", id, e);
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

}
