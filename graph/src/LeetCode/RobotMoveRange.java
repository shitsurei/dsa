package LeetCode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 地上有一个m行n列的方格，从坐标 [0,0] 到坐标 [m-1,n-1] 。
 * 一个机器人从坐标 [0, 0] 的格子开始移动，它每次可以向左、右、上、下移动一格（不能移动到方格外），也不能进入行坐标和列坐标的数位之和大于k的格子。
 * 例如，当k为18时，机器人能够进入方格 [35, 37] ，因为3+5+3+7=18。
 * 但它不能进入方格 [35, 38]，因为3+5+3+8=19。
 * 请问该机器人能够到达多少个格子？
 * <p>
 * 示例 1：
 * 输入：m = 2, n = 3, k = 1
 * 输出：3
 * 示例 2：
 * 输入：m = 3, n = 1, k = 0
 * 输出：1
 * 提示：
 * 1 <= n,m <= 100
 * 0 <= k <= 20
 */
public class RobotMoveRange {
    public static void main(String[] args) {
        System.out.println(new RobotMoveRange().bfs(1, 2, 1));
        System.out.println(new RobotMoveRange().movingCount(1, 2, 1));
    }

    public int movingCount(int m, int n, int k) {
        return dfs(new boolean[m][n], 0, 0, m, n, k);
    }

    /**
     * 思路1：dfs
     * 从左上角深度优先搜索，使用二维数组记录地图中的点是否已经搜索过
     */
    private int dfs(boolean[][] visited, int i, int j, int m, int n, int k) {
        if (i < 0 || j < 0 || i >= m || j >= n || visited[i][j] || i / 10 + i % 10 + j / 10 + j % 10 > k)
            return 0;
        visited[i][j] = true;
        return dfs(visited, i + 1, j, m, n, k) + dfs(visited, i - 1, j, m, n, k) + dfs(visited, i, j + 1, m, n, k) + dfs(visited, i, j - 1, m, n, k) + 1;
    }

    /**
     * 思路2：bfs
     * 使用队列辅助，从左上角开始广度优先搜索，每次只需要向右和向下扩展
     */
    private int bfs(int m, int n, int k) {
        int num = 1;
        boolean[][] visited = new boolean[m][n];
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{0, 0, 0, 0});
        while (!queue.isEmpty()) {
            int[] location = queue.poll();
            int xDown = location[0] + 1, yRight = location[1] + 1;
            int xDownPoint = xDown / 10 + xDown % 10, yRightPoint = yRight / 10 + yRight % 10;
            if (xDown < m && !visited[xDown][location[1]] && xDownPoint + location[3] <= k) {
                visited[xDown][location[1]] = true;
                num++;
                queue.add(new int[]{xDown, location[1], xDownPoint, location[3]});
            }
            if (yRight < n && !visited[location[0]][yRight] && yRightPoint + location[2] <= k) {
                visited[location[0]][yRight] = true;
                num++;
                queue.add(new int[]{location[0], yRight, location[2], yRightPoint});
            }
        }
        return num;
    }
}
