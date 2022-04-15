package com.sunchaser.sparrow.microservice.dubbo.xml.provider;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ImportResource;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/3/17
 */
@SpringBootApplication
@ImportResource(value = "classpath*:META-INF/applicationContext-bean.xml")
public class DubboXmlProviderApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(DubboXmlProviderApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
