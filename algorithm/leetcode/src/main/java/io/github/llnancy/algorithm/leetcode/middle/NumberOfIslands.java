package io.github.llnancy.algorithm.leetcode.middle;

/**
 * 200. 岛屿数量
 * <p>
 * https://leetcode-cn.com/problems/number-of-islands/
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/11/16
 */
public class NumberOfIslands {
    public int numIslands(char[][] grid) {
        int res = 0;
        int m = grid.length;
        int n = grid[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '1') {// 发现一个岛屿
                    res++;
                    // 使用dfs将岛屿淹了
                    dfs(grid, i, j);
                }
            }
        }
        return res;
    }

    private void dfs(char[][] grid, int i, int j) {
        int m = grid.length;
        int n = grid[0].length;
        if (i < 0 || j < 0 || i >= m || j >= n) {
            return;// 越界
        }
        if (grid[i][j] == '0') {
            return;// 已经是海水了
        }
        grid[i][j] = '0';
        dfs(grid, i - 1, j);// 上
        dfs(grid, i + 1, j);// 下
        dfs(grid, i, j - 1);// 左
        dfs(grid, i, j + 1);// 右
    }
}
