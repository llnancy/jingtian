package io.github.llnancy.algorithm.leetcode.hot100;

/**
 * 在排序数组中查找元素的第一个和最后一个位置
 * <a href="https://leetcode.cn/problems/find-first-and-last-position-of-element-in-sorted-array/description/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj">https://leetcode.cn/problems/find-first-and-last-position-of-element-in-sorted-array/description/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj</a>
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2023/10/26
 */
public class FindFirstAndLastPositionOfElementInSortedArray {

    public int[] searchRange(int[] nums, int target) {
        return new int[]{firstPosition(nums, target), lastPosition(nums, target)};
    }

    private int firstPosition(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] < target) {
                left = mid + 1;
            } else if (nums[mid] > target) {
                right = mid - 1;
            } else if (nums[mid] == target) {
                // 寻找左边界：收缩右边界
                right = mid - 1;
            }
        }
        // 越界和没找到 target
        if (left >= nums.length || nums[left] != target) {
            return -1;
        }
        return left;
    }

    private int lastPosition(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] < target) {
                left = mid + 1;
            } else if (nums[mid] > target) {
                right = mid - 1;
            } else if (nums[mid] == target) {
                // 寻找右边界：收缩左边界。
                left = mid + 1;
            }
        }
        // 越界和没找到 target
        if (right < 0 || nums[right] != target) {
            return -1;
        }
        return right;
    }
}
