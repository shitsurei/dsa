package LeetCode;

/**
 * 在一个数组 nums 中除一个数字只出现一次之外，其他数字都出现了三次。请找出那个只出现一次的数字。
 * <p>
 * 示例 1：
 * 输入：nums = [3,4,3,3]
 * 输出：4
 * 示例 2：
 * 输入：nums = [9,1,7,9,7,9,7]
 * 输出：1
 * <p>
 * 限制：
 * 1 <= nums.length <= 10000
 * 1 <= nums[i] < 2^31
 */
public class NumTimes {
    public static void main(String[] args) {
        int[] nums = new int[]{3, 4, 3, 3};
        System.out.println(new NumTimes().singleNumber(nums));
    }

    public int singleNumber(int[] nums) {
        int[] bitCount = new int[32];
        for (int i = 0; i < nums.length; i++) {
            for (int j = 31; j >= 0; j--) {
                if ((nums[i] & 1) == 1)
                    bitCount[j]++;
                nums[i] >>= 1;
            }
        }
        int re = 0;
        for (int i = 0; i < 32; i++) {
            re <<= 1;
            if (bitCount[i] % 3 == 1)
                re++;
        }
        return re;
    }
}
