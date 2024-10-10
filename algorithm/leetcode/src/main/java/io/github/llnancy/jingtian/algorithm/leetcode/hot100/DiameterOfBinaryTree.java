package io.github.llnancy.jingtian.algorithm.leetcode.hot100;

import io.github.llnancy.jingtian.algorithm.common.TreeNode;

/**
 * 543. 二叉树的直径
 * <a href="https://leetcode.cn/problems/diameter-of-binary-tree/">https://leetcode.cn/problems/diameter-of-binary-tree/</a>
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2023/11/17
 */
public class DiameterOfBinaryTree {

    /*
     * 二叉树的直径等于左右子树最大深度之和
     */

    /**
     * 记录最大直径的外部变量
     */
    int maxDiameter = 0;

    public int diameterOfBinaryTree(TreeNode root) {
        maxDepth(root);
        return maxDiameter;
    }

    private int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftMax = maxDepth(root.left);
        int rightMax = maxDepth(root.right);
        // 后序遍历位置更新全局最大直径
        maxDiameter = Math.max(maxDiameter, leftMax + rightMax);
        // 当前节点最大深度
        return Math.max(leftMax, rightMax) + 1;
    }
}
