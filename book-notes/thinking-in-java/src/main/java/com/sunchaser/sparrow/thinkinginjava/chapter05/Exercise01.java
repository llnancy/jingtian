package com.sunchaser.sparrow.thinkinginjava.chapter05;

/**
 * 练习一：创建一个类，它包含一个未初始化的String引用，验证该引用被Java初始化成了null。
 * @author sunchaser
 * @since JDK8 2020/1/8
 */
public class Exercise01 {
    private String str;

    public static void main(String[] args) {
        Exercise01 exercise01 = new Exercise01();
        // print null
        System.out.println(exercise01.str);
    }
}
