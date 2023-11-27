package io.github.llnancy.jingtian.algorithm.leetcode.hot100;

/**
 * 搜索二维矩阵 II
 * <a href="https://leetcode.cn/problems/search-a-2d-matrix-ii/description/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj">https://leetcode.cn/problems/search-a-2d-matrix-ii/description/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj</a>
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2023/11/13
 */
public class SearchA2dMatrixII {

    /*
    从右上角（或左下角）开始，只能向左或向下移动。向左元素在减小，向下元素在增大，根据和 target 目标值的大小关系判断该向哪移动。
     */

    public boolean searchMatrix(int[][] matrix, int target) {
        int m = 0;
        int n = matrix[0].length - 1;
        while (m < matrix.length && n >= 0) {
            if (matrix[m][n] == target) {
                return true;
            }
            if (matrix[m][n] > target) {
                n--;
            }
            if (matrix[m][n] < target) {
                m++;
            }
        }
        return false;
    }
}
