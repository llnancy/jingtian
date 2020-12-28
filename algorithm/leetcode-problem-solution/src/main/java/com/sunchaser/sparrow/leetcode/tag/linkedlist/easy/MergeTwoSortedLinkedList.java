package com.sunchaser.sparrow.leetcode.tag.linkedlist.easy;

import com.sunchaser.sparrow.algorithm.common.SinglyLinkedListNode;
import com.sunchaser.sparrow.algorithm.common.util.LinkedListUtils;

/**
 * 合并两个有序链表
 *
 * 将两个升序链表合并为一个新的 升序 链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。
 *
 * 示例：
 *
 * 输入：1->2->4, 1->3->4
 * 输出：1->1->2->3->4->4
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/merge-two-sorted-lists
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2020/12/28
 */
public class MergeTwoSortedLinkedList {

    public static void main(String[] args) {
        SinglyLinkedListNode l1 = LinkedListUtils.generateSinglyLinkedList();
        SinglyLinkedListNode l2 = LinkedListUtils.generateSinglyLinkedList();
        SinglyLinkedListNode recursionImplMergeHead = mergeTwoSortedLinkedListRecursionImpl(l1, l2);
        LinkedListUtils.printLink(recursionImplMergeHead);
        SinglyLinkedListNode l3 = LinkedListUtils.generateSinglyLinkedList();
        SinglyLinkedListNode l4 = LinkedListUtils.generateSinglyLinkedList();
        SinglyLinkedListNode mergeHead = mergeTwoSortedLinkedList(l3, l4);
        LinkedListUtils.printLink(mergeHead);
    }

    public static SinglyLinkedListNode mergeTwoSortedLinkedListRecursionImpl(SinglyLinkedListNode l1,
                                                                             SinglyLinkedListNode l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;
        if (l1.val < l2.val) {
            l1.next = mergeTwoSortedLinkedListRecursionImpl(l1.next, l2);
            return l1;
        } else {
            l2.next = mergeTwoSortedLinkedListRecursionImpl(l1,l2.next);
            return l2;
        }
    }

    public static SinglyLinkedListNode mergeTwoSortedLinkedList(SinglyLinkedListNode l1,
                                                                SinglyLinkedListNode l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;
        SinglyLinkedListNode hair = new SinglyLinkedListNode();
        SinglyLinkedListNode cur = hair;
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                cur.next = l1;
                l1 = l1.next;
            } else {
                cur.next = l2;
                l2 = l2.next;
            }
            cur = cur.next;
        }
        if (l1 == null) cur.next = l2;
        if (l2 == null) cur.next = l1;
        return hair.next;
    }
}
