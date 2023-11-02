package io.github.llnancy.jingtian.algorithm.leetcode.easy;

/**
 * 删除有序数组中的重复项
 * <a href="https://leetcode.cn/problems/remove-duplicates-from-sorted-array/description/">https://leetcode.cn/problems/remove-duplicates-from-sorted-array/description/</a>
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2023/11/2
 */
public class RemoveDuplicatesFromSortedArray {

    /*
    快慢指针技巧。让快指针走在前面探路，找到一个不重复的元素就让慢指针前进一步再赋值，最后返回慢指针前面的元素个数。
     */

    public int removeDuplicates(int[] nums) {
        int fast = 0;
        int slow = 0;
        while (fast < nums.length) {
            // 找到一个不重复的元素
            if (nums[fast] != nums[slow]) {
                // 慢指针前进一步
                slow++;
                // 和快指针不相等时进行赋值
                if (fast != slow) {
                    nums[slow] = nums[fast];
                }
            }
            fast++;
        }
        // 数组长度为索引加一
        return slow + 1;
    }
}
