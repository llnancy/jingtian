package com.sunchaser.sparrow.microservice.springcloud.netflix.eureka.client.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/2/6
 */
@RestController
public class ConsumerController {
    @Autowired
    private RestTemplate restTemplate;
//
//    @Autowired
//    private DiscoveryClient discoveryClient;

    @GetMapping("/consumer/{id}")
    public String consume(@PathVariable String id) {
        return restTemplate.getForObject("http://localhost:9001/provider?id=" + id, String.class);
    }
//
//    @GetMapping("/provider/instance")
//    public List<ServiceInstance> getProviderInstanceList() {
//        return discoveryClient.getInstances("eureka-client-service-provider");
//    }
}
