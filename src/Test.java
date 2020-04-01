import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        int[] a = new int[]{2, 5, 4, 3, -2};
        System.out.println(Arrays.toString(new Test().sortArray(a)));
    }

    public int[] sortArray(int[] nums) {
//        quickSort(nums, 0, nums.length);
        bucketSort(nums);
        return nums;
    }

    public void bucketSort(int[] nums) {
        int[] bucket = new int[100002];
        for (int e : nums)
            bucket[e + 50000]++;
        for (int i = 0, j = 0; i < bucket.length && j < nums.length; i++, j++) {
            if (bucket[i] > 0) {
                nums[j] = i - 50000;
                bucket[i]--;
                i--;
            } else {
                j--;
            }
        }
    }

    public void quickSort(int[] nums, int start, int end) {
        if (start < end) {
            int[] range = nlFlag(nums, start, end);
            quickSort(nums, start, range[0]);
            quickSort(nums, range[1] + 1, end);
        }
    }

    public int[] nlFlag(int[] nums, int start, int end) {
//        随机快排
        swap(nums, start, (int) (Math.random() * (end - start) + start));
//        比较轴
        int pivot = nums[start];
        int less = start, more = end;
        for (int i = less + 1; i < more; i++)
            if (nums[i] > pivot)
                swap(nums, i--, --more);
            else if (nums[i] < pivot)
                swap(nums, i, less++);
        return new int[]{less, more - 1};
    }

    public void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public void merge(int[] nums1, int m, int[] nums2, int n) {
        for (int i = m + n - 1; i >= 0; i--) {
            if (m > 0 && n > 0)
                nums1[i] = nums1[m - 1] > nums2[n - 1] ? nums1[--m] : nums2[--n];
            else if (n > 0)
                nums1[i] = nums2[--n];
        }
    }

}
