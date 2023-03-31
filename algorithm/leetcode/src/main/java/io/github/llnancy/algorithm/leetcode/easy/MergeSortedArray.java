package io.github.llnancy.algorithm.leetcode.easy;

import java.util.Arrays;

/**
 * 88. 合并两个有序数组
 * <p>
 * https://leetcode-cn.com/problems/merge-sorted-array/
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/11/13
 */
public class MergeSortedArray {

    public static void main(String[] args) {
        int[] nums1 = {1, 2, 3, 0, 0, 0};
        int[] nums2 = {2, 5, 6};
        merge(nums1, 3, nums2, 3);
        System.out.println(Arrays.toString(nums1));
    }

    public static void merge(int[] nums1, int m, int[] nums2, int n) {
        int p1 = m - 1;
        int p2 = n - 1;
        int p = nums1.length - 1;
        while (p2 >= 0) {
            if (p1 < 0) {
                nums1[p--] = nums2[p2--];
            } else if (nums1[p1] < nums2[p2]) {
                nums1[p--] = nums2[p2--];
            } else {
                nums1[p--] = nums1[p1--];
            }
        }
    }
}
