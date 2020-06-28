package LeetCode;

/**
 * 给定一个含有 n 个正整数的数组和一个正整数 s ，找出该数组中满足其和 ≥ s 的长度最小的连续子数组，并返回其长度。
 * 如果不存在符合条件的连续子数组，返回 0。
 * <p>
 * 示例：
 * 输入：s = 7, nums = [2,3,1,2,4,3]
 * 输出：2
 * 解释：子数组 [4,3] 是该条件下的长度最小的连续子数组。
 */
public class MinimumSizeSubarraySum {
    public static void main(String[] args) {
        int[] nums = {2, 3, 1, 2, 4, 3};
        System.out.println(new MinimumSizeSubarraySum().minSubArrayLen(7, nums));
    }

    public int minSubArrayLen(int s, int[] nums) {
        int sum = nums[0], left = 0, right = 1, ans = nums.length + 1;
        while (right <= nums.length) {
            if (sum >= s) {
                ans = Math.min(ans, right - left);
                if (ans == 1)
                    break;
                sum -= nums[left];
                left++;
            } else {
                if (right == nums.length)
                    break;
                sum += nums[right];
                right++;
            }
        }
        if (ans == nums.length + 1)
            ans = 0;
        return ans;
    }
}
