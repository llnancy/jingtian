package io.github.llnancy.jingtian.javase.concurrency;

import lombok.extern.slf4j.Slf4j;

/**
 * Java 线程状态
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/10/17
 */
@Slf4j
public class ThreadState {

    public static void main(String[] args) throws InterruptedException {
        // NEW
        Thread t1 = new Thread(() -> {
            LOGGER.info("{} running.", Thread.currentThread().getName());
        }, "t1");

        // RUNNABLE
        Thread t2 = new Thread(() -> {
            while (true) {
                // 可能分到了时间片，也可能没有
            }
        }, "t2");
        t2.start();

        // TERMINATED
        Thread t3 = new Thread(() -> {
            LOGGER.info("{} running.", Thread.currentThread().getName());
        }, "t3");
        t3.start();

        // TIMED_WAITING
        Thread t4 = new Thread(() -> {
            synchronized (ThreadState.class) {
                try {
                    Thread.sleep(10000000L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "t4");
        t4.start();

        // WAITING
        Thread t5 = new Thread(() -> {
            try {
                t2.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "t5");
        t5.start();

        // BLOCKED
        Thread t6 = new Thread(() -> {
            // t4 线程先执行，锁被 t4 线程拿到
            synchronized (ThreadState.class) {
                try {
                    Thread.sleep(10000000L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "t6");
        t6.start();

        Thread.sleep(500L);
        LOGGER.info("t1 state is {}.", t1.getState());
        LOGGER.info("t2 state is {}.", t2.getState());
        LOGGER.info("t3 state is {}.", t3.getState());
        LOGGER.info("t4 state is {}.", t4.getState());
        LOGGER.info("t5 state is {}.", t5.getState());
        LOGGER.info("t6 state is {}.", t6.getState());
    }
}
