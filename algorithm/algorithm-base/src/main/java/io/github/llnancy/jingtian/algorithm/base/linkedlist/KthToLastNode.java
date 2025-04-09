package io.github.llnancy.jingtian.algorithm.base.linkedlist;

import io.github.llnancy.jingtian.algorithm.common.SinglyLinkedListNode;
import io.github.llnancy.jingtian.algorithm.common.util.LinkedListUtils;

/**
 * 返回倒数第 k 个节点
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2020/12/23
 */
public class KthToLastNode {
    public static void main(String[] args) {
        SinglyLinkedListNode list = LinkedListUtils.generateSinglyLinkedList();
        LinkedListUtils.printLink(list);
        System.out.println(kthToLastNode(list, 3));
    }

    public static int kthToLastNode(SinglyLinkedListNode head, int k) {
        if (head == null || k <= 0) {
            return 0;
        }
        SinglyLinkedListNode first = head;
        SinglyLinkedListNode second = head;
        while (k-- > 1) {
            if (first.next == null) {
                return 0;
            }
            first = first.next;
        }
        while (first.next != null) {
            first = first.next;
            second = second.next;
        }
        return second.val;
    }
}
