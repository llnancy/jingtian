package com.sunchaser.sparrow.algorithm.leetcode.easy;

/**
 * 704. 二分查找
 * <p>
 * https://leetcode-cn.com/problems/binary-search/
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/11/19
 */
public class BinarySearch {
    public static int search(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        int mid;
        while (left < right) {
            mid = (left + right) >>> 1;
            if (nums[mid] < target) {
                left = mid + 1;
            } else if (nums[mid] > target) {
                right = mid - 1;
            } else {
                return mid;
            }
        }
        return -1;
    }
}
