package io.github.llnancy.jingtian.algorithm.leetcode.easy;

import io.github.llnancy.jingtian.algorithm.common.ListNode;

/**
 * 删除链表的节点
 * <a href="https://leetcode.cn/problems/shan-chu-lian-biao-de-jie-dian-lcof/description/">https://leetcode.cn/problems/shan-chu-lian-biao-de-jie-dian-lcof/description/</a>
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2023/11/2
 */
public class DeleteLinkedNode {

    /*
    用 pre 指针记录上一个节点，如果找到目标值，则将 pre 指针指向当前节点的 next 节点；
    如果头节点就是目标值，则直接返回头节点的 next 节点。
     */

    public ListNode deleteNode(ListNode head, int val) {
        ListNode pre = null;
        ListNode cur = head;
        while (cur != null) {
            if (cur.val == val) {
                if (pre != null) {
                    pre.next = cur.next;
                    cur.next = null;
                    break;
                } else {
                    return head.next;
                }
            }
            pre = cur;
            cur = cur.next;
        }
        return head;
    }
}
