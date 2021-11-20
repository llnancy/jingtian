package com.sunchaser.sparrow.algorithm.base.array;

/**
 * DFS遍历二维数组
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/11/16
 */
public class DFS {
    public void dfs(int[][] grid, int i, int j, boolean[][] visited) {
        int m = grid.length;// 二维数组的长度
        int n = grid[0].length;// 二维数组的宽度
        if (i < 0 || j < 0 || i >= m || j >= n) {
            // 索引越界
            return;
        }
        if (visited[i][j]) {
            // 已经遍历过
            return;
        }
        visited[i][j] = true;
        dfs(grid, i - 1, j, visited);// 上
        dfs(grid, i + 1, j, visited);// 下
        dfs(grid, i, j - 1, visited);// 左
        dfs(grid, i, j + 1, visited);// 右
    }
}
