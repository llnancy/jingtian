package com.sunchaser.sparrow.leetcode.easy;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 给定一个整数数组 nums和一个目标值 target，请你在该数组中找出和为目标值的那两个整数，并返回他们的数组下标。
 * <p>
 * 你可以假设每种输入只会对应一个答案。但是，你不能重复利用这个数组中同样的元素
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/two-sum
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author sunchaser
 * @since JDK8 2019/7/15
 */
public class TwoNumsSum {
    public static void main(String[] args) {
        int[] nums = {2, 7, 11, 15};
        int target = 9;
        int[] result = fun2(nums, target);
        Arrays.stream(result).forEach(System.out::println);
    }

    /**
     * 暴力法
     *
     * @param nums
     * @param target
     * @return
     */
    public static int[] fun1(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        throw new IllegalArgumentException("no two nums match");
    }

    /**
     * 一遍哈希表法
     *
     * @param nums
     * @param target
     * @return
     */
    public static int[] fun2(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            // 待找寻的第二个数
            int num2 = target - nums[i];
            // 如果哈希表中存在第二个数则已找到符合条件的两个数，返回即可。
            if (map.containsKey(num2)) {
                return new int[]{map.get(num2), i};
            }
            // 否则没找到，将当前数作为key，数组下标索引作为value放入哈希表中。
            map.put(nums[i], i);
        }
        throw new IllegalArgumentException("no two nums match");
    }
}
