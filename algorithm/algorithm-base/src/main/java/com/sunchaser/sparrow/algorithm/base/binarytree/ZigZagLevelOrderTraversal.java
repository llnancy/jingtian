package com.sunchaser.sparrow.algorithm.base.binarytree;

import com.sunchaser.sparrow.algorithm.common.TreeNode;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * "之"字形打印二叉树
 * 二叉树的锯齿形层序遍历
 * <p>
 * 从左至右 => 左出 右进
 * 从右至左 => 右出 左进
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/11/14
 */
public class ZigZagLevelOrderTraversal {

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

        System.out.println(zigZagLevelOrder(root));
    }

    public static List<List<Integer>> zigZagLevelOrder(TreeNode root) {
        List<List<Integer>> res = new LinkedList<>();
        Deque<TreeNode> deque = new LinkedList<>();
        if (root != null) {
            deque.offerLast(root);
        }
        boolean isLeft = true;// 从左至右遍历
        while (!deque.isEmpty()) {
            List<Integer> list = new LinkedList<>();
            int size = deque.size();
            while (size > 0) {
                TreeNode poll;
                if (isLeft) {// 从左至右
                    poll = deque.pollFirst();// 左出
                    if (poll.left != null) deque.offerLast(poll.left);// 右进
                    if (poll.right != null) deque.offerLast(poll.right);
                } else {// 从右至左
                    poll = deque.pollLast();// 右出
                    if (poll.right != null) deque.offerFirst(poll.right);// 左进
                    if (poll.left != null) deque.offerFirst(poll.left);
                }
                list.add(poll.val);
                size--;
            }
            // 换方向
            isLeft = !isLeft;
            res.add(list);
        }
        return res;
    }
}
