package com.sunchaser.sparrow.microservice.springcloud.eureka.client.provider.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/2/6
 */
@RestController
public class ProviderController {
    @GetMapping("/provider")
    public String provide(String id) {
        return "provide:" + id;
    }
}
