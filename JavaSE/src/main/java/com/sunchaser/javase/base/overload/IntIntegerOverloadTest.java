package com.sunchaser.javase.base.overload;

import org.junit.Test;

/**
 * @author sunchaser
 * @date 2020/1/8
 * @description int和Integer的形参是重载方法吗？
 * @since 1.0
 */
public class IntIntegerOverloadTest {

    /**
     * print:
     *
     * int
     * int
     * Integer
     *
     * 字面量值1是int基本数据类型
     *
     * 是重载方法
     */
    @Test
    public void testIntInteger() {
        foo(1);
        int x = 1;
        foo(x);
        Integer y = 1;
        foo(y);
    }

    void foo(int x) {
        System.out.println("int");
    }

    void foo(Integer y) {
        System.out.println("Integer");
    }
}
