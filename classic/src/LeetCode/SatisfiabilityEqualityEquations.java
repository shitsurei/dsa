package LeetCode;

import java.util.Arrays;

/**
 * 给定一个由表示变量之间关系的字符串方程组成的数组，每个字符串方程 equations[i] 的长度为 4，并采用两种不同的形式之一：
 * "a==b" 或 "a!=b"。
 * 在这里，a 和 b 是小写字母（不一定不同），表示单字母变量名。
 * 只有当可以将整数分配给变量名，以便满足所有给定的方程时才返回 true，否则返回 false。 
 * <p>
 * 示例 1：
 * 输入：["a==b","b!=a"]
 * 输出：false
 * 解释：如果我们指定，a = 1 且 b = 1，那么可以满足第一个方程，但无法满足第二个方程。没有办法分配变量同时满足这两个方程。
 * 示例 2：
 * 输出：["b==a","a==b"]
 * 输入：true
 * 解释：我们可以指定 a = 1 且 b = 1 以满足满足这两个方程。
 * 示例 3：
 * 输入：["a==b","b==c","a==c"]
 * 输出：true
 * 示例 4：
 * 输入：["a==b","b!=c","c==a"]
 * 输出：false
 * 示例 5：
 * 输入：["c==c","b==d","x!=z"]
 * 输出：true
 * <p>
 * 提示：
 * 1 <= equations.length <= 500
 * equations[i].length == 4
 * equations[i][0] 和 equations[i][3] 是小写字母
 * equations[i][1] 要么是 '='，要么是 '!'
 * equations[i][2] 是 '='
 */
public class SatisfiabilityEqualityEquations {
    /**
     * 思路：并查集（这结构太牛了）
     * 这道题本质上是要求我们找到相互关联的字母集合，再去验证是否存在不应该存在于同一个集合中的字母对
     * 而这个关联关系是有可能随着已知信息的增加不断对原有集合进行扩展合并的，如何处理合并集合是难点
     * 并查集的作用就是将数据集合从逻辑上变成一个树形结构，而在集合合并时只需要将其中一棵树的根变成另一棵树根的子节点即可
     * 同时当要判断两个元素是否存在于同一个集合中时，只需要查看两个元素所在树的根节点是否相同即可。
     */
    public boolean equationsPossible(String[] equations) {
//        这里使用数组模拟树结构，数组下标表示元素，对应26个字母，数组的值表示当前元素在树结构中的父节点
        int[] unionSet = new int[26];
//        初始时每个元素都是独立的树，-1表示该元素就是根节点
        Arrays.fill(unionSet, -1);
//        第一次遍历将所有可合并的集合进行合并
        for (String e : equations)
            if (e.charAt(1) == '=')
                union(unionSet, e.charAt(0), e.charAt(3));
        boolean ans = true;
//        第二次遍历验证是否存在应该不属于同一集合的两个元素属于同一集合
        for (String e : equations)
            if (e.charAt(1) == '!' && findRoot(unionSet, e.charAt(0)) == findRoot(unionSet, e.charAt(3))) {
                ans = false;
                break;
            }
        return ans;
    }

    /**
     * 寻找一个元素的根节点，时间复杂度O(N)
     * @param unionSet
     * @param c
     * @return
     */
    public int findRoot(int[] unionSet, char c) {
        int index = c - 'a';
        while (unionSet[index] != -1)
            index = unionSet[index];
        return index;
    }

    /**
     * 合并两个元素所在的树（即合并两个关联集合）
     * @param unionSet
     * @param c1
     * @param c2
     */
    public void union(int[] unionSet, char c1, char c2) {
//        找出两棵树的根节点元素
        int c1Root = findRoot(unionSet, c1);
        int c2Root = findRoot(unionSet, c2);
//        当两棵树不同时合并
        if (c1Root != c2Root)
//            直接将其中一棵树挂在另一棵树的根节点下
            unionSet[c2Root] = c1Root;
    }
}
