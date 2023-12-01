package io.github.llnancy.jingtian.algorithm.leetcode.hot100;

import io.github.llnancy.jingtian.algorithm.common.TreeNode;

/**
 * 二叉树中的最大路径和
 * <a href="https://leetcode.cn/problems/binary-tree-maximum-path-sum/description/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj">https://leetcode.cn/problems/binary-tree-maximum-path-sum/description/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj</a>
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2023/11/27
 */
public class BinaryTreeMaximumPathSum {

    private int maxSum = Integer.MIN_VALUE;

    public int maxPathSum(TreeNode root) {
        // 定义 traverse 函数返回节点 root 的最大贡献值
        traverse(root);
        return maxSum;
    }

    private int traverse(TreeNode root) {
        if (root == null) {
            return 0;
        }
        // 递归计算左右子树的最大贡献值
        int leftMax = Math.max(traverse(root.left), 0);
        int rightMax = Math.max(traverse(root.right), 0);
        // 更新最大路径和结果
        maxSum = Math.max(maxSum, root.val + leftMax + rightMax);
        // 返回当前节点最大贡献值
        return root.val + Math.max(leftMax, rightMax);
    }
}
