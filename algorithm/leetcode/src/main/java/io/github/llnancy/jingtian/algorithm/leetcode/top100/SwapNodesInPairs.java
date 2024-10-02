package io.github.llnancy.jingtian.algorithm.leetcode.top100;

import io.github.llnancy.jingtian.algorithm.common.ListNode;

/**
 * 两两交换链表中的节点
 * <a href="https://leetcode.cn/problems/swap-nodes-in-pairs/description/?envType=study-plan-v2&envId=top-100-liked">https://leetcode.cn/problems/swap-nodes-in-pairs/description/?envType=study-plan-v2&envId=top-100-liked</a>
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2023/11/14
 */
public class SwapNodesInPairs {

    /*
    迭代或递归。迭代利用虚拟头节点，每次迭代虚拟头节点后面的两个节点。
     */

    public ListNode swapPairs(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode hair = new ListNode();
        hair.next = head;
        ListNode cur = hair;
        // 每次交换 cur 后面的两个节点
        while (cur.next != null && cur.next.next != null) {
            ListNode p1 = cur.next;
            ListNode p2 = cur.next.next;
            cur.next = p2;
            p1.next = p2.next;
            p2.next = p1;
            cur = p1;
        }
        return hair.next;
    }

    public ListNode swapPairsRecursion(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        // 递归写法
        ListNode second = head.next;
        ListNode others = head.next.next;
        second.next = head;
        head.next = swapPairsRecursion(others);
        return second;
    }
}
