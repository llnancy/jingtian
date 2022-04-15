package com.sunchaser.sparrow.algorithm.leetcode.hard;

/**
 * 接雨水
 * <p>
 * https://leetcode-cn.com/problems/trapping-rain-water/
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/11/24
 */
public class TrappingRainWater {
    public static int trap(int[] height) {
        int n = height.length;
        int res = 0;
        for (int i = 0; i < n - 1; i++) {// i < n - 1：i = n - 1的时候最右边是空的接不到雨水
            int leftMax = 0;
            int rightMax = 0;
            for (int j = i; j < n; j++) {// 找i右边最高的柱子
                rightMax = Math.max(rightMax, height[j]);
            }
            for (int j = i; j >= 0; j--) {
                leftMax = Math.max(leftMax, height[j]);
            }
            res += Math.min(leftMax, rightMax) - height[i];
        }
        return res;
    }

    public static int trap2(int[] height) {
        int n = height.length;
        int res = 0;
        // 用两个数组充当备忘录
        int[] leftMax = new int[n];
        int[] rightMax = new int[n];
        // 初始化base case
        leftMax[0] = height[0];
        rightMax[n - 1] = height[n - 1];
        // 从左向右计算leftMax
        for (int i = 1; i < n; i++) {
            leftMax[i] = Math.max(leftMax[i - 1], height[i]);
        }
        // 从右向左计算rightMax
        for (int i = n - 2; i >= 0; i++) {
            rightMax[i] = Math.max(rightMax[i + 1], height[i]);
        }
        // 累加结果
        for (int i = 1; i < n - 1; i++) {
            res += Math.min(leftMax[i], rightMax[i]) - height[i];
        }
        return res;
    }

    public static int trap3(int[] height) {
        int n = height.length;
        int left = 0;
        int right = n - 1;
        int leftMax = height[left];
        int rightMax = height[right];
        int res = 0;
        while (left <= right) {
            leftMax = Math.max(leftMax, height[left]);
            rightMax = Math.max(rightMax, height[right]);
            if (leftMax < rightMax) {
                res += leftMax - height[left];
                left++;
            } else if (leftMax > rightMax) {
                res += rightMax - height[right];
                right--;
            }
        }
        return res;
    }
}
