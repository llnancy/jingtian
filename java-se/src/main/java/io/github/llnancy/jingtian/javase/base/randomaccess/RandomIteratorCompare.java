package io.github.llnancy.jingtian.javase.base.randomaccess;

import java.util.*;

/**
 * 随机访问和迭代器访问效率比较
 *
 * @author sunchaser
 * @since JDK8 2020/4/26
 */
public class RandomIteratorCompare {
    public static void main(String[] args) {
        // init ArrayList data
        List<String> arrayList = new ArrayList<>();
        for (int i = 0; i < 10000000; i++) {
            arrayList.add(String.valueOf(i));
        }
        // random access
        long arrayListRandomStartTime = System.currentTimeMillis();
        for (int i = 0,size = arrayList.size(); i < size; i++) {
            arrayList.get(i);
        }
        long arrayListRandomEndTime = System.currentTimeMillis();
        System.out.println("ArrayList random access:" + (arrayListRandomEndTime - arrayListRandomStartTime));
        // sequential access
        long arrayListSequentialStartTime = System.currentTimeMillis();
        Iterator<String> arrayListIterator = arrayList.iterator();
        while (arrayListIterator.hasNext()) {
            arrayListIterator.next();
        }
        long arrayListSequentialEndTime = System.currentTimeMillis();
        System.out.println("sequential access:" + (arrayListSequentialEndTime - arrayListSequentialStartTime));

        // init LinkedList data
        List<String> linkedList = new LinkedList<>();
        for (int i = 0; i < 100000; i++) {
            linkedList.add(String.valueOf(i));
        }
        // random access
        long linkedListRandomStartTime = System.currentTimeMillis();
        for (int i = 0,size = linkedList.size(); i < size; i++) {
            linkedList.get(i);
        }
        long linkedListRandomEndTime = System.currentTimeMillis();
        System.out.println("random access:" + (linkedListRandomEndTime - linkedListRandomStartTime));
        // sequential access
        long linkedListSequentialStartTime = System.currentTimeMillis();
        Iterator<String> linkedListIterator = arrayList.iterator();
        while (linkedListIterator.hasNext()) {
            linkedListIterator.next();
        }
        long linkedListSequentialEndTime = System.currentTimeMillis();
        System.out.println("sequential access:" + (linkedListSequentialEndTime - linkedListSequentialStartTime));
    }
}
