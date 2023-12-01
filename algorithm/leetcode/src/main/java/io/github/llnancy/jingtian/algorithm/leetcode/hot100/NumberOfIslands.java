package io.github.llnancy.jingtian.algorithm.leetcode.hot100;

/**
 * 岛屿数量
 * <a href="https://leetcode.cn/problems/number-of-islands/description/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj">https://leetcode.cn/problems/number-of-islands/description/?envType=featured-list&envId=2cktkvj?envType=featured-list&envId=2cktkvj</a>
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2023/11/30
 */
public class NumberOfIslands {

    public int numIslands(char[][] grid) {
        int res = 0;
        // 遍历 grid
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '1') {
                    // 每发现一个岛屿，数量加一
                    res++;
                    // 使用 dfs 将岛屿淹了
                    dfs(grid, i, j);
                }
            }
        }
        return res;
    }

    private void dfs(char[][] grid, int i, int j) {
        // 索引越界
        if (i < 0 || j < 0 || i >= grid.length || j >= grid[0].length) {
            return;
        }
        // 已经是海水了
        if (grid[i][j] == '0') {
            return;
        }
        // 将 [i][j] 变为海水
        grid[i][j] = '0';
        // 淹没上下左右的陆地
        dfs(grid, i - 1, j);
        dfs(grid, i + 1, j);
        dfs(grid, i, j - 1);
        dfs(grid, i, j + 1);
    }
}
