package io.github.llnancy.jingtian.javase.concurrency;

import cn.hutool.core.thread.ThreadUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

/**
 * interrupt 给线程设置中断标记
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/10/17
 */
@Slf4j
public class Interrupt {

    public static void main(String[] args) {
        // interruptBlockedThread();
        // interruptRunningThread();
        interruptParkThread();
    }

    /**
     * 中断 park 线程
     */
    private static void interruptParkThread() {
        Thread thread = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                LockSupport.park();
                // 如果中断标记已经是 true，则 park 会失效。
                LOGGER.info("interrupt flag: {}", Thread.currentThread().isInterrupted());
            }
        });
        thread.start();
        ThreadUtil.sleep(500L);
        // 中断 park 线程不会清空中断标记
        thread.interrupt();
    }

    /**
     * 中断正在运行的线程
     */
    private static void interruptRunningThread() {
        Thread thread = new Thread(() -> {
            while (true) {
                Thread currentThread = Thread.currentThread();
                boolean interrupted = currentThread.isInterrupted();
                if (interrupted) {
                    LOGGER.info("interrupt flag is true, break loop.");
                    break;
                }
            }
        });
        thread.start();
        ThreadUtil.sleep(500L);
        // 中断正在运行的线程，不会清空中断状态
        thread.interrupt();
    }

    /**
     * 中断阻塞状态的线程
     * sleep、wait、join 方法会让线程进入阻塞状态
     */
    private static void interruptBlockedThread() {
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(1000L);// wait join
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        ThreadUtil.sleep(500L);
        // 中断正在 sleep 的线程，会清空中断状态
        thread.interrupt();
        LOGGER.info("interrupt flag: {}", thread.isInterrupted());
    }
}
