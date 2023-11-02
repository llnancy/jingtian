package io.github.llnancy.jingtian.algorithm.leetcode.middle;

import io.github.llnancy.jingtian.algorithm.common.TreeNode;

/**
 * 236. 二叉树的最近公共祖先
 * <p>
 * https://leetcode-cn.com/problems/lowest-common-ancestor-of-a-binary-tree/
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/11/14
 */
public class BinaryTreeLowestCommonAncestor {

    public static void main(String[] args) {

    }

    /**
     * 后序遍历，递归
     */
    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return null;
        }
        if (root == p || root == q) {
            return root;
        }
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        if (left != null && right != null) {
            return root;
        }
        if (left == null && right == null) {
            return null;
        }
        return left == null ? right : left;
    }
}
