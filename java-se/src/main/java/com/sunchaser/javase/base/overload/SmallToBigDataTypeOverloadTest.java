package com.sunchaser.javase.base.overload;

import org.junit.Test;

/**
 * @author sunchaser
 * @date 2020/1/8
 * @description
 * 基本数据类型重载的测试：基本数据类型范围较小的会被自动类型转换（隐式转换）成范围较大的类型。
 *
 * 基本数据类型（除去boolean）范围如下：
 * '[' 和 ']' 表示闭区间。
 *
 * byte   |  8 bits | [-128, +127]
 * char   | 16 bits | ['\u0000', '\uFFFF']
 * short  | 16 bits | [-2^15, +2^15 - 1]
 * int    | 32 bits | [-2^31, +2^31 - 1]
 * float  | 32 bits | IEEE754范围
 * double | 64 bits | IEEE754范围
 * long   | 64 bits | [-2^63, +2^63 - 1]
 *
 * @since 1.0
 */
public class SmallToBigDataTypeOverloadTest {

    /**
     * print:
     *
     * const: 6
     * foo1(int x)
     * foo2(int x)
     * foo3(int x)
     * foo4(int x)
     * foo5(long x)
     * foo6(float x)
     * foo7(double x)
     *
     * 常量值6被当做int值进行处理。如果没有int类型的重载方法，则自动类型转换成“较大”的类型。
     */
    @Test
    public void testConstValue() {
        System.out.println("const: 6");
        foo1(6);
        foo2(6);
        foo3(6);
        foo4(6);
        foo5(6);
        foo6(6);
        foo7(6);
    }

    /**
     * print:
     *
     * char: x
     * foo1(char x)
     * foo2(int x)
     * foo3(int x)
     * foo4(int x)
     * foo5(long x)
     * foo6(float x)
     * foo7(double x)
     *
     * 除foo1外，char类型的'x'被自动类型转换成了“较大”的类型。
     * 在foo2、foo3和foo4中，char类型被直接转换成了int类型。
     */
    @Test
    public void testChar() {
        char c = 'x';
        System.out.println("char: " + c);
        foo1(c);
        foo2(c);
        foo3(c);
        foo4(c);
        foo5(c);
        foo6(c);
        foo7(c);
    }

    /**
     * print:
     *
     * byte: 1
     * foo1(byte x)
     * foo2(byte x)
     * foo3(short x)
     * foo4(int x)
     * foo5(long x)
     * foo6(float x)
     * foo7(double x)
     *
     * 除foo1和foo2外，byte类型的1被自动类型转换成了“较大”的类型。
     */
    @Test
    public void testByte() {
        byte b = 1;
        System.out.println("byte: " + b);
        foo1(b);
        foo2(b);
        foo3(b);
        foo4(b);
        foo5(b);
        foo6(b);
        foo7(b);
    }

    /**
     * print:
     *
     * short: 1
     * foo1(short x)
     * foo2(short x)
     * foo3(short x)
     * foo4(int x)
     * foo5(long x)
     * foo6(float x)
     * foo7(double x)
     *
     * 除foo1、foo2和foo3外，short类型的1被自动类型转换成了“较大”的类型。
     */
    @Test
    public void testShort() {
        short s = 1;
        System.out.println("short: " + s);
        foo1(s);
        foo2(s);
        foo3(s);
        foo4(s);
        foo5(s);
        foo6(s);
        foo7(s);
    }

    /**
     * print:
     *
     * int: 1
     * foo1(int x)
     * foo2(int x)
     * foo3(int x)
     * foo4(int x)
     * foo5(long x)
     * foo6(float x)
     * foo7(double x)
     *
     * foo5、foo6和foo7方法，int类型的1都被自动类型转换成了“较大”的类型。
     */
    @Test
    public void testInt() {
        int i = 1;
        System.out.println("int: " + i);
        foo1(i);
        foo2(i);
        foo3(i);
        foo4(i);
        foo5(i);
        foo6(i);
        foo7(i);
    }

    /**
     * print:
     *
     * long: 1
     * foo1(long x)
     * foo2(long x)
     * foo3(long x)
     * foo4(long x)
     * foo5(long x)
     * foo6(float x)
     * foo7(double x)
     *
     * foo6和foo7方法，long类型的1L被自动类型转换成了“较大”的类型。
     */
    @Test
    public void testLong() {
        long l = 1L;
        System.out.println("long: " + l);
        foo1(l);
        foo2(l);
        foo3(l);
        foo4(l);
        foo5(l);
        foo6(l);
        foo7(l);
    }

    /**
     * print:
     *
     * float: 1.0
     * foo1(float x)
     * foo2(float x)
     * foo3(float x)
     * foo4(float x)
     * foo5(float x)
     * foo6(float x)
     * foo7(double x)
     *
     * foo7方法中，float类型的1F被自动类型转换成了“较大”的类型。
     */
    @Test
    public void testFloat() {
        float f = 1F;
        System.out.println("float: " + f);
        foo1(f);
        foo2(f);
        foo3(f);
        foo4(f);
        foo5(f);
        foo6(f);
        foo7(f);
    }

    /**
     * print:
     *
     * double: 1.0
     * foo1(double x)
     * foo2(double x)
     * foo3(double x)
     * foo4(double x)
     * foo5(double x)
     * foo6(double x)
     * foo7(double x)
     */
    @Test
    public void testDouble() {
        double d = 1D;
        System.out.println("double: " + d);
        foo1(d);
        foo2(d);
        foo3(d);
        foo4(d);
        foo5(d);
        foo6(d);
        foo7(d);
    }

    /*==========================foo1 Overload===========================*/
    void foo1(char x) {
        System.out.println("foo1(char x)");
    }
    void foo1(byte x) {
        System.out.println("foo1(byte x)");
    }
    void foo1(short x) {
        System.out.println("foo1(short x)");
    }
    void foo1(int x) {
        System.out.println("foo1(int x)");
    }
    void foo1(long x) {
        System.out.println("foo1(long x)");
    }
    void foo1(float x) {
        System.out.println("foo1(float x)");
    }
    void foo1(double x) {
        System.out.println("foo1(double x)");
    }

    /*==========================foo2 Overload===========================*/
    void foo2(byte x) {
        System.out.println("foo2(byte x)");
    }
    void foo2(short x) {
        System.out.println("foo2(short x)");
    }
    void foo2(int x) {
        System.out.println("foo2(int x)");
    }
    void foo2(long x) {
        System.out.println("foo2(long x)");
    }
    void foo2(float x) {
        System.out.println("foo2(float x)");
    }
    void foo2(double x) {
        System.out.println("foo2(double x)");
    }

    /*==========================foo3 Overload===========================*/
    void foo3(short x) {
        System.out.println("foo3(short x)");
    }
    void foo3(int x) {
        System.out.println("foo3(int x)");
    }
    void foo3(long x) {
        System.out.println("foo3(long x)");
    }
    void foo3(float x) {
        System.out.println("foo3(float x)");
    }
    void foo3(double x) {
        System.out.println("foo3(double x)");
    }

    /*==========================foo4 Overload===========================*/
    void foo4(int x) {
        System.out.println("foo4(int x)");
    }
    void foo4(long x) {
        System.out.println("foo4(long x)");
    }
    void foo4(float x) {
        System.out.println("foo4(float x)");
    }
    void foo4(double x) {
        System.out.println("foo4(double x)");
    }

    /*==========================foo5 Overload===========================*/
    void foo5(long x) {
        System.out.println("foo5(long x)");
    }
    void foo5(float x) {
        System.out.println("foo5(float x)");
    }
    void foo5(double x) {
        System.out.println("foo5(double x)");
    }

    /*==========================foo6 Overload===========================*/
    void foo6(float x) {
        System.out.println("foo6(float x)");
    }
    void foo6(double x) {
        System.out.println("foo6(double x)");
    }

    /*==========================foo7 Overload===========================*/
    void foo7(double x) {
        System.out.println("foo7(double x)");
    }
}
