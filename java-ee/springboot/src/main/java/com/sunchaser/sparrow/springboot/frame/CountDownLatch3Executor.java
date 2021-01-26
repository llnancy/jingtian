package com.sunchaser.sparrow.springboot.frame;

import groovy.lang.Tuple3;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class CountDownLatch3Executor<RESPONSE1, RESPONSE2, RESPONSE3> {

    private final CacheTemplate cacheTemplate;

    public Tuple3<RESPONSE1, RESPONSE2, RESPONSE3> execute(ExecutorService executorService,
                                                           Supplier<RESPONSE1> supplier1,
                                                           Supplier<RESPONSE2> supplier2,
                                                           Supplier<RESPONSE3> supplier3) {
        AtomicReference<RESPONSE1> response1 = new AtomicReference<>();
        AtomicReference<RESPONSE2> response2 = new AtomicReference<>();
        AtomicReference<RESPONSE3> response3 = new AtomicReference<>();
        try {
            CountDownLatch latch3 = new CountDownLatch(3);
            executorService.execute(() -> {
                StopWatch watch = null;
                try {
                    watch = new StopWatch("latch1");
                    watch.start();
                    response1.set(supplier1.get());
                    log.info("CountDownLatch3Executor#latch1#todo -------------------");
                } catch (Exception e) {
                    log.info("CountDownLatch3Executor#latch1#Exception#todo -------------------");
                    e.printStackTrace();
                } finally {
                    if (watch != null) {
                        watch.stop();
                    }
                    latch3.countDown();
                }
            });
            executorService.execute(() -> {
                StopWatch watch = null;
                try {
                    watch = new StopWatch("latch2");
                    watch.start();
                    response2.set(supplier2.get());
                    log.info("CountDownLatch3Executor#latch2#todo-------------------");
                } catch (Exception e) {
                    e.printStackTrace();
                    log.info("CountDownLatch3Executor#latch2#Exception#todo-------------------");
                } finally {
                    if (watch != null) {
                        watch.stop();
                    }
                    latch3.countDown();
                }
            });
            executorService.execute(() -> {
                StopWatch watch = null;
                try {
                    watch = new StopWatch("latch3");
                    watch.start();
                    response3.set(supplier3.get());
                    log.info("CountDownLatch3Executor#latch3#todo-------------------");
                } catch (Exception e) {
                    e.printStackTrace();
                    log.info("CountDownLatch3Executor#latch3#todo-------------------");
                } finally {
                    if (watch != null) {
                        watch.stop();
                    }
                    latch3.countDown();
                }
            });
            latch3.await();
        } catch (Exception e) {
            log.error("todo ---------------------", e);
            e.printStackTrace();
        }
        RESPONSE1 r1 = response1.get();
        RESPONSE2 r2 = response2.get();
        RESPONSE3 r3 = response3.get();
        return new Tuple3<>(r1, r2, r3);
    }

    @SuppressWarnings("unchecked")
    public Tuple3<RESPONSE1, RESPONSE2, RESPONSE3> execute(ExecutorService executorService,
                                                           Supplier<RESPONSE1> execute1,
                                                           Supplier<RESPONSE2> execute2,
                                                           Supplier<RESPONSE3> execute3,
                                                           String cacheKey1,
                                                           String cacheKey2,
                                                           String cacheKey3,
                                                           RESPONSE1 defaultV1,
                                                           RESPONSE2 defaultV2,
                                                           RESPONSE3 defaultV3) {
        AtomicReference<RESPONSE1> response1 = new AtomicReference<>();
        AtomicReference<RESPONSE2> response2 = new AtomicReference<>();
        AtomicReference<RESPONSE3> response3 = new AtomicReference<>();
        try {
            CountDownLatch latch3 = new CountDownLatch(3);
            executorService.execute(() -> {
                StopWatch watch = null;
                try {
                    watch = new StopWatch("latch1");
                    watch.start();
                    Class<RESPONSE1> response1Class = (Class<RESPONSE1>) ((ParameterizedType) getClass()
                            .getGenericSuperclass())
                            .getActualTypeArguments()[0];
                    response1.set(execute1.get());
                    log.info("CountDownLatch3Executor#latch1#todo -------------------");
                } catch (Exception e) {
                    log.info("CountDownLatch3Executor#latch1#Exception#todo -------------------");
                    e.printStackTrace();
                } finally {
                    if (watch != null) {
                        watch.stop();
                    }
                    latch3.countDown();
                }
            });
            executorService.execute(() -> {
                StopWatch watch = null;
                try {
                    watch = new StopWatch("latch2");
                    watch.start();
                    Class<RESPONSE2> response2Class = (Class<RESPONSE2>) ((ParameterizedType) getClass()
                            .getGenericSuperclass())
                            .getActualTypeArguments()[0];
                    response2.set(execute2.get());
                    log.info("CountDownLatch3Executor#latch2#todo-------------------");
                } catch (Exception e) {
                    e.printStackTrace();
                    log.info("CountDownLatch3Executor#latch2#Exception#todo-------------------");
                } finally {
                    if (watch != null) {
                        watch.stop();
                    }
                    latch3.countDown();
                }
            });
            executorService.execute(() -> {
                StopWatch watch = null;
                try {
                    watch = new StopWatch("latch3");
                    watch.start();
                    Class<RESPONSE3> response3Class = (Class<RESPONSE3>) ((ParameterizedType) getClass()
                            .getGenericSuperclass())
                            .getActualTypeArguments()[0];
                    response3.set(execute3.get());
                    log.info("CountDownLatch3Executor#latch3#todo-------------------");
                } catch (Exception e) {
                    e.printStackTrace();
                    log.info("CountDownLatch3Executor#latch3#todo-------------------");
                } finally {
                    if (watch != null) {
                        watch.stop();
                    }
                    latch3.countDown();
                }
            });
            latch3.await();
        } catch (Exception e) {
            log.error("todo ---------------------", e);
            e.printStackTrace();
        }
        RESPONSE1 r1 = response1.get();
        RESPONSE2 r2 = response2.get();
        RESPONSE3 r3 = response3.get();
        return new Tuple3<>(r1, r2, r3);
    }

    @SuppressWarnings("unchecked")
    public Tuple3<RESPONSE1, RESPONSE2, RESPONSE3> execute(ExecutorService executorService,
                                                           Supplier<RESPONSE1> execute1,
                                                           Supplier<RESPONSE2> execute2,
                                                           Supplier<RESPONSE3> execute3,
                                                           String cacheKey1,
                                                           String cacheKey2,
                                                           String cacheKey3) {
        AtomicReference<RESPONSE1> response1 = new AtomicReference<>();
        AtomicReference<RESPONSE2> response2 = new AtomicReference<>();
        AtomicReference<RESPONSE3> response3 = new AtomicReference<>();
        try {
            CountDownLatch latch3 = new CountDownLatch(3);
            executorService.execute(() -> {
                StopWatch watch = null;
                try {
                    watch = new StopWatch("latch1");
                    watch.start();
                    Class<RESPONSE1> response1Class = (Class<RESPONSE1>) ((ParameterizedType) getClass()
                            .getGenericSuperclass())
                            .getActualTypeArguments()[0];
                    response1.set(execute1.get());
                    log.info("CountDownLatch3Executor#latch1#todo -------------------");
                } catch (Exception e) {
                    log.info("CountDownLatch3Executor#latch1#Exception#todo -------------------");
                    e.printStackTrace();
                } finally {
                    if (watch != null) {
                        watch.stop();
                    }
                    latch3.countDown();
                }
            });
            executorService.execute(() -> {
                StopWatch watch = null;
                try {
                    watch = new StopWatch("latch2");
                    watch.start();
                    Class<RESPONSE2> response2Class = (Class<RESPONSE2>) ((ParameterizedType) getClass()
                            .getGenericSuperclass())
                            .getActualTypeArguments()[0];
                    response2.set(execute2.get());
                    log.info("CountDownLatch3Executor#latch2#todo-------------------");
                } catch (Exception e) {
                    e.printStackTrace();
                    log.info("CountDownLatch3Executor#latch2#Exception#todo-------------------");
                } finally {
                    if (watch != null) {
                        watch.stop();
                    }
                    latch3.countDown();
                }
            });
            executorService.execute(() -> {
                StopWatch watch = null;
                try {
                    watch = new StopWatch("latch3");
                    watch.start();
                    Class<RESPONSE3> response3Class = (Class<RESPONSE3>) ((ParameterizedType) getClass()
                            .getGenericSuperclass())
                            .getActualTypeArguments()[0];
                    response3.set(execute3.get());
                    log.info("CountDownLatch3Executor#latch3#todo-------------------");
                } catch (Exception e) {
                    e.printStackTrace();
                    log.info("CountDownLatch3Executor#latch3#todo-------------------");
                } finally {
                    if (watch != null) {
                        watch.stop();
                    }
                    latch3.countDown();
                }
            });
            latch3.await();
        } catch (Exception e) {
            log.error("todo ---------------------", e);
        }
        RESPONSE1 r1 = response1.get();
        RESPONSE2 r2 = response2.get();
        RESPONSE3 r3 = response3.get();
        return new Tuple3<>(r1, r2, r3);
    }
}