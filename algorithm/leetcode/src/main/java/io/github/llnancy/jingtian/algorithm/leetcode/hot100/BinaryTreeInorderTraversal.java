package io.github.llnancy.jingtian.algorithm.leetcode.hot100;

import io.github.llnancy.jingtian.algorithm.common.TreeNode;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * 94. 二叉树的中序遍历
 * <a href="https://leetcode.cn/problems/binary-tree-inorder-traversal/">https://leetcode.cn/problems/binary-tree-inorder-traversal/</a>
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2023/11/17
 */
public class BinaryTreeInorderTraversal {

    /*
     * 遍历思路：用一个外部变量记录中序遍历结果，在二叉树遍历框架中序位置更新结果。
     * 分解问题思路：一颗二叉树的中序遍历结果可以通过子树的中序遍历结果推导出来。
     */

    /**
     * 记录中序遍历结果的外部变量
     */
    private final List<Integer> res = new ArrayList<>();

    public List<Integer> inorderTraversal(TreeNode root) {
        traverse(root);
        return res;
    }

    private void traverse(TreeNode root) {
        if (root == null) {
            return;
        }
        traverse(root.left);
        // 中序位置，更新中序遍历结果
        res.add(root.val);
        traverse(root.right);
    }

    /*
     * 定义：输入根节点，返回该二叉树的中序遍历结果
     */
    public List<Integer> inorderTraversalII(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        // 利用定义得到左右子树的中序遍历结果
        res.addAll(inorderTraversalII(root.left));
        res.add(root.val);
        res.addAll(inorderTraversalII(root.right));
        return res;
    }

    public List<Integer> inorderTraversalIII(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        Deque<TreeNode> stack = new LinkedList<>();
        while (root != null || !stack.isEmpty()) {
            // 不断往左子树迭代，压栈模拟递归
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            // 左子树走到叶子节点了，出栈并转向右子树
            root = stack.pop();
            res.add(root.val);
            root = root.right;
        }
        return res;
    }
}
