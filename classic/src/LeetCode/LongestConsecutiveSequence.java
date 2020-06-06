package LeetCode;

import java.util.HashMap;
import java.util.Map;

/**
 * 给定一个未排序的整数数组，找出最长连续序列的长度。
 * 要求算法的时间复杂度为 O(n)。
 * <p>
 * 示例:
 * 输入: [100, 4, 200, 1, 3, 2]
 * 输出: 4
 * 解释: 最长连续序列是 [1, 2, 3, 4]。它的长度为 4。
 */
public class LongestConsecutiveSequence {
    public static void main(String[] args) {
        System.out.println(new LongestConsecutiveSequence().longestConsecutive(new int[]{100, 4, 200, 1, 3, 2}));
    }

    /**
     * 思路：并查集
     * 遍历数组的过程中记录每个数所在的连续子序列的长度，只需要更新当前数和连续序列的左右端点位置的信息即可
     * 因为当出现新的数可以归并到已有的连续序列时只会访问到已有连续子序列的左右端点位置
     */
    public int longestConsecutive(int[] nums) {
        if (nums.length < 2)
            return nums.length;
        int max = 0;
        Map<Integer, Boolean> exists = new HashMap<>();
        Map<Integer, Integer> ranges = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            boolean exist = exists.getOrDefault(nums[i], false);
            if (!exist) {
//                当前数左右位置相邻的数不存在时，当前数的连续序列长度增加值为0
                int left = ranges.getOrDefault(nums[i] - 1, 0);
                int right = ranges.getOrDefault(nums[i] + 1, 0);
                ranges.put(nums[i], left + right + 1);
                exists.put(nums[i], true);
//                如果左右位置相邻的数存在时，更新左右两端已有连续序列的端点位置信息
                if (exists.containsKey(nums[i] - 1))
                    ranges.put(nums[i] - left, left + right + 1);
                if (exists.containsKey(nums[i] + 1))
                    ranges.put(nums[i] + right, left + right + 1);
                max = Math.max(max, left + right + 1);
            }
        }
        return max;
    }
}
