package LeetCode;

import java.util.Arrays;
import java.util.Stack;

/**
 * 给定 n 个非负整数，用来表示柱状图中各个柱子的高度。每个柱子彼此相邻，且宽度为 1 。
 * 求在该柱状图中，能够勾勒出来的矩形的最大面积。
 * <p>
 * 示例：每个柱子的宽度为 1，给定的高度为 [2,1,5,6,2,3]。
 * 所能勾勒出的最大矩形面积，其面积为 10 个单位。
 * <p>
 * 示例:
 * 输入: [2,1,5,6,2,3]
 * 输出: 10
 */
public class MaxSInHistogram {
    public static void main(String[] args) {
        System.out.println(new MaxSInHistogram().largestRectangleArea(new int[]{2, 4, 5, 3, 2}));
    }

    /**
     * 思路：单调栈
     */
    public int largestRectangleArea(int[] heights) {
        if (heights.length == 0)
            return 0;
        if (heights.length == 1)
            return heights[0];
        int max = 0;
        heights = Arrays.copyOf(heights, heights.length + 1);
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < heights.length; i++) {
            while (!stack.isEmpty() && heights[i] < heights[stack.peek()]) {
                int index = stack.pop();
                max = Math.max(max, heights[index] * (stack.isEmpty() ? i : i - stack.peek() - 1));
            }
            stack.push(i);
        }
        return max;
    }
}
