package com.sunchaser.sparrow.javase.collect;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 集合的交差并集
 * @author sunchaser
 * @date 2020/4/22
 * @since 1.0
 */
public class CrossAndMerge {
    public static void main(String[] args) {
        List<Long> list1 = Lists.newArrayList(1L, 3L, 5L, 8L);
        List<Long> list2 = Lists.newArrayList(3L, 5L, 6L, 7L);
        Set<Long> collect1 = new HashSet<>(list1);
        Set<Long> collect2 = new HashSet<>(list2);
        // 交集
        Sets.SetView<Long> intersection = Sets.intersection(collect1, collect2);
        System.out.println(intersection);
        // 差集：collect1集合中除去交集以外的部分
        Sets.SetView<Long> difference1 = Sets.difference(collect1, collect2);
        System.out.println(difference1);
        // 差集：collect2集合中除去交集以外的部分
        Sets.SetView<Long> difference2 = Sets.difference(collect2, collect1);
        System.out.println(difference2);
        // 并集
        Sets.SetView<Long> union = Sets.union(collect1, collect2);
        System.out.println(union);
    }
}
