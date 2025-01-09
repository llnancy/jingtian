package io.github.llnancy.jingtian.algorithm.leetcode.hot100;

import io.github.llnancy.jingtian.algorithm.common.TreeNode;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

/**
 * 297. 二叉树的序列化与反序列化
 * <a href="https://leetcode.cn/problems/serialize-and-deserialize-binary-tree/">https://leetcode.cn/problems/serialize-and-deserialize-binary-tree/</a>
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2024/10/21
 */
public class SerializeAndDeserializeBinaryTree {

    /*
     * 本题可使用深度优先遍历中的先序遍历和后序遍历，以及广度优先遍历实现。
     */

    public String serialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        doSerialize(root, sb);
        return sb.toString();
    }

    private void doSerialize(TreeNode root, StringBuilder sb) {
        if (root == null) {
            sb.append("#,");
            return;
        }
        sb.append(root.val).append(",");
        doSerialize(root.left, sb);
        doSerialize(root.right, sb);
    }

    public TreeNode deserialize(String data) {
        Deque<String> nodes = new LinkedList<>(Arrays.asList(data.split(",")));
        return doDeserialize(nodes);
    }

    private TreeNode doDeserialize(Deque<String> nodes) {
        if (nodes.isEmpty()) {
            return null;
        }
        String first = nodes.removeFirst();
        if ("#".equals(first)) {
            return null;
        }
        TreeNode root = new TreeNode(Integer.parseInt(first));
        root.left = doDeserialize(nodes);
        root.right = doDeserialize(nodes);
        return root;
    }
}
