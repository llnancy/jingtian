package io.github.llnancy.jingtian.algorithm.base.linkedlist;

import io.github.llnancy.jingtian.algorithm.common.SinglyLinkedListNode;
import io.github.llnancy.jingtian.algorithm.common.util.LinkedListUtils;

/**
 * 奇数个元素的链表，获取中间位置元素
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2020/12/11
 */
public class MiddleNode {

    public static void main(String[] args) {
        SinglyLinkedListNode head = LinkedListUtils.generateSinglyLinkedList();
        SinglyLinkedListNode middleNode = middleNode(head);
        LinkedListUtils.printLink(middleNode);
    }

    /**
     * 快慢指针：对于奇数个元素的链表，定义一快一慢两个指针。
     * 快指针一次走两步，慢指针一次走一步，当快指针走到链表尾部的时候，慢指针所在的位置就是中间位置元素。
     *
     * @param head 单链表头节点
     * @return 中间位置节点
     */
    public static SinglyLinkedListNode middleNode(SinglyLinkedListNode head) {
        SinglyLinkedListNode fast = head;
        SinglyLinkedListNode slow = head;
        while (fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }
}
