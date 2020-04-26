package LeetCode.weekend3;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class MaxPoints {
    public static void main(String[] args) {
        List<Integer> l1 = Arrays.asList(1, 2, 3);
        List<Integer> l2 = Arrays.asList(4, 5, 6);
        List<Integer> l3 = Arrays.asList(7, 8, 9);
        List<List<Integer>> all = Arrays.asList(l1, l2, l3);
        System.out.println(new MaxPoints().findDiagonalOrder(all));
    }

    public int maxScore(int[] cardPoints, int k) {
        int sum = 0;
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < k; i++) {
            stack.push(cardPoints[i]);
            sum += cardPoints[i];
        }
        int max = sum;
        for (int i = cardPoints.length - 1; i >= 0; i--) {
            sum -= stack.pop();
            sum += cardPoints[i];
            max = Math.max(max, sum);
            if (stack.isEmpty())
                break;
        }
        return max;
    }

    public int[] findDiagonalOrder(List<List<Integer>> nums) {
        int i = 0;
        List<Integer> ansList = new LinkedList<>();
        for (; i < nums.size(); i++) {
            int x = i, y = 0;
            while (x >= 0) {
                if (exist(nums, x, y))
                    ansList.add(nums.get(x).get(y));
                x--;
                y++;
            }
        }
        i--;
        for (int j = 1; ; j++) {
            int x = i, y = j;
            boolean finish = true;
            while (x >= 0) {
                if (exist(nums, x, y)) {
                    ansList.add(nums.get(x).get(y));
                    finish = false;
                }
                x--;
                y++;
            }
            if (finish)
                break;
        }
        int[] ans = new int[ansList.size()];
        for (int j = 0; j < ans.length; j++)
            ans[j] = ansList.get(j);
        return ans;
    }

    private boolean exist(List<List<Integer>> nums, int i, int j) {
        if (nums.size() <= i)
            return false;
        if (nums.get(i).isEmpty())
            return false;
        if (nums.get(i).size() <= j)
            return false;
        return true;
    }
}
