package io.github.llnancy.jingtian.algorithm.leetcode.medium;

import io.github.llnancy.jingtian.algorithm.common.TreeNode;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * 103. 二叉树的锯齿形层序遍历
 * 之字形打印二叉树
 * <a href="https://leetcode-cn.com/problems/binary-tree-zigzag-level-order-traversal/">https://leetcode-cn.com/problems/binary-tree-zigzag-level-order-traversal/</a>
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/11/14
 */
public class BinaryTreeZigzagLevelOrderTraversal {

    /**
     * Deque 双端队列
     */
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        // 标记变量，控制遍历方向，true 表示从左往右，false 表示从右往左
        boolean flag = true;
        Deque<TreeNode> deque = new LinkedList<>();
        deque.offer(root);
        while (!deque.isEmpty()) {
            LinkedList<Integer> level = new LinkedList<>();
            int size = deque.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = deque.poll();
                if (flag) {
                    level.addLast(node.val);
                } else {
                    level.addFirst(node.val);
                }
                if (node.left != null) {
                    deque.offer(node.left);
                }
                if (node.right != null) {
                    deque.offer(node.right);
                }
            }
            flag = !flag;
            result.add(level);
        }
        return result;
    }
}
