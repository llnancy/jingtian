package io.github.llnancy.jingtian.algorithm.leetcode.hot100;

import io.github.llnancy.jingtian.algorithm.common.TreeNode;

/**
 * 226. 翻转二叉树
 * <a href="https://leetcode.cn/problems/invert-binary-tree/">https://leetcode.cn/problems/invert-binary-tree/</a>
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2023/11/17
 */
public class InvertBinaryTree {

    /*
     * 遍历思路：遍历每个节点时交换左右子节点。
     * 分解问题思路：翻转一个二叉树的结果可由子树的翻转结果推导出来。
     */

    public TreeNode invertTree(TreeNode root) {
        traverse(root);
        return root;
    }

    private void traverse(TreeNode root) {
        if (root == null) {
            return;
        }

        // 每一个节点需要做的事就是交换它的左右子节点
        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;

        // 遍历框架，去遍历左右子树的节点
        traverse(root.left);
        traverse(root.right);
    }

    /**
     * 定义：输入二叉树根节点，返回翻转后的二叉树根节点
     */
    public TreeNode invertTreeII(TreeNode root) {
        if (root == null) {
            return null;
        }

        // 利用定义翻转左右子树
        TreeNode left = invertTreeII(root.left);
        TreeNode right = invertTreeII(root.right);

        // 翻转当前节点
        root.left = right;
        root.right = left;

        return root;
    }
}
