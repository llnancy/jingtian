package com.sunchaser.sparrow.microservice.springcloud.openfeign.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/9/13
 */
//@FeignClient(url = "http://127.0.0.1:9000", name = "ProviderFeignClient")
@FeignClient(name = "eureka-client-service-provider")
public interface ProviderFeignClient {
    @GetMapping("/provider")
    String provide(@RequestParam(value = "id") String id);
}
