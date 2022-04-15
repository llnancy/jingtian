package com.sunchaser.sparrow.algorithm.leetcode.middle;

import com.sunchaser.sparrow.algorithm.common.ListNode;
import com.sunchaser.sparrow.algorithm.common.SinglyLinkedListNode;

/**
 * 82. 删除排序链表中的重复元素 II
 * <p>
 * https://leetcode-cn.com/problems/remove-duplicates-from-sorted-list-ii/
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/11/24
 */
public class RemoveDuplicatesFromSortedList {

    public static ListNode deleteDuplicates(ListNode head) {
        ListNode hair = new ListNode();
        hair.next = head;
        ListNode cur = hair;
        while (cur.next != null && cur.next.next != null) {
            if (cur.next.val == cur.next.next.val) {
                int val = cur.next.val;
                while (cur.next != null && cur.next.val == val) {
                    cur = cur.next.next;
                }
            } else {
                cur = cur.next;
            }
        }
        return hair.next;
    }

    public static SinglyLinkedListNode deleteDuplicates(SinglyLinkedListNode head) {
        SinglyLinkedListNode hair = new SinglyLinkedListNode();
        hair.next = head;
        SinglyLinkedListNode cur = hair;
        while (cur.next != null && cur.next.next != null) {
            if (cur.next.val.equals(cur.next.next.val)) {
                Integer val = cur.next.val;// 用一个变量记录下重复的val
                while (cur.next != null && cur.next.val.equals(val)) {
                    cur.next = cur.next.next;
                }
            } else {
                cur = cur.next;
            }
        }
        return hair.next;
    }
}
