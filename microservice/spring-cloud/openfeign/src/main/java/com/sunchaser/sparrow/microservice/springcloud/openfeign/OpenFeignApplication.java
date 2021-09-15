package com.sunchaser.sparrow.microservice.springcloud.openfeign;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/2/23
 */
@SpringBootApplication
@EnableFeignClients
public class OpenFeignApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(OpenFeignApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
