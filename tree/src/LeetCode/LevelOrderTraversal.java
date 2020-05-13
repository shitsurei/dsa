package LeetCode;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * 给你一个二叉树，请你返回其按 层序遍历 得到的节点值。 （即逐层地，从左到右访问所有节点）。
 * <p>
 * 示例：
 * 二叉树：[3,9,20,null,null,15,7],
 * 3
 * / \
 * 9  20
 * /  \
 * 15   7
 * 返回其层次遍历结果：
 * [
 * [3],
 * [9,20],
 * [15,7]
 * ]
 */
public class LevelOrderTraversal {
    public static void main(String[] args) {
        TreeNode t1 = new TreeNode(1);
        TreeNode t2 = new TreeNode(2);
        TreeNode t3 = new TreeNode(3);
        TreeNode t4 = new TreeNode(4);
        TreeNode t5 = new TreeNode(5);
        t1.left = t2;
        t2.left = t3;
        t3.left = t4;
        t4.left = t5;
        System.out.println(new LevelOrderTraversal().levelOrder(t1));
    }

    /**
     * 思路：借助队列实现bfs，使用当前层的节点个数和下一层的节点个数记录交替
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> ans = new LinkedList<>();
        if (root == null)
            return ans;
        Deque<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int curLevel = 1, nextLevel = 0;
        List<Integer> temp = new LinkedList<>();
        while (!queue.isEmpty()) {
            TreeNode t = queue.poll();
            curLevel--;
            temp.add(t.val);
            if (t.left != null) {
                queue.add(t.left);
                nextLevel++;
            }
            if (t.right != null) {
                queue.add(t.right);
                nextLevel++;
            }
            if (curLevel == 0) {
                curLevel = nextLevel;
                nextLevel = 0;
                ans.add(temp);
                temp = new LinkedList<>();
            }
        }
        return ans;
    }
}
