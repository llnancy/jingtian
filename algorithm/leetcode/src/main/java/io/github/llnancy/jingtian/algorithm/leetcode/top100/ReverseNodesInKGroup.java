package io.github.llnancy.jingtian.algorithm.leetcode.top100;

import io.github.llnancy.jingtian.algorithm.common.ListNode;

/**
 * K 个一组翻转链表
 * <a href="https://leetcode.cn/problems/reverse-nodes-in-k-group/description/?envType=study-plan-v2&envId=top-100-liked">https://leetcode.cn/problems/reverse-nodes-in-k-group/description/?envType=study-plan-v2&envId=top-100-liked</a>
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2023/11/14
 */
public class ReverseNodesInKGroup {

    /*
    递归。先反转以 head 开头的 k 个元素得到新的头节点 newHead，再将第 k + 1 个元素作为 head 递归调用 reverseKGroup 函数得到后面部分的头节点，最后将两个部分连接起来返回 newHead。
     */

    public ListNode reverseKGroup(ListNode head, int k) {
        if (head == null) {
            return null;
        }
        ListNode cur = head;
        // cur 指针走 k 步
        for (int i = 0; i < k; i++) {
            // 不足 k 个不需要反转
            if (cur == null) {
                return head;
            }
            cur = cur.next;
        }
        // 反转前 k 个元素，区间 [head, cur)
        ListNode newHead = reverse(head, cur);
        // 第 k + 1 个元素作为头节点递归反转并连接
        head.next = reverseKGroup(cur, k);
        return newHead;
    }

    private ListNode reverse(ListNode head, ListNode p) {
        ListNode pre = null;
        ListNode cur = head;
        ListNode next;
        // 反转 [head, p) 区间内的元素
        while (cur != p) {
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }
}
