package io.github.llnancy.jingtian.javase.concurrency;

import cn.hutool.core.thread.ThreadUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * Thread#join 等待线程执行结束
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
        // multipleJoin();
        timedJoin();
    }

    private static void timedJoin() throws InterruptedException {
        Thread thread = new Thread(() -> {
            ThreadUtil.sleep(1000L);
            i = 10;
        });
        long start = System.currentTimeMillis();
        thread.start();
        // 线程执行结束会导致 join 提前结束
        // thread.join(1500L);
        // 最多等待 millis 时间
        thread.join(500L);
        long end = System.currentTimeMillis();
        LOGGER.info("i: {}, times: {}.", i, end - start);
    }

    private static void multipleJoin() throws InterruptedException {
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
        // join 等待线程执行结束
        thread.join();
        LOGGER.info("i: {}", i);
    }

}
