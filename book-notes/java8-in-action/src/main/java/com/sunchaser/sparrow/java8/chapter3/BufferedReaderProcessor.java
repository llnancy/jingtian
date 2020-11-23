package com.sunchaser.sparrow.java8.chapter3;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * 函数式接口
 * @author sunchaser
 * @since JDK8 2019/8/12
 */
@FunctionalInterface
public interface BufferedReaderProcessor {
    String process(BufferedReader b) throws IOException;
}
