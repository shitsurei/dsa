package leetcode;

import java.util.LinkedList;
import java.util.List;

/**
 * 给定一个字符串 s，将 s 分割成一些子串，使每个子串都是回文串。
 * 返回 s 所有可能的分割方案。
 * <p>
 * 示例:
 * 输入: "aab"
 * 输出:
 * [
 * ["aa","b"],
 * ["a","a","b"]
 * ]
 */
public class DividePalindrome {
    public static void main(String[] args) {
        String s = "ababa";
        System.out.println(new DividePalindrome().partition1(s));
    }

    List<List<String>> plans = new LinkedList<>();

    /**
     * 思路1：正常回溯
     *
     * @param s
     * @return
     */
    public List<List<String>> partition(String s) {
        char[] strs = s.toCharArray();
        dfs(strs, new LinkedList<>(), 0);
        return plans;
    }

    /**
     * 正常回溯时，从字符串头开始，逐渐向后搜索，每发现一个回文子串，就递归调用剩余未搜索子串
     *
     * @param strs  字符数组
     * @param plan  用于回溯的过程中保存临时生成的回文子串集合
     * @param start 每次搜索时剩余子串起始位置
     */
    public void dfs(char[] strs, List<String> plan, int start) {
//        递归终止条件为所有子串均已纳入回文子串列表
        if (start == strs.length) {
            plans.add(new LinkedList<>(plan));
            return;
        }
        for (int i = start; i < strs.length; i++) {
            if (isPalindrome(strs, start, i)) {
                plan.add(String.copyValueOf(strs, start, i - start + 1));
                dfs(strs, plan, i + 1);
                plan.remove(plan.size() - 1);
            }
        }
    }

    /**
     * 辅助函数，用于判断字符串是否为回文串
     *
     * @param strs
     * @param start
     * @param end
     * @return
     */
    public boolean isPalindrome(char[] strs, int start, int end) {
        while (start < end)
            if (strs[start++] != strs[end--])
                return false;
        return true;
    }

    /**
     * 思路2：动态规划+回溯，上面的回溯过程中我们重复判断了部分子串是否为回文串
     * 利用动态规划，将整个数组中所有的回文子串位置都记录下来，加速回溯的过程
     * 以字符串“ababa”为例，二维dp数组如下，每个位置(x,y)代表从字符索引x位置到y位置是否为回文串
     *      a   b   a   b   a
     *   a  T
     *   b      T
     *   a          T
     *   b              T
     *   a                  T
     * 由于回文子串从前往后和从后往前是重复的，因此我们只需要利用一半的二维数组
     * 初始条件为左上至右下的对角线均为True，因为每个字符本身属于回文串
     * 下面我们来考虑任意位置的状态依赖关系，以(x,y)位置为例，代表字符串中下标x位置到y位置子串的回文情况
     * 可以分为两种情况：
     * 1 (x+1,y-1)这一段子串不为回文子串，那么此时(x,y)一定不为回文子串
     * 2 (x+1,y-1)这一段子串是回文子串，那么(x,y)是否为回文子串就要考虑两端的x下标字符和y下标字符是否相等
     * 2-1 相等(x,y)就是回文子串
     * 2-1 不相等(x,y)就不是回文子串
     * 因此在dp表中表现出来的规律是除起始位置外，每个位置的状态依赖于左下位置的回文状态
     * 【注意，当左下位置不在我们的判别范围内时，也就是紧挨着初始位置之后的一个位置，也可以视为初始条件的一部分，直接(x,y)位置下标比较即可】
     * 如下图所示：
     *      a   b   a   b   a
     *   a  T   F
     *   b      T   F
     *   a          T   F
     *   b              T   F
     *   a                  T
     * 从这里可以发现，我们在完善dp表格的时候，需要按照从左上到右下斜着一列一列的去填充，这里需要一些下标的变换处理
     * 最后整个dp表状态如下：
     *      a   b   a   b   a
     *   a  T   F   T   F   T
     *   b      T   F   T   F
     *   a          T   F   T
     *   b              T   F
     *   a                  T
     */
    public List<List<String>> partition1(String s) {
        int len = s.length();
        boolean[][] dp = new boolean[len][len];
//        第一步，计算dp数组，确定字符串中所有子串是否为回文串
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len - i; j++) {
//                情况1：对角线一（斜）列均为true
                if (j == j + i)
                    dp[j][j + i] = true;
//                情况2：对角线之后的一（斜）列直接比较(x,y)下标对应字符是否相等
                else if (j + 1 > j + i - 1 && s.charAt(j) == s.charAt(j + i))
                    dp[j][j + i] = true;
//                情况3：其余位置需要依赖左下位置的回文状态和(x,y)下标对比确定
                else if (dp[j + 1][j + i - 1] && s.charAt(j) == s.charAt(j + i))
                    dp[j][j + i] = true;
                else
                    dp[j][j + i] = false;
            }
        }
        dfs(s, dp, 0, len, new LinkedList<>());
        return plans;
    }

    /**
     * 利用求出的回文状态dp表进行深度优先搜索回溯
     * @param s
     * @param dp
     * @param index
     * @param len
     * @param plan
     */
    public void dfs(String s, boolean[][] dp, int index, int len, List<String> plan) {
        if (index == len) {
            plans.add(new LinkedList<>(plan));
            return;
        }
        a:
        for (int i = index; i < len; i++) {
            for (int j = i; j < len; j++) {
                if (dp[i][j]) {
                    plan.add(s.substring(i, j + 1));
                    dfs(s, dp, j + 1, len, plan);
                    plan.remove(plan.size() - 1);
                }
//                当遍历至每行最后一个状态时，不论是否可以划分回文串都直接跳出整个循环
                if (j == len - 1)
                    break a;
            }
        }
    }
}
