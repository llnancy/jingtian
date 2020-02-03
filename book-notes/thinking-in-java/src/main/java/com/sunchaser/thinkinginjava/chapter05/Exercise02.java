package com.sunchaser.thinkinginjava.chapter05;

/**
 * @author sunchaser
 * @date 2020/1/8
 * @description
 * 练习2：创建一个类，它包含一个在定义时就被初始化了的String域，以及另一个通过构造器初始化的String域。
 * 这两种方式有何差异？
 *
 * 差异：
 * 通过构造器初始化的String域str2是从null值变成构造器中传入的str2的值，可传入不同的值。
 * 而在定义时就被初始化了的String域str1，在调用构造器初始化之前就已被赋值。
 * 即str1初始化在str2之前。
 * @since 1.0
 */
public class Exercise02 {
    private String str1 = "pre-init";
    private String str2;

    public Exercise02(String str2) {
        this.str2 = str2;
    }

    public static void main(String[] args) {
        Exercise02 exercise02 = new Exercise02("init");
        System.out.println(exercise02.str1);
        System.out.println(exercise02.str2);
    }
}
