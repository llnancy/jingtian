package com.sunchaser.sparrow.algorithm.base.array;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * 数组中第K个最大的元素：top K
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/11/11
 */
public class KthLargest {

    public static void main(String[] args) {
        int[] nums = {3, 2, 1, 5, 6, 4};
        int k = 2;
        System.out.println(findKthLargest(nums, k));
        System.out.println(findKthLargestV2(nums, k));
    }

    public static int findKthLargest(int[] nums, int k) {
        Queue<Integer> queue = new PriorityQueue<>(nums.length);
        for (int num : nums) {
            queue.offer(num);
            if (queue.size() > k) {
                queue.poll();
            }
        }
        return queue.peek();
    }

    public static int findKthLargestV2(int[] nums, int k) {
        int left = 0;
        int right = nums.length - 1;
        k = nums.length - k;
        while (left <= right) {
            int p = partition(nums, left, right);
            if (p < k) {
                left = p + 1;
            } else if (p > k) {
                right = p - 1;
            } else {
                return nums[p];
            }
        }
        return -1;
    }

    private static int partition(int[] nums, int left, int right) {
        if (left == right) {
            return left;
        }
        int pivot = nums[left];
        int i = left;
        int j = right + 1;
        while (true) {
            while (nums[++i] < pivot) {
                if (i == right) break;
            }
            while (nums[--j] > pivot) {
                if (j == left) break;
            }
            if (i >= j) break;
            swap(nums, i, j);
        }
        swap(nums, j, left);
        return j;
    }

    private static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
