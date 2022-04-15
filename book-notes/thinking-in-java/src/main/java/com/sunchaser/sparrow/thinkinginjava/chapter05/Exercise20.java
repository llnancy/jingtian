package com.sunchaser.sparrow.thinkinginjava.chapter05;

import java.util.Arrays;

/**
 * 练习20：创建一个使用可变参数列表而不是普通的main()语法的main()。
 * 打印所产生的args数组的所有元素，并用各种不同数量的命令行参数来测试它。
 * @author sunchaser
 * @since JDK8 2020/2/2
 */
public class Exercise20 {
    public static void main(String ... args) {
        for (String s : args) {
            System.out.println(s);
        }
        System.out.println(Arrays.toString(args));
    }
}
