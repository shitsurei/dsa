package leetcode;

import java.util.Stack;

/**
 * 给定一个经过编码的字符串，返回它解码后的字符串。
 * 编码规则为: k[encoded_string]，表示其中方括号内部的 encoded_string 正好重复 k 次。注意 k 保证为正整数。
 * 你可以认为输入字符串总是有效的；输入字符串中没有额外的空格，且输入的方括号总是符合格式要求的。
 * 此外，你可以认为原始数据不包含数字，所有的数字只表示重复的次数 k ，例如不会出现像 3a 或 2[4] 的输入。
 * <p>
 * 示例:
 * s = "3[a]2[bc]", 返回 "aaabcbc".
 * s = "3[a2[c]]", 返回 "accaccacc".
 * s = "2[abc]3[cd]ef", 返回 "abcabccdcdcdef".
 */
public class DecodeString {
    public static void main(String[] args) {
        System.out.println(new DecodeString().decodeString("100[leet]"));
    }

    /**
     * 思路：借助栈进行字符串的转换，将数字部门和字符串子串部分合并处理
     */
    public String decodeString(String s) {
        Stack<String> stack = new Stack<>();
        StringBuilder builder = null;
        int i = 0;
        while (i < s.length()) {
            builder = new StringBuilder();
//            将遇到的数字字符合并成一个字符串后压栈
            while (Character.isDigit(s.charAt(i)))
                builder.append(s.charAt(i++));
            if (builder.length() > 0) {
                stack.push(builder.toString());
                stack.push(String.valueOf(s.charAt(i++)));
                continue;
            }
//            将遇到的每一组字母字符合并成一个字符串后压栈
            while (i < s.length() && Character.isLetter(s.charAt(i)))
                builder.append(s.charAt(i++));
            if (builder.length() > 0)
                stack.push(builder.toString());
//            如果遇到右括号字符就将子串弹栈并进行合并相乘后再压栈
            if (i < s.length() && s.charAt(i) == ']') {
                i++;
                builder = new StringBuilder();
                String subStr = stack.pop();
                while (!subStr.equals("[")) {
//                    注意此处拼接字符串时每次弹栈的子串要拼在已有子串的最前面，因为出栈顺序和子串的拼接顺序相反
                    builder.insert(0, subStr);
                    subStr = stack.pop();
                }
                String str = builder.toString();
//                由于数字子串已经处理过，可以直接转换为int类型
                int time = Integer.parseInt(stack.pop());
                for (int j = 0; j < time - 1; j++)
                    builder.append(str);
                stack.push(builder.toString());
            }
        }
//        最终将全部处理完后的栈元素弹栈并拼接，得到最终编译结果
        builder = new StringBuilder();
        while (!stack.isEmpty())
//            此处的拼接同理需要与出栈顺序相逆
            builder.insert(0, stack.pop());
        return builder.toString();
    }
}
