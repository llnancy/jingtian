package com.sunchaser.sparrow.javaee.netty.future;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Test JDK Future
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/16
 */
@Slf4j
public class TestJdkFuture {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(2);
        Future<Integer> future = threadPool.submit(() -> {
            Thread.sleep(1000L);
            return 50;
        });
        LOGGER.info("等待结果中...");
        Integer result = future.get();
        LOGGER.info("执行结果：{}", result);
    }
}
