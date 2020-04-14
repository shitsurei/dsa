package LeetCode;

/**
 * 给定两个字符串 text1 和 text2，返回这两个字符串的最长公共子序列。
 * 一个字符串的子序列是指这样一个新的字符串：它是由原字符串在不改变字符的相对顺序的情况下删除某些字符（也可以不删除任何字符）后组成的新字符串。
 * 例如，"ace" 是 "abcde" 的子序列，但 "aec" 不是 "abcde" 的子序列。两个字符串的「公共子序列」是这两个字符串所共同拥有的子序列。
 * 若这两个字符串没有公共子序列，则返回 0。
 * <p>
 * 示例 1:
 * 输入：text1 = "abcde", text2 = "ace"
 * 输出：3
 * 解释：最长公共子序列是 "ace"，它的长度为 3。
 * 示例 2:
 * 输入：text1 = "abc", text2 = "abc"
 * 输出：3
 * 解释：最长公共子序列是 "abc"，它的长度为 3。
 * 示例 3:
 * 输入：text1 = "abc", text2 = "def"
 * 输出：0
 * 解释：两个字符串没有公共子序列，返回 0。
 *  
 * 提示:
 * 1 <= text1.length <= 1000
 * 1 <= text2.length <= 1000
 * 输入的字符串只含有小写英文字符。
 */
public class LongestCommonSubsequence {
    public static void main(String[] args) {
        System.out.println(new LongestCommonSubsequence().longestCommonSubsequence("mhunuzqrkzsnidwbun", "szulspmhwpazoxijwbq"));
    }

    public int longestCommonSubsequence(String text1, String text2) {
        int[][] memo = new int[text1.length() + 1][text2.length() + 1];
        int ans = recursion(text1.toCharArray(), text1.length(), text2.toCharArray(), text2.length(), memo);
        return ans;
    }

    private int recursion(char[] word1, int end1, char[] word2, int end2, int[][] memo) {
        if (end1 < 0 || end2 < 0)
            return 0;
        if (memo[end1][end2] != 0)
            return memo[end1][end2] - 1;
        int lcs = 0;
        while (end1 > 0 && end2 > 0 && word1[end1 - 1] == word2[end2 - 1]) {
            lcs++;
            end1--;
            end2--;
        }
        memo[end1][end2] = 1 + lcs + Math.max(recursion(word1, end1 - 1, word2, end2, memo), recursion(word1, end1, word2, end2 - 1, memo));
        return memo[end1][end2] - 1;
//        return lcs + Math.max(recursion(word1, end1 - 1, word2, end2, memo), recursion(word1, end1, word2, end2 - 1, memo));
    }
}
