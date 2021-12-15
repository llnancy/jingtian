package com.sunchaser.sparrow.leetcode.hard;

/**
 * 4. 寻找两个正序数组的中位数
 * <p>
 * https://leetcode-cn.com/problems/median-of-two-sorted-arrays/
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/11/28
 */
public class MedianOfTwoSortedArrays {

    /**
     * 开辟一个新的数组空间
     * 合并两个有序数组后再求中位数。
     */
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        int[] merge = new int[m + n];
        if (m == 0) {
            if (n % 2 == 0) {
                return (nums2[n / 2 - 1] + nums2[n / 2]) / 2.0;
            } else {
                return nums2[n / 2];
            }
        }

        if (n == 0) {
            if (m % 2 == 0) {
                return (nums1[m / 2 - 1] + nums1[m / 2]) / 2.0;
            } else {
                return nums1[m / 2];
            }
        }

        int count = 0;
        int i = 0;
        int j = 0;
        while (count != m + n) {
            if (i == m) {
                while (j != n) {
                    merge[count++] = nums2[j++];
                }
                break;
            }
            if (j == n) {
                while (i != m) {
                    merge[count++] = nums1[i++];
                }
                break;
            }

            if (nums1[i] < nums2[j]) {
                merge[count++] = nums1[i++];
            } else {
                merge[count++] = nums2[j++];
            }
        }

        if (count % 2 == 0) {
            return (merge[count / 2 - 1] + merge[count / 2]) / 2.0;
        } else {
            return merge[count / 2];
        }
    }
}
