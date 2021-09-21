package com.sunchaser.sparrow.microservice.springcloud.gateway;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author sunchaserlilu@didiglobal.com
 * @since JDK8 2021/9/19
 */
@SpringBootApplication
public class GatewayApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(GatewayApplication.class)
                .run(args);
    }
}
