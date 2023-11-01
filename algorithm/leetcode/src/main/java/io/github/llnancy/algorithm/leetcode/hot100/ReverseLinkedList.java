package io.github.llnancy.algorithm.leetcode.hot100;

import io.github.llnancy.algorithm.common.ListNode;

/**
 * 反转链表
 * <a href="https://leetcode.cn/problems/reverse-linked-list/description/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj">https://leetcode.cn/problems/reverse-linked-list/description/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj</a>
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2023/10/23
 */
public class ReverseLinkedList {

    /*
    由于单链表只能从前往后遍历的特性，反转每一个节点前需使用变量记录 next 节点的引用。
     */

    public ListNode reverseList(ListNode head) {
        ListNode pre = null;
        ListNode cur = head;
        ListNode next;
        while (cur != null) {
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }
}
