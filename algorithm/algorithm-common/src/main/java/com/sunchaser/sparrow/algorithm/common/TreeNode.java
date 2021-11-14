package com.sunchaser.sparrow.algorithm.common;

/**
 * 二叉树节点
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/11/13
 */
public class TreeNode {
    public Integer val;
    public TreeNode left;
    public TreeNode right;

    public TreeNode() {
    }

    public TreeNode(Integer val) {
        this.val = val;
    }

    public TreeNode(Integer val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}
