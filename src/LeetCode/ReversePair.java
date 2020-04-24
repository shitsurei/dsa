package LeetCode;

/**
 * 在数组中的两个数字，如果前面一个数字大于后面的数字，则这两个数字组成一个逆序对。输入一个数组，求出这个数组中的逆序对的总数。
 *
 * 示例 1:
 * 输入: [7,5,6,4]
 * 输出: 5
 *
 * 限制：
 * 0 <= 数组长度 <= 50000
 */
public class ReversePair {
    public static void main(String[] args) {
        System.out.println(new ReversePair().reversePairs(new int[]{1, 3, 2, 3, 1}));
    }

    public int reversePairs(int[] nums) {
        if (nums == null || nums.length < 2)
            return 0;
        return divide(nums, 0, nums.length - 1);
    }

    private int divide(int[] arr, int left, int right) {
        if (left == right)
            return 0;
        int mid = left + ((right - left) >> 1);
        int leftSum = divide(arr, left, mid);
        int rightSum = divide(arr, mid + 1, right);
        return leftSum + rightSum + merge(arr, left, mid, right);
    }

    private int merge(int[] arr, int left, int mid, int right) {
        int[] help = new int[right - left + 1];
        int p = left, q = mid + 1, i = 0, sum = 0;
        while (p <= mid && q <= right) {
            if (arr[p] > arr[q])
                sum += mid - p + 1;
//            注意这里是小于等于，即比较相同元素时，优先排除位置靠前的部分中的相同元素
//            因为后半部分中的相同元素可能与前半部分相同元素之后的元素产生逆序对
            help[i++] = arr[p] <= arr[q] ? arr[p++] : arr[q++];
        }
        while (p <= mid)
            help[i++] = arr[p++];
        while (q <= right)
            help[i++] = arr[q++];
        for (i = 0; i < help.length; i++)
            arr[left + i] = help[i];
        return sum;
    }
}
