package io.github.llnancy.jingtian.algorithm.leetcode.middle;

import java.util.LinkedList;
import java.util.List;

/**
 * 46. 全排列
 * <p>
 * https://leetcode-cn.com/problems/permutations/
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/11/17
 */
public class Permutations {

    /**
     * result = []
     * def backtrack(路径, 选择列表):
     * if 满足结束条件:
     * result.add(路径)
     * return
     * <p>
     * for 选择 in 选择列表:
     * 做选择
     * backtrack(路径, 选择列表)
     * 撤销选择
     */

    static List<List<Integer>> res = new LinkedList<>();

    public static List<List<Integer>> permute(int[] nums) {
        LinkedList<Integer> track = new LinkedList<>();
        backtrack(nums, track);
        return res;
    }

    /**
     * 路径：记录在track中
     * 选择列表：nums中不存在于track中的那些元素
     * 结束条件：nums中的元素全部都在track中出现
     */
    public static void backtrack(int[] nums, LinkedList<Integer> track) {
        if (track.size() == nums.length) {
            res.add(new LinkedList<>(track));
            return;
        }
        for (int num : nums) {
            if (track.contains(num)) {
                continue;
            }
            track.add(num);
            backtrack(nums, track);
            track.removeLast();
        }
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 3};
        System.out.println(permute(nums));
    }
}
