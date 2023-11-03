package io.github.llnancy.jingtian.algorithm.leetcode.hot100;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 三数之和
 * <a href="https://leetcode.cn/problems/3sum/description/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj">https://leetcode.cn/problems/3sum/description/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj</a>
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2023/10/16
 */
public class ThreeSum {

    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        // 穷举三数之和的第一个数
        for (int i = 0; i < nums.length; i++) {
            // 对 target - nums[i] 计算两数之和（target = 0）
            List<List<Integer>> twoSumRes = twoSum(nums, i + 1, -nums[i]);
            // 如果存在和为 target - nums[i] 的二元组，再加上 nums[i] 就是结果三元组
            for (List<Integer> twoSumRe : twoSumRes) {
                twoSumRe.add(nums[i]);
                res.add(twoSumRe);
            }
            // 跳过第一个数重复的情况，否则会出现重复结果
            // 例如输入是 nums = [1, 1, 1, 2, 3], target = 6，如果不去重，前面 3 个 1 就会导致结果重复。
            while (i < nums.length - 1 && nums[i] == nums[i + 1]) i++;
        }
        return res;
    }

    /**
     * 从 start 开始的两数之和等于 target
     *
     * @param nums   数组
     * @param start  开始索引
     * @param target 目标值
     * @return 结果二元组
     */
    private List<List<Integer>> twoSum(int[] nums, int start, int target) {
        int left = start;
        int right = nums.length - 1;
        List<List<Integer>> res = new ArrayList<>();
        while (left < right) {
            int l = nums[left];
            int r = nums[right];
            int sum = l + r;
            if (sum < target) {
                // 去重
                while (left < right && nums[left] == l) left++;
            } else if (sum > target) {
                // 去重
                while (left < right && nums[right] == r) right--;
            } else {
                // sum = target 得到目标值
                List<Integer> list = new ArrayList<>();
                list.add(l);
                list.add(r);
                res.add(list);
                // 去重
                while (left < right && nums[left] == l) left++;
                while (left < right && nums[right] == r) right--;
            }
        }
        return res;
    }
}
