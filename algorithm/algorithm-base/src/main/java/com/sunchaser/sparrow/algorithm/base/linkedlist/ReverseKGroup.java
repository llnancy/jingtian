package com.sunchaser.sparrow.algorithm.base.linkedlist;

import com.sunchaser.sparrow.algorithm.common.SinglyLinkedListNode;
import com.sunchaser.sparrow.algorithm.common.util.LinkedListUtils;

import java.util.Deque;
import java.util.LinkedList;

/**
 * K 个一组翻转链表
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2020/12/24
 */
public class ReverseKGroup {
    public static void main(String[] args) {
        SinglyLinkedListNode head = LinkedListUtils.generateSinglyLinkedList();
        LinkedListUtils.printLink(head);
        // 套娃式递归实现
        SinglyLinkedListNode kGroup = reverseKGroup(head, 2);
        LinkedListUtils.printLink(kGroup);
        SinglyLinkedListNode headUseStack = LinkedListUtils.generateSinglyLinkedList();
        // 代码栈实现
        SinglyLinkedListNode singlyLinkedListNode = reverseKGroupUseStack(headUseStack, 2);
        LinkedListUtils.printLink(singlyLinkedListNode);
    }

    /**
     * k个一组翻转链表，利用代码栈结构实现
     * @param head 链表头节点
     * @param k k
     * @return 反转后的新链表头节点
     */
    public static SinglyLinkedListNode reverseKGroupUseStack(SinglyLinkedListNode head, int k) {
        if (head == null || k < 2) {
            return head;
        }
        Deque<SinglyLinkedListNode> stack = new LinkedList<>();
        SinglyLinkedListNode newHead = head;
        SinglyLinkedListNode cur = head;
        SinglyLinkedListNode pre = null;
        SinglyLinkedListNode next;
        while (cur != null) {
            // 记录下一个节点
            next = cur.next;
            // 压栈
            stack.push(cur);
            // 如果达到k个元素，则反转栈中的k个元素
            if (stack.size() == k) {
                // 弹出栈中的第一个元素，即为此批k个元素中的头节点
                SinglyLinkedListNode first = stack.pop();
                if (pre != null) {
                    // 不是第一批的k个元素，将上一批k个元素反转后的尾节点指向这一批k个元素的头节点
                    pre.next = first;
                }
                SinglyLinkedListNode right;
                // 依次弹出栈中剩下的元素并进行连接
                while (!stack.isEmpty()) {
                    right = stack.pop();
                    // 反转first
                    first.next = right;
                    // first右移
                    first = right;
                }
                // 栈中元素连接完成，即这一批的k个元素反转完成
                // 此时first是这一批的k个元素反转之后的尾节点，需要与下一个节点连接起来
                first.next = next;
                // pre指向这一批的k个元素反转之后的尾节点
                pre = first;
                // 如果是第一批的k个元素，则最终需要返回的新头节点为第一批的k个元素反转之后的头节点，即cur。
                // 后面的反转操作都不会再改变最终需要返回的新头节点
                newHead = newHead == head ? cur : newHead;
            }
            // cur右移
            cur = next;
        }
        return newHead;
    }

    /**
     * k个一组翻转链表，递归（系统栈）实现
     * @param head 链表头节点
     * @param k k
     * @return 反转后的新链表头节点
     */
    public static SinglyLinkedListNode reverseKGroup(SinglyLinkedListNode head, int k) {
        if (head == null || k < 2) {
            return head;
        }
        SinglyLinkedListNode b = head;
        for (int i = 0; i < k; i++) {
            if (b.next == null) {
                // 不足k个
                return head;
            }
            b = b.next;
        }
        // for循环执行3次，b向右移动了3个节点，所以反转[a,b)左闭右开区间
        SinglyLinkedListNode reverseHead = reverseRecursionImpl(head, b);
        // a节点指向后面的头节点
        head.next = reverseKGroup(b, k);
        return reverseHead;
    }

    /**
     * 非递归实现反转[a,b)区间
     * @param a 区间开始节点
     * @param b 区间结束节点（不反转该节点）
     * @return [a,b)反转后的头节点
     */
    private static SinglyLinkedListNode reverse(SinglyLinkedListNode a, SinglyLinkedListNode b) {
        SinglyLinkedListNode pre = null;
        SinglyLinkedListNode cur = a;
        SinglyLinkedListNode next;
        while (cur != b) {
            // 1. 记录下一个节点
            next = cur.next;
            // 2. 反转当前节点
            cur.next = pre;
            // 3. pre右移
            pre = cur;
            // 4. cur右移
            cur = next;
        }
        return pre;
    }

    /**
     * 递归实现反转[a,b)区间
     * @param a 区间开始节点
     * @param b 区间结束节点（不反转该节点）
     * @return [a,b)反转后的头节点
     */
    private static SinglyLinkedListNode reverseRecursionImpl(SinglyLinkedListNode a, SinglyLinkedListNode b) {
        if (a.next == b) {
            return a;
        }
        SinglyLinkedListNode next = a.next;
        SinglyLinkedListNode newHead = reverseRecursionImpl(next, b);
        // 连接a节点
        next.next = a;
        a.next = null;
        return newHead;
    }
}
