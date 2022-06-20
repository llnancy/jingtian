package com.sunchaser.microservice.rpc.handler;

import com.google.common.collect.Maps;
import com.sunchaser.microservice.rpc.common.RpcContext;
import com.sunchaser.microservice.rpc.protocol.Message;
import com.sunchaser.microservice.rpc.protocol.Request;
import com.sunchaser.microservice.rpc.protocol.Response;
import com.sunchaser.microservice.rpc.transport.RpcFuture;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultEventLoop;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.DefaultPromise;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/20
 */
public class RpcResponseHandler extends SimpleChannelInboundHandler<Message<Response>> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message<Response> msg) throws Exception {
        RpcFuture<Response> rpcFuture = RpcResponseHolder.RPC_FUTURE_MAP.remove(msg.getHeader().getMessageId());
        Response response = msg.getContent();
        if (Objects.isNull(response) && RpcContext.isHeartBeat(msg.getHeader().getProtocolInfo())) {
            response = new Response();
            response.setCode(RpcContext.HEARTBEAT_CODE);
        }
        rpcFuture.getPromise().setSuccess(response);
    }

    static class RpcResponseHolder {

        private static final AtomicLong ID = new AtomicLong(0);

        private static final Map<Long, RpcFuture<Response>> RPC_FUTURE_MAP = Maps.newConcurrentMap();

        private Channel channel;

        public RpcFuture<Response> sendRequest(Message<Request> message, long timeout) {
            long id = ID.incrementAndGet();
            message.getHeader().setMessageId(id);
            RpcFuture<Response> rpcFuture = new RpcFuture<>(new DefaultPromise<>(new DefaultEventLoop()));
            RPC_FUTURE_MAP.put(id, rpcFuture);
            try {
                channel.writeAndFlush(message);
            } catch (Exception e) {
                RPC_FUTURE_MAP.remove(id);
                throw e;
            }
            return rpcFuture;
        }
    }
}
