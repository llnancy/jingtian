package io.github.llnancy.jingtian.algorithm.leetcode.hot100;

import io.github.llnancy.jingtian.algorithm.common.TreeNode;

/**
 * 101. 对称二叉树
 * <a href="https://leetcode.cn/problems/symmetric-tree/">https://leetcode.cn/problems/symmetric-tree/</a>
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2023/11/17
 */
public class SymmetricTree {

    /*
     * 遍历思路：用一个外部变量记录是否对称；用两个指针遍历二叉树，先序遍历位置判断两个指针是否有值和值是否相等，更新结果。如果对称，则遍历子树，指针一的左子树和指针二的右子树；指针一的右子树和指针二的左子树。
     * 分解问题思路：一颗二叉树是否对称可由子树是否对称的结果推导出来。
     */

    /**
     * 记录是否对称的外部变量
     */
    boolean res = true;

    public boolean isSymmetric(TreeNode root) {
        traverse(root, root);
        return res;
    }

    private void traverse(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return;
        }
        // 先序遍历位置
        // 判断两个指针是否有值
        if (p == null || q == null) {
            res = false;
            return;
        }
        // 判断两个指针值是否相等
        if (p.val != q.val) {
            res = false;
            return;
        }
        if (res) {
            traverse(p.left, q.right);
            traverse(p.right, q.left);
        }
    }

    public boolean isSymmetricII(TreeNode root) {
        return isSubSymmetric(root.left, root.right);
    }

    /**
     * 定义：输入两棵树的根节点，返回这两个树是否对称
     */
    private boolean isSubSymmetric(TreeNode left, TreeNode right) {
        if (left == null && right == null) {
            return true;
        }
        if (left == null || right == null) {
            return false;
        }
        // 判断两棵树的当前节点是否对称
        if (left.val != right.val) {
            return false;
        }
        // 利用定义得到 left 的左子树和 right 的右子树是否对称
        boolean m = isSubSymmetric(left.left, right.right);
        // 利用定义得到 left 的右子树和 right 的左子树是否对称
        boolean n = isSubSymmetric(left.right, right.left);
        return m && n;
    }
}
