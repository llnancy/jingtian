package com.sunchaser.javase.collect;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * @author sunchaser
 * @date 2020/5/20
 * @since 1.0
 */
public class LinkedListTest {
    public static void main(String[] args) {
        List<String> list = new LinkedList<>();
        list.add("aaa");
        list.add("bbb");
        list.add("ccc");
        Object[] obj = new Object[] {"AAA","BBB"/**,"CCC","DDD","EEE","FFF"**/};
        Object[] objects = list.toArray(obj);
        ListIterator<String> iterator1 = list.listIterator(list.size());
        while (iterator1.hasPrevious()) {
            String previous = iterator1.previous();
            iterator1.add("zzzzzzzzzzzzz");
            System.out.println(list);
        }

//        ListIterator<String> iterator2 = list.listIterator(0);
//        while (iterator2.hasNext()) {
//            String next = iterator2.next();
//            iterator2.add("xxxxxxxxx");
//        }

        System.out.println(list);
    }
}
