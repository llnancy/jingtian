package io.github.llnancy.algorithm.leetcode.hot100;

import io.github.llnancy.algorithm.common.ListNode;

/**
 * 相交链表
 * <a href="https://leetcode.cn/problems/intersection-of-two-linked-lists/description/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj">https://leetcode.cn/problems/intersection-of-two-linked-lists/description/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj</a>
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2023/10/23
 */
public class IntersectionOfTwoLinkedLists {

    /*
    双指针技巧，指针 p1 p2 分别在两个链表上前进，当 p1 遍历完链表 A 时让其遍历链表 B，当 p2 遍历完链表 B 时让其遍历链表 A。
    如果链表相交，则两指针相遇点为交点，否则都为 null。
     */

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode p1 = headA;
        ListNode p2 = headB;
        while (p1 != p2) {
            if (p1 == null) {
                p1 = headB;
            } else {
                p1 = p1.next;
            }
            if (p2 == null) {
                p2 = headA;
            } else {
                p2 = p2.next;
            }
        }
        return p1;
    }
}
