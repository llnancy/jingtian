package com.sunchaser.sparrow.javaee.netty.future;

import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;

/**
 * Test Netty Future
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/16
 */
@Slf4j
public class TestNettyFuture {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();
        EventLoop eventLoop = group.next();
        Future<Integer> future = eventLoop.submit(() -> {
            Thread.sleep(1000L);
            return 50;
        });
        // 同步获取结果
        // LOGGER.info("等待结果中...");
        // Integer result = future.get();
        // LOGGER.info("执行结果：{}", result);

        // 异步获取结果
        future.addListener(new GenericFutureListener<Future<? super Integer>>() {
            @Override
            public void operationComplete(Future<? super Integer> future) throws Exception {
                Integer result = (Integer) future.getNow();
                // 由NioEventLoop线程获取执行结果
                LOGGER.info("异步获取结果：{}", result);
            }
        });
    }
}
