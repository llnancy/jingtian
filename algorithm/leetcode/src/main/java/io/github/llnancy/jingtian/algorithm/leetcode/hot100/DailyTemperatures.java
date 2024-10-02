package io.github.llnancy.jingtian.algorithm.leetcode.hot100;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

/**
 * 每日温度
 * <a href="https://leetcode.cn/problems/daily-temperatures/?envType=problem-list-v2&envId=2cktkvj">...</a>
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2024/5/10
 */
public class DailyTemperatures {

    public static void main(String[] args) {
        int[] temperatures = {73, 74, 75, 71, 69, 72, 76, 73};
        DailyTemperatures d = new DailyTemperatures();
        int[] ints = d.dailyTemperatures(temperatures);
        System.out.println(Arrays.toString(ints));
    }

    public int[] dailyTemperatures(int[] temperatures) {
        int len = temperatures.length;
        // 存放元素索引
        Deque<Integer> stack = new LinkedList<>();
        int[] res = new int[len];
        for (int i = len - 1; i >= 0; i--) {
            while (!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]) {
                stack.pop();
            }
            res[i] = stack.isEmpty() ? 0 : stack.peek() - i;
            stack.push(i);
        }
        return res;
    }

    /**
     * 单调栈算法模版
     *
     * @param nums 原数组
     * @return 结果数组
     */
    public int[] nextGreaterElement(int[] nums) {
        int length = nums.length;
        int[] res = new int[length];
        Deque<Integer> stack = new LinkedList<>();
        for (int i = length - 1; i >= 0; i--) {
            while (!stack.isEmpty() && nums[i] > stack.peek()) {
                stack.pop();
            }
            res[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(nums[i]);
        }
        return res;
    }
}
