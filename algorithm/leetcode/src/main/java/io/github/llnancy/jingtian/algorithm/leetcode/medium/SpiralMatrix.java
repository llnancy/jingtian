package io.github.llnancy.jingtian.algorithm.leetcode.medium;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 54. 螺旋矩阵
 * <a href="https://leetcode.cn/problems/spiral-matrix/description/?envType=study-plan-v2&envId=top-100-liked">https://leetcode.cn/problems/spiral-matrix/description/?envType=study-plan-v2&envId=top-100-liked</a>
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/11/19
 */
public class SpiralMatrix {

    /*
    按右、下、左、上的顺序遍历数组，使用四个变量圈定未遍历元素的边界，随着螺旋遍历，对应边界进行收缩，直到遍历完整个矩阵。
     */

    public List<Integer> spiralOrder(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        int top = 0;
        int right = n - 1;
        int bottom = m - 1;
        int left = 0;
        List<Integer> res = new ArrayList<>();
        // res.size() == m * n 时数组遍历完成
        while (res.size() < m * n) {
            if (top <= bottom) {
                // 顶部从左往右遍历
                for (int i = left; i <= right; i++) {
                    res.add(matrix[top][i]);
                }
                top++;
            }
            if (left <= right) {
                // 右侧从上往下遍历
                for (int i = top; i <= bottom; i++) {
                    res.add(matrix[i][right]);
                }
                right--;
            }
            if (top <= bottom) {
                // 底部从右往左遍历
                for (int i = right; i >= left; i--) {
                    res.add(matrix[bottom][i]);
                }
                bottom--;
            }
            if (left <= right) {
                // 左侧从下往上遍历
                for (int i = bottom; i >= top; i--) {
                    res.add(matrix[i][left]);
                }
                left++;
            }
        }
        return res;
    }
}
