package io.github.llnancy.jingtian.algorithm.leetcode.hot100;

import io.github.llnancy.jingtian.algorithm.common.TreeNode;

import java.util.HashMap;
import java.util.Map;

/**
 * 从前序与中序遍历序列构造二叉树
 * <a href="https://leetcode.cn/problems/construct-binary-tree-from-preorder-and-inorder-traversal/description/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj">https://leetcode.cn/problems/construct-binary-tree-from-preorder-and-inorder-traversal/description/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj</a>
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2023/11/23
 */
public class ConstructBinaryTreeFromPreorderAndInorderTraversal {

    /*
    前序遍历数组的第一个元素就是根节点。通过根节点的值将前序遍历和中序遍历数组分为两部分，然后分别递归构造根节点的左右子树。
     */

    /**
     * 中序遍历值到索引的映射
     */
    private final Map<Integer, Integer> inorderValToIndexMap = new HashMap<>();

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        for (int i = 0; i < inorder.length; i++) {
            inorderValToIndexMap.put(inorder[i], i);
        }
        return build(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1);
    }

    private TreeNode build(int[] preorder, int preStart, int preEnd, int[] inorder, int inStart, int inEnd) {
        if (preStart > preEnd) {
            return null;
        }
        // 前序遍历数组第一个元素为根节点
        int rootVal = preorder[preStart];
        // 找根节点在中序遍历中的索引位置
        int index = inorderValToIndexMap.get(rootVal);
        // 左子树长度
        int leftSize = index - inStart;
        // 构造根节点
        TreeNode root = new TreeNode(rootVal);
        // 递归构建左右子树
        root.left = build(preorder, preStart + 1, preStart + leftSize, inorder, inStart, index - 1);
        root.right = build(preorder, preStart + leftSize + 1, preEnd, inorder, index + 1, inEnd);
        return root;
    }
}
