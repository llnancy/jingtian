package io.github.llnancy.jingtian.algorithm.leetcode.hot100;

import io.github.llnancy.jingtian.algorithm.common.TreeNode;

/**
 * 538. 把二叉搜索树转换为累加树
 * <a href="https://leetcode.cn/problems/convert-bst-to-greater-tree/">https://leetcode.cn/problems/convert-bst-to-greater-tree/</a>
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2024/10/22
 */
public class ConvertBstToGreaterTree {

    /*
     * 二叉搜索树正常中序遍历结果是升序的，如果修改左右子树遍历顺序则遍历结果是降序的。
     * 用一个外部变量 sum 记录累加和，每次在中序遍历位置累加当前节点的值，sum 的值就是当前节点的新值。
     */

    public TreeNode convertBST(TreeNode root) {
        traverse(root);
        return root;
    }

    /**
     * 记录累加和
     */
    private int sum;

    private void traverse(TreeNode root) {
        if (root == null) {
            return;
        }
        traverse(root.right);
        // 累加当前节点的值
        sum += root.val;
        // 转为累加树
        root.val = sum;
        traverse(root.left);
    }
}
