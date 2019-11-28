package com.sunchaser.java8.chapter3;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * @author sunchaser
 * @date 2019/8/12
 * @description 函数式接口
 */
@FunctionalInterface
public interface BufferedReaderProcessor {
    String process(BufferedReader b) throws IOException;
}
