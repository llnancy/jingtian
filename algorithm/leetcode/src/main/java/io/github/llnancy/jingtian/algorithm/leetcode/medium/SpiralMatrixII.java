package io.github.llnancy.jingtian.algorithm.leetcode.medium;

/**
 * 螺旋矩阵 II
 * <a href="https://leetcode.cn/problems/spiral-matrix-ii/description/">https://leetcode.cn/problems/spiral-matrix-ii/description/</a>
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2023/11/13
 */
public class SpiralMatrixII {

    public int[][] generateMatrix(int n) {
        int[][] matrix = new int[n][n];
        int top = 0;
        int right = n - 1;
        int bottom = n - 1;
        int left = 0;
        int count = 1;
        while (count <= n * n) {
            if (top <= bottom) {
                for (int i = left; i <= right; i++) {
                    matrix[top][i] = count++;
                }
                top++;
            }
            if (left <= right) {
                for (int i = top; i <= bottom; i++) {
                    matrix[i][right] = count++;
                }
                right--;
            }
            if (top <= bottom) {
                for (int i = right; i >= left; i--) {
                    matrix[bottom][i] = count++;
                }
                bottom--;
            }
            if (left <= right) {
                for (int i = bottom; i >= top; i--) {
                    matrix[i][left] = count++;
                }
                left++;
            }
        }
        return matrix;
    }
}
