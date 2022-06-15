package com.sunchaser.sparrow.javaee.netty.eventloop;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Objects;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/15
 */
@Slf4j
public class EventLoopClient {

    private static Channel channel;

    public static void main(String[] args) throws InterruptedException {
        ChannelFuture channelFuture = new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline()
                                .addLast(new StringEncoder());// 将String编码为ByteBuf
                    }
                })
                .connect(new InetSocketAddress("127.0.0.1", 8080));// connect是异步非阻塞的，连接是由NioEventLoop线程建立的。

        /*
        // 方法一：同步获取连接对象channel
        channelFuture.sync();// 阻塞方法，等待NioEventLoop线程连接建立完成
        // 因为连接是由NioEventLoop线程建立的，此处如果不调用sync方法阻塞等待连接成功，主线程获取到的channel对象就是未建立连接的channel，无法发送数据。
        channel = channelFuture.channel();// 返回连接对象channel
        */

        // 方法二：异步获取连接对象channel
        LOGGER.debug("channelFuture: {}", channelFuture);
        channelFuture.addListener((ChannelFutureListener) future -> {
            // lambda中传递的future和channelFuture对象是同一个
            LOGGER.debug("future: {}", future);
            channel = future.channel();
            if (Objects.nonNull(channel) && channel.isActive()) {
                LOGGER.info("Netty EventLoopClient started. {}", channel);
            }
        });

        LOGGER.debug("channel: {}", channel);
        channel.writeAndFlush("Hello World!");// 往服务端发数据
    }
}
