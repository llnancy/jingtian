package io.github.llnancy.jingtian.javase.java10;

/**
 * javah 用于生成 c 语言的头文件。从 java8 开始，javah 的功能已经集成到 javac 中，所以删除 javah 工具。
 * javac -h /path/ JavaH.java
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK10 2022/2/15
 */
public class JavaH {
    public native void sayHello();
}
