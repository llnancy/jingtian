package com.sunchaser.sparrow.javaee.spring;

import com.sunchaser.sparrow.javaee.spring.beans.scope.InheritableRequestContextListener;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.RequestContextFilter;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/3/1
 */
@SpringBootApplication
public class SpringApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }

//    @Bean
//    public InheritableRequestContextListener requestContextListener() {
//        return new InheritableRequestContextListener();
//    }
//
//    @Bean
//    public RequestContextFilter requestContextFilter() {
//        RequestContextFilter requestContextFilter = new RequestContextFilter();
//        requestContextFilter.setThreadContextInheritable(true);
//        return requestContextFilter;
//    }
}
