package io.github.llnancy.jingtian.algorithm.leetcode.top100;

import java.util.HashMap;
import java.util.Map;

/**
 * 随机链表的复制
 * <a href="https://leetcode.cn/problems/copy-list-with-random-pointer/">https://leetcode.cn/problems/copy-list-with-random-pointer/</a>
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2023/11/15
 */
public class CopyListWithRandomPointer {

    /*
    深拷贝。方法一：在原链表上遍历三次。方法二：借助 HashMap，遍历两次。
     */

    /**
     * 随机链表节点类。
     */
    public static class Node {

        int val;

        Node next;

        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }

    public Node copyRandomList(Node head) {
        if (head == null) return null;
        Node p = head;
        // 第一次遍历：创建新节点
        while (p != null) {
            Node newNode = new Node(p.val);
            Node next = p.next;
            newNode.next = next;
            p.next = newNode;
            p = next;
        }
        p = head;
        // 第二次遍历：连接新节点的随机指针
        while (p != null) {
            if (p.random != null) {
                p.next.random = p.random.next;
            }
            p = p.next.next;
        }
        p = head;
        Node hair = new Node(0);
        Node cur = hair;
        // 第三次遍历：分离新节点形成新链表
        while (p != null) {
            cur.next = p.next;
            cur = cur.next;
            p.next = cur.next;
            p = p.next;
        }
        return hair.next;
    }

    public Node copyRandomList2(Node head) {
        // 存储旧节点 -> 新节点的映射
        Map<Node, Node> map = new HashMap<>();
        Node p = head;
        // 第一次遍历：创建新节点存入 map
        while (p != null) {
            map.put(p, new Node(p.val));
            p = p.next;
        }
        p = head;
        // 第二次遍历：连接新节点的 next 和 random 指针
        while (p != null) {
            Node newNode = map.get(p);
            if (p.next != null) {
                newNode.next = map.get(p.next);
            }
            if (p.random != null) {
                newNode.random = map.get(p.random);
            }
            p = p.next;
        }
        return map.get(head);
    }
}
