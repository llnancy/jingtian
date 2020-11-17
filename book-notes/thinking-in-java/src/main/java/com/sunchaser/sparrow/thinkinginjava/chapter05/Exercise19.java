package com.sunchaser.sparrow.thinkinginjava.chapter05;

import java.util.Arrays;

/**
 * @author sunchaser
 * @date 2020/2/2
 * @description
 * 练习19：写一个类，它接受一个可变参数的String数组。
 * 验证你可以向该方法传递一个用逗号分隔的String列表，或是一个String[]。
 * @since 1.0
 */
public class Exercise19 {
    public static void foo(String ... args) {
        System.out.println(Arrays.toString(args));
    }

    public static void main(String[] args) {
        Exercise19.foo("aa","bb","cc","dd","ee");
        Exercise19.foo(new String[] {"ff","gg"});
    }
}
