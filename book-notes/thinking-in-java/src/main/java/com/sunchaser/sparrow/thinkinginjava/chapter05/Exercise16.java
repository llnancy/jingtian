package com.sunchaser.sparrow.thinkinginjava.chapter05;

/**
 * @author sunchaser
 * @date 2020/2/2
 * @description
 * 练习16：创建一个String对象数组，并为每一个元素都赋值一个String。用for循环来打印该数组。
 * @since 1.0
 */
public class Exercise16 {
    public static void main(String[] args) {
        String[] strArray = {"aaa","bbb","ccc","ddd","eee","fff"};
        for (int i = 0; i < strArray.length; i++) {
            System.out.println(strArray[i]);
        }
    }
}
