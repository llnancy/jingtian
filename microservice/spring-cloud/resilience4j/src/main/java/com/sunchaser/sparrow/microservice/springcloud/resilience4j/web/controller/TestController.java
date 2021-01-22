package com.sunchaser.sparrow.microservice.springcloud.resilience4j.web.controller;

import com.alibaba.fastjson.JSON;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeoutException;

import static io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.SlidingWindowType.COUNT_BASED;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/1/16
 */
@RestController
public class TestController {
    @GetMapping("/testResilience4j")
    public void testResilience4j() {
        doTestResilience4j();
    }

    public static void main(String[] args) {
        doTestResilience4j();
    }

    public static void doTestResilience4j() {
        // 生成自定义熔断器配置
        CircuitBreakerConfig customConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofMillis(1000))
                .slidingWindowSize(2)
                .minimumNumberOfCalls(2)
                .slidingWindowType(COUNT_BASED)
                .permittedNumberOfCallsInHalfOpenState(2)
                .recordExceptions(IOException.class, TimeoutException.class)
                .ignoreExceptions(RuntimeException.class)
                .enableAutomaticTransitionFromOpenToHalfOpen()
                .build();

        // 获取默认的熔断器的注册器实例
        CircuitBreakerRegistry defaultRegistry = CircuitBreakerRegistry.ofDefaults();
        // 根据自定义熔断器配置生成自定义熔断器的注册器
        CircuitBreakerRegistry customConfigRegistry = CircuitBreakerRegistry.of(customConfig);

        // 生成熔断器实例
        // 使用默认注册器生成
        CircuitBreaker defaultConfigCircuitBreaker = defaultRegistry.circuitBreaker("defaultConfigCircuitBreaker");
        // 使用自定义注册器生成
        CircuitBreaker customConfigCircuitBreaker = customConfigRegistry.circuitBreaker("customConfigCircuitBreaker");
        // 使用默认注册器+自定义熔断器配置生成
        CircuitBreaker defaultRegistryCustomConfigCircuitBreaker = defaultRegistry.circuitBreaker("defaultRegistryCustomConfigCircuitBreaker", customConfig);
        // 对Java8的lambda表达式和方法引用的支持：在生成熔断器时进行自定义配置
        CircuitBreaker lambdaCustomConfigCircuitBreaker = defaultRegistry.circuitBreaker("lambdaCustomConfigCircuitBreaker", TestController::buildCustomCircuitBreakerConfig);

        // 由注册器生成的熔断器的配置进行输出打印
        CircuitBreakerConfig defaultCircuitBreakerConfig = defaultConfigCircuitBreaker.getCircuitBreakerConfig();
        CircuitBreakerConfig customCircuitBreakerConfig = customConfigCircuitBreaker.getCircuitBreakerConfig();
        CircuitBreakerConfig defaultRegistryCustomCircuitBreakerConfig = defaultRegistryCustomConfigCircuitBreaker.getCircuitBreakerConfig();
        CircuitBreakerConfig lambdaCustomCircuitBreakerConfig = lambdaCustomConfigCircuitBreaker.getCircuitBreakerConfig();
        System.out.println(JSON.toJSONString(defaultCircuitBreakerConfig));
        System.out.println(JSON.toJSONString(customCircuitBreakerConfig));
        System.out.println(JSON.toJSONString(defaultRegistryCustomCircuitBreakerConfig));
        System.out.println(JSON.toJSONString(lambdaCustomCircuitBreakerConfig));

        // 使用熔断器的静态方法生成熔断器
        CircuitBreaker defaultCircuitBreaker = CircuitBreaker.ofDefaults("defaultCircuitBreaker");
        CircuitBreaker configCircuitBreaker = CircuitBreaker.of("customConfigCircuitBreaker", customCircuitBreakerConfig);

    }

    private static CircuitBreakerConfig buildCustomCircuitBreakerConfig() {
        return CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofMillis(1000))
                .slidingWindowSize(4)
                .minimumNumberOfCalls(4)
                .slidingWindowType(COUNT_BASED)
                .permittedNumberOfCallsInHalfOpenState(3)
                .recordExceptions(IOException.class, TimeoutException.class)
                .ignoreExceptions(RuntimeException.class)
                .enableAutomaticTransitionFromOpenToHalfOpen()
                .build();
    }
}
