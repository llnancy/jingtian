package com.sunchaser.sparrow.algorithm.base;

import java.util.Arrays;

/**
 * 二分查找：https://leetcode-cn.com/problems/binary-search/
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/10/7
 */
public class BinarySearch {
    public static void main(String[] args) {
        int[] nums = {1, 3, 5, 2, 7, 4, 6, 8};
        int target = 2;
        int search = search(nums, target);
        System.out.println(search);
        int binarySearch = Arrays.binarySearch(nums, target);
        System.out.println(binarySearch);
    }

    public static int search(int[] nums, int target) {
        int l = 0;
        int r = nums.length - 1;
        while (l <= r) {
            int m = (l + r) >>> 1;
            if (nums[m] == target) {
                return m;
            } else if (nums[m] < target) {
                l = m + 1;
            } else {
                r = m - 1;
            }
        }
        return -1;
    }
}
