package com.sunchaser.sparrow.microservice.springcloud.netflix.eureka.client.provider;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/1/7
 */
@SpringBootApplication
public class EurekaClientProviderApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(EurekaClientProviderApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
