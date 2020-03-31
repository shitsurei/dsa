package LeetCode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 给定一个整数数组nums和一个目标值target，请你在该数组中找出和为目标值的那两个整数，并返回他们的数组下标。
 * 你可以假设每种输入只会对应一个答案。但是，你不能重复利用这个数组中同样的元素。
 * <p>
 * 示例:
 * 给定 nums = [2, 7, 11, 15], target = 9
 * 因为 nums[0] + nums[1] = 2 + 7 = 9
 * 所以返回 [0, 1]
 */
public class TwoNumSum {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(new TwoNumSum().twoSum(new int[]{7, 2, 7, 11, 15}, 13)));
    }

    /**
     * 一遍hash表
     * 将每一次遍历到的数的目标值和当前下标存入hash表
     * 一旦遍历到当前数已经存在在hash表中，说明当前数就是之前遍历到的某个数的目标值
     * 返回之前的数的下标（因为当时存储的目标值就是当前数，所以使用当前数查hash表就可获取之前数的下标）和当前数的下标即可
     */
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> hash = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int component = target - nums[i];
            if (hash.containsKey(nums[i]))
                return new int[]{hash.get(nums[i]), i};
            hash.put(component, i);
        }
        return null;
    }
}
