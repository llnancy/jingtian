package com.sunchaser.sparrow.algorithm.base.sort;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/11/1
 */
public class BubbleSort {

    public static void main(String[] args) {
        Deque<Character> stack = new LinkedList<>();
        Stack<Character> ch = new Stack<>();
        System.out.println(isValid("(]"));
        String s = "";
        char[] chars = s.toCharArray();

//        int mySqrt = mySqrt(2147483647);
//        System.out.println(mySqrt);
        int[] nums = {4, 5, 6, 7, 0, 1, 2};
        System.out.println(search(nums, 3));
    }

    public static boolean isValid(String s) {
        Deque<Character> stack = new LinkedList<>();
        char[] c = s.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if ('(' == c[i] || '{' == c[i] || '[' == c[i]) {
                stack.push(c[i]);
            } else {
                Character tmp = null;
                if (c[i] == ')') {
                    tmp = '(';
                } else if (c[i] == '}') {
                    tmp = '{';
                } else if (c[i] == ']') {
                    tmp = '[';
                }
                if (tmp == stack.peek()) {
                    stack.pop();
                } else {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    public static int search(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        int mid;
        while (left <= right) {
            mid = (left + right) >>> 1;
            if (nums[mid] == target) {
                return mid;
            }
            if (nums[left] <= nums[mid]) {// 左边有序，mid = left时，左边只有一个值
                if (nums[left] <= target && target < nums[mid]) {// 目标值在左边
                    right = mid - 1;
                } else {// 目标值在右边
                    left = mid + 1;
                }
            } else if (nums[mid] < nums[right]) {// 右边有序
                if (nums[mid] < target && target <= nums[right]) {// 目标值在右边
                    left = mid + 1;
                } else {// 目标值在左边
                    right = mid - 1;
                }
            }
        }
        return -1;
    }

    public static int mySqrt(int x) {
        int left = 0;
        int right = x;
        while (left <= right) {
            int mid = (left + right) >>> 1;
            if ((long) (mid * mid) == x) {
                return mid;
            } else if ((long) (mid * mid) < x && (long) ((mid + 1) * (mid + 1)) > x) {
                return mid;
            } else if ((long) (mid * mid) < x) {
                left = mid + 1;
            } else if ((long) (mid * mid) > x) {
                right = mid - 1;
            }
        }
        return -1;
    }

    public static void bubbleSort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] > arr[j]) {
                    swap(arr, i, j);
                }
            }
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

}
