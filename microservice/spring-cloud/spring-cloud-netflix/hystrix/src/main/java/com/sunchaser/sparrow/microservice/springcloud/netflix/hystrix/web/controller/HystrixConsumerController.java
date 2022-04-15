package com.sunchaser.sparrow.microservice.springcloud.netflix.hystrix.web.controller;

import com.netflix.hystrix.*;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.sunchaser.sparrow.microservice.springcloud.netflix.hystrix.clients.ProviderFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/1/16
 */
@RestController
@Slf4j
@DefaultProperties(defaultFallback = "defaultFallback")
public class HystrixConsumerController {
    @Autowired
    private ProviderFeignClient providerFeignClient;
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/thread")
    public String getThread() {
        return Thread.currentThread().getName();
    }

    /**
     * Hystrix编程式命令
     * 调用execute()方法后会自动执行重写的run方法
     * 异常时执行getFallback()降级
     * @param id param
     * @return response
     */
    @GetMapping("/hystrix/command/consumer/{id}")
    public String commandConsume(@PathVariable String id) {
        return new ConsumeHystrixCommand(restTemplate, id).execute();
    }

    static class ConsumeHystrixCommand extends HystrixCommand<String> {

        private final RestTemplate restTemplate;
        private final String id;

        public ConsumeHystrixCommand(RestTemplate restTemplate, String id) {
            super(setter());
            this.restTemplate = restTemplate;
            this.id = id;
        }

        private static Setter setter() {
            // 服务分组
            HystrixCommandGroupKey groupKey
                    = HystrixCommandGroupKey.Factory.asKey("hystrix-command-group");
            // 服务标识
            HystrixCommandKey commandKey
                    = HystrixCommandKey.Factory.asKey("hystrix-command-consume");
            // 线程池名称
            HystrixThreadPoolKey threadPoolKey
                    = HystrixThreadPoolKey.Factory.asKey("hystrix-command-consume-pool");
            /**
             * 线程池配置
             * coreSize：核心线程数
             * keepAliveTimeMinutes：线程空闲存活时间，单位：秒
             * queueSizeRejectionThreshold：阻塞队列大小，超过设定值（例如：1000）则执行拒绝策略
             */
            HystrixThreadPoolProperties.Setter threadPoolProperties = HystrixThreadPoolProperties.Setter()
                    .withCoreSize(10)
                    .withKeepAliveTimeMinutes(15)
                    .withQueueSizeRejectionThreshold(1000);
            /**
             * 设置Command属性
             * executionIsolationStrategy：线程池方式实现隔离
             * executionTimeoutEnabled：禁止超时。
             */
            HystrixCommandProperties.Setter commandProperties = HystrixCommandProperties.Setter()
                    .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.THREAD)
                    .withExecutionTimeoutEnabled(false);
            return Setter.withGroupKey(groupKey)
                    .andCommandKey(commandKey)
                    .andThreadPoolKey(threadPoolKey)
                    .andThreadPoolPropertiesDefaults(threadPoolProperties)
                    .andCommandPropertiesDefaults(commandProperties);
        }

        @Override
        protected String run() throws Exception {
            log.info("Thread: {}", Thread.currentThread().getName());
            return restTemplate.getForObject("http://localhost:9001/provider?id=" + id, String.class);
        }

        @Override
        protected String getFallback() {
            log.info("Thread: {}", Thread.currentThread().getName());
            return Thread.currentThread().getName() + "hystrix command fallback!" + id;
        }
    }

    /**
     * HystrixCommand注解形式
     * fallbackMethod：指定降级方法名
     * @param id param
     * @return response
     */
    @GetMapping("/hystrix/rest/consumer/{id}")
    @com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand(fallbackMethod = "restConsumeFallback")
    public String restConsume(@PathVariable String id) {
        log.info("Thread: {}", Thread.currentThread().getName());
        return restTemplate.getForObject("http://localhost:9001/provider?id=" + id, String.class);
    }

    public String restConsumeFallback(String id) {
        log.info("Thread: {}", Thread.currentThread().getName());
        return "hystrix rest fallback!" + id;
    }

    public String defaultFallback() {
        return "全局默认降级";
    }

    /**
     * 使用全局（单个controller）默认降级：defaultFallback
     * @param id param
     * @return response
     */
    @GetMapping("/hystrix/default/consumer/{id}")
    @com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand
    public String hystrixDefaultFallback(@PathVariable String id) {
        return providerFeignClient.provide(id);
    }

    /**
     * feign使用hystrix
     * 需要配置feign.hystrix.enable=true
     * @param id param
     * @return response
     */
    @GetMapping("/hystrix/feign/consumer/{id}")
    public String hystrixFeignConsume(@PathVariable String id) {
        return providerFeignClient.provide(id);
    }

}
