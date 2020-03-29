package LeetCode;

/**
 * 你现在手里有一份大小为 N x N 的『地图』（网格） grid，上面的每个『区域』（单元格）都用 0 和 1 标记好了。
 * 其中 0 代表海洋，1 代表陆地，你知道距离陆地区域最远的海洋区域是是哪一个吗？请返回该海洋区域到离它最近的陆地区域的距离。
 * 我们这里说的距离是『曼哈顿距离』（ Manhattan Distance）：
 * (x0, y0) 和 (x1, y1) 这两个区域之间的距离是 |x0 - x1| + |y0 - y1| 。
 * 如果我们的地图上只有陆地或者海洋，请返回 -1。
 * <p>
 * 输入：[[1,0,1],[0,0,0],[1,0,1]]
 * 输出：2
 * 解释：
 * 海洋区域 (1, 1) 和所有陆地区域之间的距离都达到最大，最大距离为 2。
 * 输入：[[1,0,0],[0,0,0],[0,0,0]]
 * 输出：4
 * 解释：
 * 海洋区域 (2, 2) 和所有陆地区域之间的距离都达到最大，最大距离为 4。
 */
public class MapAnalysis1162 {
    int ocean = 0;

    /**
     * 算法思路：每一次将陆地一圈的海洋进行感染，并逐次增大感染值
     * 每一块海洋一定是被距离其最近的陆地感染，因此整个地图最后一次感染的海洋一定是距离所有陆地最远的海洋
     * @param grid
     * @return
     */
    public int maxDistance(int[][] grid) {
        int m = grid.length, n = grid[0].length;
//        第一次遍历数出海洋的个数
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                if (grid[i][j] == 0)
                    ocean++;
        if (ocean == 0 || ocean == m * n)
            return -1;
        int feet = 1;
        while (ocean > 0) {
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (grid[i][j] == feet) {
                        inject(grid, i + 1, j, m, n, feet + 1);
                        inject(grid, i - 1, j, m, n, feet + 1);
                        inject(grid, i, j + 1, m, n, feet + 1);
                        inject(grid, i, j - 1, m, n, feet + 1);
                    }
                }
            }
            feet++;
        }
        return feet;
    }

    /**
     * 感染函数
     * @param grid
     * @param x
     * @param y
     * @param m
     * @param n
     * @param feet
     */
    public void inject(int[][] grid, int x, int y, int m, int n, int feet) {
        if (x < 0 || x >= m || y < 0 || y >= n)
            return;
        if (grid[x][y] == 0) {
            ocean--;
            grid[x][y] = feet;
        }
    }
}
