package io.github.llnancy.jingtian.algorithm.leetcode.easy;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * 下一个更大元素 I
 * <a href="https://leetcode.cn/problems/next-greater-element-i/description/">https://leetcode.cn/problems/next-greater-element-i/description/</a>
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2024/6/4
 */
public class NextGreaterElementI {

    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        int[] res = new int[nums1.length];
        Deque<Integer> stack = new LinkedList<>();
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = nums2.length - 1; i >= 0; i--) {
            while (!stack.isEmpty() && nums2[i] > stack.peek()) {
                stack.pop();
            }
            map.put(nums2[i], stack.isEmpty() ? -1 : stack.peek());
            stack.push(nums2[i]);
        }
        for (int i = 0; i < nums1.length; i++) {
            res[i] = map.get(nums1[i]);
        }
        return res;
    }
}
