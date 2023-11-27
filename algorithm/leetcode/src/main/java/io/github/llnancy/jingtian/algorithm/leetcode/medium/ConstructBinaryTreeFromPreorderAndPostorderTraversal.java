package io.github.llnancy.jingtian.algorithm.leetcode.medium;

import io.github.llnancy.jingtian.algorithm.common.TreeNode;

import java.util.HashMap;
import java.util.Map;

/**
 * 根据前序和后序遍历构造二叉树
 * <a href="https://leetcode.cn/problems/construct-binary-tree-from-preorder-and-postorder-traversal/description/">https://leetcode.cn/problems/construct-binary-tree-from-preorder-and-postorder-traversal/description/</a>
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2023/11/24
 */
public class ConstructBinaryTreeFromPreorderAndPostorderTraversal {

    /*
    前序遍历第一个节点或后序遍历最后一个节点为根节点。前序遍历第二个节点为左子树根节点，在后序遍历中找左子树根节点，从而确定左子树长度，最后分别递归左右子树。
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
        if (preStart == preEnd) {
            return new TreeNode(preorder[preStart]);
        }
        // 前序遍历第一个节点为根节点
        int rootVal = preorder[preStart];
        // 前序遍历第二个节点为左子树根节点
        int leftRootVal = preorder[preStart + 1];
        // 通过左子树根节点在后序遍历中的索引值计算左子树长度
        int leftSize = postorderValToIndexMap.get(leftRootVal) - postStart + 1;
        // 构造根节点
        TreeNode root = new TreeNode(rootVal);
        // 递归构建左右子树
        root.left = build(preorder, preStart + 1, preStart + leftSize, postorder, postStart, postStart + leftSize - 1);
        root.right = build(preorder, preStart + leftSize + 1, preEnd, postorder, postStart + leftSize, postEnd - 1);
        return root;
    }
}
