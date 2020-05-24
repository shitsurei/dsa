package LeetCode;

/**
 * 给你两个数组 nums1 和 nums2 。
 * 请你返回 nums1 和 nums2 中两个长度相同的 非空 子序列的最大点积。
 * 数组的非空子序列是通过删除原数组中某些元素（可能一个也不删除）后剩余数字组成的序列，但不能改变数字间相对顺序。
 * 比方说，[2,3,5] 是 [1,2,3,4,5] 的一个子序列而 [1,5,3] 不是。
 *
 * 示例 1：
 * 输入：nums1 = [2,1,-2,5], nums2 = [3,0,-6]
 * 输出：18
 * 解释：从 nums1 中得到子序列 [2,-2] ，从 nums2 中得到子序列 [3,-6] 。
 * 它们的点积为 (2*3 + (-2)*(-6)) = 18 。
 * 示例 2：
 * 输入：nums1 = [3,-2], nums2 = [2,-6,7]
 * 输出：21
 * 解释：从 nums1 中得到子序列 [3] ，从 nums2 中得到子序列 [7] 。
 * 它们的点积为 (3*7) = 21 。
 * 示例 3：
 * 输入：nums1 = [-1,-1], nums2 = [1,1]
 * 输出：-1
 * 解释：从 nums1 中得到子序列 [-1] ，从 nums2 中得到子序列 [1] 。
 * 它们的点积为 -1 。
 *
 * 提示：
 * 1 <= nums1.length, nums2.length <= 500
 * -1000 <= nums1[i], nums2[i] <= 100
 */
public class MaxDotProduct {

    /**
     * 思路：动态规划
     * dp[i][j]表示截止以nums1第i个数字和以nums2第j个数字为结尾的子序列中，点积最大值
     */
    public int maxDotProduct(int[] nums1, int[] nums2) {
        int m = nums1.length, n = nums2.length;
//        dp数组初始值取int最小值，累加时可能存在溢出情况，因此使用long类型来存储
        long[][] dp = new long[m + 1][n + 1];
//        第一行和第一列为初始值设int最小值，这样当遇到累加和为负数的情况可以统一运算
        for (int i = 0; i <= m; i++)
            dp[i][0] = Integer.MIN_VALUE;
        for (int i = 0; i <= n; i++)
            dp[0][i] = Integer.MIN_VALUE;
//        dp推算截止以nums1第i个数字和以nums2第j个数字为结尾的子序列中点积最大值
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
//                情况1：将nums1第i个数字和nums2第j个数字的乘积作为dp结果，适用于之前的累加结果为负数的情况
                dp[i][j] = nums1[i - 1] * nums2[j - 1];
//                情况2：在之前累加和的基础上加上nums1第i个数字和nums2第j个数字的乘积
                dp[i][j] = Math.max(dp[i][j], dp[i][j] + dp[i - 1][j - 1]);
//                情况3：跳过nums1第i个数字
                dp[i][j] = Math.max(dp[i][j], dp[i - 1][j]);
//                情况4：跳过nums2第j个数字
                dp[i][j] = Math.max(dp[i][j], dp[i][j - 1]);
            }
        }
        return (int) dp[m][n];
    }
}
