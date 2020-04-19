package leetcode;

import java.util.*;

/**
 * 给定一个字符串 s 和一些长度相同的单词 words。找出 s 中恰好可以由 words 中所有单词串联形成的子串的起始位置。
 * 注意子串要与 words 中的单词完全匹配，中间不能有其他字符，但不需要考虑 words 中单词串联的顺序。
 * <p>
 * 示例 1：
 * 输入：
 * s = "barfoothefoobarman",
 * words = ["foo","bar"]
 * 输出：[0,9]
 * 解释：
 * 从索引 0 和 9 开始的子串分别是 "barfoo" 和 "foobar" 。
 * 输出的顺序不重要, [9,0] 也是有效答案。
 * 示例 2：
 * 输入：
 * s = "wordgoodgoodgoodbestword",
 * words = ["word","good","best","word"]
 * 输出：[]
 */
public class ConcatenationSubstring {
    public static void main(String[] args) {
        String s = "ababaab";
        String[] words = {"ab", "ba", "ba"};
        System.out.println(new ConcatenationSubstring().findSubstring(s, words));
    }
    public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> ans = new LinkedList<>();
        if (words == null || words.length == 0) {
            return ans;
        }
        Map<String, Integer> wordNum = new HashMap<>();
        Map<String, Integer> wordCur = new HashMap<>();
        for (String e : words) {
            wordNum.put(e, wordNum.getOrDefault(e, 0) + 1);
            wordCur.put(e, 0);
        }
        int wordLen = words[0].length();
        for (int i = 0; i < s.length() && i + words.length * wordLen <= s.length(); i++) {
            int cur = i;
            while (cur + wordLen <= s.length() && wordCur.containsKey(s.substring(cur, cur + wordLen))
                    && wordCur.get(s.substring(cur, cur + wordLen)) < wordNum.get(s.substring(cur, cur + wordLen))) {
                wordCur.put(s.substring(cur, cur + wordLen), wordCur.get(s.substring(cur, cur + wordLen)) + 1);
                cur += wordLen;
            }
            if (cur == i + words.length * wordLen) {
                ans.add(i);
            }
            for (String e : words) {
                wordCur.put(e, 0);
            }
        }
        return ans;
    }
}
