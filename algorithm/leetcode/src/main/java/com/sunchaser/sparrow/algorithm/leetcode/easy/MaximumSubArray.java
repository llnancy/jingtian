package com.sunchaser.sparrow.algorithm.leetcode.easy;

/**
 * 53. 最大子序和
 * <p>
 * https://leetcode-cn.com/problems/maximum-subarray/
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/11/15
 */
public class MaximumSubArray {

    /**
     * 动态规划
     * 要么自成一派，要么和前面的数组合并
     * 用一个变量记录整体最大值
     */
    public static int maxSubArray(int[] nums) {
        int n = nums.length;
        if (n == 0) return 0;
        // base case
        // 第一个元素前面没有任何子数组
        int dp0 = nums[0];
        int dp1;
        int max = dp0;
        // 状态转移方程
        for (int i = 1; i < n; i++) {
            dp1 = Math.max(nums[i], nums[i] + dp0);
            dp0 = dp1;
            max = Math.max(max, dp1);
        }
        return max;
    }
}
