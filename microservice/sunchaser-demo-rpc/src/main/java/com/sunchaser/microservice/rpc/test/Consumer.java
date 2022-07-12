package com.sunchaser.microservice.rpc.test;

import com.sunchaser.microservice.rpc.proxy.RpcProxy;
import com.sunchaser.microservice.rpc.registry.impl.ZookeeperRegistry;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/21
 */
public class Consumer {

    public static void main(String[] args) throws Exception {
        ZookeeperRegistry zookeeperRegistry = new ZookeeperRegistry();
        zookeeperRegistry.start();
        HelloService helloService = RpcProxy.newInstance(HelloService.class, zookeeperRegistry);
        String res = helloService.sayHello("sunchaser");
        System.out.println(res);
    }
}
