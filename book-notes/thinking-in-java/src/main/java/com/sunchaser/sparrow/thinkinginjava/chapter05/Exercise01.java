package com.sunchaser.sparrow.thinkinginjava.chapter05;

/**
 * @author sunchaser
 * @date 2020/1/8
 * @description
 * 练习一：创建一个类，它包含一个未初始化的String引用，验证该引用被Java初始化成了null。
 * @since 1.0
 */
public class Exercise01 {
    private String str;

    public static void main(String[] args) {
        Exercise01 exercise01 = new Exercise01();
        // print null
        System.out.println(exercise01.str);
    }
}
