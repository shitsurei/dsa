package LeetCode;

/**
 * n*3的矩阵，每个位置可以有三种颜色
 * 要求每个位置与其上下左右相邻位置的上色不同
 * 求有多少中上色方案
 */
public class GridPaint {
    public static void main(String[] args) {
        System.out.println(new GridPaint().numOfWays(5000));
    }

    /**
     * 思路：数学归纳加动态规划
     * 由题目可知，该矩阵不论n取几，都是一个n*3的形状，要求相邻两个色块之间不重复，即每一行的色块可以分为ABA和ABC两种形式
     * 我们假设每一行的色块只依赖于该行色块和上一行色块，因此只需要保证每个色块和上，左，右三个方向颜色不同即可
     * 由于第一层的色块没有来自上一层色块的干扰，只要保证该行色块不相同即可
     * 归纳可得第一行可分为6中ABA的形式和6中ABC的形式
     * 1 对于上一行色块排列形式为ABA的情况，当前行色块可以有3种ABA的排列和2中ABC的排列满足条件
     * 分别是【CAC】【BAB】【BCB】和【BAC】【CAB】
     * 2 对于上一行色块排列形式为ABC的情况，当前行色块可以有2种ABA的排列和2中ABC的排列满足条件
     * 分别是【BAB】【BCB】和【BCA】【CAB】
     * 由于我们不关心排列方式，只关心满足条件的不同排列个数
     * 因此n取1时，总排列数为6【ABA】+6【ABC】
     * n取2时，总排列数为(3*6+2*6)【ABA】+(2*6+2*6)【ABC】
     * n取3时，总排列数为(3*(3*6+2*6)+2*(2*6+2*6))【ABA】+(2*(3*6+2*6)+2*(2*6+2*6))【ABC】
     * ……
     */
    public int numOfWays(int n) {
        long aba = 6, abc = 6, max = (long) (1e9 + 7);
        for (int i = 1; i < n; i++) {
            long tempAba = aba, tempAbc = abc;
            aba = 3 * tempAba + 2 * tempAbc;
//            由于数字过大，每次运算要取模
            aba %= max;
            abc = 2 * tempAba + 2 * tempAbc;
            abc %= max;
        }
        return (int) ((aba + abc) % max);
    }

}
