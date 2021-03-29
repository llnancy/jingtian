package com.sunchaser.sparrow.microservice.dubbo.api.consumer;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/3/26
 */
@SpringBootApplication
public class DubboApiConsumerApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(DubboApiConsumerApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
