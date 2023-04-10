package io.github.llnancy.jingtian.javase.base.overload;

import org.junit.jupiter.api.Test;

/**
 * 基本数据类型重载的测试：基本数据类型范围较大的需要强制类型转换（显式转换）进行窄化处理，转换成范围较小的类型。
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
 * @author sunchaser
 * @since JDK8 2020/1/8
 *
 */
public class BigToSmallDataTypeOverloadTest {

    /**
     * print:
     *
     * double: 3.1415926
     * foo1(double x)
     * foo2(float x)
     * foo3(long x)
     * foo4(int x)
     * foo5(short x)
     * foo6(byte x)
     * foo7(char x)
     * (float) d：3.1415925  // 可看到精度已经丢失
     * (long) d：3
     * (int) d：3
     * (short) d：3
     * (byte) d：3
     * (char) d：3
     */
    @Test
    public void testDouble() {
        double d = 3.1415926;
        System.out.println("double: " + d);
        foo1(d);
        foo2((float) d);
        foo3((long) d);
        foo4((int) d);
        foo5((short) d);
        foo6((byte) d);
        foo7((char) d);
        float f = (float) d;
        System.out.println("(float) d：" + f);
        long l = (long) d;
        System.out.println("(long) d：" + l);
        int i = (int) d;
        System.out.println("(int) d：" + i);
        short s = (short) d;
        System.out.println("(short) d：" + s);
        byte b = (byte) d;
        System.out.println("(byte) d：" + b);
        char c = (char) d;
        System.out.println("(char) d：" + (c + 1 - 1)); // 查看char表示的数值
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
    void foo2(char x) {
        System.out.println("foo2(char x)");
    }
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

    /*==========================foo3 Overload===========================*/
    void foo3(char x) {
        System.out.println("foo3(char x)");
    }
    void foo3(byte x) {
        System.out.println("foo3(byte x)");
    }
    void foo3(short x) {
        System.out.println("foo3(short x)");
    }
    void foo3(int x) {
        System.out.println("foo3(int x)");
    }
    void foo3(long x) {
        System.out.println("foo3(long x)");
    }

    /*==========================foo4 Overload===========================*/
    void foo4(char x) {
        System.out.println("foo4(char x)");
    }
    void foo4(byte x) {
        System.out.println("foo4(byte x)");
    }
    void foo4(short x) {
        System.out.println("foo4(short x)");
    }
    void foo4(int x) {
        System.out.println("foo4(int x)");
    }

    /*==========================foo5 Overload===========================*/
    void foo5(char x) {
        System.out.println("foo5(char x)");
    }
    void foo5(byte x) {
        System.out.println("foo5(byte x)");
    }
    void foo5(short x) {
        System.out.println("foo5(short x)");
    }

    /*==========================foo6 Overload===========================*/
    void foo6(char x) {
        System.out.println("foo6(char x)");
    }
    void foo6(byte x) {
        System.out.println("foo6(byte x)");
    }

    /*==========================foo7 Overload===========================*/
    void foo7(char x) {
        System.out.println("foo7(char x)");
    }
}
