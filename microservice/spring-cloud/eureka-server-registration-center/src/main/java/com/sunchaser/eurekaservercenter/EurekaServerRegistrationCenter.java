package com.sunchaser.eurekaservercenter;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @auther: sunchaser
 * @date 2019/11/25
 * @description
 * @since 1.0
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerRegistrationCenter {
    public static void main(String[] args) {
        new SpringApplicationBuilder(EurekaServerRegistrationCenter.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
