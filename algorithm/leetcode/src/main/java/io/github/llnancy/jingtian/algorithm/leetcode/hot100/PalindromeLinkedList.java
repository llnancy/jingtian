package io.github.llnancy.jingtian.algorithm.leetcode.hot100;

import io.github.llnancy.jingtian.algorithm.common.ListNode;

/**
 * 回文链表
 * <a href="https://leetcode.cn/problems/palindrome-linked-list/description/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj">https://leetcode.cn/problems/palindrome-linked-list/description/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj</a>
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2023/10/23
 */
public class PalindromeLinkedList {

    /*
    利用快慢双指针技巧寻找链表中点，在慢指针迭代过程中顺便将前半部分链表反转，然后进行回文值比较，在比较过程中再次将前半部分链表反转恢复链表结构。
     */

    public boolean isPalindrome(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;

        // 快慢指针找中点
        ListNode pre = null;
        ListNode next;
        // 找中点过程中将慢指针迭代部分进行链表反转
        while (fast != null && fast.next != null) {
            // 快指针要先走，因为慢指针迭代过程中会反转节点
            fast = fast.next.next;
            next = slow.next;
            slow.next = pre;
            pre = slow;
            slow = next;
        }

        if (fast != null) {
            // fast 不为 null，说明链表长度为奇数，slow 需要再往前走一步以错开中点
            slow = slow.next;
        }

        ListNode p1 = pre;
        ListNode pre1 = slow;
        ListNode next1;
        boolean result = true;
        while (p1 != null && slow != null) {
            // 值回文比较
            if (p1.val != slow.val) {
                // 非回文更新结果，但依然要遍历完所有节点以便还原整个链表
                result = false;
            }
            next1 = p1.next;
            p1.next = pre1;
            pre1 = p1;
            p1 = next1;
            slow = slow.next;
        }
        return result;
    }
}
