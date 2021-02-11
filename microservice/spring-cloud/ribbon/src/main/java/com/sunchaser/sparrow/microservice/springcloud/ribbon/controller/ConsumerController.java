package com.sunchaser.sparrow.microservice.springcloud.ribbon.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
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
    private static final Logger log = LoggerFactory.getLogger(ConsumerController.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @GetMapping("/consumer/{id}")
    public String consume(@PathVariable String id) {
        return restTemplate.getForObject("http://eureka-client-service-provider/provider?id=" + id, String.class);
    }

    @GetMapping("/print/provider/instance")
    public void getProviderInstanceList() {
        ServiceInstance serviceInstance = this.loadBalancerClient.choose("eureka-client-service-provider");
        log.info("service instance:{}", serviceInstance);
    }
}
