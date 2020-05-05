package LeetCode;

import java.util.Stack;

/**
 * 给定一个二叉树，检查它是否是镜像对称的。
 * <p>
 * 例如，二叉树 [1,2,2,3,4,4,3] 是对称的。
 * 1
 * / \
 * 2   2
 * / \ / \
 * 3  4 4  3
 * 但是下面这个 [1,2,2,null,3,null,3] 则不是镜像对称的:
 * 1
 * / \
 * 2   2
 * \   \
 * 3    3
 * <p>
 * 进阶：
 * 你可以运用递归和迭代两种方法解决这个问题吗？
 */
public class SymmetryTree {
    public static void main(String[] args) {
        TreeNode t1 = new TreeNode(1);
        TreeNode t2 = new TreeNode(2);
        TreeNode t3 = new TreeNode(2);
        TreeNode t4 = new TreeNode(3);
        TreeNode t5 = new TreeNode(4);
        TreeNode t6 = new TreeNode(4);
        TreeNode t7 = new TreeNode(3);
        t1.left = t2;
        t1.right = t3;
        t2.left = t4;
        t2.right = t5;
        t3.left = t6;
        t3.right = t7;
        System.out.println(new SymmetryTree().iteration(t1));
    }

    public boolean isSymmetric(TreeNode root) {
        return recursion(root, root);
    }

    /**
     * 思路1：递归
     * 1 对称二叉树的左右子树都是对称二叉树
     * 2 对称二叉树的左右子树要么都为空，要么都不为空
     * 3 对称二叉树的左右子树不为空时，其左子树的左子树和右子树的右子树值相等且对称，其左子树的右子树和右子树的左子树值相等且对称
     */
    public boolean recursion(TreeNode left, TreeNode right) {
        if (left == null && right == null)
            return true;
        if (left == null || right == null)
            return false;
        if (left.val != right.val)
            return false;
        return recursion(left.left, right.right) && recursion(left.right, right.left);
    }

    /**
     * 思路2：非递归
     * 使用两个栈辅助递归过程：一个栈以中左右的顺序遍历，另一个栈以中右左的顺序遍历
     * 每次判断弹栈的元素是否相等或都为空
     */
    public boolean iteration(TreeNode root) {
        if (root == null)
            return true;
        Stack<TreeNode> lmr = new Stack<>();
        lmr.push(root);
        Stack<TreeNode> rml = new Stack<>();
        rml.push(root);
        while (!lmr.isEmpty() && !rml.isEmpty()) {
            TreeNode lt = lmr.pop();
            TreeNode rt = rml.pop();
            if (lt == null && rt == null)
                continue;
            if (lt == null || rt == null || lt.val != rt.val)
                return false;
            lmr.push(lt.left);
            lmr.push(lt.right);
            rml.push(rt.right);
            rml.push(rt.left);
        }
        return true;
    }
}
