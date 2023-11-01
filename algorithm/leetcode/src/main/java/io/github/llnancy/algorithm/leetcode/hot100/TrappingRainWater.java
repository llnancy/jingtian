package io.github.llnancy.algorithm.leetcode.hot100;

/**
 * 接雨水
 * <a href="https://leetcode-cn.com/problems/trapping-rain-water/">https://leetcode-cn.com/problems/trapping-rain-water/</a>
 *
 * @author sunchaser admin@lilu.org.cn
 * @since 2022/1/26
 */
public class TrappingRainWater {

    /*
     对于任意位置i，能够装的水为左边最高的柱子和右边最高的柱子当中较小的那个减去当前位置高度
     water[i] = min(max(height[0..i]), max(height[i..end])) - height[i];
     */

    /**
     * 暴力解法
     */
    public int trap(int[] height) {
        int length = height.length;
        int res = 0;
        for (int i = 1; i < length - 1; i++) {
            int leftMax = 0;
            int rightMax = 0;
            // 左边最高
            for (int j = i; j >= 0; j--) {
                leftMax = Math.max(height[j], leftMax);
            }
            // 右边最高
            for (int j = i; j < length; j++) {
                rightMax = Math.max(height[j], rightMax);
            }
            // 当前位置能装的水
            res += Math.min(leftMax, rightMax) - height[i];
        }
        return res;
    }

    /**
     * 用数组充当备忘录优化后的解法
     */
    public int memoTrap(int[] height) {
        int length = height.length;
        int res = 0;
        // 备忘录，提前计算好每个索引位置的左边最高和右边最高
        int[] leftMaxArr = new int[length];
        int[] rightMaxArr = new int[length];
        // 初始化
        leftMaxArr[0] = height[0];
        rightMaxArr[length - 1] = height[length - 1];
        // 从左到右计算 leftMax
        for (int i = 1; i < length; i++) {
            leftMaxArr[i] = Math.max(height[i], leftMaxArr[i - 1]);
        }
        // 从右到左计算 rightMax
        for (int i = length - 2; i >= 0; i--) {
            rightMaxArr[i] = Math.max(height[i], rightMaxArr[i + 1]);
        }
        // 计算结果
        for (int i = 1; i < length - 1; i++) {
            res += Math.min(leftMaxArr[i], rightMaxArr[i]) - height[i];
        }
        return res;
    }

    /**
     * 双指针解法，边迭代边计算
     */
    public int doublePointTrap(int[] height) {
        int left = 0;
        int right = height.length - 1;
        int leftMax = height[left];
        int rightMax = height[right];
        int res = 0;
        while (left < right) {
            leftMax = Math.max(leftMax, height[left]);
            rightMax = Math.max(rightMax, height[right]);
            if (leftMax < rightMax) {
                res += leftMax - height[left];
                left++;
            } else {
                res += rightMax - height[right];
                right--;
            }
        }
        return res;
    }
}
