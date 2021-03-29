package com.sunchaser.sparrow.microservice.dubbo.api.consumer.web.controller;

import com.sunchaser.sparrow.microservice.dubbo.facade.EchoFacade;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/3/26
 */
@RestController
public class ApiConsumeController implements InitializingBean {

    private ReferenceConfig<EchoFacade> reference;

    @Override
    public void afterPropertiesSet() throws Exception {
        reference = new ReferenceConfig<>();
        reference.setApplication(new ApplicationConfig(applicationName));
        reference.setRegistry(new RegistryConfig(zookeeperAddress));
        reference.setInterface(EchoFacade.class);
    }

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${zookeeper.address}")
    private String zookeeperAddress;

    @GetMapping("/echo")
    public String echo(String msg) {
        EchoFacade echoFacade = reference.get();
        System.out.println("echo api");
        return echoFacade.echo(msg);
    }
}
