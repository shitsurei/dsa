package LeetCode;

/**
 * 把一个数组最开始的若干个元素搬到数组的末尾，我们称之为数组的旋转。
 * 输入一个递增排序的数组的一个旋转，输出旋转数组的最小元素。
 * 例如，数组 [3,4,5,1,2] 为 [1,2,3,4,5] 的一个旋转，该数组的最小值为1。  
 * <p>
 * 示例 1：
 * 输入：[3,4,5,1,2]
 * 输出：1
 * 示例 2：
 * 输入：[2,2,2,0,1]
 * 输出：0
 */
public class SpinMinnest {
    public static void main(String[] args) {
        System.out.println(new SpinMinnest().minArray(new int[]{2, 2, 2, 0, 1}));
    }

    /**
     * 思路2：二分查找
     */
    public int minArray(int[] numbers) {
        int start = 0, end = numbers.length - 1;
        while (start < end) {
            int mid = (start + end) / 2;
            if (numbers[mid] < numbers[start])
                end = mid;
            else if (numbers[mid] > numbers[end])
                start = mid + 1;
            else
//                用于在不确定旋转点的位置时将二分退化为遍历
                end--;
        }
        return numbers[start];
    }

    /**
     * 思路1：憨憨遍历
     */
    public int minArray1(int[] numbers) {
        for (int i = 1; i < numbers.length; i++)
            if (numbers[i] < numbers[i - 1])
                return numbers[i];
        return numbers[0];
    }
}
