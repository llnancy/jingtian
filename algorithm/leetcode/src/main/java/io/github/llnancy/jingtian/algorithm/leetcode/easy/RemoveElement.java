package io.github.llnancy.jingtian.algorithm.leetcode.easy;

/**
 * 移除元素
 * <a href="https://leetcode.cn/problems/remove-element/description/">https://leetcode.cn/problems/remove-element/description/</a>
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2023/11/3
 */
public class RemoveElement {

    public int removeElement(int[] nums, int val) {
        int fast = 0;
        int slow = 0;
        while (fast < nums.length) {
            // 遇到不等于 val 的元素
            if (nums[fast] != val) {
                if (fast != slow) {
                    // 和 slow 指针交换
                    nums[slow] = nums[fast];
                }
                slow++;
            }
            fast++;
        }
        // 先交换再移动的慢指针，所以数组长度就等于 slow。
        return slow;
    }
}
