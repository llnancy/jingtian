package com.sunchaser.sparrow.javaee.netty.future;

import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultPromise;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;

/**
 * Test Netty Promise
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/16
 */
@Slf4j
public class TestNettyPromise {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        EventLoop eventLoop = new NioEventLoopGroup().next();

        DefaultPromise<Integer> promise = new DefaultPromise<>(eventLoop);

        new Thread(() -> {
            LOGGER.info("begin...");
            try {
                // int i = 1 / 0;
                Thread.sleep(1000L);
                promise.setSuccess(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
                promise.setFailure(e);
            }
        }).start();

        // 同步获取结果
        // LOGGER.info("等待结果中...");
        // Integer result = promise.get();
        // LOGGER.info("执行结果：{}", result);

        promise.addListener(future -> {
            Integer result = (Integer) future.get();
            // 由NioEventLoop线程获取执行结果
            LOGGER.info("异步获取结果：{}", result);
        });
    }
}
