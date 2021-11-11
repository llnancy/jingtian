package com.sunchaser.sparrow.algorithm.base.sort;

/**
 * 快排
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/11/12
 */
public class QuickSort {
    public void quickSort(int[] nums) {
        doQuickSort(nums, 0, nums.length - 1);
    }

    private void doQuickSort(int[] nums, int left, int right) {
        if (left >= right) {
            return;
        }
        int p = partition(nums, left, right);
        doQuickSort(nums, left, p - 1);
        doQuickSort(nums, p + 1, right);
    }

    private int partition(int[] nums, int left, int right) {
        if (left == right) {
            return left;
        }
        // 将nums[left]作为默认分界点pivot
        int pivot = nums[left];
        int i = left;
        int j = right + 1;
        while (true) {
            // 保证 nums[left...i] 都小于pivot
            while (nums[++i] < pivot) {
                if (i == right) break;
            }
            // 保证 nums[j...right] 都大于pivot
            while (nums[--j] > pivot) {
                if (j == left) break;
            }
            if (i >= j) break;
            // 如果走到这里，一定有：
            // nums[i] > pivot && nums[j] < pivot
            // 所以需要交换 nums[i] 和 nums[j]
            // 保证 nums[left...i] < pivot < nums[j...right]
            swap(nums, i, j);
        }
        // 将pivot值（nums[left]）交换到正确的位置
        swap(nums, j, left);
        // 现在nums[left...j - 1] < nums[j] < nums[j + 1...right]
        return j;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
