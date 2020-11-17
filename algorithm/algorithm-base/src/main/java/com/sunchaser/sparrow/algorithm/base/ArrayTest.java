package com.sunchaser.sparrow.algorithm.base;

import java.util.ArrayList;
import java.util.List;

/**
 * 数组：连续内存空间。
 * 普通数组：创建时就固定了容量，无法在后续操作中进行元素添加。
 * 动态数组：ArrayList，可动态进行容量的扩充。需要保证内存的连续性。
 * @author sunchaser
 * @date 2020/6/2
 * @since 1.0
 */
public class ArrayTest {
    public static void main(String[] args) {
        // 数组在创建时就固定了容量，无法在后续进行添加元素。
        String[] arr = new String[] {"a","b","c","d"};

        // 动态容量数组：ArrayList
        // 创建ArrayList对象，初始化一个空数组
        List<String> list = new ArrayList<>();
        // 第一次添加元素，数组扩容，容量为10
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.add("e");
        list.add("f");
        list.add("g");
        list.add("h");
        list.add("i");
        list.add("j");
        // 添加第11个元素，容量不足，再次扩容，为旧容量的1.5倍：15
        list.add("k");
        System.out.println(list);
    }
}
