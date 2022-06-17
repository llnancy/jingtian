package com.sunchaser.sparrow.javaee.netty.protocol;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * Redis协议 客户端
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/17
 */
@Slf4j
public class RedisProtocol {

    /**
     * 命令：set key value
     * 发送的字节：
     * 1. 首先固定有一个星号*，然后将整个命令看做一个以空格分隔的数组，星号后面加上数组长度（最后还需要加上回车换行）
     * 2. 依次发送数组中每个元素的长度（长度前面加上美元符号$）和值（长度和值之间加上回车换行）
     */
    public static void main(String[] args) {
        final byte[] LINE = {13, 10};// 13: 回车；10：换行。
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            ChannelFuture channelFuture = new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new LoggingHandler())
                                    .addLast(new ChannelInboundHandlerAdapter() {

                                        @Override
                                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                            // 连接建立时发送set name sunchaser命令
                                            ByteBuf buf = ctx.alloc().buffer();
                                            buf.writeBytes("*3".getBytes(CharsetUtil.UTF_8));
                                            buf.writeBytes(LINE);
                                            buf.writeBytes("$3".getBytes(CharsetUtil.UTF_8));
                                            buf.writeBytes(LINE);
                                            buf.writeBytes("set".getBytes(CharsetUtil.UTF_8));
                                            buf.writeBytes(LINE);
                                            buf.writeBytes("$4".getBytes(CharsetUtil.UTF_8));
                                            buf.writeBytes(LINE);
                                            buf.writeBytes("name".getBytes(CharsetUtil.UTF_8));
                                            buf.writeBytes(LINE);
                                            buf.writeBytes("$9".getBytes(CharsetUtil.UTF_8));
                                            buf.writeBytes(LINE);
                                            buf.writeBytes("sunchaser".getBytes(CharsetUtil.UTF_8));
                                            buf.writeBytes(LINE);
                                            ctx.writeAndFlush(buf);
                                        }

                                        @Override
                                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                            // 接收Redis的返回
                                            ByteBuf buf = (ByteBuf) msg;
                                            LOGGER.info("redis return: {}", buf.toString(CharsetUtil.UTF_8));
                                        }
                                    });
                        }
                    })
                    .connect("127.0.0.1", 6379)
                    .sync();
            channelFuture.channel()
                    .closeFuture()
                    .sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }
}
