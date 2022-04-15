package com.sunchaser.sparrow.javase.java10;

/**
 * javah用于生成c语言的头文件。从java8开始，javah的功能已经集成到javac中，所以删除javah工具。
 * javac -h /path/ JavaH.java
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK10 2022/2/15
 */
public class JavaH {
    public native void sayHello();
}
