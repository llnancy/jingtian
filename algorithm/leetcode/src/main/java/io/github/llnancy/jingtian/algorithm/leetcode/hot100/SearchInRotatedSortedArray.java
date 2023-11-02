package io.github.llnancy.jingtian.algorithm.leetcode.hot100;

/**
 * 搜索旋转排序数组
 * <a href="https://leetcode.cn/problems/search-in-rotated-sorted-array/solutions/221435/duo-si-lu-wan-quan-gong-lue-bi-xu-miao-dong-by-swe/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj">https://leetcode.cn/problems/search-in-rotated-sorted-array/solutions/221435/duo-si-lu-wan-quan-gong-lue-bi-xu-miao-dong-by-swe/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj</a>
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2023/10/26
 */
public class SearchInRotatedSortedArray {

    /*
    二分查找。旋转后的数组部分有序，我们从中间开始进行二分，一定有一部分是有序的，而另一部分是无序的。
    根据有序部分我们确定二分的上下界：
    1. 如果索引区间 [left, mid - 1] 的部分是有序的，且 target 的大小在这之间，则我们应该缩小二分范围为 [left, mid - 1]，否则二分范围为 [mid + 1, right]。
    2. 如果索引区间 [mid, right] 的部分是有序的，且 target 的大小在这之间，则我们应该缩小二分范围为 [mid + 1, right]，否则二分范围为 [left, mid - 1]。
     */

    public int search(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return mid;
            }

            // 左边有序
            if (nums[left] <= nums[mid]) {
                // target 在 [nums[left], nums[mid]) 之间
                if (nums[left] <= target && target < nums[mid]) {
                    // 右侧缩小
                    right = mid - 1;
                } else {
                    // 在右侧无序区间内
                    left = mid + 1;
                }
            } else {
                // 右边有序
                // target 在 (nums[mid], nums[right]] 之间
                if (nums[mid] < target && target <= nums[right]) {
                    // 左侧缩小
                    left = mid + 1;
                } else {
                    // 在左侧无序区间内
                    right = mid - 1;
                }
            }
        }
        return -1;
    }
}
