package com.sunchaser.microservice.rpc.transport;

import com.sunchaser.microservice.rpc.handler.RpcResponseHandler;
import com.sunchaser.microservice.rpc.transport.netty.codec.RpcCodec;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/21
 */
public class RpcClient {

    private final Bootstrap bootstrap;

    private final EventLoopGroup group;

    private final String host;

    private final int port;

    public RpcClient(String host, int port) {
        this.host = host;
        this.port = port;
        bootstrap = new Bootstrap();
        group = new NioEventLoopGroup();
        bootstrap.group(group)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline()
                                .addLast("rpc-codec", new RpcCodec())
                                .addLast("client-handler", new RpcResponseHandler());
                    }
                });
    }

    public ChannelFuture connect() {
        ChannelFuture connect = bootstrap.connect(host, port);
        connect.awaitUninterruptibly();
        return connect;
    }

    public void close() {
        group.shutdownGracefully();
    }
}
