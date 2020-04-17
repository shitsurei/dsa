package LeetCode;

/**
 * 给定一个非负整数数组，你最初位于数组的第一个位置。
 * 数组中的每个元素代表你在该位置可以跳跃的最大长度。
 * 判断你是否能够到达最后一个位置。
 * <p>
 * 示例 1:
 * 输入: [2,3,1,1,4]
 * 输出: true
 * 解释: 我们可以先跳 1 步，从位置 0 到达 位置 1, 然后再从位置 1 跳 3 步到达最后一个位置。
 * 示例 2:
 * 输入: [3,2,1,0,4]
 * 输出: false
 * 解释: 无论怎样，你总会到达索引为 3 的位置。但该位置的最大跳跃长度是 0 ， 所以你永远不可能到达最后一个位置。
 */
public class JumpGame {
    public static void main(String[] args) {
        System.out.println(new JumpGame().canJump(new int[]{0}));
    }

    /**
     * 思路2：从前往后跳，判断每个点可以到达的最远位置
     * 可以提前结束遍历
     */
    public boolean canJump(int[] nums) {
        int far = 0;
        for (int i = 0; i < nums.length; i++) {
            far = Math.max(far, nums[i] + i);
            if (far == nums.length - 1)
                return true;
            if (far <= i)
                return false;
        }
        return true;
    }

    /**
     * 思路1：从后往前跳，判断所有位置是否均可达
     */
    public boolean canJump1(int[] nums) {
        if (nums.length < 2)
            return true;
        int minDistance = 1;
        boolean ans = false;
        for (int i = nums.length - 2; i >= 0; i--) {
            if (nums[i] < minDistance) {
                minDistance++;
                ans = false;
            } else {
                minDistance = 1;
                ans = true;
            }
        }
        return ans;
    }
}
