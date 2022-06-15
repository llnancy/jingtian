package com.sunchaser.sparrow.javaee.netty.eventloop;

import io.netty.channel.nio.NioEventLoopGroup;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * Test EventLoop
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/15
 */
@Slf4j
public class TestEventLoop {

    public static void main(String[] args) {
        // 创建事件循环组
        NioEventLoopGroup group = new NioEventLoopGroup(2);
        // next()方法获取下一个事件循环对象（轮询）
        LOGGER.info("next: {}", group.next());
        LOGGER.info("next: {}", group.next());
        LOGGER.info("next: {}", group.next());
        LOGGER.info("next: {}", group.next());
        LOGGER.info("next: {}", group.next());
        LOGGER.info("next: {}", group.next());

        // 执行普通任务
        group.next()
                .submit(() -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    LOGGER.debug("ok");
                });

        // 执行定时任务（长连接时用来心跳保活）
        group.next()
                .scheduleAtFixedRate(() -> LOGGER.debug("schedule ok"), 0, 1, TimeUnit.SECONDS);

        LOGGER.debug("main ok");
    }
}
