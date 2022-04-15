package com.sunchaser.sparrow.javase.java11;

import java.util.stream.Stream;

/**
 * java.lang.String新增多个字符串处理方法
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK11 2022/2/17
 */
public class StringMethod {
    public static void main(String[] args) {
        String str1 = " \t \n \t";
        boolean blank = str1.isBlank();
        // true
        System.out.println("isBlank()==========>" + blank);

        String str2 = " java 666! ";
        String strip = str2.strip();
        // "java 666!"
        System.out.println("strip()============>" + strip);

        String stripTrailing = str2.stripTrailing();
        // " java 666!"
        System.out.println("stripTrailing()====>" + stripTrailing);

        String stripLeading = str2.stripLeading();
        // "java 666! "
        System.out.println("stripLeading()=====>" + stripLeading);

        String str3 = "java";
        String repeat = str3.repeat(3);
        // "javajavajava"
        System.out.println("repeat(count)======>" + repeat);

        String str4 = " j \n a \t v \n a \n";
        Stream<String> lines = str4.lines();
        lines.forEach(line -> System.out.println("lines()============>" + line));
    }
}
