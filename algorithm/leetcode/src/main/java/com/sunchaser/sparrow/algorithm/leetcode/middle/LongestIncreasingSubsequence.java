package com.sunchaser.sparrow.algorithm.leetcode.middle;

import java.util.Arrays;

/**
 * 300. 最长递增子序列
 * <p>
 * https://leetcode-cn.com/problems/longest-increasing-subsequence/
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/11/19
 */
public class LongestIncreasingSubsequence {

    public static void main(String[] args) {
        int[] nums = {10, 9, 2, 5, 3, 7, 101, 18};
        System.out.println(lengthOfLIS(nums));

        int[] nums2 = {6, 3, 5, 10, 11, 2, 9, 1, 13, 7, 4, 8, 12};
        int[] nums3 = {2, 1, 5, 3, 6, 4, 8, 9, 7};
        System.out.println(lengthOfLISByPoker(nums3));
    }

    public static int lengthOfLIS(int[] nums) {
        int[] dp = new int[nums.length];
        Arrays.fill(dp, 1);
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }
        int res = 0;
        for (int d : dp) {
            res = Math.max(res, d);
        }
        return res;
    }

    /**
     * 蜘蛛纸牌思路
     * <p>
     * 二分
     */
    public static int lengthOfLISByPoker(int[] nums) {
        int[] top = new int[nums.length];
        int piles = 0;
        for (int i = 0; i < nums.length; i++) {
            int poker = nums[i];

            int left = 0;
            int right = piles;
            int mid;
            while (left < right) {
                mid = (left + right) / 2;
                if (top[mid] > poker) {
                    right = mid;
                } else if (top[mid] < poker) {
                    left = mid + 1;
                } else {
                    right = mid;
                }
            }
            if (left == piles) {
                piles++;
            }
            top[left] = poker;
        }
        return piles;
    }
}
