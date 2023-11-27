package io.github.llnancy.jingtian.algorithm.leetcode.hot100;

/**
 * 最大子数组和
 * <a href="https://leetcode.cn/problems/maximum-subarray/description/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj">https://leetcode.cn/problems/maximum-subarray/description/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj</a>
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2023/11/1
 */
public class MaximumSubarray {

    /*
    思路一：滑动窗口。在窗口元素和大于 0 时扩大窗口，小于 0 时缩小窗口。
    思路二：动态规划。假设 f(i) 表示以第 i 个数结尾的连续子数组的最大和，那只需求出每个位置的 f(i)，然后比较最大值即可，而每个位置的 f(i) 要么加入前面 f(i - 1)，要么自己单独成序列。
     */

    public int maximumSubArray(int[] nums) {
        int left = 0;
        int right = 0;
        int windowSum = 0;
        int maxSum = Integer.MIN_VALUE;
        while (right < nums.length) {
            // 窗口右移
            windowSum += nums[right++];

            // 更新结果
            maxSum = Math.max(windowSum, maxSum);

            // 和小于 0 时缩小窗口。
            while (windowSum < 0) {
                windowSum -= nums[left++];
            }
        }
        return maxSum;
    }

    public int maximumSubArray2(int[] nums) {
        int pre = 0;
        int maxAns = nums[0];
        for (int num : nums) {
            // 要么加入前面的序列，要么自己单独成序列
            pre = Math.max(pre + num, num);
            // 更新最大值
            maxAns = Math.max(maxAns, pre);
        }
        return maxAns;
    }
}
