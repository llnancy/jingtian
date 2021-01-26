package com.sunchaser.sparrow.springboot.frame;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import groovy.lang.Tuple2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.lang.reflect.ParameterizedType;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

/**
 * @author sunchaserlilu@didiglobal.com
 * @since JDK8 2021/1/25
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class CountDownLatch2Executor<RESPONSE1, RESPONSE2> {

    private final CacheTemplate cacheTemplate;

    public Tuple2<RESPONSE1, RESPONSE2> execute(ExecutorService executorService,
                                                Supplier<RESPONSE1> execute1,
                                                Supplier<RESPONSE2> execute2,
                                                String cacheKey1,
                                                String cacheKey2) {
        log.info("cacheKey1={},cacheKey2={}", cacheKey1, cacheKey2);
        AtomicReference<RESPONSE1> response1 = new AtomicReference<>();
        AtomicReference<RESPONSE2> response2 = new AtomicReference<>();
        try {
            CountDownLatch latch2 = new CountDownLatch(2);
            executorService.execute(() -> {
                StopWatch watch = null;
                try {
                    watch = new StopWatch("latch1");
                    watch.start();
                    response1.set(execute1.get());
                    log.info("todo -------------------");
                } catch (Exception e) {
                    log.info("exception -------------------");
                    e.printStackTrace();
                } finally {
                    if (watch != null) {
                        watch.stop();
                    }
                    latch2.countDown();
                }
            });
            executorService.execute(() -> {
                StopWatch watch = null;
                try {
                    watch = new StopWatch("latch2");
                    watch.start();
                    response2.set(execute2.get());
                    log.info("todo -------------------");
                } catch (Exception e) {
                    e.printStackTrace();
                    log.info("Exception -------------------");
                } finally {
                    if (watch != null) {
                        watch.stop();
                    }
                    latch2.countDown();
                }
            });
            latch2.await();
        } catch (Exception e) {
            log.error("todo ---------------------", e);
            e.printStackTrace();
        }
        RESPONSE1 r1 = response1.get();
        RESPONSE2 r2 = response2.get();
        return new Tuple2<>(r1, r2);
    }
}