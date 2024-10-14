package io.github.llnancy.jingtian.algorithm.leetcode.medium;

import io.github.llnancy.jingtian.algorithm.common.TreeNode;

/**
 * 654. 最大二叉树
 * <a href="https://leetcode.cn/problems/maximum-binary-tree/">https://leetcode.cn/problems/maximum-binary-tree/</a>
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2023/11/24
 */
public class MaximumBinaryTree {

    /*
    先遍历数组找到最大值，即整颗树的根节点，然后递归调用最大值左右两边的子数组构造左右子树。
     */

    public TreeNode constructMaximumBinaryTree(int[] nums) {
        return build(nums, 0, nums.length - 1);
    }

    private TreeNode build(int[] nums, int left, int right) {
        if (left > right) {
            return null;
        }
        // 找到数组中的最大值和对应索引
        int maxIndex = -1;
        int maxValue = Integer.MIN_VALUE;
        for (int i = left; i <= right; i++) {
            if (nums[i] > maxValue) {
                maxIndex = i;
                maxValue = nums[i];
            }
        }
        // 构造根节点
        TreeNode root = new TreeNode(maxValue);
        // 递归调用构造左右子树
        root.left = build(nums, left, maxIndex - 1);
        root.right = build(nums, maxIndex + 1, right);
        return root;
    }
}
