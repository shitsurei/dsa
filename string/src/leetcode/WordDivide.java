package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 给定一个非空字符串 s 和一个包含非空单词列表的字典 wordDict，判定 s 是否可以被空格拆分为一个或多个在字典中出现的单词。
 * <p>
 * 说明：
 * 拆分时可以重复使用字典中的单词。
 * 你可以假设字典中没有重复的单词。
 * <p>
 * 示例 1：
 * 输入: s = "leetcode", wordDict = ["leet", "code"]
 * 输出: true
 * 解释: 返回 true 因为 "leetcode" 可以被拆分成 "leet code"。
 * 示例 2：
 * 输入: s = "applepenapple", wordDict = ["apple", "pen"]
 * 输出: true
 * 解释: 返回 true 因为 "applepenapple" 可以被拆分成 "apple pen apple"。
 *      注意你可以重复使用字典中的单词。
 * 示例 3：
 * 输入: s = "catsandog", wordDict = ["cats", "dog", "sand", "and", "cat"]
 * 输出: false
 */
public class WordDivide {
    /**
     * 本题可以理解为完全背包问题，利用动态规划思想解决
     */
    public static void main(String[] args) {
        WordDivide wd = new WordDivide();
        String s = "abcd";
        List<String> wordDict = new ArrayList<>(Arrays.asList("a", "abc", "b", "cd"));
        System.out.println(wd.wordBreak(s, wordDict));
    }

    private PrefixTreeNode root = new PrefixTreeNode();
    private int maxLen = 0;
    boolean[] dp;

    /**
     * 辅助方法
     */
    public boolean[] divideDP(String s, List<String> wordDict) {
        wordBreak(s, wordDict);
        return dp;
    }

    public boolean wordBreak(String s, List<String> wordDict) {
//        这里使用前缀树将单词表建成一棵树，用于后面判断字符串是否存在于字典中
        for (String word : wordDict) {
            root.insert(word);
//            同时记录最长的单词长度，方便后面剪枝操作
            maxLen = Math.max(maxLen, word.length());
        }
        int len = s.length();
//        dp表中每一位的状态表示当前位置之前的所有字符串是否可以拆分成字典中已有的字符串
        dp = new boolean[len + 1];
//        0位置初始状态置为true
        dp[0] = true;
//        从第二个位置开始遍历到末尾
        for (int i = 1; i <= len; i++) {
//            采用倒序的方式截取每次内部遍历的子串，这样可以利用前面已有的状态信息
            for (int j = 0; j < i; j++) {
//                剪枝操作，每次内部倒序判断时都是从首个字母开始的，这样判断到后面很多单词的长度一定是大于单词表中最大长度的
                if (i - j > maxLen)
                    continue;
//                倒序判断中一旦出现满足的单词并且该单词之前的子串状态为true（即能被分割）
                if (dp[j] && root.search(s.substring(j, i))) {
//                    则表示当前位置之前的子串能被分割，记录状态，跳出循环
                    dp[i] = true;
                    break;
                }
            }
        }
//        返回最终整个子串是否能被分割
        return dp[len];
    }
}
