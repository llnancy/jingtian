package com.sunchaser.sparrow.algorithm.base.binarytree;

import com.sunchaser.sparrow.algorithm.common.TreeNode;

/**
 * 二叉树的最近公共祖先
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/11/14
 */
public class LowestCommonAncestor {

    public static void main(String[] args) {

    }

    /**
     * 后序遍历进行回溯
     */
    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) return root;
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        if (left != null && right != null) return root;
        if (left == null && right == null) return null;
        return left == null ? right : left;
    }
}
