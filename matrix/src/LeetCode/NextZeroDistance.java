package LeetCode;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

/**
 * 给定一个由 0 和 1 组成的矩阵，找出每个元素到最近的 0 的距离。
 * 两个相邻元素间的距离为 1 。
 * <p>
 * 示例 1:
 * 输入:
 * 0 0 0
 * 0 1 0
 * 0 0 0
 * 输出:
 * 0 0 0
 * 0 1 0
 * 0 0 0
 * 示例 2:
 * 输入:
 * 0 0 0
 * 0 1 0
 * 1 1 1
 * 输出:
 * 0 0 0
 * 0 1 0
 * 1 2 1
 * <p>
 * 注意:
 * 给定矩阵的元素个数不超过 10000。
 * 给定矩阵中至少有一个元素是 0。
 * 矩阵中的元素只在四个方向上相邻: 上、下、左、右。
 */
public class NextZeroDistance {
    public static void main(String[] args) {
        int[][] test = new int[][]{
                {0, 0, 0},
                {0, 1, 0},
                {0, 0, 0},
        };
        NextZeroDistance distance = new NextZeroDistance();
        System.out.println(Arrays.toString(distance.updateMatrix(test)));
    }

    /**
     * 思路2：动态规划
     * 任意一个值为1的点距离与其最近的值为0的点的曼哈顿距离是固定的（等于两点的x坐标差值的绝对值和y坐标差值的绝对值之和）
     * 由于题目中明确地图中存在值为0的点，因此地图中距离0坐标最远的1坐标的点之间的曼哈顿距离不会超过整个地图的m+n
     * 我们对整个地图可以进行三次遍历：
     * 第一次，将地图中坐标为0的点在最终返回的数组中的最远距离设为0，坐标为1的点在最终返回的数组中的最远距离设为m+n
     * 【第一次遍历可以压缩进后面的遍历中一起完成】
     * 第二次，从地图的左上位置开始向右下角遍历，我们假设遍历到的值为1的点，与其对应的曼哈顿距离【最近的值为0的点位于其左上部分中】，
     *      此时我们只需要比较其上方和左侧中的较小值加1，即为当前位置的最小值
     * 第三次，从地图的右下位置开始向左上角遍历，同理我们取最小值
     * 遍历结束后，每个位置上的最终答案可以分为两种情况：
     * 1 值为0，表示该点在原本的地图中值为0
     * 2 值不为0，表示该点在原本的地图中值为1，且当前距离是通过将其上下左右四个位置中取最小值加1得到的
     */
    public int[][] updateMatrix(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 1)
                    matrix[i][j] = m + n;
                if (i > 0)
                    matrix[i][j] = Math.min(matrix[i][j], matrix[i - 1][j] + 1);
                if (j > 0)
                    matrix[i][j] = Math.min(matrix[i][j], matrix[i][j - 1] + 1);
            }
        }
        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                if (i < m - 1)
                    matrix[i][j] = Math.min(matrix[i][j], matrix[i + 1][j] + 1);
                if (j < n - 1)
                    matrix[i][j] = Math.min(matrix[i][j], matrix[i][j + 1] + 1);
            }
        }
        return matrix;
    }

    /**
     * 思路1：BFS
     * 1 借助队列，将原本矩阵中坐标为0的位置全部入队，并标记为已访问
     * 2 从队列中不断拿出坐标并判断其上下左右是否未被访问，未被访问的位置标记位已访问并入队，同时设置坐标的距离为之前取出的坐标距离+1
     * 3 直到队列为空，表示所有位置都已遍历
     * @param matrix
     * @return
     */
    public int[][] updateMatrix1(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        Queue<int[]> queue = new ArrayDeque<>();
        boolean[][] visited = new boolean[m][n];
        int[][] ans = new int[m][n];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                if (matrix[i][j] == 0) {
                    queue.add(new int[]{i, j});
//                    由于新申请的矩阵所有元素均为0，因此只需设置访问标记位true
                    visited[i][j] = true;
                }
        int[] xChange = {-1, 0, 1, 0};
        int[] yChange = {0, 1, 0, -1};
        while (!queue.isEmpty()) {
            int[] poll = queue.poll();
            for (int k = 0; k < 4; k++)
                if ((poll[0] + xChange[k]) >= 0 && (poll[0] + xChange[k]) < m
                        && (poll[1] + yChange[k]) >= 0 && (poll[1] + yChange[k]) < n
                        && !visited[poll[0] + xChange[k]][poll[1] + yChange[k]]) {
                    visited[poll[0] + xChange[k]][poll[1] + yChange[k]] = true;
                    ans[poll[0] + xChange[k]][poll[1] + yChange[k]] = ans[poll[0]][poll[1]] + 1;
                    queue.add(new int[]{poll[0] + xChange[k], poll[1] + yChange[k]});
                }
        }
        return ans;
    }
}
