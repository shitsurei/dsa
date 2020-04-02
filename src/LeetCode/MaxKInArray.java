package LeetCode;

import java.util.PriorityQueue;

/**
 * 在未排序的数组中找到第 k 个最大的元素。请注意，你需要找的是数组排序后的第 k 个最大的元素，而不是第 k 个不同的元素。
 * <p>
 * 示例 1:
 * 输入: [3,2,1,5,6,4] 和 k = 2
 * 输出: 5
 * 示例 2:
 * 输入: [3,2,3,1,2,4,5,5,6] 和 k = 4
 * 输出: 4
 * <p>
 * 说明:
 * 你可以假设 k 总是有效的，且 1 ≤ k ≤ 数组的长度。
 */
public class MaxKInArray {
    public static void main(String[] args) {
        int[] arr = new int[]{-1, 2, 0};
        System.out.println(new MaxKInArray().findKthLargest1(arr, 2));
    }

    /**
     * 求未排序数组中第k大的数
     * 方法一：使用小顶堆结构辅助，遍历整个数组，仅记录遍历过程中的K个较大元素
     * 等到整个数组遍历结束，堆中正好存储着整个数组序列中最大的K的元素，且由于是小顶堆，堆顶元素即为第K大元素
     * 时间复杂度为O(N*2log(K))
     * 整个数组遍历时间为N，每次遍历一个元素都有入堆和出堆时间，由于堆最大容量为K，因为出堆入堆时间为log(K)
     * <p>
     * 效率：
     * 执行用时 :5 ms, 在所有 Java 提交中击败了65.30%的用户
     * 内存消耗 :40.3 MB, 在所有 Java 提交中击败了5.02%的用户
     *
     * @param nums
     * @param k
     * @return
     */
    public int findKthLargest1(int[] nums, int k) {
        PriorityQueue<Integer> heap = new PriorityQueue<>(k);
        for (int e : nums) {
//            先添加k个元素入堆
            if (heap.size() < k)
                heap.add(e);
//            再去筛选较大的k的数
            else if (e > heap.peek()) {
                heap.poll();
                heap.add(e);
            }
        }
        return heap.peek();
    }

    /**
     * 求未排序数组中第k大的数
     * 方法二：利用快速排序的partition过程，每次确定一个元素在整个数组中最终排序的位置
     * 当该位置包含n-k时，即可获取最终答案
     *
     * 效率：
     * 执行用时 :3 ms, 在所有 Java 提交中击败了75.91%的用户
     * 内存消耗 :40.3 MB, 在所有 Java 提交中击败了5.02%的用户
     * @param nums
     * @param k
     * @return
     */
    public int findKthLargest(int[] nums, int k) {
        int start = 0, end = nums.length;
        k = end - k;
        while (true) {
            int[] range = partition(nums, start, end);
            if (k >= range[0] && k < range[1])
                return nums[k];
            else if (k < range[0])
                end = range[0];
            else
                start = range[1];
        }
    }

    public int[] partition(int[] nums, int start, int end) {
        int pivot = (int) (Math.random() * (end - start) + start);
        swap(nums, start, pivot);
        pivot = nums[start];
        int less = start, more = end;
        for (int i = less + 1; i < end; i++) {
            if (nums[i] > pivot)
                swap(nums, i--, --more);
            else if (nums[i] < pivot)
                swap(nums, i, less++);
            if (i == more - 1)
                break;
        }
        return new int[]{less, more};
    }

    private void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
