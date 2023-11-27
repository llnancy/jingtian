package io.github.llnancy.jingtian.algorithm.leetcode.top100;

import java.util.HashMap;
import java.util.Map;

/**
 * 随机链表的复制
 * <a href="https://leetcode.cn/problems/copy-list-with-random-pointer/description/?envType=study-plan-v2&envId=top-100-liked">https://leetcode.cn/problems/copy-list-with-random-pointer/description/?envType=study-plan-v2&envId=top-100-liked</a>
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2023/11/15
 */
public class CopyListWithRandomPointer {

    /*
    深拷贝，递归或两次遍历写法。
     */

    private final Map<Node, Node> cacheNode = new HashMap<>();

    public Node copyRandomList(Node head) {
        if (cacheNode.containsKey(head)) {
            return cacheNode.get(head);
        }
        Node newHead = new Node(head.val);
        cacheNode.put(head, newHead);
        if (head.next != null) {
            newHead.next = copyRandomList(head.next);
        }
        if (head.random != null) {
            newHead.random = copyRandomList(head.random);
        }
        return newHead;
    }

    public Node copyRandomList2(Node head) {
        Map<Node, Node> cloneMapping = new HashMap<>();
        for (Node p = head; p != null; p = p.next) {
            if (!cloneMapping.containsKey(p)) {
                cloneMapping.put(p, new Node(p.val));
            }
        }
        for (Node p = head; p != null; p = p.next) {
            if (p.next != null) {
                cloneMapping.get(p).next = cloneMapping.get(p.next);
            }
            if (p.random != null) {
                cloneMapping.get(p).random = cloneMapping.get(p.random);
            }
        }
        return cloneMapping.get(head);
    }
}

class Node {

    int val;

    Node next;

    Node random;

    public Node(int val) {
        this.val = val;
        this.next = null;
        this.random = null;
    }
}
