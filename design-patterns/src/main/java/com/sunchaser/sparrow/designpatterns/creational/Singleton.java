package com.sunchaser.sparrow.designpatterns.creational;

/**
 * @author sunchaser
 * @since JDK8 2020/1/2
 */
public class Singleton {
    private Singleton() {
    }
    private static Singleton instance;

    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
