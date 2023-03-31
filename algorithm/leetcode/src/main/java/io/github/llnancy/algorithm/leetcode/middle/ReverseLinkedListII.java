package io.github.llnancy.algorithm.leetcode.middle;

import io.github.llnancy.algorithm.common.SinglyLinkedListNode;
import io.github.llnancy.algorithm.common.util.LinkedListUtils;

/**
 * 92. 反转链表 II
 * <p>
 * https://leetcode-cn.com/problems/reverse-linked-list-ii/
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/11/16
 */
public class ReverseLinkedListII {

    public static void main(String[] args) {
        SinglyLinkedListNode head = LinkedListUtils.generateSinglyLinkedList();
        LinkedListUtils.printLink(head);
        SinglyLinkedListNode newHead = reverseBetween(head, 1, 3);
        LinkedListUtils.printLink(newHead);
    }

    public static SinglyLinkedListNode reverseBetween(SinglyLinkedListNode head, int left, int right) {
        if (left == 1) {
            // 反正链表的前n个元素
            return reverseN(head, right);
        }
        head.next = reverseBetween(head.next, left - 1, right - 1);
        return head;
    }

    static SinglyLinkedListNode nodeN;

    private static SinglyLinkedListNode reverseN(SinglyLinkedListNode head, int n) {
        if (n == 1) {
            // 记录第n+1个元素
            nodeN = head.next;
            // 反转前1个元素
            return head;
        }
        SinglyLinkedListNode newHead = reverseN(head.next, n - 1);
        // 反转head
        head.next.next = head;
        head.next = nodeN;
        return newHead;
    }
}
