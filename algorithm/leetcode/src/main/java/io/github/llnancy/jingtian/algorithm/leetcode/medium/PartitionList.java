package io.github.llnancy.jingtian.algorithm.leetcode.medium;

import io.github.llnancy.jingtian.algorithm.common.ListNode;

/**
 * 86. 分隔链表
 * <a href="https://leetcode.cn/problems/partition-list/">https://leetcode.cn/problems/partition-list/</a>
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2024/10/3
 */
public class PartitionList {

    public ListNode partition(ListNode head, int x) {
        // 存放小于 x 的链表的虚拟头节点
        ListNode hair1 = new ListNode();
        // 存放大于等于 x 的链表的虚拟头节点
        ListNode hair2 = new ListNode();
        // p1 和 p2 指针负责连接结果链表
        ListNode p1 = hair1;
        ListNode p2 = hair2;
        // p 指针负责遍历原链表
        ListNode p = head;
        while (p != null) {
            if (p.val < x) {
                p1.next = p;
                p1 = p1.next;
            } else {
                p2.next = p;
                p2 = p2.next;
            }
            // 这里不能直接 p = p.next，要将其断连
            ListNode next = p.next;
            p.next = null;
            p = next;
        }
        // 连接大于等于 x 的链表
        p1.next = hair2.next;
        return hair1.next;
    }
}
