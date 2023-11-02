package io.github.llnancy.jingtian.algorithm.leetcode.hot100;

import io.github.llnancy.jingtian.algorithm.common.ListNode;

/**
 * 环形链表 II
 * <a href="https://leetcode.cn/problems/linked-list-cycle-ii/description/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj">https://leetcode.cn/problems/linked-list-cycle-ii/description/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj</a>
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2023/10/23
 */
public class LinkedListCycleII {

    /*
    先用快慢指针技巧判断链表是否有环。无环直接返回 null；有环则让慢指针重新指向头节点，然后同时走，再次相遇点即为环的入口节点。
     */

    public ListNode detectCycle(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;
        // 快指针走两步，慢指针走一步
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (fast == slow) {
                // 快慢指针相遇说明链表有环
                break;
            }
        }
        if (fast == null || fast.next == null) {
            // fast 为空说明链表无环
            return null;
        }
        // 慢指针重新指向头节点
        slow = head;
        // 快慢指针同时走，相遇点即为环的入口节点
        while (fast != slow) {
            fast = fast.next;
            slow = slow.next;
        }
        return fast;
    }
}
