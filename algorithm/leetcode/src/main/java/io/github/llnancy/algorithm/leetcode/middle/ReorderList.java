package io.github.llnancy.algorithm.leetcode.middle;

import io.github.llnancy.algorithm.common.SinglyLinkedListNode;
import io.github.llnancy.algorithm.common.util.LinkedListUtils;

/**
 * 143. 重排链表
 * <p>
 * https://leetcode-cn.com/problems/reorder-list/
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/11/19
 */
public class ReorderList {

    public static void main(String[] args) {
        SinglyLinkedListNode node3 = new SinglyLinkedListNode(4, null);
        SinglyLinkedListNode node2 = new SinglyLinkedListNode(3, node3);
        SinglyLinkedListNode node1 = new SinglyLinkedListNode(2, node2);
        SinglyLinkedListNode head = new SinglyLinkedListNode(1, node1);
        reorderList(head);
    }

    public static void reorderList(SinglyLinkedListNode head) {
        // 找中点
        SinglyLinkedListNode midNode = findMidNode(head);
        SinglyLinkedListNode head1 = head;
        SinglyLinkedListNode head2 = midNode.next;
        midNode.next = null;
        // 反转后半段
        head2 = reverseRight(head2);
        // 合并两个链表
        merge(head1, head2);
        LinkedListUtils.printLink(head);
    }

    private static void merge(SinglyLinkedListNode head1, SinglyLinkedListNode head2) {
        SinglyLinkedListNode h1;
        SinglyLinkedListNode h2;
        while (head1 != null && head2 != null) {
            h1 = head1.next;
            h2 = head2.next;

            head1.next = head2;
            head1 = h1;

            head2.next = head1;
            head2 = h2;
        }
    }

    private static SinglyLinkedListNode reverseRight(SinglyLinkedListNode midNode) {
        SinglyLinkedListNode pre = null;
        SinglyLinkedListNode cur = midNode;
        SinglyLinkedListNode next = null;
        while (cur != null) {
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }

    private static SinglyLinkedListNode findMidNode(SinglyLinkedListNode head) {
        SinglyLinkedListNode fast = head;
        SinglyLinkedListNode slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }
}
