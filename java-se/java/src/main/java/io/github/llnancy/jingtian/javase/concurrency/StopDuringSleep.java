package io.github.llnancy.jingtian.javase.concurrency;

import lombok.extern.slf4j.Slf4j;

/**
 * stop during sleep
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/9/27
 */
@Slf4j
public class StopDuringSleep implements Runnable {

    @Override
    public void run() {
        int count = 0;
        while (!Thread.currentThread().isInterrupted() && count < 1000) {
            try {
                LOGGER.info("count = {}.", count++);
                // 子线程休眠1000ms
                Thread.sleep(1000L);
                // 休眠状态的线程仍能感知中断信号，抛出InterruptedException异常，同时清除中断标记
            } catch (InterruptedException e) {
                // 可在catch代码块中再次标记中断
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new StopDuringSleep());
        thread.start();
        // 主线程sleep 10ms
        Thread.sleep(10L);
        // 标记线程中断，此时子线程处于休眠状态
        thread.interrupt();
    }
}
