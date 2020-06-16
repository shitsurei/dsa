package LeetCode;

/**
 * 给定正整数数组 A，A[i] 表示第 i 个观光景点的评分，并且两个景点 i 和 j 之间的距离为 j - i。
 * 一对景点（i < j）组成的观光组合的得分为（A[i] + A[j] + i - j）：景点的评分之和减去它们两者之间的距离。
 * 返回一对观光景点能取得的最高分。
 * <p>
 * 示例：
 * 输入：[8,1,5,2,6]
 * 输出：11
 * 解释：i = 0, j = 2, A[i] + A[j] + i - j = 8 + 5 + 0 - 2 = 11
 * <p>
 * 提示：
 * 2 <= A.length <= 50000
 * 1 <= A[i] <= 1000
 */
public class BestTourGroup {
    public static void main(String[] args) {
        int[] a = {3, 7, 2, 3};
        System.out.println(new BestTourGroup().maxScoreSightseeingPair(a));
    }

    /**
     * 思路：题目要求A[i] + A[j] + i -j的全局最大值，可以转化为求A[i] + i + A[j] -j的全局最大值
     * 我们只需要在遍历过程中维护A[i] + i的已有最大值即可用来计算当前遍历到的已知最大值
     */
    public int maxScoreSightseeingPair(int[] A) {
        int maxAII = A[0] + 0, ans = 0;
        for (int i = 1; i < A.length; i++) {
//            实际上这里A[i] - i表示题目中的A[j]-j
            ans = Math.max(ans, maxAII + A[i] - i);
            maxAII = Math.max(maxAII, A[i] + i);
        }
        return ans;
    }
}
