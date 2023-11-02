package io.github.llnancy.jingtian.algorithm.leetcode.tag.linkedlist.easy;

import io.github.llnancy.jingtian.algorithm.common.SinglyLinkedListNode;

/**
 * 链表相交求交点
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/1/4
 */
public class IntersectionOfTwoLinkedList {
    public SinglyLinkedListNode getIntersectionNode(SinglyLinkedListNode headA,
                                                    SinglyLinkedListNode headB) {
        SinglyLinkedListNode p1 = headA;
        SinglyLinkedListNode p2 = headB;
        while (p1 != p2) {
            p1 = p1 == null ? headB : p1.next;
            p2 = p2 == null ? headA : p2.next;
        }
        return p1;
    }
}
