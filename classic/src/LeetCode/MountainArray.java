package LeetCode;


import java.util.*;

/**
 * 山峰数组找出峰值点
 */
public class MountainArray {
    public static void main(String[] args) {
        int[] mount = new int[]{3, 5, 3, 2, 0};
        int target = 0;
        int top = mountainTop(mount);
        int index = binarySearch(mount, target, 0, top, true);
        if (index == -1)
            index = binarySearch(mount, target, top, mount.length - 1, false);
        System.out.println(index);
    }

    public static int mountainTop(int[] mount) {
        int start = 0, end = mount.length - 1;
        while (start < end) {
            int mid = start + ((end - start) >> 1);
            if (mount[mid] > mount[mid + 1])
                end = mid - 1;
            else
                start = mid + 1;
        }
        return end;
    }

    public static int binarySearch(int[] array, int target, int start, int end, boolean asc) {
        while (start <= end) {
            int mid = start + ((end - start) >> 1);
            if (array[mid] > target) {
                if (asc)
                    end = mid - 1;
                else
                    start = mid + 1;
            } else if (array[mid] < target) {
                if (asc)
                    start = mid + 1;
                else
                    end = mid - 1;
            } else
                return mid;
        }
        return -1;
    }
}
