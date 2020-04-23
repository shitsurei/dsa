package ACWing;

import java.util.Scanner;

/**
 * 01背包问题
 * 有 N 件物品和一个容量是 V 的背包。每件物品只能使用一次。
 * 第 i 件物品的体积是 vi，价值是 wi。
 * 求解将哪些物品装入背包，可使这些物品的总体积不超过背包容量，且总价值最大。
 * 输出最大价值。
 *
 * 输入格式
 * 第一行两个整数，N，V，用空格隔开，分别表示物品数量和背包容积。
 * 接下来有 N 行，每行两个整数 vi,wi，用空格隔开，分别表示第 i 件物品的体积和价值。
 *
 * 输出格式
 * 输出一个整数，表示最大价值。
 *
 * 数据范围
 * 0<N,V≤1000
 * 0<vi,wi≤1000
 *
 * 输入样例
 * 4 5
 * 1 2
 * 2 4
 * 3 4
 * 4 5
 * 输出样例：
 * 8
 */
public class Bag01 {
    public static void main(String[] args) {
        unZip();
        zip();
    }

    /**
     * 状态压缩的一维dp
     * 因为二维dp中每一次计算状态转移时当前位置的数据只会依赖上一行中当前列及其之前的数据，因此我们可以用一维dp保存当前行的dp结果
     * 转移到下一行时只需要从后往前遍历即可复用上一行的dp结果
     * 空间复杂度从O(VN)压缩为O(V)
     */
    public static void zip() {
        Scanner cin = new Scanner(System.in);
        int N = cin.nextInt(), V = cin.nextInt();
        int v[] = new int[N], w[] = new int[N];
        for (int i = 0; i < N; i++) {
            v[i] = cin.nextInt();
            w[i] = cin.nextInt();
        }
        cin.close();
//        一维dp值记录每次多选择一个物品时，背包容量为j的最大价值dp[j]
        int[] dp = new int[V + 1];
        for (int i = 1; i <= N; i++)
            for (int j = V; j >= 0; j--)
//                只有在背包容量可以放得下当前物品时比较放与不放的价值哪个更高，如果放不下可以复用上一轮dp的结果
                if (j >= v[i - 1])
                    dp[j] = Math.max(dp[j], dp[j - v[i - 1]] + w[i - 1]);
        System.out.println(dp[V]);
    }

    /**
     * 状态未压缩的二维dp
     */
    public static void unZip() {
        Scanner cin = new Scanner(System.in);
        int N = cin.nextInt(), V = cin.nextInt();
        int v[] = new int[N], w[] = new int[N];
        for (int i = 0; i < N; i++) {
            v[i] = cin.nextInt();
            w[i] = cin.nextInt();
        }
        cin.close();
//        dp数组中[i,j]位置的状态表示，可选择0~i-1号物品且背包容量为j的情况下，可以获取的最大价值
//        首行表示未选择任何物品的状态，默认价值都为0
//        首列表示背包容量为0的状态，无法选择任何物品，默认价值都为0
//        这两行初始条件可以使得状态转移的过程中不会出现数组下标越界的情况
        int[][] dp = new int[N + 1][V + 1];
        for (int i = 1; i <= N; i++)
            for (int j = 1; j <= V; j++)
//                当前容量无法容纳该物品时
                if (j < v[i - 1])
                    dp[i][j] = dp[i - 1][j];
                else
//                当前容量可以容纳该物品时，比较容纳该物品所获得的价值和不容纳该物品所获得的价值，取较大值
//                容纳该物品的价值：dp[i - 1][j - v[i - 1]] + w[i - 1]，其中dp[i - 1][j - v[i - 1]]表示容纳该物品后的剩余空间可以获得最大价值
//                不容纳该物品的价值：dp[i - 1][j]
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - v[i - 1]] + w[i - 1]);
        System.out.println(dp[N][V]);
    }
}
