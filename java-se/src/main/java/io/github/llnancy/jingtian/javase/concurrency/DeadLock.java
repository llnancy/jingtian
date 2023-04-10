package io.github.llnancy.jingtian.javase.concurrency;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 死锁演示
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/7/16
 */
@Slf4j
public class DeadLock {

    private static final Object LOCK_OBJECT_1 = new Object();

    private static final Object LOCK_OBJECT_2 = new Object();

    private void deadLock() {
        new Thread(() -> {
            synchronized (LOCK_OBJECT_1) {
                LOGGER.info("Thread one get lock object 1.");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (LOCK_OBJECT_2) {
                    LOGGER.info("Thread one get lock object 2.");
                }
            }
        }).start();

        new Thread(() -> {
            synchronized (LOCK_OBJECT_2) {
                LOGGER.info("Thread two get lock object 2.");
                synchronized (LOCK_OBJECT_1) {
                    LOGGER.info("Thread two get lock object 1.");
                }
            }
        }).start();
    }

    public static void main(String[] args) {
        new DeadLock().deadLock();
    }

    /*
     * 如何避免死锁：
     * 1、避免一个线程同时获取多个锁。
     * 2、避免一个线程在锁内同时占用多个资源，尽量保证每个锁只占用一个资源。
     * 3、尝试使用定时锁，使用lock.tryLock(timeout)来代替使用内部锁机制。
     * 4、对于数据库锁，加锁和解锁必须在同一个数据库连接里，否则会出现解锁失败的情况。
     */
}
