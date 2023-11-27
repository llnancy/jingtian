package io.github.llnancy.jingtian.algorithm.leetcode.medium;

import io.github.llnancy.jingtian.algorithm.common.TreeNode;

import java.util.HashMap;
import java.util.Map;

/**
 * 从中序与后序遍历序列构造二叉树
 * <a href="https://leetcode.cn/problems/construct-binary-tree-from-inorder-and-postorder-traversal/description/">https://leetcode.cn/problems/construct-binary-tree-from-inorder-and-postorder-traversal/description/</a>
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2023/11/24
 */
public class ConstructBinaryTreeFromInorderAndPostorderTraversal {

    /*
    后序遍历最后一个元素为根节点。通过根节点的值将中序遍历和后序遍历数组分为两部分，然后分别递归构造根节点的左右子树。
     */

    private final Map<Integer, Integer> inorderValToIndexMap = new HashMap<>();

    public TreeNode buildTree(int[] inorder, int[] postorder) {
        for (int i = 0; i < inorder.length; i++) {
            inorderValToIndexMap.put(inorder[i], i);
        }
        return build(inorder, 0, inorder.length - 1, postorder, 0, postorder.length - 1);
    }

    private TreeNode build(int[] inorder, int inStart, int inEnd, int[] postorder, int postStart, int postEnd) {
        if (inStart > inEnd) {
            return null;
        }
        // 后序遍历最后一个元素为根节点
        int rootVal = postorder[postEnd];
        // 找根节点在中序遍历中的索引位置
        int index = inorderValToIndexMap.get(rootVal);
        // 左子树长度
        int leftSize = index - inStart;
        // 构造根节点
        TreeNode root = new TreeNode(rootVal);
        // 递归构建左右子树
        root.left = build(inorder, inStart, index - 1, postorder, postStart, postStart + leftSize - 1);
        root.right = build(inorder, index + 1, inEnd, postorder, postStart + leftSize, postEnd - 1);
        return root;
    }
}
