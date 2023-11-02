package io.github.llnancy.jingtian.algorithm.leetcode.hot100;

import io.github.llnancy.jingtian.algorithm.common.ListNode;

/**
 * 环形链表
 * <a href="https://leetcode.cn/problems/linked-list-cycle/description/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj">https://leetcode.cn/problems/linked-list-cycle/description/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj</a>
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2023/10/23
 */
public class LinkedListCycle {

    /*
    快慢指针。快指针每次走两步，慢指针每次走一步，如果快慢指针相遇则链表有环，否则无环。
     */

    public boolean hasCycle(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (fast == slow) {
                return true;
            }
        }
        return false;
    }
}
