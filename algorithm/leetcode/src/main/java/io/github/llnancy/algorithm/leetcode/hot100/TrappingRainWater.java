package io.github.llnancy.algorithm.leetcode.hot100;

/**
 * 接雨水
 * https://leetcode-cn.com/problems/trapping-rain-water/
 *
 * @author sunchaser admin@lilu.org.cn
 * @since 2022/1/26
 */
public class TrappingRainWater {

    public static void main(String[] args) {
        int[] height = {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        int trap = trap(height);
        int memoTrap = memoTrap(height);
        int doublePointTrap = doublePointTrap(height);
        System.out.println(trap);
        System.out.println(memoTrap);
        System.out.println(doublePointTrap);
    }

    /**
     * 对于任意位置i，能够装的水为左边最高的柱子和右边最高的柱子当中较小的那个减去当前位置高度
     * water[i] = min(max(height[0..i]), max(height[i..end])) - height[i];
     */

    /**
     * 暴力解法
     */
    public static int trap(int[] height) {
        int n = height.length;
        int res = 0;
        for (int i = 1; i < n - 1; i++) {
            int lMax = 0, rMax = 0;
            // 右边最高
            for (int j = i; j < n; j++) {
                rMax = Math.max(rMax, height[j]);
            }
            // 左边最高
            for (int j = i; j >= 0; j--) {
                lMax = Math.max(lMax, height[j]);
            }
            // 当前位置能装的水
            res += Math.min(lMax, rMax) - height[i];
        }
        return res;
    }

    /**
     * 用数组充当备忘录优化后的解法
     */
    public static int memoTrap(int[] height) {
        int n = height.length;
        int res = 0;
        // 备忘录，提前计算好
        int[] lMaxArr = new int[n];
        int[] rMaxArr = new int[n];
        // 初始化
        lMaxArr[0] = height[0];
        rMaxArr[n - 1] = height[n - 1];
        // 从左到右计算lMax
        for (int i = 1; i < n; i++) {
            lMaxArr[i] = Math.max(height[i], lMaxArr[i - 1]);
        }
        // 从右到左计算rMax
        for (int i = n - 2; i >= 0; i--) {
            rMaxArr[i] = Math.max(height[i], rMaxArr[i + 1]);
        }
        // 计算结果
        for (int i = 1; i < n - 1; i++) {
            res += Math.min(lMaxArr[i], rMaxArr[i]) - height[i];
        }
        return res;
    }

    /**
     * 双指针解法，边迭代边计算
     */
    public static int doublePointTrap(int[] height) {
        int left = 0;
        int right = height.length - 1;
        int lMax = height[left];
        int rMax = height[right];
        int res = 0;
        while (left < right) {
            lMax = Math.max(lMax, height[left]);
            rMax = Math.max(rMax, height[right]);
            if (lMax < rMax) {
                res += lMax - height[left];
                left++;
            } else {
                res += rMax - height[right];
                right--;
            }
        }
        return res;
    }
}
