package io.github.llnancy.jingtian.algorithm.leetcode.easy;

import io.github.llnancy.jingtian.algorithm.common.ListNode;

/**
 * 删除排序链表中的重复元素
 * <a href="https://leetcode.cn/problems/remove-duplicates-from-sorted-list/description/">https://leetcode.cn/problems/remove-duplicates-from-sorted-list/description/</a>
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2023/11/2
 */
public class RemoveDuplicatesFromSortedList {

    /*
    快慢指针技巧。让快指针走在前面探路，找到一个不重复的节点就让慢指针指向快指针，然后慢指针前进，最后断开慢指针与后面重复节点的连接。返回链表头节点。
     */

    public ListNode deleteDuplicates(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode fast = head;
        ListNode slow = head;
        while (fast != null) {
            if (fast.val != slow.val) {
                // 慢指针指向快指针（删除中间部分的重复元素）
                slow.next = fast;
                slow = slow.next;
            }
            fast = fast.next;
        }
        // 断开与后面重复元素的连接
        slow.next = null;
        return head;
    }
}
