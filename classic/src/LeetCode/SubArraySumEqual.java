package LeetCode;

import java.util.HashMap;
import java.util.Map;

/**
 * 给定一个整数数组和一个整数 k，你需要找到该数组中和为 k 的连续的子数组的个数。
 * <p>
 * 示例 1 :
 * 输入:nums = [1,1,1], k = 2
 * 输出: 2 , [1,1] 与 [1,1] 为两种不同的情况。
 * 说明 :
 * 数组的长度为 [1, 20,000]。
 * 数组中元素的范围是 [-1000, 1000] ，且整数 k 的范围是 [-1e7, 1e7]。
 */
public class SubArraySumEqual {
    public static void main(String[] args) {
        int[] nums = new int[]{0, 0, 0, 0};
        System.out.println(new SubArraySumEqual().subarraySum(nums, 0));
    }

    /**
     * 思路：前缀和+hash表
     * 1 将所有的前缀和保存起来，每次只需要比较当前遍历到的数减去之前的前缀和就可以获悉某一段区间上的序列和
     * eg：数组       {1，3，2，7，4，0，9}
     * 前缀和为   {0，1，4，6，13，17，17，26}    【每一个位置上表示】
     * 想要知道下标2到下标4之间的序列和只需要计算前缀和数组中下标5和下标2之差，即17-4=13即可
     * 2 逆向思维，我们要求的是子序列其累加和等于k，因此我们只需要将每次计算的前缀和用hash表存储起来
     *      其中key为前缀累加和，value为累加和出现的次数
     * 3 当计算当前位置的累加和减去k所得到的累加和在hash表中出现过时，即表示可以建立以当前位置为子序列结尾的和为k的子序列
     * 4 计数器累加每次满足条件时子序列的数量
     */
    public int subarraySum(int[] nums, int k) {
        Map<Integer, Integer> pre = new HashMap<>();
        int count = 0;
        int preSum = 0;
//        初始条件
        pre.put(0, 1);
        for (int num : nums) {
            preSum += num;
            if (pre.containsKey(preSum - k)) {
                count += pre.get(preSum - k);
            }
//            更新累加值出现的次数或新增当前累加值出现次数为1
            pre.put(preSum, pre.getOrDefault(preSum, 0) + 1);
        }
        return count;
    }
}
