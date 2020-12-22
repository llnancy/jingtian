package com.sunchaser.sparrow.algorithm.base.linkedlist;

import com.sunchaser.sparrow.algorithm.common.SinglyLinkedListNode;
import com.sunchaser.sparrow.algorithm.common.util.LinkedListUtils;

/**
 * 判断链表是否有环
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2020/12/21
 */
public class HasCycleLink {

    public static void main(String[] args) {
        SinglyLinkedListNode singlyLinkedList = LinkedListUtils.generateSinglyLinkedList();
        boolean b = hasCycle(singlyLinkedList);
        System.out.println(b);
        SinglyLinkedListNode head = LinkedListUtils.generateCycleSinglyLinkedList();
        boolean hasCycle = hasCycle(head);
        System.out.println(hasCycle);
    }

    public static boolean hasCycle(SinglyLinkedListNode head) {
        if (head == null || head.next == null) {
            return false;
        }
        SinglyLinkedListNode fast = head;
        SinglyLinkedListNode slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                return true;
            }
        }
        return false;
    }
}
