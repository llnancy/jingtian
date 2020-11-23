package com.sunchaser.sparrow.javase.base.thread;

/**
 * @author admin@lilu.org.cn
 * @since JDK8 2020/11/22
 */
public class ThreadTest {
    public static void main(String[] args) {
        new MyExtendsThread().start();
        new Thread(new MyImplRunnableThread()).start();
    }
}
