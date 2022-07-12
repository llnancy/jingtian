package com.sunchaser.microservice.rpc.test;

import com.sunchaser.microservice.rpc.bean.BeanFactory;
import com.sunchaser.microservice.rpc.registry.ServiceInfo;
import com.sunchaser.microservice.rpc.registry.impl.ZookeeperRegistry;
import com.sunchaser.microservice.rpc.transport.RpcServer;
import org.apache.curator.x.discovery.ServiceInstance;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/21
 */
public class Provider {

    public static void main(String[] args) throws Exception {
        BeanFactory.register("helloService", new HelloServiceImpl());

        ZookeeperRegistry zookeeperRegistry = new ZookeeperRegistry();
        zookeeperRegistry.start();

        ServiceInfo serviceInfo = new ServiceInfo("127.0.0.1", 1234);
        zookeeperRegistry.register(
                ServiceInstance.<ServiceInfo>builder()
                        .name("helloService")
                        .payload(serviceInfo)
                        .build()
        );
        RpcServer rpcServer = new RpcServer(1234);
        rpcServer.start();
    }


}
