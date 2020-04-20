package leetcode;

/**
 * 给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为 1000。
 * <p>
 * 示例 1：
 * 输入: "babad"
 * 输出: "bab"
 * 注意: "aba" 也是一个有效答案。
 * 示例 2：
 * 输入: "cbbd"
 * 输出: "bb"
 */
public class LongestPalindromeSubstring {
    public static void main(String[] args) {
        System.out.println(new LongestPalindromeSubstring().longestPalindrome("bbbbbbabadab"));
    }

    /**
     * 思路1：动态规划
     * 求出字符串中所有是回文串的子串，找出其中长度最长的一个
     * 表现一般：91ms
     */
    public String longestPalindrome1(String s) {
        int len = s.length();
        if (len < 2)
            return s;
        int maxLen = 0, start = 0, end = 0;
//        dp数组中[i,j]位置的值记录字符串中第i位至第j位的子串是否是回文串
        boolean[][] dp = new boolean[len][len];
        char[] letter = s.toCharArray();
//        i表示子串的长度
        for (int i = 0; i < len; i++) {
//            j表示子串的起始位置
            for (int j = 0; j < len - i; j++) {
//                初始条件1：每一个单个字符的子串都是回文串
                if (j == j + i)
                    dp[j][j + i] = true;
//                初始条件2：只有两个字符的子串直接比较两个字符是否相等即可得到该子串是否为回文串
                else if (i < 2 && letter[j] == letter[j + i])
                    dp[j][j + i] = true;
//                中间状态依赖：[i,j]位置的字符串是否为回文串依赖于两个条件：
//                1 [i+1,j-1]位置的字符是否为回文串（即dp[j + 1][j + i - 1]）
//                2 i位置的字符和j位置的字符是否相等（即letter[j] == letter[j + i]）
//                两个条件同时满足时该子串为回文串，否则不是回文串
                else if (dp[j + 1][j + i - 1] && letter[j] == letter[j + i])
                    dp[j][j + i] = true;
                else
                    dp[j][j + i] = false;
//                当前子串是回文串且长度比之前的最大长度还要大时，记录该子串的起始终止位置，并更新子串长度
                if (dp[j][j + i] && i > maxLen) {
                    maxLen = i;
                    start = j;
                    end = j + i;
                }
            }
        }
        return String.copyValueOf(letter, start, end - start + 1);
    }

    /**
     * 思路2：中心扩散方法
     */
    public String longestPalindrome(String s) {
        if (s == null || "".equals(s)) {
            return "";
        }
        char[] chars = s.toCharArray();
//        range数组用于记录最长回文子串的起始和终止位置
        int[] range = new int[2];
//        扩散子串的左边界由0逐渐向len-1位置过度，找出每个位置所能扩散的最长回文子串
        fun(chars, 0, range);
        return s.substring(range[0], range[1] + 1);
    }

    private void fun(char[] chars, int left, int[] range) {
//        递归终止条件为左边界到达原字符串的右边界
        if (left >= chars.length - 1) {
            return;
        }
//        子串的右边界先尽可能的往右扩
        int right = left;
//        遍历出子串中所有字符都相等的部分
        while (right < chars.length - 1 && chars[right] == chars[right + 1]) {
            right++;
        }
//        将当前全是相等字符的子串右边界记录，作为下一次扩列的左边界依据
        int ans = right;
//        子串的左右边界同时开始扩列
        while (left > 0 && right < chars.length - 1 && chars[left - 1] == chars[right + 1]) {
            left--;
            right++;
        }
//        当前扩出的回文子串长度超过已知最大长度的回文子串时，更新左右边界值
        if (right - left > range[1] - range[0]) {
            range[0] = left;
            range[1] = right;
        }
//        下一次扩散时从当前子串中不相等的下一个元素开始
        fun(chars, ans + 1, range);
    }
}
