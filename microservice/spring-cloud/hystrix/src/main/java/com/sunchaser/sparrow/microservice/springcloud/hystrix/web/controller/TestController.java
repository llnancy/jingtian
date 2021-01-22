package com.sunchaser.sparrow.microservice.springcloud.hystrix.web.controller;

import com.sunchaser.sparrow.microservice.springcloud.hystrix.integration.StoreIntegration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/1/16
 */
@RestController
public class TestController {
    @Autowired
    private StoreIntegration storeIntegration;

    @GetMapping("/testHystrix")
    public String testHystrix() {
        return storeIntegration.getStores();
    }
}
