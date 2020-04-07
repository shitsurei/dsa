package LeetCode;

/**
 * 给定两个大小为 m 和 n 的有序数组 nums1 和 nums2。
 * 请你找出这两个有序数组的中位数，并且要求算法的时间复杂度为 O(log(m + n))。
 * 你可以假设 nums1 和 nums2 不会同时为空。
 * <p>
 * 示例 1:
 * nums1 = [1, 3]
 * nums2 = [2]
 * 则中位数是 2.0
 * 示例 2:
 * nums1 = [1, 2]
 * nums2 = [3, 4]
 * 则中位数是 (2 + 3)/2 = 2.5
 */
public class MedianTwoSortedArray {
    /**
     * 思路1:通过数学公式推导找出两个数组适当的切分点,切分后左边元素之和和右边元素之和相差不超过1
     * 同一个数组的切分处前后元素自然遵循前小后大,找出两个数组各自切分点处元素值较大的一个
     * 通过另一个数组切分点的下标后移找出两个切分点,保证两个切分点中的较大值小于切分点之后的较小值
     */
    public double findMedianSortedArrays2(int[] nums1, int[] nums2) {
        int m = nums1.length, n = nums2.length;
        if (m > n)
            return findMedianSortedArrays2(nums2, nums1);
        int iMin = 0, iMax = m;
        while (iMin <= iMax) {
            int i = (iMin + iMax) >> 1;
            int j = (m + n + 1) >> 1 - i;
            if (j != 0 && i != m && nums2[j - 1] > nums1[i])
                iMin = i + 1;
            else if (i != 0 && j != n && nums1[i - 1] > nums2[j])
                iMax = i - 1;
            else {
                int maxLeft = 0;
                if (i == 0)
                    maxLeft = nums2[j - 1];
                else if (j == 0)
                    maxLeft = nums1[i - 1];
                else
                    maxLeft = Math.max(nums1[i - 1], nums2[j - 1]);
                if ((m + n) % 2 == 0)
                    return maxLeft;
                int minRight = 0;
                if (i == m)
                    minRight = nums2[j];
                else if (j == n)
                    minRight = nums1[i];
                else
                    minRight = Math.min(nums1[i], nums2[j]);
                return (maxLeft + minRight) / 2.0;
            }
        }
        return 0;
    }

    /**
     * 思路2：两个有序数组合并后的大小x+y的中位数的位置在(x+y+1)/2位置上（如果x+y和位偶数，还需要得到(x+y+2)/2位置上的数）
     * 由于两个序列有序，我们可以通过删除不可能存在中位数的元素序列来加快查询速度，于是本题转化为寻找数组中第k小的数
     */
    public double findMedianSortedArrays3(int[] nums1, int[] nums2) {
        int m = nums1.length, n = nums2.length;
//        这里利用了数学上的技巧使奇数长度的中位数和偶数长度的中位数得到统一
        int left = (m + n + 1) >> 1;
//        如果原序列长度之和为奇数,那么left和right最终结果相同,等于我们把第k小的数求了两次
//        如果原序列长度之和为偶数,那么left和right为最终位于中间位置的两个数
        int right = (m + n + 2) >> 1;
        return (getKth(nums1, 0, m - 1, nums2, 0, n - 1, left) + getKth(nums1, 0, m - 1, nums2, 0, n - 1, right)) / 2.0;
    }

    /**
     * 该方法求两个有序数组中start和end位置的元素合并后第k小的元素
     */
    private int getKth(int[] nums1, int start1, int end1, int[] nums2, int start2, int end2, int k) {
//        先求出两个数组的实际长度
        int len1 = end1 - start1 + 1;
        int len2 = end2 - start2 + 1;
//        保证nums1的长度是较小的一个,简化判断
        if (len1 > len2)
            return getKth(nums2, start2, end2, nums1, start1, end1, k);
//        如果较小长度为0,表示较短的数组中的元素通过比较已经全部排除
        if (len1 == 0)
//            直接通过下标变化获取最终结果
            return nums2[start2 + k - 1];
//        如果所求的元素为第一小的元素,比较两个初始位置返回较小值即可
        if (k == 1)
            return Math.min(nums1[start1], nums2[start2]);
//        将k进行二分,找到两个初始位置向后二分k的位置进行比较大小(利用有序特性)
//        这里注意数组可能产生下表越界,所以我们取数组长度和初始位置后移二分k的较小值
        int index1 = start1 + Math.min(len1, k >> 1) - 1;
        int index2 = start2 + Math.min(len2, k >> 1) - 1;
//        这里不用考虑二分时奇偶的问题和数组越界限制的问题,因为在下一层递归函数时的k是以当前层实际排除的元素个数为准的
        if (nums1[index1] > nums2[index2])
//            比较结果较小的元素将其从初始位置到比较位置这一区间的所有元素删除,另一个数组保持不变,k减去删除的这一段距离
            return getKth(nums1, start1, end1, nums2, index2 + 1, end2, k - (index2 - start2 + 1));
        else
            return getKth(nums1, index1 + 1, end1, nums2, start2, end2, k - (index1 - start1 + 1));
    }
}
