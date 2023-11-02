package io.github.llnancy.jingtian.algorithm.leetcode.middle;

import java.util.LinkedList;
import java.util.List;

/**
 * 54. 螺旋矩阵
 * <p>
 * https://leetcode-cn.com/problems/spiral-matrix/
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/11/19
 */
public class SpiralMatrix {
    public static void main(String[] args) {
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        System.out.println(spiralOrder(matrix));
    }

    public static List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> res = new LinkedList<>();
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return res;
        }
        int left = 0;
        int right = matrix[0].length - 1;
        int top = 0;
        int bottom = matrix.length - 1;
        while (left <= right && top <= bottom) {
            for (int i = left; i <= right; i++) {
                res.add(matrix[top][i]);
            }
            for (int j = top + 1; j <= bottom; j++) {
                res.add(matrix[j][right]);
            }
            if (left < right && top < bottom) {
                for (int m = right - 1; m > left; m--) {
                    res.add(matrix[bottom][m]);
                }
                for (int n = bottom; n > top; n--) {
                    res.add(matrix[n][left]);
                }
            }
            left++;
            right--;
            top++;
            bottom--;
        }
        return res;
    }
}
