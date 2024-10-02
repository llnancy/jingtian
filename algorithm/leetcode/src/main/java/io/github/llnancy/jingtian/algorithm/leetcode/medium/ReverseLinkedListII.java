package io.github.llnancy.jingtian.algorithm.leetcode.medium;

import io.github.llnancy.jingtian.algorithm.common.ListNode;

/**
 * 92. 反转链表 II
 * <a href="https://leetcode.cn/problems/reverse-linked-list-ii/">https://leetcode.cn/problems/reverse-linked-list-ii/</a>
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2021/11/16
 */
public class ReverseLinkedListII {

    /**
     * 递归。
     * reverseBetween 函数的定义是反转以 head 为头节点，区间 [left, right] 内的元素。
     * 那 reverseBetween(head.next, left - 1, right - 1) 则表示反转以 head.next 为头节点，区间 [left - 1, right - 1] 内的元素。
     * 当 left = 1 时，得到 base case，反转链表的前 right 个元素。
     * 反转链表前 right 个元素时需要用一个全局变量记录第 right + 1 个元素的引用。
     */

    public ListNode reverseBetween(ListNode head, int left, int right) {
        if (left == 1) {
            // 反正链表的前 n 个元素
            return reverseN(head, right);
        }
        head.next = reverseBetween(head.next, left - 1, right - 1);
        return head;
    }

    ListNode successor;

    private ListNode reverseN(ListNode head, int n) {
        if (n == 1) {
            // 记录第 n+1 个元素
            successor = head.next;
            // 反转前 1 个元素
            return head;
        }
        ListNode newHead = reverseN(head.next, n - 1);
        // 反转 head
        head.next.next = head;
        head.next = successor;
        return newHead;
    }
}
