package com.sunchaser.codinginterviewguide.arraysandmatrices;

import java.util.Scanner;

/**
 * 在行列都排好序的矩阵中找数
 *
 * 给定一个`N × M`的整型矩阵`matrix`和一个整数`K`，`matrix`的每一行每一列都是排好序的。实现一个函数，判断`K`是否在`matrix`中。
 *
 * 要求：时间复杂度为`O(N + M)`，额外空间复杂度为`O(1)`。
 *
 * 备注：
 * 1 ⩽ N, M ⩽1000
 * 0 ⩽ K, 矩阵中的数 ⩽ 10^9^
 *
 * 2、输入描述：第一行有三个整数`N`，`M`，`K`；接下来`N`行，每行`M`个整数为输入的矩阵。
 *
 * 3、输出描述：若`K`存在于矩阵中输出`"Yes"`，否则输出`"No"`。
 *
 * 4、示例
 * 4.1、示例一
 * 输入：
 * 2 4 5
 * 1 2 3 4
 * 2 4 5 6
 *
 * 输出：Yes
 *
 * 4.2、示例二
 * 输入：
 * 2 4 233
 * 1 2 3 4
 * 2 4 5 6
 *
 * 输出：No
 * @author sunchaser
 * @date 2020/3/18
 * @since 1.0
 */
public class Solution01 {

    /**
     * 由于矩阵中的行和列都是排好序的，
     * 我们可以知道最后一列是每一行的最大值，
     * 最后一行是每一列的最大值。
     * 整个矩阵的最大值在右下角。
     *
     * 要想减少时间复杂度，我们必须能够快速排除某些行或列，
     * 基于最后一列或行是每一行或列的最大值的特点，
     * 我们可以从矩阵右上角或左下角开始查找，如果不是指定的数，我们就可以排除掉一行或一列了。
     * @param args
     */
    public static void main(String[] args) {
        // receive input params
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int k = scanner.nextInt();
        // build matrix
        int[][] matrix = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                matrix[i][j] = scanner.nextInt();
            }
        }
        boolean containsOne = isContainsOne(matrix, k);
        boolean containsTwo = isContainsTwo(matrix, k);
        System.out.println(containsOne ? "Yes" : "No");
        System.out.println(containsTwo ? "Yes" : "No");
    }

    /**
     * 判断方式一：双重for循环
     * 外层循环矩阵每一行
     * 内层循环矩阵每一列
     * 相等直接返回true；
     * 若小于k，则排除当前行，结束内层循环，遍历下一行。
     * 若大于k，则排除当前列，j--，内存循环进入下一次。
     * 若双重循环完成都未找到k，则返回false。
     * @param matrix
     * @param k
     * @return
     */
    private static boolean isContainsOne(int[][] matrix,int k) {
        int col = matrix[0].length;
        for (int[] ints : matrix) {
            for (int j = col - 1; j >= 0; j--) {
                int value = ints[j];
                if (value == k) {
                    return true;
                } else if (value < k) {
                    break;
                }
            }
        }
        return false;
    }

    /**
     * 判断方式二：while循环
     * 越界条件：行遍历完或列遍历完
     * @param matrix
     * @param k
     * @return
     */
    private static boolean isContainsTwo(int[][] matrix,int k) {
        int row = matrix.length;
        int col = matrix[0].length;
        int i = 0;
        int j = col - 1;
        while (i < row && j >= 0) {
            int value = matrix[i][j];
            if (value == k) {
                return true;
            } else if (value < k) {
                i++;
            } else {
                j--;
            }
        }
        return false;
    }
}
