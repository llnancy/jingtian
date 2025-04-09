package io.github.llnancy.jingtian.algorithm.base.linkedlist;

import io.github.llnancy.jingtian.algorithm.common.SinglyLinkedListNode;
import io.github.llnancy.jingtian.algorithm.common.util.LinkedListUtils;

import java.util.Deque;
import java.util.LinkedList;

/**
 * K 个一组翻转链表
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2020/12/24
 */
public class ReverseKGroup {
    public static void main(String[] args) {
        // 套娃式递归实现
        SinglyLinkedListNode kGroup1 = LinkedListUtils.generateSinglyLinkedList();
        SinglyLinkedListNode doubleRecursionHead = reverseKGroupRecursionImpl(kGroup1, 2);
        LinkedListUtils.printLink(doubleRecursionHead);
        // 代码栈实现
        SinglyLinkedListNode kGroup2 = LinkedListUtils.generateSinglyLinkedList();
        SinglyLinkedListNode useStackHead = reverseKGroupUseStack(kGroup2, 3);
        LinkedListUtils.printLink(useStackHead);
        // 纯迭代实现
        SinglyLinkedListNode kGroup3 = LinkedListUtils.generateSinglyLinkedList();
        SinglyLinkedListNode onlyUseIteratorHead = reverseKGroupOnlyUseIterator(kGroup3, 2);
        LinkedListUtils.printLink(onlyUseIteratorHead);
    }

    public static SinglyLinkedListNode reverseKGroupOnlyUseIterator(SinglyLinkedListNode head, int k) {
        if (head == null || k < 2) {
            return head;
        }
        SinglyLinkedListNode newHead = head;
        SinglyLinkedListNode pre = null;
        SinglyLinkedListNode cur = head;
        SinglyLinkedListNode next;
        SinglyLinkedListNode kPre;
        SinglyLinkedListNode kCur;
        SinglyLinkedListNode kNext;
        int count = 1;
        while (cur != null) {
            next = cur.next;
            if (count == k) {
                kCur = kPre = pre == null ? head : pre.next;
                newHead = pre == null ? cur : newHead;
                while (kCur != next) {
                    kNext = kCur.next;
                    kCur.next = kPre;
                    kPre = kCur;
                    kCur = kNext;
                }
                pre = kPre;
                count = 0;
            }
            count++;
            cur = next;
        }
        return newHead;
    }

    /**
     * k 个一组翻转链表，利用代码栈结构实现
     *
     * @param head 链表头节点
     * @param k    k
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
            // 如果达到 k 个元素，则反转栈中的 k 个元素
            if (stack.size() == k) {
                // 弹出栈中的第一个元素，即为此批 k 个元素中的头节点
                SinglyLinkedListNode first = stack.pop();
                if (pre != null) {
                    // 不是第一批的 k 个元素，将上一批 k 个元素反转后的尾节点指向这一批 k 个元素的头节点
                    pre.next = first;
                }
                SinglyLinkedListNode right;
                // 依次弹出栈中剩下的元素并进行连接
                while (!stack.isEmpty()) {
                    right = stack.pop();
                    // 反转 first
                    first.next = right;
                    // first 右移
                    first = right;
                }
                // 栈中元素连接完成，即这一批的 k 个元素反转完成
                // 此时 first 是这一批的 k 个元素反转之后的尾节点，需要与下一个节点连接起来
                first.next = next;
                // pre 指向这一批的 k 个元素反转之后的尾节点
                pre = first;
                // 如果是第一批的 k 个元素，则最终需要返回的新头节点为第一批的 k 个元素反转之后的头节点，即 cur。
                // 后面的反转操作都不会再改变最终需要返回的新头节点
                newHead = newHead == head ? cur : newHead;
            }
            // cur 右移
            cur = next;
        }
        return newHead;
    }

    /**
     * k 个一组翻转链表，递归（系统栈）实现
     *
     * @param head 链表头节点
     * @param k    k
     * @return 反转后的新链表头节点
     */
    public static SinglyLinkedListNode reverseKGroupRecursionImpl(SinglyLinkedListNode head, int k) {
        if (head == null || k < 2) {
            return head;
        }
        SinglyLinkedListNode b = head;
        for (int i = 0; i < k; i++) {
            if (b == null) {
                // 不足 k 个
                return head;
            }
            b = b.next;
        }
        // for 循环执行 3 次，b 向右移动了 3 个节点，所以反转 [a,b) 左闭右开区间
        SinglyLinkedListNode reverseHead = reverseRecursionImpl(head, b);
        // a 节点指向后面的头节点
        head.next = reverseKGroupRecursionImpl(b, k);
        return reverseHead;
    }

    /**
     * 非递归实现反转 [a,b) 区间
     *
     * @param a 区间开始节点
     * @param b 区间结束节点（不反转该节点）
     * @return [a, b) 反转后的头节点
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
            // 3. pre 右移
            pre = cur;
            // 4. cur 右移
            cur = next;
        }
        return pre;
    }

    /**
     * 递归实现反转 [a,b) 区间
     *
     * @param a 区间开始节点
     * @param b 区间结束节点（不反转该节点）
     * @return [a, b) 反转后的头节点
     */
    private static SinglyLinkedListNode reverseRecursionImpl(SinglyLinkedListNode a, SinglyLinkedListNode b) {
        if (a.next == b) {
            return a;
        }
        SinglyLinkedListNode next = a.next;
        SinglyLinkedListNode newHead = reverseRecursionImpl(next, b);
        // 连接 a 节点
        next.next = a;
        a.next = null;
        return newHead;
    }
}
