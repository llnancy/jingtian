package com.sunchaser.sparrow.leetcode.easy;

/**
 * 反转一个单链表。
 *
 * 示例:
 *
 * 输入: 1->2->3->4->5->NULL
 * 输出: 5->4->3->2->1->NULL
 * 进阶:
 * 你可以迭代或递归地反转链表。你能否用两种方法解决这道题？
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/reverse-linked-list
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author sunchaser
 * @date 2020/6/2
 * @since 1.0
 */
public class ReverseList {
    public static void main(String[] args) {
        ReverseList reverseList = new ReverseList();
        ListNode last = new ListNode(1,null);
        ListNode node1 = new ListNode(2,last);
        ListNode node2 = new ListNode(3,node1);
        ListNode node3 = new ListNode(4,node2);
        ListNode head = new ListNode(5,node3);
        System.out.println(head);
        ListNode newHead = reverseList.reverseList(head);
        System.out.println(newHead);
    }

    public ListNode reverseList(ListNode head) {
        ListNode cursor = head;
        ListNode prev = null;
        ListNode next;
        while (cursor != null) {
            next = cursor.next;
            cursor.next = prev;
            prev = cursor;
            cursor = next;
        }
        return prev;
    }

    static class ListNode {
        int val;
        ListNode next;

        public ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("ListNode{");
            sb.append("val=").append(val);
            sb.append(", next=").append(next);
            sb.append('}');
            return sb.toString();
        }
    }
}
