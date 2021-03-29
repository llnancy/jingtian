package com.sunchaser.sparrow.microservice.dubbo.annotation.consumer.config;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ConsumerConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.apache.dubbo.config.Constants.ZOOKEEPER_PROTOCOL;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/3/26
 */
@Configuration
@EnableDubbo(scanBasePackages = "com.sunchaser.sparrow.microservice.dubbo.annotation.provider.facade")
public class DubboConfig {

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${zookeeper.address}")
    private String zookeeperAddress;

    @Bean
    public ConsumerConfig consumerConfig() {
        return new ConsumerConfig();
    }

    @Bean
    public ApplicationConfig applicationConfig() {
        return new ApplicationConfig(applicationName);
    }

    @Bean
    public RegistryConfig registryConfig() {
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setProtocol(ZOOKEEPER_PROTOCOL);
        registryConfig.setAddress(zookeeperAddress);
        return registryConfig;
    }
}
