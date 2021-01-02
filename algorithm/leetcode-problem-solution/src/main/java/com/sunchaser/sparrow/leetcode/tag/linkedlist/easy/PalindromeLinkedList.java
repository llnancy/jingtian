package com.sunchaser.sparrow.leetcode.tag.linkedlist.easy;

import com.sunchaser.sparrow.algorithm.common.SinglyLinkedListNode;
import com.sunchaser.sparrow.algorithm.common.util.LinkedListUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 回文链表：请判断一个链表是否为回文链表。
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2020/12/30
 */
public class PalindromeLinkedList {

    public static void main(String[] args) {
        SinglyLinkedListNode head = LinkedListUtils.generateSinglyLinkedList();
        boolean noPalindrome = isPalindromeUseArray(head);
        System.out.println(head);
        System.out.println(noPalindrome);
        boolean noPalindromeUseReverse = isPalindromeUseReverse(head);
        System.out.println(head);
        System.out.println(noPalindromeUseReverse);
        SinglyLinkedListNode optHead = LinkedListUtils.generateSinglyLinkedList();
        boolean noPalindromeUseReverseOptimization = isPalindromeUseReverseOptimization(optHead);
        System.out.println(optHead);
        System.out.println(noPalindromeUseReverseOptimization);
        SinglyLinkedListNode palindromeHead = LinkedListUtils.generatePalindromeLinkedList();
        boolean palindrome = isPalindromeUseArray(palindromeHead);
        System.out.println(head);
        System.out.println(palindrome);
        boolean palindromeUseReverse = isPalindromeUseReverse(palindromeHead);
        System.out.println(head);
        System.out.println(palindromeUseReverse);
        SinglyLinkedListNode optPalindromeHead = LinkedListUtils.generatePalindromeLinkedList();
        boolean palindromeUseReverseOptimization = isPalindromeUseReverseOptimization(optPalindromeHead);
        System.out.println(head);
        System.out.println(palindromeUseReverseOptimization);
    }

    public static boolean isPalindromeUseArray(SinglyLinkedListNode head) {
        List<SinglyLinkedListNode> list = new ArrayList<>();
        while (head != null) {
            list.add(head);
            head = head.next;
        }
        for (int i = 0,size = list.size();i < size / 2;i++) {
            if (!list.get(i).val.equals(list.get(size - i - 1).val)) return false;
        }
        return true;
    }

    public static boolean isPalindromeUseReverse(SinglyLinkedListNode head) {
        if (head == null || head.next == null) return true;
        SinglyLinkedListNode fast = head;
        SinglyLinkedListNode slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        SinglyLinkedListNode p1 = head;
        SinglyLinkedListNode p2 = slow;
        SinglyLinkedListNode pre = slow;
        SinglyLinkedListNode next;
        while (p1 != p2) {
            next = p1.next;
            p1.next = pre;
            pre = p1;
            p1 = next;
        }
        if (fast != null) p2 = p2.next;
        while (p2 != null) {
            if (!pre.val.equals(p2.val)) return false;
            pre = pre.next;
            p2 = p2.next;
        }
        return true;
    }

    public static boolean isPalindromeUseReverseOptimization(SinglyLinkedListNode head) {
        if (head == null || head.next == null) return true;
        SinglyLinkedListNode fast = head;
        SinglyLinkedListNode slow = head;
        SinglyLinkedListNode pre = null;
        SinglyLinkedListNode next;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            next = slow.next;
            slow.next = pre;
            pre = slow;
            slow = next;
        }
        if (fast != null) slow = slow.next;
        while (slow != null) {
            assert pre != null;
            if (!pre.val.equals(slow.val)) return false;
            pre = pre.next;
            slow = slow.next;
        }
        return true;
    }
}
