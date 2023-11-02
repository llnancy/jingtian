package io.github.llnancy.jingtian.algorithm.leetcode.hot100;

/**
 * 寻找两个正序数组的中位数
 * <a href="https://leetcode.cn/problems/median-of-two-sorted-arrays/">https://leetcode.cn/problems/median-of-two-sorted-arrays/</a>
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/5/25
 */
public class MedianOfTwoSortedArrays {

    /**
     * 先将两个有序的数组合并为一个大的有序数组，然后根据数组长度是奇数还是偶数，返回对应中位数。
     * 时间复杂度 O(m + n)
     * 空间复杂的 O(m + n)
     */
    public static double findMedianSortedArrays1(int[] nums1, int[] nums2) {
        int len1 = nums1.length;
        int len2 = nums2.length;
        int[] nums = new int[len1 + len2];
        // 数组 1 为空
        if (len1 == 0) {
            if (len2 % 2 == 0) {
                // 数组 2 长度为偶数，返回中间两个数的平均值。
                return (nums2[len2 / 2 - 1] + nums2[len2 / 2]) / 2.0;
            } else {
                // 数组 2 长度为奇数，返回中间那个数。
                return nums2[len2 / 2];
            }
        }
        // 数组 2 为空
        if (len2 == 0) {
            if (len1 % 2 == 0) {
                // 数组 1 长度为偶数，返回中间两个数的平均值。
                return (nums1[len1 / 2 - 1] + nums1[len1 / 2]) / 2.0;
            } else {
                // 数组 1 长度为奇数，返回中间那个数。
                return nums1[len1 / 2];
            }
        }
        // 数组 1 和 2 都不为空，合并两个数组
        int m = 0, n = 0;
        for (int i = 0; i < len1 + len2; i++) {
            // 数组 1 合并完成
            if (m == len1) {
                // 将数组 2 的剩余部分合并
                while (n != len2) {
                    nums[i] = nums2[n++];
                }
                break;
            }
            // 数组 2 合并完成
            if (n == len2) {
                // 将数组 1 的剩余部分合并
                while (m != len1) {
                    nums[i] = nums1[m++];
                }
            }
            if (nums1[m] < nums2[n]) {
                nums[i] = nums1[m++];
            } else {
                nums[i] = nums2[n++];
            }
        }

        // 合并完成，nums 是整体有序的数组
        if (nums.length % 2 == 0) {
            // 偶数
            return (nums[nums.length / 2] + nums[nums.length / 2 - 1]) / 2.0;
        } else {
            // 奇数
            return nums[nums.length / 2];
        }
    }

    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int length1 = nums1.length;
        int length2 = nums2.length;
        int totalLength = length1 + length2;
        if (totalLength % 2 == 1) {
            int midIndex = totalLength / 2;
            return getKthElement(nums1, nums2, midIndex + 1);
        } else {
            int midIndex1 = totalLength / 2 - 1;
            int midIndex2 = totalLength / 2;
            return (getKthElement(nums1, nums2, midIndex1 + 1) + getKthElement(nums1, nums2, midIndex2 + 1)) / 2.0;
        }
    }

    private static double getKthElement(int[] nums1, int[] nums2, int k) {
        int length1 = nums1.length;
        int length2 = nums2.length;
        int index1 = 0;
        int index2 = 0;
        while (true) {
            if (index1 == length1) {
                return nums2[index2 + k - 1];
            }
            if (index2 == length2) {
                return nums1[index1 + k - 1];
            }
            if (k == 1) {
                return Math.min(nums1[index1], nums2[index2]);
            }
            int half = k / 2;
            int newIndex1 = Math.min(index1 + half, length1) - 1;
            int newIndex2 = Math.min(index2 + half, length2) - 1;
            int pivot1 = nums1[newIndex1];
            int pivot2 = nums2[newIndex2];
            if (pivot1 < pivot2) {
                k -= (newIndex1 - index1 + 1);
                index1 = newIndex1 + 1;
            } else {
                k -= (newIndex2 - index2 + 1);
                index2 = newIndex2 + 1;
            }
        }
    }
}
