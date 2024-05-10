package io.github.llnancy.jingtian.algorithm.leetcode.hot100;

import java.util.LinkedList;
import java.util.List;

/**
 * 子集
 * <a href="https://leetcode.cn/problems/subsets/description/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj">https://leetcode.cn/problems/subsets/description/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj</a>
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2023/12/26
 */
public class Subsets {

    private final List<List<Integer>> res = new LinkedList<>();

    public List<List<Integer>> subsets(int[] nums) {
        backtrack(nums, new LinkedList<>(), 0);
        return res;
    }

    private void backtrack(int[] nums, LinkedList<Integer> track, int start) {
        res.add(new LinkedList<>(track));
        for (int i = start; i < nums.length; i++) {
            track.add(nums[i]);
            backtrack(nums, track, i + 1);
            track.removeLast();
        }
    }
}
