package io.github.llnancy.jingtian.algorithm.leetcode.easy;

import io.github.llnancy.jingtian.algorithm.common.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * 144. 二叉树的前序遍历
 * <a href="https://leetcode.cn/problems/binary-tree-preorder-traversal/">https://leetcode.cn/problems/binary-tree-preorder-traversal/</a>
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2024/10/9
 */
public class BinaryTreePreorderTraversal {

    /**
     * 记录前序遍历结果的外部变量
     */
    private final List<Integer> res = new ArrayList<>();

    public List<Integer> preorderTraversal(TreeNode root) {
        traverse(root);
        return res;
    }

    private void traverse(TreeNode root) {
        if (root == null) {
            return;
        }
        // 前序位置，更新前序遍历结果
        res.add(root.val);
        traverse(root.left);
        traverse(root.right);
    }

    /*
     * 定义：输入根节点，返回该二叉树的前序遍历结果
     */
    public List<Integer> preorderTraversalII(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        res.add(root.val);
        // 利用定义得到左右子树的前序遍历结果
        res.addAll(preorderTraversalII(root.left));
        res.addAll(preorderTraversalII(root.right));
        return res;
    }
}
