package com.sunchaser.sparrow.javase.java9;

import java.util.Comparator;

/**
 * 钻石操作符
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK9 2022/2/11
 */
public class DiamondOperator {
    public static void main(String[] args) {
        Comparator<Integer> comparator = new Comparator<>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return 0;
            }
        };
    }
}
