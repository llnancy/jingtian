package com.sunchaser.sparrow.algorithm.base.util;

import com.sunchaser.sparrow.algorithm.base.linkedlist.SinglyLinkedListNode;

/**
 * 链表工具类
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2020/12/11
 */
public class LinkedListUtils {
    private LinkedListUtils() {
    }

    /**
     * 构造一个单链表
     * @return 单链表头节点
     */
    public static SinglyLinkedListNode generateSinglyLinkedList() {
        SinglyLinkedListNode node4 = new SinglyLinkedListNode(5,null);
        SinglyLinkedListNode node3 = new SinglyLinkedListNode(4,node4);
        SinglyLinkedListNode node2 = new SinglyLinkedListNode(3,node3);
        SinglyLinkedListNode node1 = new SinglyLinkedListNode(2,node2);
        return new SinglyLinkedListNode(1,node1);
    }

    /**
     * 按顺序打印单链表
     * @param head 要打印的单链表头节点
     */
    public static void printLink(SinglyLinkedListNode head) {
        if (head == null) {
            System.out.print("");
            return;
        }
        if (head.next == null) {
            System.out.println("->" + head.val + "->null");
            return;
        }
        System.out.print("->" + head.val);
        printLink(head.next);
    }
}
