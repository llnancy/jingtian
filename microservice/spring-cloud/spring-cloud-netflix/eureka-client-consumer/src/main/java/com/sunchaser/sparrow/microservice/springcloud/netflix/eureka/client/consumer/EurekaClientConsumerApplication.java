package com.sunchaser.sparrow.microservice.springcloud.netflix.eureka.client.consumer;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/2/5
 */
@SpringBootApplication
public class EurekaClientConsumerApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(EurekaClientConsumerApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
