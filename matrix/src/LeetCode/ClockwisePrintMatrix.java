package LeetCode;

import java.util.Arrays;

/**
 * 输入一个矩阵，按照从外向里以顺时针的顺序依次打印出每一个数字。
 *
 * 示例 1：
 * 输入：matrix = [[1,2,3],[4,5,6],[7,8,9]]
 * 输出：[1,2,3,6,9,8,7,4,5]
 * 示例 2：
 * 输入：matrix = [[1,2,3,4],[5,6,7,8],[9,10,11,12]]
 * 输出：[1,2,3,4,8,12,11,10,9,5,6,7]
 *
 * 限制：
 * 0 <= matrix.length <= 100
 * 0 <= matrix[i].length <= 100
 */
public class ClockwisePrintMatrix {
    public static void main(String[] args) {
        String s = Arrays.toString(new ClockwisePrintMatrix().spiralOrder(new int[][]{
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
        }));
        System.out.println(s);
    }

    /**
     * 思路：通过记录每一层的左上角和右下角的坐标信息按层旋转遍历
     */
    public int[] spiralOrder(int[][] matrix) {
        if (matrix.length==0)
            return new int[0];
        int m = matrix.length, n = matrix[0].length;
        int[] ans = new int[m * n];
        int index = 0, x1 = 0, y1 = 0, x2 = m - 1, y2 = n - 1;
        while (x1 < x2 && y1 < y2) {
            for (int i = y1; i <= y2; i++) ans[index++] = matrix[x1][i];
            for (int i = x1 + 1; i < x2; i++) ans[index++] = matrix[i][y2];
            for (int i = y2; i >= y1; i--) ans[index++] = matrix[x2][i];
            for (int i = x2 - 1; i > x1; i--) ans[index++] = matrix[i][y1];
            x1++;
            y1++;
            x2--;
            y2--;
        }
        if (x1 == x2 && y1 == y2) ans[index] = matrix[x1][y1];
        else if (x1 == x2 && y1 <= y2) for (int i = y1; i <= y2; i++) ans[index++] = matrix[x1][i];
        else if (y1 == y2 && x1 <= x2) for (int i = x1; i <= x2; i++) ans[index++] = matrix[i][y1];
        return ans;
    }
}
