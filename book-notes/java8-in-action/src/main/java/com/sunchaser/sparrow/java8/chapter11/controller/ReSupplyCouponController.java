package com.sunchaser.sparrow.java8.chapter11.controller;

import com.sunchaser.sparrow.java8.chapter11.biz.service.ReSupplyCouponBizService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sunchaser
 * @date 2019/9/8
 * @description 用单元测试模拟控制器层的请求
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ReSupplyCouponController {

    private static List<String> uIds = new ArrayList<>();

    /**
     * 初始化操作，模拟待补发用户
     */
    static {
//        for (int i = 0; i < 250; i = i + 2) {
//            uIds.add(String.valueOf(i));
//        }
        for (int i = 0; i < 125; i++) {
            uIds.add(String.valueOf(i));
        }
    }

    @Autowired
    private ReSupplyCouponBizService reSupplyCouponBizService;

    /**
     * 测试同步 补发劵方法
     *
     * done in 37634msecs
     */
    @Test
    public void testSyncReSupplyCoupon() throws Exception {
        long start = System.nanoTime();
        List<String> failedUIDs = reSupplyCouponBizService.syncReSupplyCoupon(uIds, "1");
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("done in " + duration + "msecs");
        failedUIDs.stream().forEach(System.out::println);
    }

    /**
     * 测试基于Future的异步补发劵方法
     *
     * done in 1msecs
     * 线程并未阻塞，而是继续往下执行
     */
    @Test
    public void testAsyncFutureReSupplyCoupon() throws InterruptedException {
        long start = System.nanoTime();
        // 模拟前端点击补发劵按钮发送请求
        reSupplyCouponBizService.asyncFutureReSupplyCoupon(uIds,"1");
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("done in " + duration + "msecs");
        // 模拟时间间隔，此时间内前端页面应显示补发中，一段时间后轮询后端接口获取失败UIDs
        Thread.sleep(5);
        List<String> failedUIDs = reSupplyCouponBizService.getFailedUIDs();
        failedUIDs.stream().forEach(System.out::println);
    }

    /**
     * 测试使用Java 8的并行流进行的补发劵操作
     *
     * 8个UID
     * done in 312msecs
     *
     * 9个UID
     * done in 617msecs
     *
     * 125个UID
     * done in 5432msecs
     */
    @Test
    public void testParallelReSupplyCoupon() {
        long start = System.nanoTime();
        List<String> failedUIDs = reSupplyCouponBizService.parallelReSupplyCoupon(uIds, "1");
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("done in " + duration + "msecs");
        failedUIDs.stream().forEach(System.out::println);
    }

    /**
     * 测试 异步 劵补发操作 每一个UID之间都是异步的 基于JDK 8的CompletableFuture接口
     *
     * 8个UID
     * done in 610msecs
     *
     * 9个UID
     * done in 611msecs
     *
     * 125个UID
     * done in 5429msecs
     */
    @Test
    public void testAsyncCompletableFutureReSupplyCoupon() {
        long start = System.nanoTime();
        List<String> failedUIDs = reSupplyCouponBizService.asyncCompletableFutureReSupplyCoupon(uIds, "1");
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("done in " + duration + "msecs");
        failedUIDs.stream().forEach(System.out::println);
    }

    /**
     * Java虚拟机支持的最大线程数
     *
     * 8线程
     */
    @Test
    public void testThreadCount() {
        System.out.println(Runtime.getRuntime().availableProcessors());
    }

    /**
     * 测试 异步 劵补发操作 定制CompletableFuture接口的执行器
     *
     * 125个UID
     * done in 369msecs
     */
    @Test
    public void testAsyncCompletableFutureCustomExecutorReSupplyCoupon() {
        long start = System.nanoTime();
        List<String> failedUIDs = reSupplyCouponBizService.asyncCompletableFutureCustomExecutorReSupplyCoupon(uIds, "1");
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("done in " + duration + "msecs");
        failedUIDs.stream().forEach(System.out::println);
    }
}
