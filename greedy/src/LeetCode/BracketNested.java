package LeetCode;

import java.util.Arrays;

/**
 * 有效括号字符串 仅由 "(" 和 ")" 构成，并符合下述几个条件之一：
 * 空字符串
 * 连接，可以记作 AB（A 与 B 连接），其中 A 和 B 都是有效括号字符串
 * 嵌套，可以记作 (A)，其中 A 是有效括号字符串
 * 类似地，我们可以定义任意有效括号字符串 s 的 嵌套深度 depth(S)：
 * <p>
 * s 为空时，depth("") = 0
 * s 为 A 与 B 连接时，depth(A + B) = max(depth(A), depth(B))，其中 A 和 B 都是有效括号字符串
 * s 为嵌套情况，depth("(" + A + ")") = 1 + depth(A)，其中 A 是有效括号字符串
 * 例如：""，"()()"，和 "()(()())" 都是有效括号字符串，嵌套深度分别为 0，1，2，而 ")(" 和 "(()" 都不是有效括号字符串。
 * <p>
 * 给你一个有效括号字符串 seq，将其分成两个不相交的子序列 A 和 B，且 A 和 B 满足有效括号字符串的定义
 * （注意：A.length + B.length = seq.length）。
 * 现在，你需要从中选出 任意 一组有效括号字符串 A 和 B，使 max(depth(A), depth(B)) 的可能取值最小。
 * 返回长度为 seq.length 答案数组 answer ，选择 A 还是 B 的编码规则是：如果 seq[i] 是 A 的一部分，那么 answer[i] = 0。
 * 否则，answer[i] = 1。即便有多个满足要求的答案存在，你也只需返回 一个。
 * <p>
 * 示例 1：
 * 输入：seq = "(()())"
 * 输出：[0,1,1,1,1,0]
 * 示例 2：
 * 输入：seq = "()(())()"
 * 输出：[0,0,0,1,1,0,1,1]
 * <p>
 * 提示：
 * 1 <= text.size <= 10000
 */
public class BracketNested {
    /**
     * 本题的难点在于读懂题意和划分序列，我们其实只需要关注左括号的分配方案就好了，因为有效括号意味着左右括号一一对应
     * 所以我们应该找出一种合理的方式来划分所有的左括号，使得他们的数量相差不超过1
     */
    public static void main(String[] args) {
        System.out.println(Arrays.toString(new BracketNested().maxDepthAfterSplit("(((()))())")));
    }

    /**
     * 甜姨的给跪思路，O(N)复杂度
     * 我们的目的是把整个深度划分为接近平均的两半，两个子序列在原序列中的位置关系是没有限制的
     * 所以我们没必要求出原序列的最大深度，采用奇偶分配的方式进行划分，结果一定是接近平均的
     * 而右括号的奇偶分配只要和左括号相反就可以了，这里使用位运算来操作，又简洁又高效
     * 再次给跪了▄█▀█●
     */
    public int[] maxDepthAfterSplit(String seq) {
        int[] divide = new int[seq.length()];
        int i = 0;
        for (char c : seq.toCharArray())
            divide[i++] = c == '(' ? i & 1 : (i + 1) & 1;
        return divide;
    }

    /**
     * 我的憨憨思路，O(2N)复杂度
     * 第一遍遍历整个序列，找出所有括号中最深的深度，以此为依据
     * 将最深的深度除以二（向下取整和向上取整的结果不影响，如果不能被2整除说明最终划分的两个子序列深度相差为1）
     * 第二遍遍历整个序列，当深度小于等于1/2最大深度时分为一组，其余的分为一组
     */
    public int[] maxDepthAfterSplit1(String seq) {
        if (seq == null || seq.length() == 0)
            return new int[0];
        int curDeep = 0, maxDeep = 0;
        for (char c : seq.toCharArray()) {
            if (c == '(') {
                curDeep++;
                maxDeep = Math.max(maxDeep, curDeep);
            } else
                curDeep--;
        }
        int[] divide = new int[seq.length()];
        curDeep = 0;
        int index = 0;
        for (char c : seq.toCharArray()) {
            if (c == '(') {
                if (curDeep < maxDeep / 2)
                    divide[index] = 0;
                else
                    divide[index] = 1;
                curDeep++;
            } else {
//                这里注意由于深度计算时的递增递减，导致左括号和右括号的判定条件有所差距
                if (curDeep < maxDeep / 2 + 1)
                    divide[index] = 0;
                else
                    divide[index] = 1;
                curDeep--;
            }
            index++;
        }
        return divide;
    }
}
