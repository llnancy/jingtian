package com.sunchaser.sparrow.javase.java10;

import java.util.Optional;

/**
 * java10中java.util.Optional类新增orElseThrow()无参方法
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK10 2022/2/17
 */
public class OptionalMethod {
    public static void main(String[] args) {
        Optional<Object> empty = Optional.empty();
        Optional<String> op = Optional.of("java");
        // Exception in thread "main" java.util.NoSuchElementException: No value present
        // Object orElseThrow = empty.orElseThrow();
        String elseThrow = op.orElseThrow();
        System.out.println(elseThrow);// java
    }
}
