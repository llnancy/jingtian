package io.github.llnancy.algorithm.leetcode.hot100;

import io.github.llnancy.algorithm.common.ListNode;

/**
 * 合并两个有序链表
 * <a href="https://leetcode.cn/problems/merge-two-sorted-lists/description/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj">https://leetcode.cn/problems/merge-two-sorted-lists/description/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj</a>
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2023/10/23
 */
public class MergeTwoSortedLists {

    /*
    注意两个链表都是升序的，使用虚拟头节点和双指针依次进行合并即可。
     */

    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode hair = new ListNode();
        ListNode cur = hair;
        ListNode p1 = list1;
        ListNode p2 = list2;
        while (p1 != null && p2 != null) {
            if (p1.val < p2.val) {
                cur.next = p1;
                p1 = p1.next;
            } else {
                cur.next = p2;
                p2 = p2.next;
            }
            cur = cur.next;
        }
        if (p1 == null) {
            cur.next = p2;
        }
        if (p2 == null) {
            cur.next = p1;
        }
        return hair.next;
    }
}
