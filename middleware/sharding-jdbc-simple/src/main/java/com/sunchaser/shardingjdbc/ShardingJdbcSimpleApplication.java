package com.sunchaser.shardingjdbc;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author sunchaser
 * @date 2019/11/26
 * @description
 * @since 1.0
 */
@SpringBootApplication
public class ShardingJdbcSimpleApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(ShardingJdbcSimpleApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
