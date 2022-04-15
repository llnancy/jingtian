package com.sunchaser.sparrow.algorithm.leetcode.hot100;

import com.sunchaser.sparrow.algorithm.common.ListNode;

/**
 * 两数相加
 * https://leetcode-cn.com/problems/add-two-numbers/
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/12/17
 */
public class AddTwoNumbers {
    public static void main(String[] args) {
        ListNode l1 = new ListNode(2);
        ListNode l11 = new ListNode(4);
        ListNode l12 = new ListNode(3);
        ListNode l2 = new ListNode(5);
        ListNode l21 = new ListNode(6);
        ListNode l22 = new ListNode(4);
        l1.next = l11;
        l11.next = l12;
        l2.next = l21;
        l21.next = l22;
        System.out.println(addTwoNumbers(l1, l2));
    }

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        // 虚拟哨兵节点（hair：头发。）
        ListNode hair = new ListNode();
        // cur指针连接新链表
        ListNode cur = hair;
        // 每次相加的进位
        int jin = 0;
        while (l1 != null || l2 != null || jin != 0) {
            // 加上进位
            int sum = jin;
            // 加上l1的值
            if (l1 != null) {
                sum += l1.val;
                l1 = l1.next;
            }
            // 加上l2的值
            if (l2 != null) {
                sum += l2.val;
                l2 = l2.next;
            }
            // 更新进位
            jin = sum / 10;
            // 当前结果
            sum = sum % 10;
            // 连接新节点
            cur.next = new ListNode(sum);
            // cur右移
            cur = cur.next;
        }
        // 返回新的头节点（虚拟哨兵节点的下一个就是新的头节点）
        return hair.next;
    }
}
