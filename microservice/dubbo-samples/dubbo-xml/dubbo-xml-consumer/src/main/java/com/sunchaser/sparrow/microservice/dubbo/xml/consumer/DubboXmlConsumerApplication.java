package com.sunchaser.sparrow.microservice.dubbo.xml.consumer;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ImportResource;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/3/19
 */
@SpringBootApplication
@ImportResource(value = "classpath*:META-INF/applicationContext-bean.xml")
public class DubboXmlConsumerApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(DubboXmlConsumerApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
