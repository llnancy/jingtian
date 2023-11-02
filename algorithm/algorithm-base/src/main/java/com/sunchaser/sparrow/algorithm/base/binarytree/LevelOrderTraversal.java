package com.sunchaser.sparrow.algorithm.base.binarytree;

import io.github.llnancy.jingtian.algorithm.common.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 二叉树层次遍历
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/11/13
 */
public class LevelOrderTraversal {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(3);
        TreeNode node1 = new TreeNode(9);
        TreeNode node2 = new TreeNode(20);
        TreeNode node3 = new TreeNode(15);
        TreeNode node4 = new TreeNode(7);
        root.left = node1;
        root.right = node2;
        node2.left = node3;
        node2.right = node4;

        System.out.println(levelOrderTraversal(root));
    }

    public static List<List<Integer>> levelOrderTraversal(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        if (root != null) {
            queue.offer(root);
        }
        while (!queue.isEmpty()) {
            List<Integer> list = new ArrayList<>();
            int size = queue.size();
            while (size > 0) {
                TreeNode poll = queue.poll();
                list.add(poll.val);
                if (poll.left != null) queue.offer(poll.left);
                if (poll.right != null) queue.offer(poll.right);
                size--;
            }
            res.add(list);
        }
        return res;
    }
}
