package com.sunchaser.sparrow.javase.base.thread;

/**
 * @author admin@lilu.org.cn
 * @since JDK8 2020/11/22
 */
public class MyExtendsThread extends Thread {
    @Override
    public void run() {
        System.out.println("extend thread to create a thread.");
    }
}
