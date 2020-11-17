package com.sunchaser.sparrow.algorithm.base;

/**
 * 递归法拿下反转链表的三杀
 * @author sunchaser
 * @date 2020/6/3
 * @since 1.0
 */
public class ReverseLink {
    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        ListNode node1 = new ListNode(2);
        ListNode node2 = new ListNode(3);
        ListNode node3 = new ListNode(4);
        ListNode node4 = new ListNode(5);
        // link node
        head.next = node1;
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = null;
        printLink(head);
        // first blood
        // ListNode firstBlood = reverse(head);
        // printLink(firstBlood);
        // double kill
        // ListNode doubleKill = reverseN(head, 3);
        // printLink(doubleKill);
        // triple kill
        ListNode tripleKill = reverseBetweenMToN(head, 2, 4);
        printLink(tripleKill);
    }

    public static void printLink(ListNode head) {
        if (head == null) {
            System.out.print("");
            return;
        }
        if (head.next == null) {
            System.out.println("->" + head.val + "->null");
            return;
        }
        System.out.print("->" + head.val);
        printLink(head.next);
    }

    public static ListNode reverse(ListNode head) {
        if (head.next == null)
            return head;
        ListNode reverseHead = reverse(head.next);
        head.next.next = head;
        head.next = null;
        return reverseHead;
    }

    static ListNode nNext = null;

    public static ListNode reverseN(ListNode head,int n) {
        if (n == 1) {
            nNext = head.next;
            return head;
        }
        ListNode reverseHead = reverseN(head.next,n - 1);
        head.next.next = head;
        head.next = nNext;
        return reverseHead;
    }

    /**
     * ->1->2->3->4->5->null
     *
     * m = 2,n = 4
     * ->1->4->3->2->5->null
     *
     * @param head 头节点
     * @param m m
     * @param n n
     * @return 反转后头节点
     */
    public static ListNode reverseBetweenMToN(ListNode head,int m,int n) {
        if (m == 1) {
            return reverseN(head,n);
        }
        head.next = reverseBetweenMToN(head.next, m - 1, n - 1);
        return head;
    }
}

