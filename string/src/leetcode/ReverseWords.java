package leetcode;

/**
 * 给定一个字符串，逐个翻转字符串中的每个单词。
 * <p>
 * 示例 1：
 * 输入: "the sky is blue"
 * 输出: "blue is sky the"
 * 示例 2：
 * 输入: "  hello world!  "
 * 输出: "world! hello"
 * 解释: 输入字符串可以在前面或者后面包含多余的空格，但是反转后的字符不能包括。
 * 示例 3：
 * 输入: "a good   example"
 * 输出: "example good a"
 * 解释: 如果两个单词间有多余的空格，将反转后单词间的空格减少到只含一个。
 * <p>
 * 说明：
 * 无空格字符构成一个单词。
 * 输入字符串可以在前面或者后面包含多余的空格，但是反转后的字符不能包括。
 * 如果两个单词间有多余的空格，将反转后单词间的空格减少到只含一个。
 * <p>
 * 进阶：
 * 请选用 C 语言的用户尝试使用 O(1) 额外空间复杂度的原地解法。
 */
public class ReverseWords {
    public static void main(String[] args) {
        System.out.println(new ReverseWords().reverseWords("a good   example"));
    }

    /**
     * 思路：
     * 1 调用库函数
     * 2 双指针
     * @param s
     * @return
     */
    public String reverseWords(String s) {
        if (s == null)
            return "";
        s = s.trim();
        if (s.length() == 0)
            return "";
        StringBuilder res = new StringBuilder();
        int j = s.length() - 1, i = j;
        while (i >= 0) {
            while (i >= 0 && s.charAt(i) != ' ')
                i--;
            res.append(s, i + 1, j + 1).append(' ');
            while (i >= 0 && s.charAt(i) == ' ')
                i--;
            j = i;
        }
        return res.deleteCharAt(res.length() - 1).toString();
    }
}
