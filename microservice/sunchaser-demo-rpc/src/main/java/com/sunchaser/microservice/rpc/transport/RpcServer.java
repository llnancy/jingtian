package com.sunchaser.microservice.rpc.transport;

import com.sunchaser.microservice.rpc.handler.RpcRequestHandler;
import com.sunchaser.microservice.rpc.transport.netty.codec.RpcCodec;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/21
 */
public class RpcServer {

    private final ServerBootstrap bootstrap;

    private final int port;

    public RpcServer(int port) {
        this.port = port;
        bootstrap = new ServerBootstrap()
                .group(new NioEventLoopGroup(), new NioEventLoopGroup())
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_REUSEADDR, Boolean.TRUE)
                .childOption(ChannelOption.TCP_NODELAY, Boolean.TRUE)
                .handler(new LoggingHandler(LogLevel.DEBUG))
                .childHandler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline()
                                .addLast("rpc-codec", new RpcCodec())
                                .addLast(new RpcRequestHandler());
                    }
                });
    }

    public ChannelFuture start() {
        return bootstrap.bind(port);
    }
}
