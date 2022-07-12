package com.sunchaser.microservice.rpc.proxy;

import com.sunchaser.microservice.rpc.common.RpcContext;
import com.sunchaser.microservice.rpc.handler.RpcResponseHandler.RpcResponseHolder;
import com.sunchaser.microservice.rpc.protocol.Header;
import com.sunchaser.microservice.rpc.protocol.Message;
import com.sunchaser.microservice.rpc.protocol.Request;
import com.sunchaser.microservice.rpc.protocol.Response;
import com.sunchaser.microservice.rpc.registry.Registry;
import com.sunchaser.microservice.rpc.registry.ServiceInfo;
import com.sunchaser.microservice.rpc.transport.RpcClient;
import com.sunchaser.microservice.rpc.transport.RpcFuture;
import io.netty.channel.ChannelFuture;
import org.apache.curator.x.discovery.ServiceInstance;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/21
 */
public class RpcProxy implements InvocationHandler {

    private final String serviceName;

    private final Registry<ServiceInfo> registry;

    public RpcProxy(String serviceName, Registry<ServiceInfo> registry) {
        this.serviceName = serviceName;
        this.registry = registry;
    }

    @SuppressWarnings("unchecked")
    public static <T> T newInstance(Class<T> clazz, Registry<ServiceInfo> registry) {
        return (T) Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                new Class[]{clazz},
                new RpcProxy(clazz.getName(), registry)
        );
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        List<ServiceInstance<ServiceInfo>> serviceInstanceList = registry.query(serviceName);
        ServiceInstance<ServiceInfo> instance = serviceInstanceList.get(ThreadLocalRandom.current().nextInt(serviceInstanceList.size()));
        ServiceInfo serviceInfo = instance.getPayload();

        RpcClient rpcClient = new RpcClient(serviceInfo.getHost(), serviceInfo.getPort());
        ChannelFuture channelFuture = rpcClient.connect().awaitUninterruptibly();
        RpcResponseHolder rpcResponseHolder = new RpcResponseHolder(channelFuture.channel());

        Header header = new Header();
        header.setMagic(RpcContext.MAGIC);
        header.setVersion(RpcContext.VERSION);
        header.setProtocolInfo((byte) 0);

        Request request = new Request();
        request.setServiceName(serviceName);
        request.setMethodName(method.getName());
        request.setArgs(args);
        Class<?>[] argTypes = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            argTypes[i] = args[i].getClass();
        }
        request.setArgTypes(argTypes);
        Message<Request> message = new Message<>(header, request);

        RpcFuture<Response> rpcFuture = rpcResponseHolder.sendRequest(message, RpcContext.DEFAULT_TIMEOUT);
        return rpcFuture.getPromise().get(RpcContext.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
    }
}
