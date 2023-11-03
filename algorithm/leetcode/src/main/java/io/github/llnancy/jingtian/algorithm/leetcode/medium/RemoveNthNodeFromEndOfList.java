package io.github.llnancy.jingtian.algorithm.leetcode.medium;

import io.github.llnancy.jingtian.algorithm.common.SinglyLinkedListNode;

/**
 * 19. 删除链表的倒数第 N 个结点
 * <p>
 * https://leetcode-cn.com/problems/remove-nth-node-from-end-of-list/
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/11/18
 */
public class RemoveNthNodeFromEndOfList {

    public static SinglyLinkedListNode removeNthFromEnd(SinglyLinkedListNode head, int n) {
        SinglyLinkedListNode hair = new SinglyLinkedListNode();
        hair.next = head;
        // 要删除倒数第N个节点就要找到倒数第N+1个节点
        SinglyLinkedListNode x = findKthFromEnd(hair, n + 1);
        x.next = x.next.next;
        return hair.next;
    }

    private static SinglyLinkedListNode findKthFromEnd(SinglyLinkedListNode head, int k) {
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
