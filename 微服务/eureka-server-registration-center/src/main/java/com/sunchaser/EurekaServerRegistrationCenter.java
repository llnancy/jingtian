package com.sunchaser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @auther: sunchaser
 * @date: 2019/10/22
 * @description: Eureka注册中心
 * @since 1.0
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerRegistrationCenter {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerRegistrationCenter.class,args);
    }
}
