package com.sunchaser.sparrow.javase.concurrency;

import cn.hutool.core.thread.ThreadUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * Thread join
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/10/14
 */
@Slf4j
public class ThreadJoin {

    private static int i = 0;

    private static int n1 = 0;

    private static int n2 = 0;

    public static void main(String[] args) throws InterruptedException {
        // simpleJoin();
        Thread t1 = new Thread(() -> {
            ThreadUtil.sleep(1000L);
            n1 = 10;
        });
        Thread t2 = new Thread(() -> {
            ThreadUtil.sleep(2000L);
            n2 = 20;
        });
        long start = System.currentTimeMillis();
        t1.start();
        t2.start();
        // 交换两个 join 的顺序结果一致
        t1.join();
        t2.join();
        long end = System.currentTimeMillis();
        LOGGER.info("n1: {}, n2: {}, times: {}.", n1, n2, end - start);
    }

    private static void simpleJoin() throws InterruptedException {
        Thread thread = new Thread(() -> {
            ThreadUtil.sleep(1000L);
            i = 10;
        });
        thread.start();
        thread.join();
        LOGGER.info("i={}", i);
    }

}
