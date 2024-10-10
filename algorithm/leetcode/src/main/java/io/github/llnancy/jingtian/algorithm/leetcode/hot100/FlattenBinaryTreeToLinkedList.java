package io.github.llnancy.jingtian.algorithm.leetcode.hot100;

import io.github.llnancy.jingtian.algorithm.common.TreeNode;

/**
 * 114. 二叉树展开为链表
 * <a href="https://leetcode.cn/problems/flatten-binary-tree-to-linked-list/">https://leetcode.cn/problems/flatten-binary-tree-to-linked-list/</a>
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2023/11/23
 */
public class FlattenBinaryTreeToLinkedList {

    /*
     * 分解问题思路：一颗二叉树展开为链表可以通过子树展开为链表的结果推导出来。
     * 如果左右子树已经展开为链表了，则先将左子树换到右子树位置，再将原右子树拼接到末尾。
     */

    /*
     * 定义：输入二叉树根节点，该二叉树会展开为链表
     */
    public void flatten(TreeNode root) {
        if (root == null) {
            return;
        }
        // 利用定义将左右子树展开为链表
        flatten(root.left);
        flatten(root.right);

        // 后序遍历位置
        // 得到递归后的左右子树
        TreeNode left = root.left;
        TreeNode right = root.right;

        // 将左子树换到右子树位置
        root.left = null;
        root.right = left;

        // 遍历到原左子树末尾
        TreeNode p = root;
        while (p.right != null) {
            p = p.right;
        }
        // 拼接原右子树
        p.right = right;
    }
}
