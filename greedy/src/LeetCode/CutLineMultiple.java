package LeetCode;

/**
 * 给你一根长度为n的绳子，请把绳子剪成整数长度的m段（m、n都是整数，n>1并且m>1），每段绳子的长度记为 k[0],k[1]...k[m]。
 * 请问 k[0]*k[1]*...*k[m] 可能的最大乘积是多少？
 * 例如，当绳子的长度是8时，我们把它剪成长度分别为2、3、3的三段，此时得到的最大乘积是18。
 * <p>
 * 示例 1：
 * 输入: 2
 * 输出: 1
 * 解释: 2 = 1 + 1, 1 × 1 = 1
 * 示例 2:
 * 输入: 10
 * 输出: 36
 * 解释: 10 = 3 + 3 + 4, 3 × 3 × 4 = 36
 * <p>
 * 提示：
 * 2 <= n <= 58
 */
public class CutLineMultiple {
    public static void main(String[] args) {
        System.out.println(new CutLineMultiple().cuttingRope(120));
    }

    //    大佬的贪心
    public int cuttingRope(int n) {
        if (n == 2)
            return 1;
        if (n == 3)
            return 2;
//        答案需要取模 1e9+7（1000000007），如计算初始结果为：1000000008，请返回 1。
//        这么要求int型的范围就不够用了
        long sum = 1;
        while (n > 4) {
            sum = (sum * 3) % 1000000007;
            n -= 3;
        }
        return (int) (sum * n % 1000000007);
    }

    //    我的贪心
    public int cuttingRope2(int n) {
        int max = 0;
        for (int i = 2; i <= n; i++) {
            int remainder = n % i;
            int multi = n / i;
            int sum = 1;
            for (int j = 0; j < i; j++) {
                if (remainder-- > 0)
                    sum *= multi + 1;
                else
                    sum *= multi;
            }
            if (sum < max)
                break;
            else
                max = Math.max(max, sum);
        }
        return max;
    }
}
