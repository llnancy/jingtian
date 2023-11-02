package io.github.llnancy.jingtian.algorithm.leetcode.hot100;

/**
 * 移动零
 * <a href="https://leetcode.cn/problems/move-zeroes/description/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj">https://leetcode.cn/problems/move-zeroes/description/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj</a>
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2023/11/2
 */
public class MoveZeroes {

    /*
    快慢指针技巧。让快指针走在前面探路，找到第一个不为 0 的数就和慢指针交换并让慢指针前进一步。
     */

    public void moveZeroes(int[] nums) {
        int fast = 0;
        int slow = 0;
        while (fast < nums.length) {
            if (nums[fast] != 0) {
                if (fast != slow) {
                    int temp = nums[fast];
                    nums[fast] = nums[slow];
                    nums[slow] = temp;
                }
                slow++;
            }
            fast++;
        }
    }
}
