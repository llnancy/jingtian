package com.sunchaser.javase.base.cloneable;

import com.google.common.collect.Lists;

import java.util.ArrayList;

/**
 * 将一个ArrayList集合的数据克隆至另一个新的集合
 * @author sunchaser
 * @date 2020/4/1
 * @since 1.0
 */
public class ArrayListCloneTest {
    public static void main(String[] args) {
        ArrayList<String> list = Lists.newArrayList();
        list.add("Java大法好");
        list.add("PHP是世界上最好的语言");
        list.add("向日葵的自我修养");
        Object clone = list.clone();
        System.out.println(clone == list);
        System.out.println(clone);
        System.out.println(list);
    }
}
