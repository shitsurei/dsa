package LeetCode;

/**
 * 给定一个非负整数数组，你最初位于数组的第一个位置。
 * 数组中的每个元素代表你在该位置可以跳跃的最大长度。
 * 你的目标是使用最少的跳跃次数到达数组的最后一个位置。
 * <p>
 * 示例:
 * 输入: [2,3,1,1,4]
 * 输出: 2
 * 解释: 跳到最后一个位置的最小跳跃数是 2。
 *      从下标为 0 跳到下标为 1 的位置，跳 1 步，然后跳 3 步到达数组的最后一个位置。
 * 说明:
 * 假设你总是可以到达数组的最后一个位置。
 */
public class JumpGame2 {
    public static void main(String[] args) {
        System.out.println(new JumpGame2().jump(new int[]{2, 3, 0, 1, 4}));
    }

    /**
     * 思路2：贪心策略
     * 在当前位置所能跳跃的范围内，下一次跳跃距离最远的点即为最优策略
     * 将整个数组分为三个部分：
     * 第一区间：已经跳跃过，以0为起始位置，当前跳跃的起点为终止位置
     * 第二区间：当前跳跃可达的范围
     * 第三区间：当前跳跃不可达的范围
     * 每一次跳跃都找到那个下一次跳跃可以到达最远位置的点并进行跳跃次数累加，当第二区间的范围包括了整个数组的终点时结束遍历
     * 时间复杂度：O(N)
     */
    public int jump(int[] nums) {
        if (nums.length < 3)
            return nums.length - 1;
//        left遍历为当前跳跃范围的起始位置，right为终止位置
//        初始从第一个点开始跳跃
        int left = 1, right = nums[0], count = 1;
//        结束条件为当前跳跃的可达位置的右边界覆盖数组右边界
        while (right < nums.length - 1) {
            int maxIndex = left;
//            遍历当前跳跃的可达位置
            for (int i = left; i <= right; i++) {
//                一旦某个跳跃点的可达位置超过数组右边界，直接将当前点作为下一次的跳跃点，跳出循环
//                （此时不用关心该点是否是当前跳跃可达位置中能跳的最远的，只要能跳到数组终点足矣）
                if (i + nums[i] > nums.length - 1) {
                    maxIndex = i;
                    break;
                }
//                找出所有位置中能跳的最远的点
                maxIndex = i + nums[i] > nums[maxIndex] + maxIndex ? i : maxIndex;
            }
//            更新可达位置的左右边界时，可以略过[maxIndex+1,right]这个区间的值
//            因为这些点虽然也在下一次的跳跃范围内，但是本次遍历中这些点的可达位置没有超过maxIndex，所以不可能出现跳跃范围的右边界比maxIndex更远的点
            left = right + 1;
            right = nums[maxIndex] + maxIndex;
            count++;
        }
        return count;
    }

    /**
     * 思路1：动态规划
     * dp数组保存每个位置要跳到数组末尾所需要的最小步数
     * 从后往前遍历，每个位置的情况分为两种：
     * 1 当前位置可以直接跳到末尾，dp值设为1
     * 2 当前位置无法跳到末尾，遍历当前位置所能跳到的范围内，跳到末尾位置需要的最小步数，dp值设为该步数加1
     * 时间复杂度为O(N^2)，表现一般
     */
    public int jump1(int[] nums) {
        int[] dp = new int[nums.length];
        for (int i = nums.length - 2; i >= 0; i--) {
            if (nums[i] + i < nums.length - 1) {
                int min = nums.length;
                for (int j = i + 1; j <= nums[i] + i; j++)
                    min = Math.min(min, dp[j]);
                dp[i] = min + 1;
            } else
                dp[i] = 1;
        }
        return dp[0];
    }
}
