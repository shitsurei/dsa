package leetcode;

import java.util.LinkedList;
import java.util.List;

/**
 * 数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合。
 * <p>
 * 示例：
 * 输入：n = 3
 * 输出：[
 * "((()))",
 * "(()())",
 * "(())()",
 * "()(())",
 * "()()()"
 * ]
 */
public class GenerateParentheses {
    /**
     * 思路：dfs+剪枝，经典回溯
     */
    public static void main(String[] args) {
        System.out.println(new GenerateParentheses().generateParenthesis(4));
    }

    private List<String> plans = new LinkedList<>();

    public List<String> generateParenthesis(int n) {
        char[] paten = new char[n * 2];
//        初始条件，左右两端的括号是确定的
        paten[0] = '(';
        paten[2 * n - 1] = ')';
        dfs(paten, 1, n - 1, n - 1);
        return plans;
    }

    /**
     * 每次递归，根据左括号和右括号的剩余个数进行剪枝
     */
    public void dfs(char[] paten, int index, int left, int right) {
//        左括号的剩余个数较多且与右括号的剩余个数相差超过1时，该序列中一定存在不合法的括号对，直接剪枝
        if (left - right > 1)
            return;
//        左括号全部用完时，剩余位置之间填充右括号，并保留结果
        if (left == 0) {
            for (int i = index; i < paten.length - 1; i++)
                paten[i] = ')';
            plans.add(String.copyValueOf(paten));
            return;
        }
//        当所有位置遍历结束时，保留结果
        if (index == paten.length - 1) {
            plans.add(String.copyValueOf(paten));
            return;
        }
//        填充左括号进行深度搜索
        paten[index] = '(';
        dfs(paten, index + 1, left - 1, right);
//        填充右括号进行深度搜索
        paten[index] = ')';
        dfs(paten, index + 1, left, right - 1);
    }
}
