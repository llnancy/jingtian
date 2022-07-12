package com.sunchaser.microservice.rpc.handler;

import com.sunchaser.microservice.rpc.bean.BeanFactory;
import com.sunchaser.microservice.rpc.common.RpcContext;
import com.sunchaser.microservice.rpc.protocol.Header;
import com.sunchaser.microservice.rpc.protocol.Message;
import com.sunchaser.microservice.rpc.protocol.Request;
import com.sunchaser.microservice.rpc.protocol.Response;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/20
 */
public class RpcRequestHandler extends SimpleChannelInboundHandler<Message<Request>> {

    private static final Executor EXECUTOR = Executors.newCachedThreadPool();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message<Request> msg) throws Exception {
        byte protocolInfo = msg.getHeader().getProtocolInfo();
        if (RpcContext.isHeartBeat(protocolInfo)) {
            ctx.writeAndFlush(msg);
            return;
        }
        EXECUTOR.execute(() -> {
            try {
                Request request = msg.getContent();
                String serviceName = request.getServiceName();
                Object bean = BeanFactory.getBean(serviceName);
                Method method = bean.getClass()
                        .getMethod(request.getMethodName(), request.getArgTypes());
                Object invoke = method.invoke(bean, request.getArgs());
                Response response = new Response();
                response.setData(invoke);
                Header header = msg.getHeader();
                header.setProtocolInfo((byte) 1);
                ctx.writeAndFlush(new Message<>(header, response));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
