package LeetCode;

/**
 * 给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
 * <p>
 * 示例:
 * 输入: [-2,1,-3,4,-1,2,1,-5,4],
 * 输出: 6
 * 解释: 连续子数组 [4,-1,2,1] 的和最大，为 6。
 * 进阶:
 * 如果你已经实现复杂度为 O(n) 的解法，尝试使用更为精妙的分治法求解。
 */
public class MaxSubArraySum {
    /**
     * 简单动态规划
     * 以当前位置为结尾的最大子序列累加和取决于【前一个位置为结尾的最大子序列之和加当前位置的值】
     * 1 相加大于当前位置时，相加结果为当前位置的累加和
     * 2 相加小于当前位置时，当前位置元素本身为累加和
     */
    public int maxSubArray(int[] nums) {
        int max = nums[0], temp = nums[0];
        for (int i = 1; i < nums.length; i++) {
            temp = nums[i] + temp < nums[i] ? nums[i] : temp + nums[i];
            max = Math.max(max, temp);
        }
        return max;
    }
}
