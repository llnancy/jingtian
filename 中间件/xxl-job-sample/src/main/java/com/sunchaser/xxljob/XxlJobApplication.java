package com.sunchaser.xxljob;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author sunchaser
 * @date 2020/1/19
 * @description
 * @since 1.0
 */
@SpringBootApplication
public class XxlJobApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(XxlJobApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
