package LeetCode;

import java.util.*;

/**
 * 给定一棵二叉树，想象自己站在它的右侧，按照从顶部到底部的顺序，返回从右侧所能看到的节点值。
 * <p>
 * 示例:
 * 输入: [1,2,3,null,5,null,4]
 * 输出: [1, 3, 4]
 * 解释:
 * <p>
 * 1            <---
 * /   \
 * 2     3         <---
 * \     \
 * 5     4       <---
 */
public class BinaryTreeSideView {

    /**
     * 根->右->左的访问顺序
     * 记录每一层访问到的第一个节点
     */
    public List<Integer> rightSideViewDFS(TreeNode root) {
        List<Integer> list = new LinkedList<>();
        if (root == null)
            return list;
        dfs(list, 0, root);
        return list;
    }

    public void dfs(List<Integer> list, int deep, TreeNode node) {
        if (node == null)
            return;
        if (deep == list.size())
            list.add(node.val);
        dfs(list, deep + 1, node.right);
        dfs(list, deep + 1, node.left);
    }

    /**
     * 层序遍历，每一层最后一个节点出队列时进行记录
     */
    public List<Integer> rightSideViewBFS(TreeNode root) {
        List<Integer> list = new LinkedList<>();
        if (root == null)
            return list;
        Queue<TreeNode> queue = new LinkedList<>();
        int nextNum = 0, thisNum = 1;
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            thisNum--;
            if (node.left != null) {
                queue.add(node.left);
                nextNum++;
            }
            if (node.right != null) {
                queue.add(node.right);
                nextNum++;
            }
            if (thisNum == 0) {
                list.add(node.val);
                thisNum = nextNum;
                nextNum = 0;
            }
        }
        return list;
    }
}
