package com.sunchaser.sparrow.javase.concurrency;

import lombok.extern.slf4j.Slf4j;

/**
 * volatile stop
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/9/27
 */
@Slf4j
public class VolatileStop implements Runnable {

    private volatile boolean canceled = false;

    @Override
    public void run() {
        int count = 0;
        while (!canceled && count < 100000) {
            LOGGER.info("count = {}.", count++);
            try {
                Thread.sleep(1L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        VolatileStop vs = new VolatileStop();
        Thread thread = new Thread(vs);
        thread.start();
        Thread.sleep(1000L);
        // 休眠一秒后将volatile变量修改为true
        vs.canceled = true;
    }
}
