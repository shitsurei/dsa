package LeetCode.weekend4;

import java.util.*;

public class TravelEnd {
    public static void main(String[] args) {
        int[] nums = new int[]{4, 2, 2, 2, 4, 4, 2, 2};
        System.out.println(new TravelEnd().longestSubarray(nums, 0));
    }

    public String destCity(List<List<String>> paths) {
        for (int i = 0; i < paths.size(); i++) {
            String end = paths.get(i).get(1);
            boolean success = true;
            for (int j = 0; j < paths.size(); j++) {
                if (end.equals(paths.get(j).get(0))) {
                    success = false;
                    break;
                }
            }
            if (success)
                return end;
        }
        return null;
    }

    public boolean kLengthApart(int[] nums, int k) {
        int gap = 0;
        boolean start = false;
        for (int i = 0; i < nums.length; i++) {
            if (!start && nums[i] == 1)
                start = true;
            else if (start && nums[i] == 0)
                gap++;
            else if (start && nums[i] == 1) {
                if (gap < k)
                    return false;
                else
                    gap = 0;
            }
        }
        return true;
    }

    public int longestSubarray(int[] nums, int limit) {
        int maxLen = 1;
        Deque<Integer> max = new LinkedList<>();
        max.addLast(nums[0]);
        Deque<Integer> min = new LinkedList<>();
        min.addLast(nums[0]);
        Deque<Integer> slide = new LinkedList<>();
        slide.addLast(nums[0]);
        for (int i = 1; i < nums.length; i++) {
            while (!max.isEmpty() && nums[i] > max.peekLast())
                max.pollLast();
            max.addLast(nums[i]);
            while (!min.isEmpty() && nums[i] < min.peekLast())
                min.pollLast();
            min.addLast(nums[i]);
            while (max.peekFirst() - min.peekFirst() > limit) {
                int temp = slide.pollFirst();
                if (temp == max.peekFirst())
                    max.pollFirst();
                else if (temp == min.peekFirst())
                    min.pollFirst();
            }
            slide.addLast(nums[i]);
            maxLen = Math.max(maxLen, slide.size());
        }
        return maxLen;
    }

}
