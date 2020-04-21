package Saima;

import java.util.*;

/**
 * 输入
 * 包含多组测试用例。
 * 对于每组测试用例：
 * 第一行包括2个整数，N（1 <= N <= 1000），M(0 <= M <= N*(N-1)/2)，代表现有N个人（用1~N编号）和M组关系；
 * 在接下来的M行里，每行包括3个整数，a，b, c，如果c为1，则代表a跟b是同乡；如果c为0，则代表a跟b不是同乡；
 * 已知1表示小赛本人。
 * <p>
 * 输出
 * 对于每组测试实例，输出一个整数，代表确定是小赛同乡的人数。
 * <p>
 * 样例输入
 * 3 1
 * 2 3 1
 * 5 4
 * 1 2 1
 * 3 4 0
 * 2 5 1
 * 3 2 1
 * <p>
 * 样例输出
 * 0
 * 3
 */
public class CountRelationship {
    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        int people, relationship;
//        读取外层数据（人数和关系组数）
        while (cin.hasNextInt()) {
            people = cin.nextInt();
            relationship = cin.nextInt();
            boolean[][] map = new boolean[people][people];
//            读取内层数据（循环组数次，每次读取两个人员序号和一个是否关联的标识信号）
            for (int i = 0; i < relationship; i++) {
                int p1 = cin.nextInt();
                int p2 = cin.nextInt();
                boolean hasRelationship = cin.nextInt() == 1;
                if (hasRelationship) {
                    map[p1 - 1][p2 - 1] = true;
                    map[p2 - 1][p1 - 1] = true;
                }
            }
            Set<Integer> set = new HashSet<>();
            int start = 1;
            set.add(start);
            recursion(set, map, start, people);
            System.out.println(set.size() - 1);
        }
    }

    /**
     * 思路：使用一个二维数组记录两两元素之间的关联关系，将得到的二维数组进行递归查询
     * 从与元素1（数组中的0位置）相关的关联关系开始，将新查询到的关联关系的另一个元素放入set中（自带去重）
     * 直到没有新的元素可以加入set集合中，我们就得到了所有与1具备关联关系的元素
     */
    public static void recursion(Set<Integer> set, boolean[][] map, int start, int people) {
        for (int i = 0; i < people; i++) {
            if (map[start - 1][i]) {
                int beforeSize = set.size();
                set.add(i + 1);
                if (set.size() > beforeSize)
                    recursion(set, map, i + 1, people);
            }
        }
    }
}
