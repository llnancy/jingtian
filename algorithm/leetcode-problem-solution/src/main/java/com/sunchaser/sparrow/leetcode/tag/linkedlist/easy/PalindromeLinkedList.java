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
        boolean noPalindrome = isPalindrome(head);
        System.out.println(noPalindrome);
        SinglyLinkedListNode palindromeHead = LinkedListUtils.generatePalindromeLinkedList();
        boolean palindrome = isPalindrome(palindromeHead);
        System.out.println(palindrome);
    }

    public static boolean isPalindrome(SinglyLinkedListNode head) {
        List<SinglyLinkedListNode> list = new ArrayList<>();
        while (head != null) {
            list.add(head);
            head = head.next;
        }
        for (int i = 0; i < list.size(); i++) {
            if (!list.get(i).val.equals(list.get(list.size() - i - 1).val)) {
                return false;
            }
        }
        return true;
    }
}
