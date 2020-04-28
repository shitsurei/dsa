package LeetCode;

import java.util.Arrays;

/**
 * 一个整型数组 nums 里除两个数字之外，其他数字都出现了两次。
 * 请写程序找出这两个只出现一次的数字。要求时间复杂度是O(n)，空间复杂度是O(1)。
 * <p>
 * 示例 1：
 * 输入：nums = [4,1,4,6]
 * 输出：[1,6] 或 [6,1]
 * 示例 2：
 * 输入：nums = [1,2,10,4,1,4,3,3]
 * 输出：[2,10] 或 [10,2]
 * <p>
 * 限制：
 * 2 <= nums <= 10000
 */
public class NumTimes2 {
    public static void main(String[] args) {
        int[] nums = {8, 1, 2, 2, 1, 4, 5, 5};
        System.out.println(Arrays.toString(new NumTimes2().singleNumbers(nums)));
    }

    /**
     * 思路：利用异或运算的性质，相同的数异或之后结果为零，不同的数异或之后结果不为0，且位运算符合交换律和结合律
     * 【且两个不同的数异或的结果中二进制位为1时表示两个不同的数中该二进制位的值不相同】
     * 1 将数组中所有的数异或得到的结果即为只出现一次的两个数的异或结果
     * 2 找出这两个数的二进制表示中任意一位值不相同的位数
     * 3 再次异或整个数组，但是要根据之前找到的不相同的那一位来对数组中的数分类
     * 这样重复的相同的数依然会被分在同一组中异或消除掉，不同的数就分在不同的组，最终两组数异或的结果就是只出现一次的数
     */
    public int[] singleNumbers(int[] nums) {
        int mix = 0;
        for (int i = 0; i < nums.length; i++)
            mix ^= nums[i];
        int diff = 1;
        while ((mix & diff) == 0)
            diff <<= 1;
        int re1 = 0, re2 = 0;
        for (int i = 0; i < nums.length; i++) {
//            只能判0，非0情况可能为1，2，4，8……
            if ((nums[i] & diff) == 0)
                re2 ^= nums[i];
            else
                re1 ^= nums[i];
        }
        return new int[]{re1, re2};
    }
}
