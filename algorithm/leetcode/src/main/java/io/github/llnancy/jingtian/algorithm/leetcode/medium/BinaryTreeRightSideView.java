package io.github.llnancy.jingtian.algorithm.leetcode.medium;

import io.github.llnancy.jingtian.algorithm.common.TreeNode;
import io.github.llnancy.jingtian.algorithm.common.util.TreeUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 199. 二叉树的右视图
 * <p>
 * https://leetcode-cn.com/problems/binary-tree-right-side-view/
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/11/20
 */
public class BinaryTreeRightSideView {

    public static void main(String[] args) {
        TreeNode root = TreeUtils.generateSimpleBinaryTree();
        System.out.println(rightSideView(root));
    }

    public static List<Integer> rightSideView(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        if (root != null) {
            queue.offer(root);
        }
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size > 0) {
                TreeNode poll = queue.poll();
                if (poll.left != null) {
                    queue.offer(poll.left);
                }
                if (poll.right != null) {
                    queue.offer(poll.right);
                }
                if (size == 1) {
                    res.add(poll.val);
                }
                size--;
            }
        }
        return res;
    }
}
