package LeetCode;

import java.util.Stack;

/**
 * 给定一个二叉树，判断其是否是一个有效的二叉搜索树。
 * 假设一个二叉搜索树具有如下特征：
 * 节点的左子树只包含小于当前节点的数。
 * 节点的右子树只包含大于当前节点的数。
 * 所有左子树和右子树自身必须也是二叉搜索树。
 * <p>
 * 示例 1:
 * 输入:
 * 2
 * / \
 * 1   3
 * 输出: true
 * 示例 2:
 * 输入:
 * 5
 * / \
 * 1   4
 *      / \
 *     3   6
 * 输出: false
 * 解释: 输入为: [5,1,4,null,null,3,6]。
 *      根节点的值为 5 ，但是其右子节点值为 4 。
 */
public class ValidSearch {
    public static void main(String[] args) {
        TreeNode t1 = new TreeNode(10);
        TreeNode t2 = new TreeNode(5);
        TreeNode t3 = new TreeNode(15);
        TreeNode t4 = new TreeNode(6);
        TreeNode t5 = new TreeNode(20);
        t1.left = t2;
        t1.right = t3;
        t3.left = t4;
        t3.right = t5;
        System.out.println(new ValidSearch().isValidBST1(t1));
    }

    private int temp = 0;
    private boolean first = true;

    /**
     * 思路1：递归
     * 中序遍历的节点值必须是严格递增顺序
     */
    public boolean isValidBST(TreeNode root) {
        boolean bst = true;
        if (root == null)
            return bst;
        bst = isValidBST(root.left);
        if (!bst)
            return bst;
        if (first || root.val > temp) {
            temp = root.val;
            first = false;
        } else
            return false;
        bst = isValidBST(root.right);
        return bst;
    }

    /**
     * 思路2：非递归
     * 借助栈结构代替本地方法调用压栈
     */
    public boolean isValidBST1(TreeNode root) {
        if (root == null)
            return true;
        Stack<TreeNode> stack = new Stack<>();
        while (!stack.isEmpty() || root != null) {
            if (root != null) {
                stack.push(root);
                root = root.left;
            } else {
                root = stack.pop();
                if (first || root.val > temp) {
                    first = false;
                    temp = root.val;
                } else
                    return false;
                root = root.right;
            }
        }
        return true;
    }
}
