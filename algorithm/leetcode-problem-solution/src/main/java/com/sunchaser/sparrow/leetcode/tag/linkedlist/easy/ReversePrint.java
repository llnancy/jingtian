package com.sunchaser.sparrow.leetcode.tag.linkedlist.easy;

import com.sunchaser.sparrow.algorithm.common.SinglyLinkedListNode;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 从尾到头打印链表
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/1/4
 */
public class ReversePrint {
    public int[] reversePrint(SinglyLinkedListNode head) {
        Deque<Integer> list = new LinkedList<>();
        while (head != null) {
            list.push(head.val);
            head = head.next;
        }
        int[] arr = new int[list.size()];
        int index = 0;
        while (!list.isEmpty()) {
            arr[index++] = list.pop();
        }
        return arr;
    }
}
