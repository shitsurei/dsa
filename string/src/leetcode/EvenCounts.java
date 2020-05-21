package leetcode;

import java.util.Arrays;

/**
 * 给你一个字符串s，请你返回满足以下条件的最长子字符串的长度：每个元音字母，即 'a'，'e'，'i'，'o'，'u' ，在子字符串中都恰好出现了偶数次。
 * <p>
 * 示例 1：
 * 输入：s = "eleetminicoworoep"
 * 输出：13
 * 解释：最长子字符串是 "leetminicowor" ，它包含 e，i，o 各 2 个，以及 0 个 a，u 。
 * 示例 2：
 * 输入：s = "leetcodeisgreat"
 * 输出：5
 * 解释：最长子字符串是 "leetc" ，其中包含 2 个 e 。
 * 示例 3：
 * 输入：s = "bcbcbc"
 * 输出：6
 * 解释：这个示例中，字符串 "bcbcbc" 本身就是最长的，因为所有的元音 a，e，i，o，u 都出现了 0 次。
 * <p>
 * 提示：
 * 1 <= s.length <= 5 x 10^5
 * s 只包含小写英文字母。
 */
public class EvenCounts {
    public static void main(String[] args) {
        String s = "elaecwwa";
        System.out.println(new EvenCounts().findTheLongestSubstring(s));
    }

    /**
     * 思路：前缀和+位运算+状态压缩（老千层饼了）
     * 暴力求解：通过O(N²)的时间遍历所有子串可能性，再用O(N)的时间统计aeiou每个元音字符出现的次数，
     *              如果都是偶数则当前子串满足条件，比较并记录所有遍历过的子串的长度中的最大值
     *                      总的时间复杂度大于O(N^3)
     * 优化1：通过前缀和pre[i][j]记录截止第i个位置第j个元音字符出现的次数，统计一遍整个字符串长度的只需要O(5*N)的时间
     *          再通过O(N²)的时间遍历每一个前缀和的区间，即找出pre[i][0-4]与pre[j][0-4]之差满足均为偶数的条件的区间中长度最长的值
     *              总的时间复杂度优化到O(N²)
     * 优化2：对于本题而言，我们没有必要统计出字串中每个元音字符出现的个数，只需要确定其奇偶状态即可，例如偶数个记为0，奇数个记为1
     *          此时就可以利用位运算的便利性，每次遇到新纳入的元音字符就对状态值取进行异或1的位运算，这样可以将5个状态位合并成1个数字
     *          例如，00000表示aeiou的状态都为偶数个，00100表示i为奇数个，其余均为偶数个
     *     关键：【对于每一个状态来说，如果之前没有出现过，那么第一次出现一定是不满足条件的，除了00000，但是当第二次出现时，那么与第一次出现该状态的长度间距就是该状态下满足条件的最大长度】
     */
    public int findTheLongestSubstring(String s) {
//        用于记录当前前缀中元音字符的奇偶状态
        int state = 0;
//        遍历过程中比较和记录满足偶数状态的最大子串长度
        int max = 0;
//        记录每种状态首次出现时最后一个元音字符所在的位置
        int[] dp = new int[32];
//        1~31之间的二进制表示的状态第一次出现时一定不满足均为偶数个这个条件
        Arrays.fill(dp, -1);
//        00000这个状态天然满足条件
        dp[0] = 0;
        char[] ch = s.toCharArray();
        for (int i = 0; i < ch.length; i++) {
//            每次遍历到元音字符时，通过位运算得到当前的奇偶状态
            switch (ch[i]) {
                case 'a':
                    state ^= 1;
                    break;
                case 'e':
                    state ^= 2;
                    break;
                case 'i':
                    state ^= 4;
                    break;
                case 'o':
                    state ^= 8;
                    break;
                case 'u':
                    state ^= 16;
                    break;
            }
//            如果这种状态从未出现过，则记录下首次出现时最后一个元音字符所在的位置
            if (dp[state] < 0)
                dp[state] = i + 1;
//            比较和更新包含当前字符的满足条件的子串长度
            max = Math.max(max, i + 1 - dp[state]);
        }
        return max;
    }
}
