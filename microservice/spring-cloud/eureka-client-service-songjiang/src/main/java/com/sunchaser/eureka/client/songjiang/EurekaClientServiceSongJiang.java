package com.sunchaser.eureka.client.songjiang;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author sunchaser
 * @date 2020/6/16
 * @since 1.0
 */
@SpringBootApplication
@EnableEurekaClient
public class EurekaClientServiceSongJiang {
    public static void main(String[] args) {
        new SpringApplicationBuilder(EurekaClientServiceSongJiang.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
