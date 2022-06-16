package com.sunchaser.sparrow.javaee.netty.pipeline;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * Test Pipeline
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/16
 */
@Slf4j
public class TestPipeline {

    public static void main(String[] args) {
        new ServerBootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        // pipeline中默认有两个handler：head和tair。内部结构是双向链表。
                        // 入站处理器：ChannelInboundHandlerAdapter
                        // 出站处理器：ChannelOutboundHandlerAdapter。（向channel中写入过数据才会触发）
                        // head => h1 => h2 => h3 => h4 => h5 => h6 => tair
                        // 入站执行顺序：从head到tair；出站执行顺序：从tair到head。
                        ch.pipeline()
                                .addLast("h1", new ChannelInboundHandlerAdapter() {
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                        LOGGER.debug("h1");
                                        ByteBuf buf = (ByteBuf) msg;
                                        String str = buf.toString(CharsetUtil.UTF_8);
                                        super.channelRead(ctx, str);// 交给下一个入站处理器
                                    }
                                })
                                .addLast("h2", new ChannelInboundHandlerAdapter() {
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                        LOGGER.debug("h2");
                                        String upper = msg.toString().toUpperCase();
                                        ctx.fireChannelRead(upper);// 交给下一个入站处理器
                                    }
                                })
                                .addLast("h4", new ChannelOutboundHandlerAdapter() {
                                    @Override
                                    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                                        LOGGER.debug("h4");
                                        super.write(ctx, msg, promise);
                                    }
                                })
                                .addLast("h3", new ChannelInboundHandlerAdapter() {
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                        LOGGER.debug("h3, msg={}", msg);
                                        // super.channelRead(ctx, msg);// 最后一个入站处理器可不调用
                                        // ch.writeAndFlush(ctx.alloc().buffer().writeBytes("server...".getBytes()));// 使用NioSocketChannel调用：从tair处理器往前寻找出站处理器。
                                        ctx.writeAndFlush(ctx.alloc().buffer().writeBytes("server...".getBytes()));// 使用ChannelHandlerContext调用：从当前处理器往前寻找出站处理器。
                                    }
                                })
                                .addLast("h5", new ChannelOutboundHandlerAdapter() {
                                    @Override
                                    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                                        LOGGER.debug("h5");
                                        super.write(ctx, msg, promise);
                                    }
                                })
                                .addLast("h6", new ChannelOutboundHandlerAdapter() {
                                    @Override
                                    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                                        LOGGER.debug("h6");
                                        super.write(ctx, msg, promise);
                                    }
                                })
                        ;
                    }
                })
                .bind(8080);
    }
}
