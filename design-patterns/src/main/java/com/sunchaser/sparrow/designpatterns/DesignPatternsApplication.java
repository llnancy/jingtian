package com.sunchaser.sparrow.designpatterns;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/1/3
 */
@SpringBootApplication
public class DesignPatternsApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(DesignPatternsApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
