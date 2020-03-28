package LeetCode;

/**
 * 在一个 m*n 的棋盘的每一格都放有一个礼物，每个礼物都有一定的价值（价值大于 0）。
 * 你可以从棋盘的左上角开始拿格子里的礼物，并每次向右或者向下移动一格、直到到达棋盘的右下角。
 * 给定一个棋盘及其上面的礼物的价值，请计算你最多能拿到多少价值的礼物？
 * <p>
 * 示例 1:
 * 输入:
 * [
 *   [1,3,1],
 *   [1,5,1],
 *   [4,2,1]
 * ]
 * 输出: 12
 * 解释: 路径 1→3→5→2→1 可以拿到最多价值的礼物
 * <p>
 * 提示：
 * 0 < grid.length <= 200
 * 0 < grid[0].length <= 200
 */
public class GiftMaxValue {
    int[][] dp;

    public static void main(String[] args) {
        int[][] grid = new int[][]{
                {1, 3, 1},
                {1, 5, 1},
                {4, 2, 1}
        };
        System.out.println(new GiftMaxValue().maxValue(grid));
    }

    public int maxValue(int[][] grid) {
        int h = grid.length, l = grid[0].length;
        int[][] dp = new int[h][l];
//        【0，0】位置的元素默认填入dp表
        dp[0][0] = grid[0][0];
//        先填充首行和首列
        for (int i = 1; i < h; i++)
            dp[i][0] = dp[i - 1][0] + grid[i][0];
        for (int i = 1; i < l; i++)
            dp[0][i] = dp[0][i - 1] + grid[0][i];
//        之后的遍历就可以利用上左边和上边的元素进行dp
        for (int i = 1; i < h; i++)
            for (int j = 1; j < l; j++)
                dp[i][j] = grid[i][j] + Math.max(dp[i - 1][j], dp[i][j - 1]);
        return dp[h - 1][l - 1];
    }

    //    本题不适合使用dfs，因为每个位置的状态依赖于左边元素和上方元素，而dfs的计算顺序在二维矩阵中总是从右到左或是从下到上的
    public void dfs(int[][] grid, int[][] dp, int x, int y, int h, int l, int count) {
        if (x == h || y == l)
            return;
        if (count + grid[x][y] < dp[x][y])
            return;
        else
            dp[x][y] = count + grid[x][y];
//        先向右dfs会导致从右到左一列一列计算dp值
        dfs(grid, dp, x + 1, y, h, l, count + grid[x][y]);
//        先向下dfs会导致从下到上一行一行计算dp值
        dfs(grid, dp, x, y + 1, h, l, count + grid[x][y]);
    }
}
