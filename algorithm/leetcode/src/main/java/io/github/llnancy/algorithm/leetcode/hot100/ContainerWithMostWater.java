package io.github.llnancy.algorithm.leetcode.hot100;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 11. 盛最多水的容器
 * <a href="https://leetcode-cn.com/problems/container-with-most-water/">https://leetcode-cn.com/problems/container-with-most-water/</a>
 *
 * @author sunchaser admin@lilu.org.cn
 * @since 2022/1/26
 */
public class ContainerWithMostWater {

    public int maxArea(int[] height) {
        int left = 0;
        int right = height.length - 1;
        int res = 0;
        while (left < right) {
            int curArea = Math.min(height[left], height[right]) * (right - left);
            res = Math.max(res, curArea);
            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }
        return res;
    }
}
