package com.sunchaser.sparrow.javaee.netty.eventloop;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Scanner;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/15
 */
@Slf4j
public class CloseFutureClient {

    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();
        ChannelFuture channelFuture = new Bootstrap()
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline()
                                .addLast(new LoggingHandler(LogLevel.DEBUG))
                                .addLast(new StringEncoder());
                    }
                })
                .connect(new InetSocketAddress("127.0.0.1", 8080));
        Channel channel = channelFuture.sync().channel();
        LOGGER.debug("channel: {}", channel);
        new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String line = scanner.nextLine();
                if ("q".equals(line)) {
                    channel.close();// close方法是异步非阻塞，由NioEventLoop中的线程进行关闭
                    // 关闭后执行一些清理操作
                    LOGGER.debug("关闭后执行的一些操作");// 放在这里不能确保已经close完成了。
                    break;
                }
                channel.writeAndFlush(line);
            }
        }, "inputThread").start();

        // 优雅关闭channel：CloseFuture对象
        // 方法一：同步处理关闭
        ChannelFuture closeFuture = channel.closeFuture();
        LOGGER.debug("等待关闭...");
        /*
        closeFuture.sync();// 阻塞等待NioEventLoop中的线程完成关闭
        // 优雅关闭NioEventLoop线程
        group.shutdownGracefully();
        LOGGER.debug("优雅关闭后执行的一些操作...");
         */

        // 方法二：异步处理关闭
        closeFuture.addListener((ChannelFutureListener) future -> {
            // 在NioEventLoop线程中执行
            LOGGER.debug("优雅关闭后执行的一些操作...");
            // 优雅关闭NioEventLoop线程，让主线程也停止。
            group.shutdownGracefully();
        });
    }
}
