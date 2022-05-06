package com.sunchaser.sparrow.javaee.graphql;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/5/6
 */
@SpringBootApplication
public class GraphQLApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(GraphQLApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
