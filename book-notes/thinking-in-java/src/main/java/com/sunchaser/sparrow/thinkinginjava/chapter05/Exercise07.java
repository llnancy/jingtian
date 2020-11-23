package com.sunchaser.sparrow.thinkinginjava.chapter05;

/**
 * 练习7：创建一个没有构造器的类，并在main()中创建其对象，用以验证编译器是否真的自动加入了默认构造器。
 * @author sunchaser
 * @since JDK8 2020/1/8
 */
public class Exercise07 {
    public static void main(String[] args) {
        Exercise07 exercise07 = new Exercise07();
        System.out.println(exercise07);
    }
}
