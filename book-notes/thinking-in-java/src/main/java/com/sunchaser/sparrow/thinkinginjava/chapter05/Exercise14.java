package com.sunchaser.sparrow.thinkinginjava.chapter05;

/**
 * @author sunchaser
 * @date 2020/2/2
 * @description
 * 练习14：编写一个类，拥有两个静态字符串域，其中一个在定义处初始化，另一个在静态块中初始化。
 * 现在，加入一个静态方法用以打印出两个字段的值。请证明它们都会在被使用之前完成初始化动作。
 * @since 1.0
 */
public class Exercise14 {
    private static String str1 = "str1初始化";
    private static String str2;
    static {
        System.out.println("静态代码块执行");
        str2 = "str2在静态代码块中初始化";
    }
    public static void print() {
        System.out.println("str1=" + str1 + "str2=" + str2);
    }

    public static void main(String[] args) {
        Exercise14.print();
    }
}
