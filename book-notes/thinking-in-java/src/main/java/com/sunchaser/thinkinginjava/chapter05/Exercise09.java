package com.sunchaser.thinkinginjava.chapter05;

/**
 * @author sunchaser
 * @date 2020/1/10
 * @description
 * 练习9：编写具有两个（重载）构造器的类，并在第一个构造器中通过this调用第二个构造器。
 * @since 1.0
 */
public class Exercise09 {
    public Exercise09(int i) {
        this("=字符串=");
        System.out.println("参数类型为int的构造器被调用了...参数为：" + i);
    }

    public Exercise09(String str) {
        System.out.println("参数类型为String的构造器被调用了...参数为：" + str);
    }

    public static void main(String[] args) {
        new Exercise09(1);
    }
}