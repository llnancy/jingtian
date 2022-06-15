package com.sunchaser.sparrow.javaee.netty.hello;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import lombok.extern.slf4j.Slf4j;

/**
 * Netty 服务端
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/15
 */
@Slf4j
public class HelloServer {

    public static void main(String[] args) {
        // ServerBootstrap：启动器。负责组装netty组件，启动服务端。
        new ServerBootstrap()
                .group(new NioEventLoopGroup())// group组：BossEventLoop，WorkEventLoop(selector, thread)
                .channel(NioServerSocketChannel.class)// 连接服务器的ServerSocketChannel实现（NioServerSocketChannel是较为通用的实现），还有OIO（BIO）、KQueue（MacOS）、Epoll。
                .childHandler(// Boss负责处理连接；worker(child)负责处理读写。childHandler决定了worker(child)能执行哪些操作
                        new ChannelInitializer<NioSocketChannel>() {// 负责组装并初始化handler
                            @Override
                            protected void initChannel(NioSocketChannel ch) throws Exception {
                                ch.pipeline()
                                        .addLast(new StringDecoder())// 将Channel中传输的ByteBuf解码为String
                                        .addLast(new ChannelInboundHandlerAdapter() {// 自定义handler
                                            @Override
                                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                                // 读事件触发
                                                LOGGER.info("msg={}", msg);
                                            }
                                        });
                            }
                        }
                )
                .bind(8080);// 绑定端口
    }
}
