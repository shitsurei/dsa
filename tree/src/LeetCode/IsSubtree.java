package LeetCode;

/**
 * 给定两个非空二叉树 s 和 t，检验 s 中是否包含和 t 具有相同结构和节点值的子树。
 * s 的一个子树包括 s 的一个节点和这个节点的所有子孙。s 也可以看做它自身的一棵子树。
 * <p>
 * 示例 1:
 * 给定的树 s:
 * 3
 * / \
 * 4   5
 * / \
 * 1   2
 * 给定的树 t：
 * 4
 * / \
 * 1   2
 * 返回 true，因为 t 与 s 的一个子树拥有相同的结构和节点值。
 * <p>
 * 示例 2:
 * 给定的树 s：
 * 3
 * / \
 * 4   5
 * / \
 * 1   2
 * /
 * 0
 * 给定的树 t：
 * 4
 * / \
 * 1   2
 * 返回 false。
 */
public class IsSubtree {
    /**
     * 思路：外层递归函数寻找s树中根节点和t相同的子树，内层递归判断这两棵子树是否相等
     */
    public boolean isSubtree(TreeNode s, TreeNode t) {
//        如果s子树为空即递归过程中没有找到与t根节点值相等的s子树，直接返回false
        if (s == null || t == null)
            return false;
//        找到与t树根节点相同的s子树，且两个子树完全相等，直接返回true
        if (s.val == t.val && sameRoot(s, t))
            return true;
//        不满足时递归查找s树的左子树和右子树是否满足条件（有一个满足条件即存在，因此用或）
        return isSubtree(s.left, t) || isSubtree(s.right, t);
    }

    /**
     * 内层递归要判断两棵树是否完全相同，即要保证：
     * 1 当前遍历的节点要么均为空，要么都不为空且节点值相等
     * 2 递归判断时左右子树都要满足条件才能说明子树相同（因此用与）
     */
    public boolean sameRoot(TreeNode r1, TreeNode r2) {
        if (r1 == null && r2 == null)
            return true;
        if (r1 == null || r2 == null)
            return false;
        if (r1.val != r2.val)
            return false;
        return sameRoot(r1.left, r2.left) && sameRoot(r1.right, r2.right);
    }
}
