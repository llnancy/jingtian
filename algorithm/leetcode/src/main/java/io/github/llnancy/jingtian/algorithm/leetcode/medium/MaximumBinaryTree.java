package io.github.llnancy.jingtian.algorithm.leetcode.medium;

import io.github.llnancy.jingtian.algorithm.common.TreeNode;

/**
 * 最大二叉树
 * <a href="https://leetcode.cn/problems/maximum-binary-tree/description/">https://leetcode.cn/problems/maximum-binary-tree/description/</a>
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

    private TreeNode build(int[] nums, int leftIndex, int rightIndex) {
        if (leftIndex > rightIndex) {
            return null;
        }
        // 找到数组中的最大值和对应索引
        int index = -1;
        int maxValue = Integer.MIN_VALUE;
        for (int i = leftIndex; i <= rightIndex; i++) {
            if (nums[i] > maxValue) {
                maxValue = nums[i];
                index = i;
            }
        }
        // 构造根节点
        TreeNode root = new TreeNode(maxValue);
        // 递归调用构造左右子树
        root.left = build(nums, leftIndex, index - 1);
        root.right = build(nums, index + 1, rightIndex);
        return root;
    }
}
