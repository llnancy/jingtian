package com.sunchaser.sparrow.designpatterns.creational;

/**
 * @author sunchaser
 * @since JDK8 2020/1/2
 */
public class Test {
    public static void main(String[] args) {
        Singleton instance = Singleton.getInstance();
        int i = 1;
        while (true) {
            i++;
            Singleton instance1 = Singleton.getInstance();
            if (instance1 != instance) {
                System.out.println("第" + i + "次单例错误");
            }
        }
    }
}
