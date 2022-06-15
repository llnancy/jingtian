package com.sunchaser.sparrow.javaee.netty.eventloop;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * EventLoop服务端
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/15
 */
@Slf4j
public class EventLoopServer {

    public static void main(String[] args) {
        // 单独的EventLoopGroup用来处理可能耗时较长的handler，避免影响其它NioEventLoop线程。
        EventLoopGroup group = new DefaultEventLoop();
        new ServerBootstrap()
                // 传递两个NioEventLoopGroup参数
                // 分工明确：一个BossEventLoop负责accept连接事件；另一个WorkerEventLoop负责channel上的读写事件。
                .group(new NioEventLoopGroup(), new NioEventLoopGroup(2))
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline()
                                .addLast("handler1",new ChannelInboundHandlerAdapter() {
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                        ByteBuf buf = (ByteBuf) msg;
                                        LOGGER.debug("msg: {}", buf.toString(StandardCharsets.UTF_8));
                                        // 传递给后面的handler
                                        super.channelRead(ctx, msg);
                                    }
                                })
                                .addLast(group,"handler2", new ChannelInboundHandlerAdapter() {// 该handler的执行权交给单独的EventLoopGroup
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                        ByteBuf buf = (ByteBuf) msg;
                                        LOGGER.debug("msg: {}", buf.toString(StandardCharsets.UTF_8));
                                    }
                                });
                    }
                })
                .bind(8080);
    }
}
