package io.github.llnancy.jingtian.algorithm.leetcode.hot100;

import java.util.LinkedList;
import java.util.List;

/**
 * 全排列
 * <a href="https://leetcode.cn/problems/permutations/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj">https://leetcode.cn/problems/permutations/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj</a>
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2023/10/17
 */
public class Permutations {

    private final List<List<Integer>> res = new LinkedList<>();

    public List<List<Integer>> permute(int[] nums) {
        if (nums == null || nums.length == 0) {
            return res;
        }
        backtrack(nums, new LinkedList<>(), new boolean[nums.length]);
        return res;
    }

    private void backtrack(int[] nums, LinkedList<Integer> list, boolean[] used) {
        if (list.size() == nums.length) {
            res.add(new LinkedList<>(list));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (used[i]) {
                continue;
            }
            list.add(nums[i]);
            used[i] = true;
            backtrack(nums, list, used);
            list.removeLast();
            used[i] = false;
        }
    }
}
