在日常生活中，相同的词可以表达多种不同的含义——它们被“重载了”。例如：“冬天能穿多少穿多少。夏天能穿多少穿多少。”、“中国足球谁也打不过。中国乒乓球谁也打不过。”。相同的名字但表达出了不同的含义，这就是方法重载。

## 构造器重载
我们知道类的构造器名字必须与类名完全相同，不接受任何参数的构造器叫做默认构造器，同其它方法一样，构造器也能带有形式参数，以便指定如何创建对象。这里就体现了方法重载，它们都有相同的名字，即类名，但形式参数不同。

## 区分方法重载
如果有几个名字相同的方法，Java如何进行区分呢？

请记住方法重载的概念：方法名相同，但形参列表不同。

即使形参顺序不同也足以区分两个方法。但是最好别这样做，细想一下，如果只是参数顺序不同，有什么实际的意义呢？这样的重载也会让代码难以琢磨。

## 基本数据类型的重载

### 传入的实际参数范围小于重载方法声明的形式参数范围

我们知道Java中有自动类型转换（隐式转换）的概念，基本数据类型能从一个范围“较小”的类型自动转换至一个范围“较大”的类型，此过程一旦涉及到方法重载，可能会造成一些混淆。

我们来进行一些测试。

foo1方法包含char/byte/short/int/long/float/double这7种基本数据类型的重载；

foo2方法包含byte/short/int/long/float/double这6种基本数据类型的重载；

foo3方法包含short/int/long/float/double这5种基本数据类型的重载；

foo4方法包含int/long/float/double这4种基本数据类型的重载；

foo5方法包含long/float/double这3种基本数据类型的重载；

foo6方法包含float/double这2种基本数据类型的重载；

foo7方法只有一个double类型参数的方法；

分别使用常量值6、char类型的'x'、byte类型的1、short类型的1、int类型的1、long类型的1、float类型的1和double类型的1分别调用foo系列的方法，观察输出结果。完整代码如下：

```java
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
```

我们会发现常量值6被foo1/foo2/foo3/foo4方法当做int值进行处理，foo5/foo6/foo7没有int类型的重载方法，被自动类型转换成了“较大”的类型（long/float/double）。

对于char类型的'x'，foo1方法包含对应类型的重载，剩余6种方法不包含char类型的重载，但foo2/foo3/foo4直接将char类型自动类型转换成了int类型。foo5/foo6/foo7分别将char类型隐式转换成了long/float/double类型。

> char类型是不会被自动类型转换成byte/short类型的。其原因可查看这篇文章：[传送门](./why-char-cannot-be-converted-to-byte-or-short.md)

对于byte类型的1，foo1/foo2方法包含对应类型的重载；剩余5种方法不包含char类型的重载，被分别隐式转换成了short/int/long/float/double类型。

对于short类型的1，foo1/foo2/foo3方法包含对应类型的重载；剩余4种方法不包含short类型的重载，被分别隐式转换成了int/long/float/double类型。

对于int类型的1，foo1/foo2/foo3/foo4方法包含对应类型的重载；foo5/foo6/foo7不包含int类型的重载，被分别隐式转换成了long/float/double类型。

对于long类型的1，foo1/foo2/foo3/foo4/foo5方法包含对应类型的重载；foo6/foo7不包含long类型的重载，被分别隐式转换成了float/double类型。

对应float类型的1，foo7不包含float类型的重载，被隐式转换成了double类型；而其它6种方法包含对应类型的重载。

对于double类型的1,7种方法都有对应类型的重载。

数值基本类型隐式转换图如下：

![basic-data-type-conversion.png](https://i.loli.net/2020/02/15/OeS472iBMyCUz9n.png)

实线箭头无精度丢失，虚线箭头可能丢失精度。

### 传入的实际参数范围大于重载方法声明的形式参数范围

反过来，如果传入的实际参数范围大于重载方法声明的形式参数，会是什么情况呢？我们再来测试一下。

foo1方法包含char/byte/short/int/long/float/double这7种基本数据类型的重载；

foo2方法包含char/byte/short/int/long/float这6种基本数据类型的重载；

foo3方法包含char/byte/short/int/long这5种基本数据类型的重载；

foo4方法包含char/byte/short/int这4种基本数据类型的重载；

foo5方法包含char/byte/short这3种基本数据类型的重载；

foo6方法包含char/byte这2种基本数据类型的重载；

foo7方法只有一个char类型参数的方法；

使用double类型的3.1415926分别调用foo系列的方法。我们可以看到编译器提示了错误：`Cannot resolve method 'foo2(double)'`等。除了foo1方法外，其余foo系列的方法都不包含double类型的重载，编译器也不会隐式将double类型自动转换成其它基本类型，其原因在于double类型的取值范围（二进制位数）是最大的，如果将double类型的数据隐式转换成其它基本类型，可能会出现精度丢失的问题，这是有风险的，编译器不会自动帮我们做。

我们需要强制类型转换（显式转换）进行窄化处理，这样做可能会丢失精度。但如果不这样做，我们无法正常进行方法的调用。

完整代码如下：
```java
package com.sunchaser.javase.base.overload;

import org.junit.Test;

/**
 * @author sunchaser
 * @date 2020/1/8
 * @description
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
 * @since 1.0
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
```

从输出结果中可看到：double类型的3.1415926强制转换成float时变成了3.1415925，精度已经丢失；而强制转换成long/int/short/byte/char时浮点部分直接被舍弃。

## 返回值能区分重载方法吗？

例如有下面两个方法：
```java
void foo() {}

int foo() {return -1;}
```

我们人用肉眼去看方法的声明很容易分辨这两个同名方法的不同之处。但是当我们去调用foo方法时，如果我们的调用方式是：`int x = foo()`，那么的确可以明确我们调用的是`int foo() {return -1;}`；但有时候我们并不在意返回值（只是调用方法去执行某个操作），调用方式为：`foo()`，这时候Java如何才能判断调用的是哪一个`foo()`呢？程序员又该如何理解这种代码呢？因此，返回值是不能区分方法重载的。

## int和Integer的形参是重载方法吗？

例如有下面两个方法：
```java
    void foo(int x) {
        System.out.println("int");
    }

    void foo(Integer y) {
        System.out.println("Integer");
    }
```

它们是重载方法吗？

即使int和Integer之间存在自动装箱/拆箱操作，但它们仍是重载的方法。

我们传递int类型的变量给foo，编译器并不会进行装箱操作，因为有对应的int类型重载方法；相应地，我们传递Integer类型的变量给foo，编译器不会进行拆箱操作，而是调用对应Integer类型的重载方法。

我们传递字面量1给foo方法，编译器会默认当成基本类型int调用foo。

测试代码如下：
```java
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
```

以上就是方法重载Overload的全部内容，完整示例代码地址见：[传送门](https://github.com/sunchaser-lilu/gold-road-to-Java/tree/master/JavaSE/src/main/java/com/sunchaser/javase/base/overload)