package io.github.llnancy.jingtian.algorithm.leetcode.hot100;

import io.github.llnancy.jingtian.algorithm.common.TreeNode;

/**
 * 二叉树的直径
 * <a href="https://leetcode.cn/problems/diameter-of-binary-tree/description/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj">https://leetcode.cn/problems/diameter-of-binary-tree/description/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj</a>
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2023/11/17
 */
public class DiameterOfBinaryTree {

    int max = 0;

    public int diameterOfBinaryTree(TreeNode root) {
        traverse(root);
        return max;
    }

    private int traverse(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftMax = traverse(root.left);
        int rightMax = traverse(root.right);
        // 计算全局最大直径
        max = Math.max(max, leftMax + rightMax);
        // 当前节点最大直径
        return Math.max(leftMax, rightMax) + 1;
    }
}
