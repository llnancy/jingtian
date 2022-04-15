package com.sunchaser.sparrow.microservice.dubbo.api.provider;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/3/26
 */
@SpringBootApplication
public class DubboApiProviderApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(DubboApiProviderApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
