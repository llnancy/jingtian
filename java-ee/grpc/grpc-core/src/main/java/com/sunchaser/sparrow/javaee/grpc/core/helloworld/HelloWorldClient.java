package com.sunchaser.sparrow.javaee.grpc.core.helloworld;

import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * grpc客户端
 * @author sunchaser admin@lilu.org.cn
 * @since 2022/1/14
 */
public class HelloWorldClient {
    private static final Logger log = Logger.getLogger(HelloWorldClient.class.getName());

    private final GreeterGrpc.GreeterBlockingStub blockingStub;

    public HelloWorldClient(Channel channel) {
        // 初始化远程服务存根stub
        blockingStub = GreeterGrpc.newBlockingStub(channel);
    }

    public void greet(String name) {
        log.info("Will try to greet " + name + " ...");
        // 构造服务调用入参对象
        HelloRequest request = HelloRequest.newBuilder()
                .setName(name)
                .build();
        HelloReply response;
        try {
            // 调用rpc远程服务
            response = blockingStub.sayHello(request);
        } catch (StatusRuntimeException e) {
            log.log(Level.WARNING, "gRPC failed: {0}", e.getStatus());
            return;
        }
        // 输出返回值
        log.info("Greeting: " + response.getMessage());
    }

    public static void main(String[] args) throws InterruptedException {
        String user = "grpc";
        String target = "localhost:50051";
        if (args.length > 0) {
            if ("--help".equals(args[0])) {
                System.err.println("Usage: [name [target]]");
                System.err.println("");
                System.err.println(" name The name you wish to be greeted by. Defaults to " + user);
                System.err.println(" target The server to connect to. Defaults to " + target);
                System.exit(1);
            }
            user = args[0];
        }
        if (args.length > 1) {
            target = args[1];
        }
        // 远程连接管理器，管理连接的生命周期。初始化远程连接
        ManagedChannel channel = ManagedChannelBuilder.forTarget(target)
                .usePlaintext()
                .build();
        try {
            // 创建客户端
            HelloWorldClient client = new HelloWorldClient(channel);
            // 远程rpc服务调用
            client.greet(user);
        } finally {
            // 关闭连接
            channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}
