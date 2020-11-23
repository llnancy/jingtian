package com.sunchaser.sparrow.javase.base.thread;

/**
 * @author admin@lilu.org.cn
 * @since JDK8 2020/11/22
 */
public class MyImplRunnableThread implements Runnable {
    @Override
    public void run() {
        System.out.println("implements Runnable to create a thread.");
    }
}
