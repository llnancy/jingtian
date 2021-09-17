package com.sunchaser.sparrow.microservice.springcloud.netflix.eureka.client.provider.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/2/6
 */
@RestController
@Slf4j
public class ProviderController {
    @GetMapping("/provider")
    public String provide(String id) {
        log.info("provide invoked: id={}", id);
        return "provide:" + id;
    }
}
