package LeetCode;

import java.util.LinkedList;

/**
 * 给定一个数组 nums，有一个大小为 k 的滑动窗口从数组的最左侧移动到数组的最右侧。
 * 你只可以看到在滑动窗口内的 k 个数字。滑动窗口每次只向右移动一位。
 * 返回滑动窗口中的最大值。
 * <p>
 * 进阶：
 * 你能在线性时间复杂度内解决此题吗？
 * <p>
 * 示例:
 * <p>
 * 输入: nums = [1,3,-1,-3,5,3,6,7], 和 k = 3
 * 输出: [3,3,5,5,6,7]
 * 解释:
 * 滑动窗口的位置                最大值
 * ---------------               -----
 * [1  3  -1] -3  5  3  6  7       3
 * 1 [3  -1  -3] 5  3  6  7       3
 * 1  3 [-1  -3  5] 3  6  7       5
 * 1  3  -1 [-3  5  3] 6  7       5
 * 1  3  -1  -3 [5  3  6] 7       6
 * 1  3  -1  -3  5 [3  6  7]      7
 * <p>
 * 提示：
 * 1 <= nums.length <= 10^5
 * -10^4 <= nums[i] <= 10^4
 * 1 <= k <= nums.length
 */
public class SlideWindowMaxValue {
    public static void main(String[] args) {
        int[] i = new int[]{1, 3, -1, -3, 5, 3, 6, 7};
        new SlideWindowMaxValue().maxSlidingWindow(i, 3);
    }

    /**
     * 思路三：动态规划
     * 以[1,3,-1,-3,5,3,6,7]，窗口大小为3为例
     * 每k个一组，将整个数组分为n组，最后不足k个的元素单独为一组
     * ——分组如下[1 3 -1 | -3 5 3 | 6 7]
     * 第一遍按顺序升序将每k个元素的升序最大值保存在maxASC数组中
     * ——保存结果如下[1 3 3 | -3 5 5 | 6 7]
     * 第二遍按逆序升序将每k个元素的升序最大值保存在maxDESC数组中
     * ——保存结果如下[3 3 -1 | 5 5 3 | 7 7]
     * （上面两次求辅助数组可以利用下标变化在一次遍历中完成）
     * 最终从逆序升序数组的开始位置和顺序升序数组的k-1位置开始向后比较，每次取两个数组中的较大值存入结果数组
     * [1 3 | 3 -3  5 5 6 7 |]
     *     [| 3  3 -1 5 5 3 | 7 7]
     * ——最终滑动窗口最大值数组如下[3,3,5,5,6,7]
     *
     * 表现较好
     * 执行结果：通过
     * 执行用时:
     * 5 ms, 在所有 Java 提交中击败了88.12%的用户
     * 内存消耗:
     * 47.6 MB, 在所有 Java 提交中击败了5.13%的用户
     */
    public int[] maxSlidingWindow(int[] nums, int k) {
        int[] max = new int[nums.length - k + 1];
        int[] maxAsc = new int[nums.length];
        int[] maxDesc = new int[nums.length];
        for (int i = 0, j = nums.length - 1; i < nums.length; i++, j--) {
            maxAsc[i] = i % k == 0 ? nums[i] : Math.max(nums[i], maxAsc[i - 1]);
            maxDesc[j] = j % k == k - 1 || j == nums.length - 1 ? nums[j] : Math.max(nums[j], maxDesc[j + 1]);
        }
        for (int i = 0, j = k - 1; i < max.length; i++, j++)
            max[i] = Math.max(maxAsc[j], maxDesc[i]);
        return max;
    }

    /**
     * 思路二：双指针
     * 尾指针向后移动的过程中记录窗口最大值下标位置，当头指针后移导致该位置移出窗口之后，遍历新的窗口范围，找到最大值下标
     * <p>
     * 表现较好
     * 执行结果：通过
     * 执行用时:
     * 2 ms, 在所有 Java 提交中击败了98.47%的用户
     * 内存消耗:
     * 47.9 MB, 在所有 Java 提交中击败了5.13%的用户
     */
    public int[] maxSlidingWindow2(int[] nums, int k) {
        int[] max = new int[nums.length - k + 1];
        int index = 0, curMaxIndex = 0;
        for (int tail = 1, head = 0; tail < nums.length; tail++) {
            if (tail - head >= k) {
                max[index++] = nums[curMaxIndex];
                head++;
                if (head - 1 == curMaxIndex) {
                    curMaxIndex = head;
                    for (int j = head + 1; j <= tail; j++)
                        curMaxIndex = nums[j] >= nums[curMaxIndex] ? j : curMaxIndex;
                }
            }
            curMaxIndex = nums[tail] >= nums[curMaxIndex] ? tail : curMaxIndex;
        }
        max[index] = nums[curMaxIndex];
        return max;
    }

    /**
     * 思路一：双端队列求滑动窗口中的最大值
     * <p>
     * 表现一般
     * 执行结果：通过
     * 执行用时:
     * 11 ms, 在所有 Java 提交中击败了76.39%的用户
     * 内存消耗:
     * 48.9 MB, 在所有 Java 提交中击败了5.13%的用户
     */
    public int[] maxSlidingWindow1(int[] nums, int k) {
        int[] max = new int[nums.length - k + 1];
        int index = 0;
        LinkedList<Integer> dequeue = new LinkedList<>();
        dequeue.addLast(nums[0]);
        for (int i = 1; i < nums.length; i++) {
            if (i >= k) {
                max[index++] = dequeue.peekFirst();
                if (nums[i - k] == dequeue.peekFirst())
                    dequeue.pollFirst();
            }
            while (!dequeue.isEmpty() && nums[i] > dequeue.peekLast())
                dequeue.pollLast();
            dequeue.addLast(nums[i]);
        }
        max[index] = dequeue.peekFirst();
        return max;
    }
}
