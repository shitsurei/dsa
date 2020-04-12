package LeetCode.weekend;

/**
 * n*3的矩阵，每个位置可以有三种颜色
 * 要求每个位置与其上下左右相邻位置的上色不同
 * 求有多少中上色方案
 */
public class GridPaint {
    public int numOfWays(int n) {
        int[][] grid = new int[n][3];
        grid[0][0] = 3;
        grid[0][1] = 6;
        grid[0][2] = 12;
        for (int i = 1; i < n; i++)
            grid[i][0] = grid[i - 1][0] * 2;
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < 3; j++) {
                grid[i][j] = Math.max(grid[i - 1][j], grid[i][j - 1]) * 3;
            }
        }
        return grid[n - 1][2];
//        return dfs(grid, 0, 0, n, 3);
    }

    public int dfs(int[][] grid, int i, int j, int m, int n) {
        if (i < 0 || i >= m || j < 0 || j >= n)
            return 0;
        if (i == m - 1 && j == n - 1) {
            int max = 0;
            for (int k = 1; k < 4; k++) {
                if ((i == 0 || grid[i - 1][j] != k) && (j == 0 || grid[i][j - 1] != k))
                    max++;
            }
            return max;
        }
        int temp = 0;
        for (int k = 1; k < 4; k++) {
            if ((i == 0 || grid[i - 1][j] != k) && (j == 0 || grid[i][j - 1] != k)) {
                grid[i][j] = k;
                temp += dfs(grid, i + 1, j, m, n);
                temp += dfs(grid, i, j + 1, m, n);
                grid[i][j] = 0;
            }
        }
        return temp;
    }

}
