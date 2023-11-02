package io.github.llnancy.jingtian.algorithm.leetcode.middle.nsum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 15. 三数之和
 * <p>
 * <a href="https://leetcode-cn.com/problems/3sum/">https://leetcode-cn.com/problems/3sum/</a>
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/11/13
 */
public class ThreeSum {

    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 0, 4, -1};
        System.out.println(threeSum(nums, 3));
    }

    public static List<List<Integer>> threeSum(int[] nums, int target) {
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {// 穷举三数之和的第一个数
            // 对 target - nums[i] 计算两数之和
            List<List<Integer>> twoSums = twoSum(nums, i + 1, target - nums[i]);
            // 如果存在和为 target - nums[i] 的二元组，再加上 nums[i] 就是结果三元组
            for (List<Integer> twoSum : twoSums) {
                twoSum.add(0, nums[i]);
                res.add(twoSum);
            }
            // 跳过第一个数字重复的情况
            while (i < nums.length - 1 && nums[i] == nums[i + 1]) i++;
        }
        return res;
    }

    /**
     * 从 start 开始的两数之和等于 target
     *
     * @param nums
     * @param start
     * @param target
     * @return
     */
    public static List<List<Integer>> twoSum(int[] nums, int start, int target) {
        int left = start;
        int right = nums.length - 1;
        List<List<Integer>> res = new ArrayList<>();
        while (left < right) {
            int sum = nums[left] + nums[right];
            int i = nums[left];
            int j = nums[right];
            if (sum < target) {
                while (left < right && nums[left] == i) left++;
            } else if (sum > target) {
                while (left < right && nums[right] == j) right--;
            } else {
                List<Integer> list = new ArrayList<>();
                list.add(i);
                list.add(j);
                res.add(list);
                while (left < right && nums[left] == i) left++;
                while (left < right && nums[right] == j) right--;
            }
        }
        return res;
    }
}
