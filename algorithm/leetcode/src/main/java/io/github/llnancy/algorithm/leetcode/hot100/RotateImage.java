package io.github.llnancy.algorithm.leetcode.hot100;

/**
 * 旋转图像
 * <a href="https://leetcode.cn/problems/rotate-image/description/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj">https://leetcode.cn/problems/rotate-image/description/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj</a>
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2023/10/27
 */
public class RotateImage {

    /*
    先将矩阵按照左上到右下的对角线进行镜像对称，然后再对矩阵的每一行进行反转。
     */

    public void rotate(int[][] matrix) {
        // 沿左上到右下对角线镜像对称二维矩阵
        for (int i = 0; i < matrix.length; i++) {
            for (int j = i; j < matrix.length; j++) {
                // [1, 0] => [0, -1]
                // swap(matrix[i][j], matrix[j][i]
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
        // 反转二维矩阵每一行
        for (int[] row : matrix) {
            reverse(row);
        }
    }

    private void reverse(int[] row) {
        int left = 0;
        int right = row.length - 1;
        while (left < right) {
            int temp = row[left];
            row[left] = row[right];
            row[right] = temp;
            left++;
            right--;
        }
    }
}
