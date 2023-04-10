package io.github.llnancy.jingtian.javase.concurrency;

import lombok.extern.slf4j.Slf4j;

/**
 * stop thread
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/9/27
 */
@Slf4j
public class StopThread implements Runnable {

    @Override
    public void run() {
        int count = 0;
        while (!Thread.currentThread().isInterrupted() && count < 1000) {
            LOGGER.info("count = {}.", count++);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new StopThread());
        thread.start();
        Thread.sleep(10L);
        thread.interrupt();
    }
}
