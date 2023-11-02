package io.github.llnancy.jingtian.algorithm.leetcode.middle;

import java.util.Arrays;

/**
 * 912. 排序数组
 * <p>
 * 快排
 * <p>
 * https://leetcode-cn.com/problems/sort-an-array/
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/11/14
 */
public class SortAnArray {

    public static void main(String[] args) {
        int[] arr = {5, 2, 3, 1};
        System.out.println(Arrays.toString(sortArray(arr)));
    }

    public static int[] sortArray(int[] arr) {
        quickSort(arr, 0, arr.length - 1);
        return arr;
    }

    private static void quickSort(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }
        int p = partition(arr, left, right);
        quickSort(arr, left, p - 1);
        quickSort(arr, p + 1, right);
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
