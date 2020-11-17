package com.sunchaser.sparrow.javase.collect;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author sunchaser
 * @date 2020/5/2
 * @since 1.0
 */
public class ArrayListTest {
    public static void main(String[] args) {
        // 插入元素
        ArrayList<String> addList = new ArrayList<>();

        // 集合尾部插入
        addList.add("测试1");
        System.out.println(addList);

        // 集合指定索引位置插入
        addList.add(1,"指定位置1");
        System.out.println(addList);

        // 集合指定索引位置插入：索引位置无元素且不是尾部：索引越界。
        // addList.add(10,"指定位置2");

        // 待插入集合初始化
        ArrayList<String> toBeAddList = new ArrayList<>();
        toBeAddList.add("测试2");
        toBeAddList.add("测试3");
        toBeAddList.add("测试4");

        // 待指定索引位置插入集合初始化
        ArrayList<String> toBeAddIndexList = new ArrayList<>();
        toBeAddIndexList.add("测试5");
        toBeAddIndexList.add("测试6");

        // 将一个集合全部元素插入至当前集合末尾
        addList.addAll(toBeAddList);
        System.out.println(addList);

        // 从当前集合指定索引位置开始，将一个集合全部元素插入
        addList.addAll(1,toBeAddIndexList);
        System.out.println(addList);

        // 删除元素
        List<Integer> removeList = new ArrayList<>();
        removeList.add(1);
        removeList.add(2);
        removeList.add(6);
        removeList.add(3);
        removeList.add(4);
        removeList.add(5);
        removeList.add(6);
        removeList.add(4);
        System.out.println(removeList);

        // 删除指定索引位置元素
        removeList.remove(1);
        System.out.println(removeList);

        // 删除指定元素在集合中第一次出现位置的元素
        removeList.remove(new Integer(6));
        System.out.println(removeList);

        // 待删除元素集合
        List<Integer> beRemovedList = new ArrayList<>();
        beRemovedList.add(2);
        beRemovedList.add(3);
        beRemovedList.add(6);
        System.out.println(removeList);
        System.out.println(beRemovedList);

        // 从当前集合中删除指定集合中包含的所有元素
        boolean b = removeList.removeAll(beRemovedList);
        System.out.println(b);
        System.out.println(removeList);

        // 删除全部元素
        removeList.clear();
        System.out.println(removeList);

        // 修改元素集合初始化
        ArrayList<Integer> operatorList = new ArrayList<>();
        operatorList.add(1);
        operatorList.add(2);
        operatorList.add(3);
        operatorList.add(2);
        operatorList.add(1);
        System.out.println(operatorList);

        // 修改元素，将索引为1的元素修改为6
        operatorList.set(1,6);
        System.out.println(operatorList);

        // 查询元素
        Integer integer = operatorList.get(1);
        System.out.println(integer);

        // 克隆
        Object clone = operatorList.clone();
        System.out.println(clone);

        // size
        System.out.println(operatorList.size());

        // isEmpty
        System.out.println(operatorList.isEmpty());

        // indexOf
        System.out.println(operatorList.indexOf(1));

        // lastIndexOf
        System.out.println(operatorList.lastIndexOf(1));

        // contains
        System.out.println(operatorList.contains(3));
        System.out.println(operatorList.contains(4));

        // 迭代器设计模式
        List<Integer> iteratorList = new ArrayList<>();
        iteratorList.add(1);
        iteratorList.add(2);
        iteratorList.add(3);
        iteratorList.add(4);
        iteratorList.add(5);
        iteratorList.add(6);
        Iterator<Integer> iterator = iteratorList.iterator();
        while (iterator.hasNext()) {
            Integer next = iterator.next();
            System.out.println(next);
        }

        // 普通fori循环遍历
        for (int i = 0,size = iteratorList.size(); i < size; i++) {
            System.out.println(iteratorList.get(i));
        }

        // forEach遍历，底层实现为迭代器
        for (Integer i : iteratorList) {
            System.out.println(i);
        }
    }
}
