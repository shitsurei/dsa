package LeetCode;

/**
 * 给你一个由 '1'（陆地）和 '0'（水）组成的的二维网格，请你计算网格中岛屿的数量。
 * 岛屿总是被水包围，并且每座岛屿只能由水平方向和/或竖直方向上相邻的陆地连接形成。
 * 此外，你可以假设该网格的四条边均被水包围。
 * <p>
 * 示例 1:
 * 输入:
 * 11110
 * 11010
 * 11000
 * 00000
 * 输出: 1
 * 示例 2:
 * 输入:
 * 11000
 * 11000
 * 00100
 * 00011
 * 输出: 3
 * 解释: 每座岛屿只能由水平和/或竖直方向上相邻的陆地连接而成。
 */
public class IslandNum {
    /**
     * 思路：利用dfs将相连的岛屿改为海洋
     */
    public int numIslands(char[][] grid) {
        if (grid.length == 0)
            return 0;
        int m = grid.length, n = grid[0].length, count = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '1') {
                    count++;
                    inject(grid, i, j, m, n);
                }
            }
        }
        return count;
    }

    public void inject(char[][] grid, int i, int j, int m, int n) {
        if (i >= 0 && j >= 0 && i < m && j < n && grid[i][j] == '1') {
            grid[i][j] = '0';
            inject(grid, i + 1, j, m, n);
            inject(grid, i - 1, j, m, n);
            inject(grid, i, j + 1, m, n);
            inject(grid, i, j - 1, m, n);
        }
    }
}
