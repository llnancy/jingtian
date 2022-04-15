package com.sunchaser.sparrow.microservice.springcloud.resilience4j;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/1/16
 */
@SpringBootApplication
public class Resilience4jApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(Resilience4jApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
