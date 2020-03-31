package leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
 * <p>
 * 示例 1:
 * 输入: "abcabcbb"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
 * 示例 2:
 * 输入: "bbbbb"
 * 输出: 1
 * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
 * 示例 3:
 * 输入: "pwwkew"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
 *      请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
 */
public class MaxSubStringWithoutRepeated {
    public static void main(String[] args) {
        System.out.println(new MaxSubStringWithoutRepeated().lengthOfLongestSubstring("abcabcbb"));
    }

    /**
     * 利用ASCII码的范围属性，使用数组充当hash表，避免了系统api的性能开销
     * 思路与hash表大体相同
     */
    public int lengthOfLongestSubstring(String s) {
        int start = 0, cur = 0, max = 0;
        int[] hash = new int[128];
        Arrays.fill(hash, -1);
        for (int k = 0; k < s.length(); k++) {
//            出现重复元素时仅更新未重复子串的长度和头位置
            if (hash[s.charAt(k)] != -1 && hash[s.charAt(k)] >= start) {
                cur = k - hash[s.charAt(k)];
                start = hash[s.charAt(k)];
            } else {
                cur++;
                max = Math.max(max, cur);
            }
            hash[s.charAt(k)] = k;
        }
        return max;
    }

    /**
     * hash表做法：
     * 遍历字符串，将每次遍历到的字符和下标索引存入hash表，判断新遍历的元素是否已经重复
     * 维护三个值：当前累计未重复子串的初始下标位置startIndex，当前子串长度curLen，全局未重复子串长度最大值maxLen
     * 出现重复时，去除累计未重复子串中重复位置以及他之前的字符在hash表中的存储记录，更新维护的相关值
     * 【时间复杂度和空间复杂度表现一般】
     * 优化的hash表做法：
     * 没有必要每次出现重复元素时就将未在新的重复字串中的元素删除，因为我们记录了重复元素的下标，索引通过下标变化即可得到新的子串长度
     * 这里要注意判断重复的元素是否真的重复？（即虽然元素在hash表中有对应下标，但已经不是当前子串范围内时，任然不视为元素重复）
     */
    public int lengthOfLongestSubstring1(String s) {
        if (s == null)
            return 0;
        else if (s.length() < 2)
            return s.length();
        Map<Character, Integer> hash = new HashMap<>();
        int maxLen = 0, curLen = 0, startIndex = 0;
        for (int i = 0; i < s.length(); i++) {
            if (hash.containsKey(s.charAt(i)) && hash.get(s.charAt(i)) >= startIndex) {
                int index = hash.get(s.charAt(i));
//                判断新出现的重复字符在累计未重复子串中位置靠近左边还是右边
//                从而决定在原hash表中去元素还是清空原hash表新增元素（选择数量少，成本低的操作）
//                if (index - startIndex > i - index) {
//                    hash.clear();
//                    for (int j = index + 1; j < i; j++)
//                        hash.put(s.charAt(j), j);
//                } else {
//                    for (int j = startIndex; j < index; j++)
//                        hash.remove(s.charAt(j));
//                }
                curLen -= index - startIndex;
                startIndex = index + 1;
            } else {
                curLen++;
                maxLen = Math.max(maxLen, curLen);
            }
            hash.put(s.charAt(i), i);
        }
        return maxLen;
    }
}
