package com.sunchaser.sparrow.springboot.frame;

import groovy.lang.Tuple2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/1/27
 */
@RestController
public class TestController {
    private static final ExecutorService executorService = Executors.newFixedThreadPool(10);

    @Autowired
    private CacheTemplate cacheTemplate;

    @GetMapping("/countDownLatchExecuteTest")
    public void test() {
        CountDownLatch2Executor<String, String> countDownLatch2Executor = new CountDownLatch2Executor<>(cacheTemplate);
        Tuple2<String, String> execute = countDownLatch2Executor.execute(
                executorService,
                () -> "do execute1",
                () -> "do execute2",
                "key1",
                "key2"
        );
        String first = execute.getFirst();
        String second = execute.getSecond();
    }

    @GetMapping("/countDownLatchExecutorTemplateTest")
    public void testTemplate() {
        testCountDownLatchExecutorUtils();
    }

    public static void main(String[] args) {
        testSpringStopWatch();
        testStopWatchWrapper();
        testCountDownLatchExecutorUtils();
    }

    private static void testStopWatchWrapper() {
        try (StopWatchWrapper watchWrapper = new StopWatchWrapper("test-watch-wrapper")){
            watchWrapper.start();
            Thread.sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void testSpringStopWatch() {
        StopWatch stopWatch = null;
        try {
            stopWatch = new StopWatch();
            stopWatch.start();
            // mock biz
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (stopWatch != null) {
                stopWatch.stop();
            }
        }
        System.out.println("执行耗时：" + stopWatch.getTotalTimeMillis());
    }

    public static void testCountDownLatchExecutorUtils() {
        AtomicReference<String> result1 = new AtomicReference<>();
        AtomicReference<String> result2 = new AtomicReference<>();
        AtomicReference<String> result3 = new AtomicReference<>();
        CoatInvoker coatInvoker1 = () -> {
            result1.set("result1");
            System.out.println("coatInvoker1");
        };
        CoatInvoker coatInvoker2 = () -> {
            result2.set("result2");
            System.out.println("coatInvoker2");
        };
        CoatInvoker coatInvoker3 = () -> {
            result3.set("result3");
            System.out.println("coatInvoker3");
        };
        List<CoatInvoker> list = new ArrayList<>();
        list.add(coatInvoker1);
        list.add(coatInvoker2);
        list.add(coatInvoker3);
        CountDownLatchExecutorUtils.execute(executorService, list, "test");
        // 得到执行结果
        String r1 = result1.get();
        String r2 = result1.get();
        String r3 = result1.get();
        executorService.shutdown();
    }
}

