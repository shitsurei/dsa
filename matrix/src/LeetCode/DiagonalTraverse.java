package LeetCode;

import java.util.*;

/**
 * 给你一个列表 nums ，里面每一个元素都是一个整数列表。请你依照下面各图的规则，按顺序返回 nums 中对角线上的整数。
 * <p>
 * 示例 1：
 * 输入：nums = [[1,2,3],[4,5,6],[7,8,9]]
 * 输出：[1,4,2,7,5,3,8,6,9]
 * 示例 2：
 * 输入：nums = [[1,2,3,4,5],[6,7],[8],[9,10,11],[12,13,14,15,16]]
 * 输出：[1,6,2,8,7,3,9,4,12,10,5,13,11,14,15,16]
 * 示例 3：
 * 输入：nums = [[1,2,3],[4],[5,6,7],[8],[9,10,11]]
 * 输出：[1,4,2,5,3,8,6,9,7,10,11]
 * 示例 4：
 * 输入：nums = [[1,2,3,4,5,6]]
 * 输出：[1,2,3,4,5,6]
 * <p>
 * 提示：
 * 1 <= nums.length <= 10^5
 * 1 <= nums[i].length <= 10^5
 * 1 <= nums[i][j] <= 10^9
 * nums 中最多有 10^5 个数字。
 */
public class DiagonalTraverse {
    public static void main(String[] args) {
        List<Integer> l1 = Arrays.asList(1, 2, 3);
        List<Integer> l2 = Arrays.asList(4, 5, 6);
        List<Integer> l3 = Arrays.asList(7, 8, 9);
        List<List<Integer>> all = Arrays.asList(l1, l2, l3);
        System.out.println(Arrays.toString(new DiagonalTraverse().findDiagonalOrder(all)));
    }

    /**
     * 思路：将x+y相等的坐标进行分类，再按照y坐标的大小对这些点进行排序，最后按x+y的值从小到大复原数组
     */
    public int[] findDiagonalOrder(List<List<Integer>> nums) {
        Map<Integer, List<int[]>> groups = new HashMap<>();
//        记录元素总个数
        int len = 0;
        for (int i = 0; i < nums.size(); i++) {
            for (int j = 0; j < nums.get(i).size(); j++) {
                List<int[]> group = groups.getOrDefault(i + j, new LinkedList<>());
                group.add(new int[]{i, j});
                groups.put(i + j, group);
            }
            len += nums.get(i).size();
        }
        int[] ans = new int[len];
        int index = 0;
//        遍历从小到大x+y的和
        int sum = 0;
        while (index < ans.length) {
            List<int[]> group = groups.getOrDefault(sum++, new LinkedList<>());
//            按照点的y坐标大小对x+y相同的集合排序
            Collections.sort(group, Comparator.comparingInt(a -> a[1]));
            for (int i = 0; i < group.size(); i++) {
                int[] temp = group.get(i);
                ans[index++] = nums.get(temp[0]).get(temp[1]);
            }
        }
        return ans;
    }
}
