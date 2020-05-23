package LeetCode;

import java.util.*;

/**
 * 给你一个字符串 S、一个字符串 T，请在字符串 S 里面找出：包含 T 所有字符的最小子串。
 * <p>
 * 示例：
 * 输入: S = "ADOBECODEBANC", T = "ABC"
 * 输出: "BANC"
 * 说明：
 * 如果 S 中不存这样的子串，则返回空字符串 ""。
 * 如果 S 中存在这样的子串，我们保证它是唯一的答案。
 */
public class MinimumWindowSubstring {
    public static void main(String[] args) {
        MinimumWindowSubstring mws = new MinimumWindowSubstring();
        System.out.println(mws.minWindow("a", "aa"));
    }

    /**
     * 思路：滑动窗口+计数索引
     */
    public String minWindow(String s, String t) {
//        存储条件串T中所包含的字符及其数量以及当前滑动窗口子串中相应字符的数量
        Map<Character, int[]> conditions = new HashMap<>();
//        条件串中字符种类个数
        int type = 0;
        for (int i = 0; i < t.length(); i++) {
            int[] condition = conditions.getOrDefault(t.charAt(i), new int[2]);
            if (condition[0] == 0)
                type++;
            condition[0]++;
            conditions.put(t.charAt(i), condition);
        }
//        存储搜索串S中属于目标范围的字符索引
        List<Integer> sequence = new ArrayList<>();
        for (int i = 0; i < s.length(); i++)
            if (conditions.containsKey(s.charAt(i)))
                sequence.add(i);
//        当S串中属于目标字符的个数小于目标字符的种类数时一定无法找到符合条件的子串直接返回
        if (sequence.size() < type)
            return "";
//        记录遍历过程中全局最小满足目标子串条件的窗口长度和左右边界
        int minLen = s.length(), minLeft = 0, minRight = s.length() - 1;
//        默认不存在符合条件的子串
        boolean exist = false;
//        滑动窗口的左右边界，默认从0位置开始，且需要统计当前窗口中目标字符数量达到目标子串的数量的个数
        int left = 0, right = 0, satisfy = 0;
//        【需要保证一次只有比较之后只移动一侧边界】
        while (right < sequence.size()) {
//            当不满足条件时，窗口右边界后移
            if (satisfy < type) {
                int[] condition = conditions.get(s.charAt(sequence.get(right)));
                if (condition[0] == condition[1] + 1) {
                    satisfy++;
                }
                condition[1]++;
            }
//            满足条件时，比较并更新符合条件的目标子串，并且窗口左边界后移
            if (satisfy == type) {
                exist = true;
                int newLen = Math.min(minLen, sequence.get(right) - sequence.get(left) + 1);
                if (newLen < minLen) {
                    minLen = newLen;
                    minLeft = sequence.get(left);
                    minRight = sequence.get(right);
                }
//                此处如果找到的目标子串最小值等于T串的长度，即无法找到长度更小的符合条件的子串，直接跳过之后的搜索
                if (minLen == t.length())
                    break;
                int[] condition = conditions.get(s.charAt(sequence.get(left++)));
                condition[1]--;
                if (condition[1] < condition[0])
                    satisfy--;
            }
//            当左边界后移之后可能出现当前窗口的子串仍然满足条件的情况，此时右边界暂时不后移
            if (satisfy < type)
                right++;
        }
        if (exist)
            return s.substring(minLeft, minRight + 1);
        else
            return "";
    }
}
