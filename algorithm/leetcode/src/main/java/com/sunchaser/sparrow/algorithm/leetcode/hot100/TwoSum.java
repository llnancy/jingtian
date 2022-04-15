package com.sunchaser.sparrow.algorithm.leetcode.hot100;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 两数之和
 * https://leetcode-cn.com/problems/two-sum/
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/12/17
 */
public class TwoSum {
    public static void main(String[] args) {
        int[] nums = {1, 3, 5, 7, 9};
        int[] twoSum = twoSum(nums, 10);
        System.out.println(Arrays.toString(twoSum));
    }

    public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> valToIndex = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (valToIndex.containsKey(target - nums[i])) {
                return new int[]{valToIndex.get(target - nums[i]), i};
            } else {
                valToIndex.put(nums[i], i);
            }
        }
        return new int[]{-1, -1};
    }
}
