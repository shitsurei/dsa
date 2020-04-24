package LeetCode;

import java.util.LinkedList;
import java.util.List;

/**
 * 给定一个 没有重复 数字的序列，返回其所有可能的全排列。
 * 示例:
 * 输入: [1,2,3]
 * 输出:
 * [
 * [1,2,3],
 * [1,3,2],
 * [2,1,3],
 * [2,3,1],
 * [3,1,2],
 * [3,2,1]
 * ]
 */
public class Permutation {
    /**
     * 全排列还可以用回溯，使用visit数组记录哪些值已经加入过数组中
     */
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> ans = new LinkedList<>();
        if (nums == null || nums.length < 1)
            return ans;
        dfs(ans, new LinkedList<>(), new boolean[nums.length], nums);
        return ans;
    }

    private void dfs(List<List<Integer>> ans, List<Integer> temp, boolean[] visited, int[] num) {
        if (temp.size() == num.length) {
            ans.add(new LinkedList<>(temp));
            return;
        }
        for (int i = 0; i < num.length; i++) {
            if (visited[i])
                continue;
            visited[i] = true;
            temp.add(num[i]);
            dfs(ans, temp, visited, num);
            temp.remove(temp.size() - 1);
            visited[i] = false;
        }
    }
}
