package LeetCode;

/**
 * 输入一个整数 n ，求1～n这n个整数的十进制表示中1出现的次数。
 * 例如，输入12，1～12这些整数中包含1 的数字有1、10、11和12，1一共出现了5次。
 * <p>
 * 示例 1：
 * 输入：n = 12
 * 输出：5
 * 示例 2：
 * 输入：n = 13
 * 输出：6
 * <p>
 * 限制：
 * 1 <= n < 2^31
 */
public class OneTimes {
    public static void main(String[] args) {
        System.out.println(new OneTimes().countDigitOne(824883294));
    }

    /**
     * 例子如n=1234，high=1, pow=1000, last=234
     * 可以将数字范围分成两部分1~999和1000~1234
     * <p>
     * 1~999这个范围1的个数是f(pow-1)
     * 1000~1234这个范围1的个数需要分为两部分：
     * 千分位是1的个数：千分位为1的个数刚好就是234+1(last+1)，注意，这儿只看千分位，不看其他位
     * 其他位是1的个数：即是234中出现1的个数，为f(last)
     * 所以全部加起来是f(pow-1) + last + 1 + f(last);
     * <p>
     * 例子如3234，high=3, pow=1000, last=234
     * 可以将数字范围分成两部分1~999，1000~1999，2000~2999和3000~3234
     * <p>
     * 1~999这个范围1的个数是f(pow-1)
     * 1000~1999这个范围1的个数需要分为两部分：
     * 千分位是1的个数：千分位为1的个数刚好就是pow，注意，这儿只看千分位，不看其他位
     * 其他位是1的个数：即是999中出现1的个数，为f(pow-1)
     * 2000~2999这个范围1的个数是f(pow-1)
     * 3000~3234这个范围1的个数是f(last)
     * 所以全部加起来是pow + high*f(pow-1) + f(last);
     *
     * @param n
     * @return
     */
    public int countDigitOne(int n) {
        if (n <= 0)
            return 0;
        int high = n, dig = 1;
        while (high / 10 > 0) {
            dig++;
            high /= 10;
        }
        int pow = (int) Math.pow(10, dig - 1);
        int low = n - high * pow;
        if (high == 1)
            return countDigitOne(pow - 1) + low + 1 + countDigitOne(low);
        else
            return pow + high * countDigitOne(pow - 1) + countDigitOne(low);
    }
}
