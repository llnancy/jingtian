package io.github.llnancy.jingtian.algorithm.leetcode.medium;

import io.github.llnancy.jingtian.algorithm.common.TreeNode;

import java.util.HashMap;
import java.util.Map;

/**
 * 889. 根据前序和后序遍历构造二叉树
 * <a href="https://leetcode.cn/problems/construct-binary-tree-from-preorder-and-postorder-traversal/">https://leetcode.cn/problems/construct-binary-tree-from-preorder-and-postorder-traversal/</a>
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2023/11/24
 */
public class ConstructBinaryTreeFromPreorderAndPostorderTraversal {

    /*
    前序遍历第一个节点或后序遍历最后一个节点为根节点。前序遍历第二个节点为左子树根节点，在后序遍历中找左子树根节点，从而确定左子树长度，最后分别递归左右子树。
     */

    /**
     * 后序遍历值到索引的映射
     */
    private final Map<Integer, Integer> postorderValToIndexMap = new HashMap<>();

    public TreeNode constructFromPrePost(int[] preorder, int[] postorder) {
        for (int i = 0; i < postorder.length; i++) {
            postorderValToIndexMap.put(postorder[i], i);
        }
        return build(preorder, 0, preorder.length - 1, postorder, 0, postorder.length - 1);
    }

    private TreeNode build(int[] preorder, int preStart, int preEnd, int[] postorder, int postStart, int postEnd) {
        if (preStart > preEnd) {
            return null;
        }
        // 仅一个节点
        if (preStart == preEnd) {
            return new TreeNode(preorder[preStart]);
        }
        // 前序遍历第一个节点为根节点
        int rootValue = preorder[preStart];
        // 前序遍历第二个节点为左子树根节点
        int leftRootValue = preorder[preStart + 1];
        // 左子树根节点在后序遍历中的索引
        int leftRootIndex = postorderValToIndexMap.get(leftRootValue);
        // 计算左子树长度
        int leftSize = leftRootIndex - postStart;
        // 构造根节点
        TreeNode root = new TreeNode(rootValue);
        // 递归构建左右子树
        root.left = build(preorder, preStart + 1, preStart + 1 + leftSize, postorder, postStart, leftRootIndex);
        root.right = build(preorder, preStart + leftSize + 2, preEnd, postorder, leftRootIndex + 1, postEnd - 1);
        return root;
    }
}
