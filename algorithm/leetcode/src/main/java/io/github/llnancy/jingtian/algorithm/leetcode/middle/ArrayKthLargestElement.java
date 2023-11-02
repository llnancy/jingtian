package io.github.llnancy.jingtian.algorithm.leetcode.middle;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * 215. 数组中的第K个最大元素
 * <p>
 * https://leetcode-cn.com/problems/kth-largest-element-in-an-array/
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/11/14
 */
public class ArrayKthLargestElement {

    public static void main(String[] args) {
        int[] arr = {3, 2, 1, 5, 6, 4};
        System.out.println(findKth1(arr, 3));
        System.out.println(findKth1(arr, 3));
    }

    /**
     * 小根堆
     */
    public static int findKth1(int[] arr, int k) {
        Queue<Integer> heap = new PriorityQueue<>();
        for (int a : arr) {
            heap.offer(a);
            if (heap.size() > k) {
                heap.poll();
            }
        }
        return heap.peek();
    }

    /**
     * 快速选择排序
     * 利用快排的partition过程
     */
    public static int findKth2(int[] arr, int k) {
        int left = 0;
        int right = arr.length - 1;
        k = arr.length - k; // 第K大元素：排序后索引为arr.length - k
        while (left <= right) {
            int p = partition(arr, left, right);
            if (p < k) {
                // 第k大元素在p右侧
                left = p + 1;
            } else if (p > k) {
                // 第k大元素在p左侧
                right = p - 1;
            } else {
                // 找到第k大的元素了
                return arr[p];
            }
        }
        return -1;
    }

    private static int partition(int[] arr, int left, int right) {
        if (left == right) {
            return left;
        }
        int pivot = arr[left];
        int i = left;
        int j = right + 1;
        while (true) {
            while (arr[++i] < pivot) {
                if (i == right) break;
            }
            while (arr[--j] > pivot) {
                if (j == left) break;
            }
            if (i >= j) break;
            swap(arr, i, j);
        }
        swap(arr, j, left);
        return j;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }


}
