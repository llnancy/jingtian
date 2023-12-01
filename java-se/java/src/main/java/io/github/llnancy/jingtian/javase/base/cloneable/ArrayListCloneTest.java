package io.github.llnancy.jingtian.javase.base.cloneable;

import com.google.common.collect.Lists;

import java.util.ArrayList;

/**
 * 将一个 ArrayList 集合的数据克隆至另一个新的集合
 *
 * @author sunchaser
 * @since JDK8 2020/4/1
 */
public class ArrayListCloneTest {

    public static void main(String[] args) {
        ArrayList<String> list = Lists.newArrayList();
        list.add("Java 大法好");
        list.add("PHP 是世界上最好的语言");
        list.add("Hello World");
        Object clone = list.clone();
        System.out.println(clone == list);
        System.out.println(clone);
        System.out.println(list);
    }
}
