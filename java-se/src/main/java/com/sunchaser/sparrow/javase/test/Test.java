package com.sunchaser.sparrow.javase.test;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.StringJoiner;

/**
 * @author sunchaser
 * @since JDK8 2019/12/5
 *
 */
public class Test {
    public static void main(String[] args) {
        List<String> str = Lists.newArrayList("aaa", "bbb", "ccc");
        StringJoiner sj = new StringJoiner("','", "'", "'");
        str.forEach(sj::add);
        System.out.println(sj.toString());
    }
}
