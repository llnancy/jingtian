package io.github.llnancy.jingtian.algorithm.leetcode.hot100;

import io.github.llnancy.jingtian.algorithm.common.TreeNode;

/**
 * 二叉树展开为链表
 * <a href="https://leetcode.cn/problems/flatten-binary-tree-to-linked-list/description/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj">https://leetcode.cn/problems/flatten-binary-tree-to-linked-list/description/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj</a>
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2023/11/23
 */
public class FlattenBinaryTreeToLinkedList {

    /*
    在后序遍历位置，先将 right 指针指向左子树，再将原右子树拼接到左子树末尾。
     */

    public void flatten(TreeNode root) {
        if (root == null) {
            return;
        }
        flatten(root.left);
        flatten(root.right);
        // 后序遍历位置
        // 得到递归后的左右子树
        TreeNode left = root.left;
        TreeNode right = root.right;

        // 将 right 指针指向左子树
        root.left = null;
        root.right = left;

        // 遍历到原左子树末尾
        TreeNode p = root;
        while (p.right != null) {
            p = p.right;
        }
        // 将原右子树拼接
        p.right = right;
    }
}
