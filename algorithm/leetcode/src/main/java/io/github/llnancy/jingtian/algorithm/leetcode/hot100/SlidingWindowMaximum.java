package io.github.llnancy.jingtian.algorithm.leetcode.hot100;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * 滑动窗口最大值
 * <a href="https://leetcode.cn/problems/sliding-window-maximum/description/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj">https://leetcode.cn/problems/sliding-window-maximum/description/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj</a>
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2023/11/7
 */
public class SlidingWindowMaximum {

    /*
    单调队列。使用单调队列实现滑动窗口，先填满窗口的前 k - 1 个位置，最后一个元素移入窗口时，记录当前窗口最大值，然后移出最先进入单调队列的元素。
     */

    public int[] maxSlidingWindow(int[] nums, int k) {
        MonotonicQueue window = new MonotonicQueue();
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            if (i < k - 1) {
                // 先填满窗口的前 k - 1
                window.push(nums[i]);
            } else {
                // 窗口向前移动，加入新元素
                window.push(nums[i]);
                // 记录当前窗口的最大值
                res.add(window.max());
                // 移出最先进入单调队列的元素
                /*
                比如说 nums 有 8 个元素，k = 7，先填满窗口前 6 个元素，
                然后窗口右移，加入第七个元素，此时 i = 6，i - k + 1 = 0，即移出最先进入单调队列的元素
                 */
                window.pop(nums[i - k + 1]);
            }
        }
        // List<Integer> 转 int[]
        return res.stream()
                .mapToInt(Integer::intValue)
                .toArray();
    }

    /**
     * 单调队列
     */
    static class MonotonicQueue {

        /**
         * 双向链表。用做队列，维护其中元素自队尾到队首单调递增
         */
        private final Deque<Integer> maxQueue = new LinkedList<>();

        public void push(int n) {
            // 将队列前面比 n 小的元素都删除
            while (!maxQueue.isEmpty() && maxQueue.getLast() < n) {
                maxQueue.pollLast();
            }
            maxQueue.addLast(n);
        }

        public void pop(int n) {
            // 如果要删除的元素 n 还在队首，就进行删除；否则可能在 push 时就被删了。
            if (maxQueue.getFirst() == n) {
                maxQueue.pollFirst();
            }
        }

        public int max() {
            // 队首为最大值
            return maxQueue.getFirst();
        }
    }
}
