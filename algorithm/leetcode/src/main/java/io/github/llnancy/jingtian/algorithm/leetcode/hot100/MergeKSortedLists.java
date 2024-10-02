package io.github.llnancy.jingtian.algorithm.leetcode.hot100;

import io.github.llnancy.jingtian.algorithm.common.ListNode;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * 合并 K 个升序链表
 * <a href="https://leetcode.cn/problems/merge-k-sorted-lists/description/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj">https://leetcode.cn/problems/merge-k-sorted-lists/description/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj</a>
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2023/10/23
 */
public class MergeKSortedLists {

    /*
    使用优先队列（小根堆）获取 k 个节点中的最小节点。
     */

    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }
        ListNode hair = new ListNode();
        ListNode cur = hair;
        // 优先队列（小根堆）
        Queue<ListNode> pq = new PriorityQueue<>(lists.length, Comparator.comparingInt(a -> a.val));
        // k 个链表的头节点加入优先队列（小根堆）
        for (ListNode head : lists) {
            if (head != null) {
                pq.offer(head);
            }
        }
        while (!pq.isEmpty()) {
            // 小根堆中弹出值最小的节点
            ListNode poll = pq.poll();
            cur.next = poll;
            if (poll.next != null) {
                pq.offer(poll.next);
            }
            cur = cur.next;
        }
        return hair.next;
    }
}
