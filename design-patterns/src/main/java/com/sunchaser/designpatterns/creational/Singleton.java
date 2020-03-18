package com.sunchaser.designpatterns.creational;

/**
 * @author sunchaser
 * @date 2020/1/2
 * @description
 * @since 1.0
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
