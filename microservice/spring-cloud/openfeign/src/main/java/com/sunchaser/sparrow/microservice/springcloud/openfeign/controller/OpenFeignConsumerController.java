package com.sunchaser.sparrow.microservice.springcloud.openfeign.controller;

import com.sunchaser.sparrow.microservice.springcloud.openfeign.feign.ProviderFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/9/13
 */
@RestController
public class OpenFeignConsumerController {
    @Autowired
    private ProviderFeignClient providerFeignClient;

    @GetMapping("/consumer/{id}")
    public String consume(@PathVariable String id) {
        return providerFeignClient.provide(id);
    }
}
