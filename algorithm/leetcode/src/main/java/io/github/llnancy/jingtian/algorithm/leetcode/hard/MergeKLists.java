package io.github.llnancy.jingtian.algorithm.leetcode.hard;

import io.github.llnancy.jingtian.algorithm.common.SinglyLinkedListNode;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/11/8
 */
public class MergeKLists {
    public static void main(String[] args) {

    }

    public static SinglyLinkedListNode mergeKLists(SinglyLinkedListNode[] listNodes) {
        if (listNodes == null) {
            return null;
        }
        SinglyLinkedListNode hair = new SinglyLinkedListNode();
        SinglyLinkedListNode p = hair;
        Queue<SinglyLinkedListNode> pq = new PriorityQueue<>(
                listNodes.length,
                Comparator.comparingInt(x -> x.val)
        );
        for (SinglyLinkedListNode eachHead : listNodes) {
            if (eachHead != null) {
                pq.add(eachHead);
            }
        }

        while (!pq.isEmpty()) {
            SinglyLinkedListNode poll = pq.poll();
            p.next = poll;
            if (poll.next != null) {
                pq.add(poll.next);
            }
            p = p.next;
        }

        return hair.next;
    }
}
