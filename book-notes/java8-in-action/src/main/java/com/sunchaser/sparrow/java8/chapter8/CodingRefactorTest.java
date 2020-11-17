package com.sunchaser.sparrow.java8.chapter8;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author sunchaser
 * @date 2019/8/23
 * @description 代码重构
 */
public class CodingRefactorTest {

    /**
     * 测试从匿名内部类到Lambda表达式
     *
     * 匿名类和Lambda表达式中的this含义是不同的。
     * 在匿名类中，this指代的是匿名类本身。但是在Lambda中，this指代的是包含Lambda表达式的类。
     */
    public void testAnonymityToLambda() {
        int a = 10;
        Runnable r1 = () -> {
            // 再次定义a变量会报错
            // int a = 1;
            System.out.println(a);
        };
        Runnable r2 = new Runnable() {
            @Override
            public void run() {
                int a = 2;
                System.out.println(a);
            }
        };
    }

    /**
     * 使用Stream接口提供的peek方法可查看流水线中每个操作的中间状态，方便调试排错。
     */
    public static void testPeek() {
        List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
        nums.stream()
                .peek(x -> System.out.println("before map" + x))
                .map(x -> x + 17)
                .peek(x -> System.out.println("before filter" + x))
                .filter(x -> x % 2 == 0)
                .peek(x -> System.out.println("before limit" + x))
                .limit(3)
                .peek(x -> System.out.println("before collect" + x))
                .collect(Collectors.toList())
                .forEach(System.out::println);

    }

    // for test
    public static void main(String[] args) {
        CodingRefactorTest.testPeek();
    }
}
