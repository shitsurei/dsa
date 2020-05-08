package LeetCode;

import java.util.*;

/**
 * 给你一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？
 * 请你找出所有满足条件且不重复的三元组。
 * 注意：答案中不可以包含重复的三元组。
 * <p>
 * 示例：
 * 给定数组 nums = [-1, 0, 1, 2, -1, -4]，
 * 满足要求的三元组集合为：
 * [
 * [-1, 0, 1],
 * [-1, -1, 2]
 * ]
 */
public class ThreeSum {
    public static void main(String[] args) {
        int[] nums = {-1, 0, 1, 2, -1, -4};
        System.out.println(new ThreeSum().threeSum(nums));
    }

    /**
     * 思路：数组排序+双指针，难点在于去重
     */
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> ans = new LinkedList<>();
        int target = 0;
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 2; i++) {
//            外层循环用于确定三个数中第一个数，由于要保证不重复，因此跳过第一个数重复的情况
            if (i > 0 && nums[i] == nums[i - 1])
                continue;
//            头尾指针用于寻找第二个和第三个数，由于整个数组有序因此只需要从第一个数之后开始找起
            int start = i + 1, end = nums.length - 1;
            while (start < end) {
                if (nums[start] + nums[end] + nums[i] > target)
                    end--;
                else if (nums[start] + nums[end] + nums[i] < target)
                    start++;
                else {
                    ans.add(Arrays.asList(nums[i], nums[start], nums[end]));
//                    找到满足条件的数之后需要跳过【头指针之后与头指针值相同的数】和【尾指针之前和尾指针值相同的数】
                    while (start < end && nums[start] == nums[start + 1])
                        start++;
                    while (start < end && nums[end] == nums[end - 1])
                        end--;
                    start++;
                    end--;
                }
            }
        }
        return ans;
    }
}
