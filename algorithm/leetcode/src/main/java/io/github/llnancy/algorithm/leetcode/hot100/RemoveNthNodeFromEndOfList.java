package io.github.llnancy.algorithm.leetcode.hot100;

import io.github.llnancy.algorithm.common.ListNode;

/**
 * 删除链表的倒数第 N 个结点
 * <a href="https://leetcode.cn/problems/remove-nth-node-from-end-of-list/description/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj">https://leetcode.cn/problems/remove-nth-node-from-end-of-list/description/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj</a>
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2023/10/23
 */
public class RemoveNthNodeFromEndOfList {

    /*
    要删除链表的倒数第 N 个节点，就要先获取倒数第 N + 1 个节点的引用。
     */

    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode hair = new ListNode();
        hair.next = head;
        // 获取倒数第 N + 1 个节点的引用
        ListNode n1 = findNthFromEnd(hair, n + 1);
        // 删掉倒数第 N 个节点
        n1.next = n1.next.next;
        return hair.next;
    }

    private ListNode findNthFromEnd(ListNode head, int n) {
        ListNode p1 = head;
        // p1 先走 n 步
        for (int i = 0; i < n; i++) {
            p1 = p1.next;
        }
        ListNode p2 = head;
        // p1 p2 同时走，直到 p1 走到链表尾部
        while (p1 != null) {
            p1 = p1.next;
            p2 = p2.next;
        }
        // p1 走到尾部时，p2 为倒数第 n 个节点。
        return p2;
    }
}
