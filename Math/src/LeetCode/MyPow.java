package LeetCode;

/**
 * 实现 pow(x, n) ，即计算 x 的 n 次幂函数。
 * <p>
 * 示例 1:
 * 输入: 2.00000, 10
 * 输出: 1024.00000
 * 示例 2:
 * 输入: 2.10000, 3
 * 输出: 9.26100
 * 示例 3:
 * 输入: 2.00000, -2
 * 输出: 0.25000
 * 解释: 2-2 = 1/22 = 1/4 = 0.25
 * 说明:
 * -100.0 < x < 100.0
 * n 是 32 位有符号整数，其数值范围是 [−231, 231 − 1] 。
 */
public class MyPow {
    public static void main(String[] args) {
        System.out.println(new MyPow().myPow(2.00000, 16));
    }

    /**
     * 思路：快速幂，分治思想
     */
    public double myPow(double x, int n) {
        double res = 1.0;
        for (int i = n; i != 0; i /= 2) {
            if (i % 2 != 0) {
//                res此处用于在每一次乘数个数二分有余数是时将余数累乘进结果
                res *= x;
            }
//            快速求出1 2 4 8 16 32……乘数因子
            x *= x;
        }
//        倒数判断
        return n < 0 ? 1 / res : res;
    }

}
