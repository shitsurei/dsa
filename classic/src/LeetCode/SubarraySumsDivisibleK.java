package LeetCode;

/**
 * 给定一个整数数组 A，返回其中元素之和可被 K 整除的（连续、非空）子数组的数目。
 * <p>
 * 示例：
 * 输入：A = [4,5,0,-2,-3,1], K = 5
 * 输出：7
 * 解释：
 * 有 7 个子数组满足其元素之和可被 K = 5 整除：
 * [4, 5, 0, -2, -3, 1], [5], [5, 0], [5, 0, -2, -3], [0], [0, -2, -3], [-2, -3]
 * <p>
 * 提示：
 * 1 <= A.length <= 30000
 * -10000 <= A[i] <= 10000
 * 2 <= K <= 10000
 */
public class SubarraySumsDivisibleK {
    public static void main(String[] args) {
        int[] A = {4, 5, 0, -2, -3, 1};
        int K = 5;
        System.out.println(new SubarraySumsDivisibleK().subarraysDivByK(A, K));
    }

    /**
     * 思路2：hash表+前缀和
     * 由于我们并不关心符合条件的子数组的下标起止索引位置，只关心满足条件的子数组个数
     * 因此可以保存前缀和对目标值K取余的结果频率（取余结果无非0~K-1）
     * 【前缀和累加过程中一旦出现pre[i]和pre[j]对K取余的结果相同，说明索引[i,j]之间的子数组一定满足要求】
     * 当取余结果X第一次出现时不做处理（0除外，因为0代表可以整除），第二次出现时说明存在一个满足条件的子序列，同时下一次出现时满足的子序列数加一
     */
    public int subarraysDivByK(int[] A, int K) {
//        取余结果频率表
        int[] squence = new int[K];
//        由于取余结果为0时表示整除满足子数组条件，因此将0的初始结果设为1，保证第一次出现前缀累加和满足条件时计数不遗漏
        squence[0] = 1;
        int sum = 0, ans = 0;
        for (int i = 0; i < A.length; i++) {
            sum += A[i];
//            前缀和取模结果，注意java中负数取模结果为负数，需要做特殊处理，转为正数
            int module = (sum % K + K) % K;
//            累加满足条件的子序列个数后下一次再遇到相同的取模结果时符合的子数组个数要加一
            ans += squence[module];
            squence[module]++;
        }
        return ans;
    }

    /**
     * 思路1：前缀和
     * 本题需要确定任意两个下表索引区间子数组的累加和是否为K的整数倍
     * 通过前缀和pre[i]和pre[j]的差就可以在O(1)时间内求出任意区间的前缀和
     * 接下来要枚举所有的子区间需要O(N^2)的时间复杂度，因此总的时间复杂度为O(N^2)
     * 【超时】
     */
    public int subarraysDivByK1(int[] A, int K) {
        int[] pre = new int[A.length + 1];
        for (int i = 1; i <= A.length; i++)
            pre[i] = pre[i - 1] + A[i - 1];
        int count = 0;
        for (int i = 0; i < pre.length; i++)
            for (int j = i + 1; j < pre.length; j++)
                if ((pre[j] - pre[i]) % K == 0)
                    count++;
        return count;
    }
}
