package io.github.llnancy.jingtian.algorithm.leetcode.top100;

/**
 * 搜索二维矩阵
 * <a href="https://leetcode.cn/problems/search-a-2d-matrix/description/?envType=study-plan-v2&envId=top-100-liked">https://leetcode.cn/problems/search-a-2d-matrix/description/?envType=study-plan-v2&envId=top-100-liked</a>
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2023/11/13
 */
public class SearchA2dMatrix {

    /*
    只要知道二维数组的行数 m 和列数 n，二维数组的坐标 [i, j] 可以映射成一维数组的 index = i * n + j；
    反过来也可以通过一维数组的 index 反解出二维数组的坐标 i = index / n，j = index % n。
    有了这个映射关系，问题可以转化为在一维数组中搜索目标值，即二分查找。
     */

    public boolean searchMatrix(int[][] matrix, int target) {
        int left = 0;
        int right = matrix.length * matrix[0].length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (get(matrix, mid) < target) {
                left = mid + 1;
            } else if (get(matrix, mid) > target) {
                right = mid - 1;
            } else {
                return true;
            }
        }
        return false;
    }

    private int get(int[][] matrix, int index) {
        return matrix[index / matrix[0].length][index % matrix[0].length];
    }
}
