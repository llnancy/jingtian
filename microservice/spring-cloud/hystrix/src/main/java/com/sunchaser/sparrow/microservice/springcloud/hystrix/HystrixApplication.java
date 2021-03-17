package com.sunchaser.sparrow.microservice.springcloud.hystrix;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/1/7
 */
@SpringBootApplication
@EnableHystrix
@EnableHystrixDashboard
@EnableCircuitBreaker
public class HystrixApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(HystrixApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
