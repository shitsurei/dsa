package LeetCode;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 给出一个区间的集合，请合并所有重叠的区间。
 * <p>
 * 示例 1:
 * 输入: [[1,3],[2,6],[8,10],[15,18]]
 * 输出: [[1,6],[8,10],[15,18]]
 * 解释: 区间 [1,3] 和 [2,6] 重叠, 将它们合并为 [1,6].
 * 示例 2:
 * 输入: [[1,4],[4,5]]
 * 输出: [[1,5]]
 * 解释: 区间 [1,4] 和 [4,5] 可被视为重叠区间。
 */
public class MergeRange {
    public static void main(String[] args) {
        int[][] test = new int[][]{
                {1, 3},
                {2, 6},
                {8, 10},
                {15, 18},
        };
        new MergeRange().merge(test);
    }

    /**
     * 思路1：贪心策略
     * 1 将区间按起始位置排序
     * 2 从前向后遍历，比较下一个元素的起始位置是否比当前位置的终止位置大
     * 1 下一个元素的起始值更大，区间无法合并，将之前的合并区间保存，初始值和终止值更新为下一个元素的初始值和终止值
     * 2 当前元素的终止值大于等于下一个元素的初始值，区间可以合并，将当前终止值和下一个元素的终止值中的较大值作为新的终止值
     */
    public int[][] merge(int[][] intervals) {
        int len = intervals.length;
        if (len == 0)
            return new int[0][];
        Arrays.sort(intervals, (o1, o2) -> {
            if (o1[0] == o2[0])
                return o1[1] - o2[1];
            else
                return o1[0] - o2[0];
        });
        List<int[]> merge = new LinkedList<>();
//        保存每个合并区间的初始值和终止值
        int start = intervals[0][0], end = intervals[0][1];
        for (int i = 1; i < len; i++) {
//        情况1 保存每个合并区间的初始值和终止值
            if (intervals[i][0] > end) {
                merge.add(new int[]{start, end});
                start = intervals[i][0];
            }
            if (intervals[i][1] > end)
                end = intervals[i][1];
        }
        merge.add(new int[]{start, end});
        return merge.toArray(new int[merge.size()][]);
    }
}
