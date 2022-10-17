package com.sunchaser.sparrow.javase.concurrency;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 两阶段终止模式：在一个线程 T1 中"优雅"停止另一个线程 T2。
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/10/17
 */
@Slf4j
public class TwoPhaseTermination {

    public static void main(String[] args) throws InterruptedException {
        // useInterrupt();
        useVolatile();
    }

    private static void useVolatile() throws InterruptedException {
        VolatileTwoPhaseTermination vt = new VolatileTwoPhaseTermination();
        vt.start();
        TimeUnit.MILLISECONDS.sleep(3500L);
        vt.stop();
    }

    private static void useInterrupt() throws InterruptedException {
        InterruptTwoPhaseTermination it = new InterruptTwoPhaseTermination();
        it.start();
        TimeUnit.MILLISECONDS.sleep(3500L);
        it.stop();
    }
}

/**
 * 基于 interrupt 状态实现两阶段终止模式
 */
@Slf4j
class InterruptTwoPhaseTermination {

    private Thread monitor;

    /**
     * 启动 monitor 线程
     */
    public void start() {
        monitor = new Thread(() -> {
            while (true) {
                Thread currentThread = Thread.currentThread();
                if (currentThread.isInterrupted()) {
                    LOGGER.info("执行终止前的资源清理操作");
                    break;
                }
                try {
                    TimeUnit.SECONDS.sleep(1L);
                    LOGGER.info("执行监控记录");
                } catch (InterruptedException e) {
                    LOGGER.error("{} start error.", Thread.currentThread().getName(), e);
                    // 重新设置打断标记
                    currentThread.interrupt();
                }
            }
        }, "interrupt-tpt-thread");
        monitor.start();
    }

    /**
     * 停止 monitor 线程
     */
    public void stop() {
        monitor.interrupt();
    }
}

/**
 * 基于 volatile 变量实现两阶段终止模式
 */
@Slf4j
class VolatileTwoPhaseTermination {

    private Thread monitor;

    private volatile boolean stop = false;

    /**
     * 启动 monitor 线程
     */
    public void start() {
        monitor = new Thread(() -> {
            while (true) {
                if (stop) {
                    LOGGER.info("执行终止前的资源清理操作");
                    break;
                }
                try {
                    TimeUnit.SECONDS.sleep(1L);
                    LOGGER.info("执行监控记录");
                } catch (InterruptedException e) {
                    LOGGER.error("{} start error.", Thread.currentThread().getName(), e);
                }
            }
        }, "volatile-tpt-thread");
        monitor.start();
    }

    /**
     * 停止 monitor 线程
     */
    public void stop() {
        stop = true;
        monitor.interrupt();
    }
}
