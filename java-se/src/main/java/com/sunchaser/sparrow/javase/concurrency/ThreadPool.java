package com.sunchaser.sparrow.javase.concurrency;

import java.util.concurrent.*;

/**
 * thread pool
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/9/28
 */
public class ThreadPool {

    public static void main(String[] args) {

        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                2,
                2,
                60,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1000),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );

        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                // task
            }
        });

        Future<String> future = threadPool.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "ThreadPoolExecutor#submit#call";
            }
        });

        threadPool.shutdown();

        try {
            String res = future.get();
        } catch (InterruptedException e) {
            // 处理中断异常
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            // 处理无法执行业务异常
            throw new RuntimeException(e);
        }

        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(2);

        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(2);

        ScheduledExecutorService singleThreadScheduledExecutor = Executors.newSingleThreadScheduledExecutor();

        ForkJoinPool forkJoinPool = new ForkJoinPool();
    }
}
