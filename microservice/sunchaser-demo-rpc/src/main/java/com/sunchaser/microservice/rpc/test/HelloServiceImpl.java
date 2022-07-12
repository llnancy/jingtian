package com.sunchaser.microservice.rpc.test;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/21
 */
public class HelloServiceImpl implements HelloService {

    @Override
    public String sayHello(String hi) {
        return "hello:" + hi;
    }
}
