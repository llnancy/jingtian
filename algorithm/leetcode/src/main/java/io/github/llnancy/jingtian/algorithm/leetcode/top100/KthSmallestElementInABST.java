package io.github.llnancy.jingtian.algorithm.leetcode.top100;

import io.github.llnancy.jingtian.algorithm.common.TreeNode;

/**
 * 230. 二叉搜索树中第 K 小的元素
 * <a href="https://leetcode.cn/problems/kth-smallest-element-in-a-bst/">https://leetcode.cn/problems/kth-smallest-element-in-a-bst/</a>
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2023/11/22
 */
public class KthSmallestElementInABST {

    /*
    二叉搜索树的中序遍历结果是有序的。中序遍历第 k 个元素即为第 k 小的元素。
     */

    public int kthSmallest(TreeNode root, int k) {
        traverse(root, k);
        return res;
    }

    /**
     * 记录结果值
     */
    private int res;

    /**
     * 记录当前遍历元素排名
     */
    private int rank;

    private void traverse(TreeNode root, int k) {
        if (root == null) {
            return;
        }
        // 中序遍历
        traverse(root.left, k);
        rank++;
        if (rank == k) {
            // 找到第 k 小的元素
            res = root.val;
            return;
        }
        traverse(root.right, k);
    }
}
