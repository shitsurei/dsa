package LeetCode;

import java.util.Arrays;

/**
 * 给你一幅由 N × N 矩阵表示的图像，其中每个像素的大小为 4 字节。请你设计一种算法，将图像旋转 90 度。
 * 不占用额外内存空间能否做到？
 * <p>
 * 示例 1:
 * 给定 matrix =
 * [
 * [1,2,3],
 * [4,5,6],
 * [7,8,9]
 * ],
 * 原地旋转输入矩阵，使其变为:
 * [
 * [7,4,1],
 * [8,5,2],
 * [9,6,3]
 * ]
 * 示例 2:
 * 给定 matrix =
 * [
 * [ 5, 1, 9,11],
 * [ 2, 4, 8,10],
 * [13, 3, 6, 7],
 * [15,14,12,16]
 * ],
 * 原地旋转输入矩阵，使其变为:
 * [
 * [15,13, 2, 5],
 * [14, 3, 4, 1],
 * [12, 6, 8, 9],
 * [16, 7,10,11]
 * ]
 */
public class RotateMatrix {
    public static void main(String[] args) {
        int[][] matrix = new int[][]{
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
        };
        new RotateMatrix().rotate(matrix);
    }

    /**
     * 顺时针旋转矩阵,即求矩阵的转置
     * 思路1:从外到内分层,每层一个循环,循环中间的元素通过下标变化转一圈
     * 思路2:先按照左上右下对角线为轴交换对应位置元素,再按列为轴交换元素
     */
    public void rotate(int[][] matrix) {
        if (matrix.length < 2)
            return;
        int x = 0, y = matrix.length - 1;
        do {
            for (int i = x, j = 0; i < y; i++, j++) {
                int x1 = x, y1 = i, x2 = i, y2 = y, x3 = y, y3 = y - j, x4 = y - j, y4 = x;
                int temp = matrix[x1][y1];
                matrix[x1][y1] = matrix[x4][y4];
                matrix[x4][y4] = matrix[x3][y3];
                matrix[x3][y3] = matrix[x2][y2];
                matrix[x2][y2] = temp;
            }
        } while (++x < --y);
    }
}
