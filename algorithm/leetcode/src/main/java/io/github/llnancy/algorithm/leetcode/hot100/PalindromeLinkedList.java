package io.github.llnancy.algorithm.leetcode.hot100;

import io.github.llnancy.algorithm.common.ListNode;

/**
 * 回文链表
 * <a href="https://leetcode.cn/problems/palindrome-linked-list/description/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj">https://leetcode.cn/problems/palindrome-linked-list/description/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj</a>
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2023/10/23
 */
public class PalindromeLinkedList {

    /*
    利用双指针技巧寻找链表中点，在慢指针迭代过程中顺便将前半部分链表反转，然后进行回文值比较。
     */

    public boolean isPalindrome(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;

        // 快慢指针找中点
        ListNode pre = null;
        ListNode next;
        // 找中点过程中将慢指针迭代部分进行链表反转
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            next = slow.next;
            slow.next = pre;
            pre = slow;
            slow = next;
        }

        // 如果快指针指向不为 null，说明链表长度为奇数。
        if (fast != null) {
            // 慢指针需要再向前推进一步
            slow = slow.next;
        }
        while (slow != null && pre != null) {
            // 值回文比较
            if (slow.val != pre.val) {
                return false;
            }
            slow = slow.next;
            pre = pre.next;
        }
        return true;
    }
}
