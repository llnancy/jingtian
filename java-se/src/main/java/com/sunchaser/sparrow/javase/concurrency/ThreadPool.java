package com.sunchaser.sparrow.javase.concurrency;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * thread pool
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/9/28
 */
public class ThreadPool {

    public static void main(String[] args) {

        // 默认线程工厂
        ThreadFactory defaultTf = Executors.defaultThreadFactory();

        // Guava 线程工厂
        ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setNameFormat("biz-pool-%s-thread-%s")
                .build();

        // 手动创建线程池
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                2,
                2,
                60,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1000),
                threadFactory,
                new ThreadPoolExecutor.CallerRunsPolicy()
        );

        // 执行无返回值任务
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                // task
            }
        });

        // 提交有返回值任务
        Future<String> future = threadPool.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "ThreadPoolExecutor#submit#call";
            }
        });

        try {
            // 从 Future 中获取返回值
            String res = future.get();
        } catch (InterruptedException e) {
            // 处理中断异常
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            // 处理无法执行业务异常
            throw new RuntimeException(e);
        }

        // 关闭线程池
        threadPool.shutdown();

        // 预热一个核心线程
        threadPool.prestartCoreThread();
        // 预热全部核心线程
        threadPool.prestartAllCoreThreads();

        // 仅包含一个线程的线程池
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

        // 固定2个线程的线程池
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(2);

        // 线程数不限的线程池
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

        // 仅包含一个线程的 ScheduledThreadPoolExecutor 线程池
        ScheduledExecutorService singleThreadScheduledExecutor = Executors.newSingleThreadScheduledExecutor();

        // 固定2个线程数的 ScheduledThreadPoolExecutor 线程池
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(2);

        // fork join pool
        ExecutorService forkJoinPool = Executors.newWorkStealingPool();
    }
}
