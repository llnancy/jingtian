package io.github.llnancy.jingtian.algorithm.common;

/**
 * 链表节点类
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/11/25
 */
public class ListNode {

    public int val;

    public ListNode next;

    public ListNode() {
    }

    public ListNode(int val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return "ListNode{" +
                "val=" + val +
                ", next=" + next +
                '}';
    }
}
