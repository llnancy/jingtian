package io.github.llnancy.jingtian.algorithm.leetcode.medium;

/**
 * 33. 搜索旋转排序数组
 * <p>
 * https://leetcode-cn.com/problems/search-in-rotated-sorted-array/
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/11/17
 */
public class SearchInRotatedSortedArray {

    public static void main(String[] args) {
        int[] nums = {1, 3};
        System.out.println(search(nums, 1));
    }

    public static int search(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        int mid;
        while (left <= right) {
            mid = (left + right) >>> 1;
            if (nums[mid] == target) {
                return mid;
            }
            if (nums[mid] < nums[right]) {// 右边有序
                if (nums[mid] < target && target <= nums[right]) {// 小于等于nums[right]
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            } else if (nums[mid] > nums[right]) {// 左边有序
                if (nums[left] <= target && target < nums[mid]) {// nums[left]小于等于
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
        }
        return -1;
    }
}
