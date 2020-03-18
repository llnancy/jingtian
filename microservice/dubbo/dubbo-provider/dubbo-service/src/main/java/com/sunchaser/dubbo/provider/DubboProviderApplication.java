package com.sunchaser.dubbo.provider;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ImportResource;

/**
 * @author sunchaser
 * @date 2020/1/29
 * @description
 * @since 1.0
 */
@SpringBootApplication
@ImportResource(locations = {"classpath*:META-INF/applicationContext-dubbo.xml"})
public class DubboProviderApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(DubboProviderApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }
}
