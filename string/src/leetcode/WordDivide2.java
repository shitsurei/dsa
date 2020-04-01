package leetcode;

import java.util.*;

/**
 * 给定一个非空字符串 s 和一个包含非空单词列表的字典 wordDict，在字符串中增加空格来构建一个句子，
 * 使得句子中所有的单词都在词典中。返回所有这些可能的句子。
 * <p>
 * 说明：
 * 分隔时可以重复使用字典中的单词。
 * 你可以假设字典中没有重复的单词。
 * <p>
 * 示例 1：
 * 输入:
 * s = "catsanddog"
 * wordDict = ["cat", "cats", "and", "sand", "dog"]
 * 输出:
 * [
 *   "cats and dog",
 *   "cat sand dog"
 * ]
 * 示例 2：
 * 输入:
 * s = "pineapplepenapple"
 * wordDict = ["apple", "pen", "applepen", "pine", "pineapple"]
 * 输出:
 * [
 *   "pine apple pen apple",
 *   "pineapple pen apple",
 *   "pine applepen apple"
 * ]
 * 解释: 注意你可以重复使用字典中的单词。
 * 示例 3：
 * 输入:
 * s = "catsandog"
 * wordDict = ["cats", "dog", "sand", "and", "cat"]
 * 输出:
 * []
 */
public class WordDivide2 {
    public static void main(String[] args) {
        WordDivide2 wd2 = new WordDivide2();
        String s = "aaaabaaa";
        List<String> wordDict = new ArrayList<>(Arrays.asList("a", "aa", "aaa", "aaaa", "aaaaa", "aaaaaa", "aaaaaaa", "ba"));
        System.out.println(wd2.wordBreak(s, wordDict));
    }

    private List<String> plans = new LinkedList<>();
    private PrefixTreeNode root = new PrefixTreeNode();
    private int maxLen;
    private boolean[] dp;

    public List<String> wordBreak(String s, List<String> wordDict) {
//        创建单词前缀树，统计出最长单词用于dfs剪枝
        for (String word : wordDict) {
            root.insert(word);
            maxLen = Math.max(maxLen, word.length());
        }
        WordDivide wd = new WordDivide();
//        计算单词s的dp数组（含义与单词拆分题1相同）
        dp = wd.divideDP(s, wordDict);
//        只有当单词可以拆分时才进入dfs
        if (dp[dp.length - 1])
            dfs(s, 0, new LinkedList<>());
        return plans;
    }

    /**
     * 利用DP数组中的分割状态进行回溯
     *
     * @param s
     * @param index 当前统计中待拆分单词的起始位置
     * @param plan  已经拆分好的部分
     */
    public void dfs(String s, int index, List<String> plan) {
        if (index == dp.length - 1) {
            plans.add(String.join(" ", plan));
            return;
        }
//        因为dp表的对应位置和单词中的字符有偏移，所以每次从index + 1位置起开始判断是否可拆分
//        i <= index + maxLen 是利用最长单词进行减值操作
        for (int i = index + 1; i < dp.length && i <= index + maxLen; i++) {
//            注意这里除了dp状态表我们还要确认[index,i-1]位置的字符是否存在于单词表中
//            只是因为dp状态表只统计是否可拆分，但是不确定如何拆分，因此无法仅凭dp表的状态来进行拆分
            if (dp[i] && root.search(s.substring(index, i))) {
                plan.add(s.substring(index, i));
                dfs(s, i, plan);
                plan.remove(plan.size() - 1);
            }
        }
    }
}

class PrefixTreeNode {
    PrefixTreeNode[] path;
    int end;

    public PrefixTreeNode() {
        path = new PrefixTreeNode[26];
        end = 0;
    }

    public void insert(String word) {
        char[] letter = word.toCharArray();
        PrefixTreeNode cur = this;
        for (int i = 0; i < letter.length; i++) {
            if (cur.path[letter[i] - 'a'] == null) {
                PrefixTreeNode node = new PrefixTreeNode();
                cur.path[letter[i] - 'a'] = node;
            }
            cur = cur.path[letter[i] - 'a'];
        }
        cur.end++;
    }

    public boolean search(String word) {
        char[] letter = word.toCharArray();
        PrefixTreeNode cur = this;
        for (int i = 0; i < letter.length; i++) {
            if (cur.path[letter[i] - 'a'] == null)
                return false;
            cur = cur.path[letter[i] - 'a'];
        }
        if (cur.end == 0)
            return false;
        else
            return true;
    }
}
