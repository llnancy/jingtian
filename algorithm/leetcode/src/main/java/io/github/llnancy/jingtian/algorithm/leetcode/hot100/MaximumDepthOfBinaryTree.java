package io.github.llnancy.jingtian.algorithm.leetcode.hot100;

import io.github.llnancy.jingtian.algorithm.common.TreeNode;

/**
 * 104. 二叉树的最大深度
 * <a href="https://leetcode.cn/problems/maximum-depth-of-binary-tree/">https://leetcode.cn/problems/maximum-depth-of-binary-tree/</a>
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2023/11/17
 */
public class MaximumDepthOfBinaryTree {

    /*
     * 遍历二叉树：用一个外部变量记录每个节点所在的深度，每次取最大值就可以得到最大深度。
     * 分解问题计算答案：一颗二叉树的最大深度可以通过子树的最大深度推导出来。
     */

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
        // 后序位置，深度减一
        depth--;
    }

    /*
     * 定义：输入根节点，返回该二叉树的最大深度
     */
    public int maxDepthII(TreeNode root) {
        if (root == null) {
            return 0;
        }
        // 利用定义分别计算左右子树最大深度
        // 左子树最大深度
        int leftMax = maxDepthII(root.left);
        // 右子树最大深度
        int rightMax = maxDepthII(root.right);
        // 整颗树的最大深度为左右子树最大深度中较大值加上根节点自己。
        return Math.max(leftMax, rightMax) + 1;
    }
}
