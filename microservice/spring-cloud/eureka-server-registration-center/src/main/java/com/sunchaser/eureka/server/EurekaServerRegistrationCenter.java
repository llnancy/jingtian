package com.sunchaser.eureka.server;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author sunchaser
 * @date 2020/6/17
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
