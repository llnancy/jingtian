package com.sunchaser.thinkinginjava.chapter05;

import java.util.Arrays;

/**
 * @author sunchaser
 * @date 2020/2/2
 * @description
 * 练习17：创建一个类，它有一个接受一个String参数的构造器。在构造阶段，打印该参数。
 * 创建一个该类的对象引用数组，但是不实际去创建对象赋值给该数组。
 * 在运行程序时，请注意来自对该构造器的调用中的初始化消息是否打印了出来。
 *
 * 练习18：通过创建对象赋值给引用数组，从而完成前一个练习。
 * @since 1.0
 */
public class Exercise17_18 {
    public Exercise17_18(String param) {
        System.out.println("构造器中的参数为：" + param);
    }

    public static void main(String[] args) {
        Exercise17_18[] exercise1718s = new Exercise17_18[4];
        for (int i = 0; i < exercise1718s.length; i++) {
            exercise1718s[i] = new Exercise17_18("param" + i);
        }
        System.out.println(Arrays.toString(exercise1718s));
    }
}
