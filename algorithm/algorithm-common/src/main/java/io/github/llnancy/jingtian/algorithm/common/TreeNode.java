package io.github.llnancy.jingtian.algorithm.common;

/**
 * 二叉树节点
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/11/13
 */
public class TreeNode {

    public int val;

    public TreeNode left;

    public TreeNode right;

    public TreeNode() {
    }

    public TreeNode(int val) {
        this.val = val;
    }

    public TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}
