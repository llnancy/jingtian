package com.sunchaser.sparrow.microservice.springcloud.hystrix.integration;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Component;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/1/7
 */
@Component
public class StoreIntegration {

    @HystrixCommand(fallbackMethod = "defaultStores")
    public void getStores() {
    }

    public void defaultStores() {
    }
}
