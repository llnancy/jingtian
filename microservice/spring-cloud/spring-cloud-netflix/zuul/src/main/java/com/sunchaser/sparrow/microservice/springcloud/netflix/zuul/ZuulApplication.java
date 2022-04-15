package com.sunchaser.sparrow.microservice.springcloud.netflix.zuul;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/9/17
 */
@SpringBootApplication
@EnableZuulProxy
public class ZuulApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(ZuulApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
