package com.sunchaser.sparrow.javase.java11;

import java.util.Optional;

/**
 * java11中java.util.Optional类新增isEmpty方法用来判断value是否为空。
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK11 2022/2/17
 */
public class OptionalMethod {
    public static void main(String[] args) {
        Optional<Object> empty = Optional.empty();
        Optional<String> op = Optional.of("java");
        boolean empty1 = empty.isEmpty(); // true
        boolean empty2 = op.isEmpty(); // false
        System.out.println(empty1);
        System.out.println(empty2);
    }
}
