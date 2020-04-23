package ACWing;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 完全背包问题
 * 有 N 种物品和一个容量是 V 的背包，每种物品都有无限件可用。
 * 第 i 种物品的体积是 vi，价值是 wi。
 * 求解将哪些物品装入背包，可使这些物品的总体积不超过背包容量，且总价值最大。
 * 输出最大价值。
 * <p>
 * 输入格式
 * 第一行两个整数，N，V，用空格隔开，分别表示物品种数和背包容积。
 * 接下来有 N 行，每行两个整数 vi,wi，用空格隔开，分别表示第 i 种物品的体积和价值。
 * <p>
 * 输出格式
 * 输出一个整数，表示最大价值。
 * <p>
 * 数据范围
 * 0<N,V≤1000
 * 0<vi,wi≤1000
 * <p>
 * 输入样例
 * 4 5
 * 1 2
 * 2 4
 * 3 4
 * 4 5
 * 输出样例：
 * 10
 */
public class BagComplete {
    public static void main(String[] args) {

    }

    /**
     * 难点：内层循环正向遍历
     * 我们在状态压缩的01背包问题中，之所以要逆序遍历内层循环，是出于保证之前的物品不被重复计算的目的
     * 但是完全背包问题中物品是可以重复使用的，因此正序遍历去更新dp数组会使得之前的物品在下一次被重复计算
     */
    public static void desc() {
        Scanner cin = new Scanner(System.in);
        int N = cin.nextInt(), V = cin.nextInt();
        int v[] = new int[N], w[] = new int[N];
        for (int i = 0; i < N; i++) {
            v[i] = cin.nextInt();
            w[i] = cin.nextInt();
        }
        cin.close();
        int[] dp = new int[V + 1];
        for (int i = 1; i <= N; i++)
//            难点：内层循环正向遍历
            for (int j = 1; j <= V; j++)
                if (j >= v[i - 1])
                    dp[j] = Math.max(dp[j], dp[j - v[i - 1]] + w[i - 1]);
        System.out.println(dp[V]);
    }

    /**
     * 将完全背包问题转化为01背包问题
     */
    public static void transverse01() {
        Scanner cin = new Scanner(System.in);
        int N = cin.nextInt(), V = cin.nextInt();
        List<Integer> vList = new ArrayList<>();
        List<Integer> wList = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            int tempV = cin.nextInt();
            int tempW = cin.nextInt();
//            将每件物品都放入背包上限次
            for (int j = 0; j < V / tempV; j++) {
                vList.add(tempV);
                wList.add(tempW);
            }
        }
        cin.close();
        int[] dp = new int[V + 1];
        for (int i = 1; i <= vList.size(); i++)
            for (int j = V; j >= 0; j--)
                if (j >= vList.get(i - 1))
                    dp[j] = Math.max(dp[j], dp[j - vList.get(i - 1)] + wList.get(i - 1));
        System.out.println(dp[V]);
    }
}
