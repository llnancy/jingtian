package io.github.llnancy.jingtian.algorithm.leetcode.medium;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 下一个更大元素 II
 * <a href="https://leetcode.cn/problems/next-greater-element-ii/description/">https://leetcode.cn/problems/next-greater-element-ii/description/</a>
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2024/6/4
 */
public class NextGreaterElementII {

    public int[] nextGreaterElements(int[] nums) {
        int len = nums.length;
        int[] res = new int[len];
        Deque<Integer> stack = new LinkedList<>();
        for (int i = 2 * len - 1; i >= 0; i--) {
            while (!stack.isEmpty() && nums[i % len] >= stack.peek()) {
                stack.pop();
            }
            res[i % len] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(nums[i % len]);
        }
        return res;
    }
}
