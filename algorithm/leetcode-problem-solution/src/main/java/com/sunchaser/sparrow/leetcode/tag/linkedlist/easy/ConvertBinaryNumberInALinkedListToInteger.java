package com.sunchaser.sparrow.leetcode.tag.linkedlist.easy;

import com.sunchaser.sparrow.algorithm.common.SinglyLinkedListNode;
import com.sunchaser.sparrow.algorithm.common.util.LinkedListUtils;

/**
 * 反转二进制链表为十进制
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/1/4
 */
public class ConvertBinaryNumberInALinkedListToInteger {

    public static void main(String[] args) {
        // 1 -> 0 -> 1 -> 0 -> 1 -> 0
        SinglyLinkedListNode binaryNumberLinkedList = LinkedListUtils.generateBinaryNumberLinkedList();
        int decimalValue = getDecimalValue(binaryNumberLinkedList);
        System.out.println(decimalValue);
        System.out.println(Math.pow(2,1));
    }

    static int index = 0;
    public static int getDecimalValue(SinglyLinkedListNode head) {
        if (head.next == null) {
            index++;
            return head.val;
        }
        int nextValue = getDecimalValue(head.next);
        // 2 + 0 + 16
        double thisValue = head.val * Math.pow(2,index++);
        double val = thisValue + nextValue;
        return (int) val;
    }
}
