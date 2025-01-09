package io.github.llnancy.jingtian.algorithm.leetcode.medium;

import io.github.llnancy.jingtian.algorithm.common.TreeNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 652. 寻找重复的子树
 * <a href="https://leetcode.cn/problems/find-duplicate-subtrees/">https://leetcode.cn/problems/find-duplicate-subtrees/</a>
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2024/10/18
 */
public class FindDuplicateSubtrees {

    /*
     * 后序遍历位置寻找重复子树，首先计算以当前节点为根的子树，然后判断以其它节点为根的子树是否与当前重复。
     */

    /**
     * 记录每个子树和出现次数的映射
     */
    private final Map<String, Integer> map = new HashMap<>();

    /**
     * 结果集
     */
    private final List<TreeNode> res = new ArrayList<>();

    public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
        traverse(root);
        return res;
    }

    private String traverse(TreeNode root) {
        if (root == null) {
            return "#";
        }
        String left = traverse(root.left);
        String right = traverse(root.right);
        // 按先序或者后序顺序拼接子树字符串。不能用中序。
        String subTree = left + "," + right + "," + root.val;
        Integer count = map.getOrDefault(subTree, 0);
        if (count == 1) {
            // 说明出现了重复
            res.add(root);
        }
        map.put(subTree, count + 1);
        return subTree;
    }
}
