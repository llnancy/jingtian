package com.sunchaser.sparrow.leetcode.easy;

import com.sunchaser.sparrow.algorithm.common.SinglyLinkedListNode;

/**
 * 160. 相交链表
 *
 * 链接：https://leetcode-cn.com/problems/intersection-of-two-linked-lists
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/11/14
 */
public class IntersectionOfTwoLinkedList {

    /**
     * A+B = B+A
     */
    public static SinglyLinkedListNode getIntersectionNode(SinglyLinkedListNode headA, SinglyLinkedListNode headB) {
        SinglyLinkedListNode p1 = headA;
        SinglyLinkedListNode p2 = headB;
        while (p1 != p2) {
            if (p1 == null) {
                p1 = headB;
            } else {
                p1 = p1.next;
            }
            if (p2 == null) {
                p2 = headA;
            } else {
                p2 = p2.next;
            }
        }
        return p1;
    }
}
