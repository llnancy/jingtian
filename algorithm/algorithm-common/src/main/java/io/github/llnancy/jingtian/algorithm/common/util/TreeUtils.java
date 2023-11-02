package io.github.llnancy.jingtian.algorithm.common.util;

import io.github.llnancy.jingtian.algorithm.common.TreeNode;

/**
 * 二叉树工具类
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/11/20
 */
public class TreeUtils {
    private TreeUtils() {
    }

    public static TreeNode generateSimpleBinaryTree() {
        TreeNode root = new TreeNode(3);
        TreeNode node1 = new TreeNode(9);
        TreeNode node2 = new TreeNode(20);
        TreeNode node3 = new TreeNode(15);
        TreeNode node4 = new TreeNode(7);
        root.left = node1;
        root.right = node2;
        node2.left = node3;
        node2.right = node4;
        return root;
    }
}
