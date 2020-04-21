package LeetCode;

/**
 * 给你一个整数数组 nums 和一个整数 k。
 * 如果某个 连续 子数组中恰好有 k 个奇数数字，我们就认为这个子数组是「优美子数组」。
 * 请返回这个数组中「优美子数组」的数目。
 * <p>
 * 示例 1：
 * 输入：nums = [1,1,2,1,1], k = 3
 * 输出：2
 * 解释：包含 3 个奇数的子数组是 [1,1,2,1] 和 [1,2,1,1] 。
 * 示例 2：
 * 输入：nums = [2,4,6], k = 1
 * 输出：0
 * 解释：数列中不包含任何奇数，所以不存在优美子数组。
 * 示例 3：
 * 输入：nums = [2,2,2,1,2,2,1,2,2,2], k = 2
 * 输出：16
 * <p>
 * 提示：
 * 1 <= nums.length <= 50000
 * 1 <= nums[i] <= 10^5
 * 1 <= k <= nums.length
 */
public class CountNiceSubArray {
    public static void main(String[] args) {
        System.out.println(new CountNiceSubArray().numberOfSubarrays(new int[]{1, 1, 1, 1, 1, 1, 1}, 2));
    }

    /**
     * 思路2：数学方法
     * 对于任何一个包含k个奇数的最小连续子序列（即该序列的左右两端都是奇数）
     * 可以通过其左右两侧相邻的偶数个数计算出【优美子数组】的个数
     * eg:求序列21122中包含2个奇数的优美子数组的个数，其中只有一个符合条件的最小连续子序列11，左右两侧分别包含1个偶数和2个偶数
     * 因此计算整个序列【优美子数组】的个数为（1+1）*（2+1）= 6个；
     * 而当k取1时，包含2个符合条件自小连续子序列1（前一个）和1（后一个）
     * 对前一个1来说，左右两侧偶数的个数分别为1个和0个（右侧紧挨着奇数，因此为0）
     * 同理对后一个1来说，左右两侧偶数的个数分别为0个和2个（左侧紧挨着奇数，因此为0）
     * 那么整个序列中【优美子数组】的个数等于两部分奇数所能组成的【优美子数组】个数之和
     * 即（1+1）*（0+1）+（0+1）*（2+1）=5个
     */
    public int numberOfSubarrays(int[] nums, int k) {
        int curOdd = 0, ans = 0, index = 0;
//        map数组用于保存序列中被奇数分割的偶数的个数，由于原数组最多有nums.length个奇数，因此做多可以分割出nums.length+1个区间
        int[] map = new int[nums.length + 1];
        for (int i = 0; i < nums.length; i++) {
//            每次出现奇数时将累计的偶数个数存入map，后将偶数计数清零（其中连续的奇数会在map中存入0这个分割区间，参考之前的思路）
            if ((nums[i] & 1) == 1) {
                map[index++] = curOdd;
                curOdd = 0;
            } else
                curOdd++;
        }
//        保存最后一次由于遍历结束未保存的计数
        map[index++] = curOdd;
//        遍历map，累加符合条件的【优美子数组】个数
//        累加方式为每隔k个连续的奇数区间所产生的子数组一定是包含k个奇数且两侧连续偶数为map[i]和map[i+k]中保存的偶数个数
        for (int i = 0; i + k < index; i++)
            ans += (map[i] + 1) * (map[i + k] + 1);
        return ans;
    }

    /**
     * 官方题解，看不太懂
     */
    public int numberOfSubarrays2(int[] nums, int k) {
        int[] cnt = new int[nums.length + 1];
        cnt[0] = 1;
        int odd = 0, ans = 0;
        for (int i = 0; i < nums.length; i++) {
            if ((nums[i] & 1) == 1)
                odd++;
            if (odd >= k)
                ans += cnt[odd - k];
            cnt[odd]++;
        }
        return ans;
    }

    /**
     * 思路1：动态规划
     * 求出原序列中所有连续子序列的奇数个数，统计出其中奇数个数恰好为k个的个数
     * 效率较低，O(N^2)的时间复杂度和空间复杂度
     */
    public int numberOfSubarrays1(int[] nums, int k) {
        int len = nums.length, ans = 0;
        int[][] dp = new int[len][len];
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len && j + i < len; j++) {
//                初始条件：以每个位置的单个字符作为起始终止点
                if (i == 0) {
                    if (nums[j] % 2 == 1)
                        dp[j][j] = 1;
//                    转移条件，每个位置的奇数个数依赖于前一个字符作为结束位置的子串
                } else if (nums[j + i] % 2 == 1)
                    dp[j][j + i] = dp[j][j + i - 1] + 1;
                else
                    dp[j][j + i] = dp[j][j + i - 1];
                if (dp[j][j + i] == k)
                    ans++;
                else if (dp[j][j + i] > k)
                    continue;
            }
        }
        return ans;
    }
}
