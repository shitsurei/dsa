package LeetCode;

/**
 * 实现函数double Power(double base, int exponent)，求base的exponent次方。不得使用库函数，同时不需要考虑大数问题。
 *
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
 *  
 * 说明:
 * -100.0 < x < 100.0
 * n 是 32 位有符号整数，其数值范围是 [−231, 231 − 1] 。
 */
public class MyPower {
    public static void main(String[] args) {
        System.out.println(new MyPower().myPow(2.00000, -2147483648));
    }

    public double myPow(double x, int n) {
        if (n == 0 || x == 1)
            return 1;
        long N = n;
        if (n < 0) {
            x = 1 / x;
            N = -N;
        }
        double re = 1;
        long j = 1;
        for (int i = 0; i < 64; i++) {
            if ((N & j) != 0)
                re *= x;
            x *= x;
            j <<= 1;
        }
        return re;
    }
}
