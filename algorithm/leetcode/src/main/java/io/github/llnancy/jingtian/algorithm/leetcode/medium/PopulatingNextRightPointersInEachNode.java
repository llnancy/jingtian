package io.github.llnancy.jingtian.algorithm.leetcode.medium;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 116. 填充每个节点的下一个右侧节点指针
 * <a href="https://leetcode.cn/problems/populating-next-right-pointers-in-each-node/">https://leetcode.cn/problems/populating-next-right-pointers-in-each-node/</a>
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2024/10/10
 */
public class PopulatingNextRightPointersInEachNode {

    /*
     * BFS 广度优先遍历
     */

    /**
     * Definition for a Node
     */
    public static class Node {

        int val;

        Node left;

        Node right;

        Node next;
    }

    public Node connect(Node root) {
        if (root == null) {
            return null;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Node poll = queue.poll();
                if (i < size - 1) {
                    poll.next = queue.peek();
                }
                if (poll.left != null) {
                    queue.offer(poll.left);
                }
                if (poll.right != null) {
                    queue.offer(poll.right);
                }
            }
        }
        return root;
    }
}
