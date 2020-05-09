package LeetCode;

/**
 * 实现 int sqrt(int x) 函数。
 * 计算并返回 x 的平方根，其中 x 是非负整数。
 * 由于返回类型是整数，结果只保留整数的部分，小数部分将被舍去。
 * <p>
 * 示例 1:
 * 输入: 4
 * 输出: 2
 * 示例 2:
 * 输入: 8
 * 输出: 2
 * 说明: 8 的平方根是 2.82842...,由于返回类型是整数，小数部分将被舍去。
 */
public class MySqrt {
    public static void main(String[] args) {
        System.out.println(new MySqrt().mySqrt(4));
    }

    /**
     * 思路1：二分查找
     * 左边界为1，右边界为x/2
     */
    public int mySqrt(int x) {
        if (x == 0 || x == 1)
            return x;
        long start = 1, end = x >> 1;
        while (start < end) {
//            这里必须取右中位数，否则代码有可能陷入死循环
            long mid = start + ((end - start + 1) >> 1);
            long multi = mid * mid;
            if (multi > x)
                end = mid - 1;
            else
                start = mid;
        }
        return (int) start;
    }

    /**
     * 思路2：牛顿法，推导出递推公式
     * 求a的平方根，即x²=a中x的取值，可以转换为求f(x)=x²-a取0时x的解
     * 根据泰勒展开式f(x)~=f(x0)+(x-x0)f'(x0)，令f(x)=0
     * ……
     * 最终得到递推公式 x = (x + a / x) /2
     */
    public int mySqrt1(int x) {
        long a = x;
        while (a * a > x) {
            a = (a + x / a) / 2;
        }
        return (int) a;
    }
}
