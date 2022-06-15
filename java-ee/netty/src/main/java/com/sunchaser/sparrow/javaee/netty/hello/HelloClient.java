package com.sunchaser.sparrow.javaee.netty.hello;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;

/**
 * Netty 客户端
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/15
 */
public class HelloClient {

    public static void main(String[] args) throws InterruptedException {
        new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline()
                                .addLast(new StringEncoder());// 将String编码为ByteBuf
                    }
                })
                .connect(new InetSocketAddress("127.0.0.1", 8080))
                .sync()// 阻塞方法，等待连接建立
                .channel()// 返回连接对象channel
                .writeAndFlush("Hello World!");// 往服务端发数据
    }
}
