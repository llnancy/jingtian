package com.sunchaser.sparrow.microservice.springcloud.resilience4j.integration;

import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/1/16
 */
@Component
public class StoreIntegration {
    public String getStores() {
        int nextInt = ThreadLocalRandom.current().nextInt(60);
        int i = 0;
        if (nextInt > 30) i = 1 / 0;
        return "getStores" + i;
    }
}
