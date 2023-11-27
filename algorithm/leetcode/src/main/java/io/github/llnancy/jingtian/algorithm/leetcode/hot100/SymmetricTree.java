package io.github.llnancy.jingtian.algorithm.leetcode.hot100;

import io.github.llnancy.jingtian.algorithm.common.TreeNode;

/**
 * 对称二叉树
 * <a href="https://leetcode.cn/problems/symmetric-tree/description/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj">https://leetcode.cn/problems/symmetric-tree/description/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj</a>
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2023/11/17
 */
public class SymmetricTree {

    boolean res = true;

    public boolean isSymmetric(TreeNode root) {
        traverse(root, root);
        return res;
    }

    private void traverse(TreeNode left, TreeNode right) {
        if (left == null && right == null) {
            return;
        }
        if (left == null || right == null) {
            res = false;
            return;
        }
        if (left.val != right.val) {
            res = false;
        }
        if (res) {
            traverse(left.left, right.right);
            traverse(left.right, right.left);
        }
    }
}
