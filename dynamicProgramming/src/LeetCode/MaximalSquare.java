package LeetCode;

/**
 * 在一个由 0 和 1 组成的二维矩阵内，找到只包含 1 的最大正方形，并返回其面积。
 * <p>
 * 示例:
 * 输入:
 * 1 0 1 0 0
 * 1 0 1 1 1
 * 1 1 1 1 1
 * 1 0 0 1 0
 * 输出: 4
 */
public class MaximalSquare {
    public static void main(String[] args) {
        char[][] matrix = new char[][]{
                {'1', '0', '1', '0', '0', '1', '1', '1', '0'},
                {'1', '1', '1', '0', '0', '0', '0', '0', '1'},
                {'0', '0', '1', '1', '0', '0', '0', '1', '1'},
                {'0', '1', '1', '0', '0', '1', '0', '0', '1'},
                {'1', '1', '0', '1', '1', '0', '0', '1', '0'},
                {'0', '1', '1', '1', '1', '1', '1', '0', '1'},
                {'1', '0', '1', '1', '1', '0', '0', '1', '0'},
                {'1', '1', '1', '0', '1', '0', '0', '0', '1'},
                {'0', '1', '1', '1', '1', '0', '0', '1', '0'},
                {'1', '0', '0', '1', '1', '1', '0', '0', '0'},
        };
        char[][] m2 = new char[][]{
                {'1', '0', '1', '0', '0'},
                {'1', '0', '1', '1', '1'},
                {'1', '1', '1', '1', '1'},
                {'1', '0', '0', '1', '0'},
        };
        System.out.println(new MaximalSquare().maximalSquare(m2));
    }

    /**
     * 思路：dp
     * 状态转移方程如下：
     * dp[i][j]=0                                               matrix[i][j]='0'
     * dp[i][j]=1                                               (i=0||j=0)&&matrix[i][j]='1'
     * dp[i][j]=min{dp[i-1][j-1],dp[i][j-1],dp[i-1][j]}+1       i>0&&j>0&&matrix[i][j]='1'
     */
    public int maximalSquare(char[][] matrix) {
        if (matrix == null || matrix.length == 0)
            return 0;
        int m = matrix.length, n = matrix[0].length;
        int[][] dp = new int[m][n];
        for (int i = 0; i < m; i++)
            if (matrix[i][0] == '1')
                dp[i][0] = 1;
        for (int i = 0; i < n; i++)
            if (matrix[0][i] == '1')
                dp[0][i] = 1;
        for (int i = 1; i < m; i++)
            for (int j = 1; j < n; j++)
                if (matrix[i][j] == '1')
                    dp[i][j] = Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1])) + 1;
        int max = 0;
//        实测最后遍历一次dp表找出最大值的效率高于每次添值时判断最大值
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                max = Math.max(max, dp[i][j]);
        return max * max;
    }
}
