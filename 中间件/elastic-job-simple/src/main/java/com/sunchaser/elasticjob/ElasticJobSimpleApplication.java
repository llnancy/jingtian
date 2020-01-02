package com.sunchaser.elasticjob;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ImportResource;

/**
 * @author sunchaser
 * @date 2020/1/2
 * @description
 * @since 1.0
 */
@SpringBootApplication
@ImportResource(value = {"classpath*:META-INF/applicationContext-job.xml"})
public class ElasticJobSimpleApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(ElasticJobSimpleApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
