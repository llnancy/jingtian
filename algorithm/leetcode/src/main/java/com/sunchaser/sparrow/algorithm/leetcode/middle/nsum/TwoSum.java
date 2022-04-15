package com.sunchaser.sparrow.algorithm.leetcode.middle.nsum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 两数之和
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/11/12
 */
public class TwoSum {

    public static void main(String[] args) {
        int[] nums = {2, 7, 11, 15};
        int target = 9;
        System.out.println(Arrays.toString(twoSum(nums, target)));

        int[] nums2 = {1, 3, 1, 2, 2, 3};
        int target2 = 4;
        System.out.println(twoSumTarget(nums2, target2));
    }

    /**
     * 排序+双指针
     * 返回和为target的两个数的下标
     *
     * @param nums
     * @param target
     * @return
     */
    public static int[] twoSum(int[] nums, int target) {
        Arrays.sort(nums);
        int left = 0;
        int right = nums.length - 1;
        while (left < right) {
            int sum = nums[left] + nums[right];
            if (sum < target) {
                left++;
            } else if (sum > target) {
                right--;
            } else {
                return new int[]{left, right};
            }
        }
        return new int[]{-1, -1};
    }

    /**
     * 返回和为target的两个数，去重
     *
     * @param nums
     * @param target
     * @return
     */
    public static List<List<Integer>> twoSumTarget(int[] nums, int target) {
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        int left = 0;
        int right = nums.length - 1;
        while (left < right) {
            int sum = nums[left] + nums[right];
            int i = nums[left];
            int j = nums[right];
            if (sum < target) {
                while (left < right && nums[left] == i) {
                    left++;
                }
            } else if (sum > target) {
                while (left < right && nums[right] == j) {
                    right--;
                }
            } else {
                List<Integer> list = new ArrayList<>();
                list.add(i);
                list.add(j);
                res.add(list);
                while (left < right && nums[left] == i) {
                    left++;
                }
                while (left < right && nums[right] == j) {
                    right--;
                }
            }
        }
        return res;
    }
}
