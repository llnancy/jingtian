package io.github.llnancy.jingtian.algorithm.leetcode.hot100;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * 组合总和
 * <a href="https://leetcode.cn/problems/combination-sum/description/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj">https://leetcode.cn/problems/combination-sum/description/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj</a>
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2023/10/27
 */
public class CombinationSum {

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        if (candidates == null || candidates.length == 0) {
            return Collections.emptyList();
        }
        List<List<Integer>> res = new ArrayList<>();
        backtrack(candidates, target, res, 0, 0);
        return res;
    }

    private final Deque<Integer> track = new LinkedList<>();

    private void backtrack(int[] candidates, int target, List<List<Integer>> res, int start, int sum) {
        if (sum == target) {
            res.add(new LinkedList<>(track));
            return;
        }
        if (sum > target) {
            return;
        }
        for (int i = start; i < candidates.length; i++) {
            int candidate = candidates[i];
            // 做选择
            track.add(candidate);
            sum += candidate;
            // 递归进入下一层回溯树。注意这里 start 还是 i，可以重复使用元素
            backtrack(candidates, target, res, i, sum);
            // 撤销选择
            track.removeLast();
            sum -= candidate;
        }
    }
}
