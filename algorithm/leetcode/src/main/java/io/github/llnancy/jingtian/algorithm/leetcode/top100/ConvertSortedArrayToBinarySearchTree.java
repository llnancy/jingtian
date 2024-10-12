package io.github.llnancy.jingtian.algorithm.leetcode.top100;

import io.github.llnancy.jingtian.algorithm.common.TreeNode;

/**
 * 108. 将有序数组转换为二叉搜索树
 * <a href="https://leetcode.cn/problems/convert-sorted-array-to-binary-search-tree/">https://leetcode.cn/problems/convert-sorted-array-to-binary-search-tree/</a>
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2024/10/11
 */
public class ConvertSortedArrayToBinarySearchTree {

    /**
     * BST 的中序遍历是升序的。本题等同于根据中序遍历构造二叉树，题目要求平衡，故每次选择中间节点作为根节点。
     */

    public TreeNode sortedArrayToBST(int[] nums) {
        return dfs(nums, 0, nums.length - 1);
    }

    private TreeNode dfs(int[] nums, int left, int right) {
        if (left > right) {
            return null;
        }
        int mid = (left + right) / 2;
        TreeNode root = new TreeNode(nums[mid]);
        root.left = dfs(nums, left, mid - 1);
        root.right = dfs(nums, mid + 1, right);
        return root;
    }
}
