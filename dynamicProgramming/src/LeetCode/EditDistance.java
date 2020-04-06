package LeetCode;

/**
 * 给你两个单词 word1 和 word2，请你计算出将 word1 转换成 word2 所使用的最少操作数 。
 * 你可以对一个单词进行如下三种操作：
 * 插入一个字符
 * 删除一个字符
 * 替换一个字符
 * <p>
 * 示例 1：
 * 输入：word1 = "horse", word2 = "ros"
 * 输出：3
 * 解释：
 * horse -> rorse (将 'h' 替换为 'r')
 * rorse -> rose (删除 'r')
 * rose -> ros (删除 'e')
 * 示例 2：
 * 输入：word1 = "intention", word2 = "execution"
 * 输出：5
 * 解释：
 * intention -> inention (删除 't')
 * inention -> enention (将 'i' 替换为 'e')
 * enention -> exention (将 'n' 替换为 'x')
 * exention -> exection (将 'n' 替换为 'c')
 * exection -> execution (插入 'u')
 */
public class EditDistance {
    /**
     * 思路：动态规划
     * 使用二维数组保存单词装换所需要操作的最小次数
     * dp[i][j]表示将单词1的[0,i]子串装换为单词2的[0,j]所需要操作的最小次数
     * 接着我们考虑初始条件：
     * 为了使初始状态的定义更加简洁清晰，我们从空字符串开始考虑，即dp表的行和列比word1和word2的长度加1行1列
     * 1 dp[0][0]位置表示从一个空串转移到另一个空串的操作，显然为0
     * 2 dp[i][0]位置表示从[0,i]位置转移到一个空串的操作，显然需要删除所有非空串的字符，操作次数为i（注意字符从下标1位置开始）
     * 3 dp[0][j]同理操作次数为j
     * 状态转移：
     * dp[i][j]的操作次数我们可以分为三种情况来考虑：
     * 1 已知dp[i-1][j]的状态，我们只需要增加word1串中的第i个字符即可使得word1的[0,i]子串和word2的[0,j]子串相等
     * 2 同理dp[i][j-1]的状态，我们只需要删除word1串中的第i个字符即可得到结果
     * 【以上两种情况dp[i][j]的值均为dp[i-1][j]或dp[i][j-1]的基础上加1（额外的增加或删除操作1次）】
     * 3 已知dp[i-1][j-1]的状态，那么此时需要比较word1和word2的第i位字符和第j位字符是否相等
     * a 如果相等，那么无须任何操作，直接dp[i][j]=dp[i-1][j-1]
     * b 如果不相等，那么需要将word1的第i位字符替换为word2的第j位字符，dp[i][j]=dp[i-1][j-1]+1
     * 综上三种情况（如果算上第三种情况的两种可能是四种），由于我们要求的是转换所需操作的最小值，那么取这几种情况的最小值作为dp[i][j]结果即可
     */
    public int minDistance(String word1, String word2) {
        int m = word1.length(), n = word2.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 0; i <= m; i++)
            dp[i][0] = i;
        for (int i = 0; i <= n; i++)
            dp[0][i] = i;
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                int min = Math.min(dp[i - 1][j], dp[i][j - 1]) + 1;
                if (word1.charAt(i - 1) == word2.charAt(j - 1))
                    min = Math.min(min, dp[i - 1][j - 1]);
                else
                    min = Math.min(min, dp[i - 1][j - 1] + 1);
                dp[i][j] = min;
            }
        }
        return dp[m][n];
    }
}
