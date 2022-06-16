package com.sunchaser.sparrow.javaee.netty.echo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * Echo Server端
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/16
 */
@Slf4j
public class EchoServer {

    public static void main(String[] args) {
        new ServerBootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline()
                                .addLast(new ChannelInboundHandlerAdapter() {
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                        ByteBuf buf = (ByteBuf) msg;
                                        LOGGER.info("server received: {}", buf.toString(CharsetUtil.UTF_8));
                                        // 推荐使用ctx.alloc().buffer();创建ByteBuf
                                        ByteBuf response = ctx.alloc().buffer();// io.netty.buffer.PooledUnsafeDirectByteBuf
                                        LOGGER.info("ByteBuf class: {}", response.getClass());
                                        response.writeBytes(buf);
                                        ctx.writeAndFlush(response);
                                    }
                                });
                    }
                })
                .bind(8080);
    }
}
