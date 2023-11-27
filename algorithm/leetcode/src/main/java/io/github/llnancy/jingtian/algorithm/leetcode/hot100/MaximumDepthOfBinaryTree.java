package io.github.llnancy.jingtian.algorithm.leetcode.hot100;

import io.github.llnancy.jingtian.algorithm.common.TreeNode;

/**
 * 二叉树的最大深度
 * <a href="https://leetcode.cn/problems/maximum-depth-of-binary-tree/description/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj">https://leetcode.cn/problems/maximum-depth-of-binary-tree/description/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj</a>
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2023/11/17
 */
public class MaximumDepthOfBinaryTree {

    /**
     * 记录最大深度结果
     */
    int max = 0;

    /**
     * 记录当前遍历节点最大深度
     */
    int depth = 0;

    public int maxDepth(TreeNode root) {
        traverse(root);
        return max;
    }

    private void traverse(TreeNode root) {
        if (root == null) {
            return;
        }
        // 前序位置，深度加一
        depth++;
        // 到达叶子节点，更新最大深度
        if (root.left == null && root.right == null) {
            max = Math.max(max, depth);
        }
        traverse(root.left);
        traverse(root.right);
        // 后续位置，深度减一
        depth--;
    }

    public int maxDepthII(TreeNode root) {
        if (root == null) {
            return 0;
        }
        // 左子树最大深度
        int leftMax = maxDepthII(root.left);
        // 右子树最大深度
        int rightMax = maxDepthII(root.right);
        // 当前节点最大深度为左右子树最大深度中较大值加一。
        return Math.max(leftMax, rightMax) + 1;
    }
}
