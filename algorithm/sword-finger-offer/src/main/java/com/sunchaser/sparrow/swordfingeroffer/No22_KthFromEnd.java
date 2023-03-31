package com.sunchaser.sparrow.swordfingeroffer;

import io.github.llnancy.algorithm.common.SinglyLinkedListNode;

/**
 * 剑指 Offer 22. 链表中倒数第k个节点
 *
 * https://leetcode-cn.com/problems/lian-biao-zhong-dao-shu-di-kge-jie-dian-lcof/
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/11/18
 */
public class No22_KthFromEnd {

    public static SinglyLinkedListNode findKthFromEnd(SinglyLinkedListNode head, int k) {
        SinglyLinkedListNode p1 = head;
        SinglyLinkedListNode p2 = head;
        while (k > 1) {
            p1 = p1.next;
            k--;
        }
        while (p1.next != null) {
            p1 = p1.next;
            p2 = p2.next;
        }
        return p2;
    }
}
