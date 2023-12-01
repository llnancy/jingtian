package io.github.llnancy.jingtian.algorithm.leetcode.hot100;

import io.github.llnancy.jingtian.algorithm.common.TreeNode;

/**
 * 二叉树的最近公共祖先
 * <a href="https://leetcode.cn/problems/lowest-common-ancestor-of-a-binary-tree/description/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj">https://leetcode.cn/problems/lowest-common-ancestor-of-a-binary-tree/description/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj</a>
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2023/11/24
 */
public class LowestCommonAncestorOfABinaryTree {

    /*
    如果一个节点能够在它的左右子树中分别找到 p 和 q，则该节点为 LCA 节点。
     */

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        return find(root, p.val, q.val);
    }

    private TreeNode find(TreeNode root, int val1, int val2) {
        if (root == null) {
            return null;
        }
        // 前序位置
        if (root.val == val1 || root.val == val2) {
            // 遇到目标值，直接返回
            return root;
        }
        TreeNode left = find(root.left, val1, val2);
        TreeNode right = find(root.right, val1, val2);
        // 后序位置
        if (left != null && right != null) {
            // 已经知道左右子树中是否存在目标值
            return root;
        }
        // 因为 p q 一定在二叉树中，所以只要找到一个就能断定是最近公共祖先，不然早就满足前面的条件走不动这一行了。
        return left != null ? left : right;
    }
}
