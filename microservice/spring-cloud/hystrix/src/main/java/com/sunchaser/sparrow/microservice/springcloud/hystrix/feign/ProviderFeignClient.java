package com.sunchaser.sparrow.microservice.springcloud.hystrix.feign;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.sunchaser.sparrow.microservice.springcloud.hystrix.feign.ProviderFeignClient.*;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/9/13
 */
@FeignClient(
        value = "eureka-client-service-provider",
        fallback = ProviderFeignClientFallback.class,
        fallbackFactory = ProviderFeignClientFallbackFactory.class
)
public interface ProviderFeignClient {
    @GetMapping("/provider")
    String provide(@RequestParam(value = "id") String id);

    /**
     * fallback提供降级
     */
    @Component
    @Slf4j
    class ProviderFeignClientFallback implements ProviderFeignClient {

        @Override
        public String provide(String id) {
            log.info("Thread: {}", Thread.currentThread().getName());
            return "ProviderFeignClientFallback降级" + id;
        }
    }

    /**
     * fallbackFactory提供降级
     */
    @Component
    @Slf4j
    class ProviderFeignClientFallbackFactory implements FallbackFactory<ProviderFeignClient> {

        @Override
        public ProviderFeignClient create(Throwable throwable) {
            return new ProviderFeignClient() {
                @Override
                public String provide(String id) {
                    log.info("Thread: {}", Thread.currentThread().getName());
                    return "ProviderFeignClientFallbackFactory降级" + id;
                }
            };
        }
    }
}
