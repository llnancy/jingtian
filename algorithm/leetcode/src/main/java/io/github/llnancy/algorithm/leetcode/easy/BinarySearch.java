package io.github.llnancy.algorithm.leetcode.easy;

/**
 * 704. 二分查找
 * <a href="https://leetcode-cn.com/problems/binary-search/">https://leetcode.cn/problems/binary-search/description/</a>
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/11/19
 */
public class BinarySearch {

    public int search(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
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
