package com.sunchaser.sparrow.algorithm.base.linkedlist;

import io.github.llnancy.jingtian.algorithm.common.SinglyLinkedListNode;
import io.github.llnancy.jingtian.algorithm.common.util.LinkedListUtils;

/**
 * 反转链表：递归和非递归实现
 *
 * @author sunchaser
 * @since JDK8 2020/6/3
 */
public class ReverseLink {
    public static void main(String[] args) {
        SinglyLinkedListNode head = LinkedListUtils.generateSinglyLinkedList();
        LinkedListUtils.printLink(head);
        // 非递归法反转单链表
        SinglyLinkedListNode reverse = reverse(head);
        LinkedListUtils.printLink(reverse);
        // first blood
        // SinglyLinkedListNode firstBlood = reverseRecursionImpl(head);
        // printLink(firstBlood);
        // double kill
        // SinglyLinkedListNode doubleKill = reverseN(head, 3);
        // printLink(doubleKill);
        // triple kill
        // SinglyLinkedListNode tripleKill = reverseBetweenMToN(head, 1, 3);
        // LinkedListUtils.printLink(tripleKill);
    }

    /**
     * 反转单链表递归实现
     *
     * @param head 单链表头节点
     * @return 反转后的链表头节点
     */
    public static SinglyLinkedListNode reverseRecursionImpl(SinglyLinkedListNode head) {
        if (head == null || head.next == null)
            return head;
        SinglyLinkedListNode reverseHead = reverseRecursionImpl(head.next);
        head.next.next = head;
        head.next = null;
        return reverseHead;
    }

    /**
     * 非递归法实现单链表反转
     *
     * @param head 原单链表头节点
     * @return 反转后链表头节点
     */
    public static SinglyLinkedListNode reverse(SinglyLinkedListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        // 定义三个指针：pre、current、next
        SinglyLinkedListNode current = head;
        SinglyLinkedListNode pre = null;
        SinglyLinkedListNode next;
        while (current.next != null) {
            // 记录current的next
            next = current.next;
            // 反转current
            current.next = pre;
            // pre右移
            pre = current;
            // current右移
            current = next;
        }
        return current;
    }

    static SinglyLinkedListNode nNext = null;

    /**
     * 反转链表的前n个元素
     *
     * @param head 原链表头节点
     * @param n    前n个元素
     * @return 反转后的链表头节点
     */
    public static SinglyLinkedListNode reverseN(SinglyLinkedListNode head, int n) {
        if (n == 1) {
            nNext = head.next;
            return head;
        }
        SinglyLinkedListNode reverseHead = reverseN(head.next, n - 1);
        head.next.next = head;
        head.next = nNext;
        return reverseHead;
    }

    /**
     * ->1->2->3->4->5->null
     * <p>
     * m = 2,n = 4
     * ->1->4->3->2->5->null
     *
     * @param head 头节点
     * @param m    m
     * @param n    n
     * @return 反转后头节点
     */
    public static SinglyLinkedListNode reverseBetweenMToN(SinglyLinkedListNode head, int m, int n) {
        if (m == 1) {
            return reverseN(head, n);
        }
        head.next = reverseBetweenMToN(head.next, m - 1, n - 1);
        return head;
    }
}

