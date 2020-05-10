package LeetCode;

/**
 * 给定一个二叉树, 找到该树中两个指定节点的最近公共祖先。
 * 百度百科中最近公共祖先的定义为：
 * “对于有根树 T 的两个结点 p、q，最近公共祖先表示为一个结点 x，满足 x 是 p、q 的祖先且 x 的深度尽可能大
 * （一个节点也可以是它自己的祖先）。”
 * <p>
 * 例如，给定如下二叉树:  root = [3,5,1,6,2,0,8,null,null,7,4]
 * 示例 1:
 * 输入: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 1
 * 输出: 3
 * 解释: 节点 5 和节点 1 的最近公共祖先是节点 3。
 * 示例 2:
 * 输入: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 4
 * 输出: 5
 * 解释: 节点 5 和节点 4 的最近公共祖先是节点 5。因为根据定义最近公共祖先节点可以为节点本身。
 * <p>
 * 说明:
 * 所有节点的值都是唯一的。
 * p、q 为不同节点且均存在于给定的二叉树中。
 */
public class LowestCommonAncestor {
    public static void main(String[] args) {
        TreeNode t1 = new TreeNode(3);
        TreeNode t2 = new TreeNode(5);
        TreeNode t3 = new TreeNode(1);
        TreeNode t4 = new TreeNode(6);
        TreeNode t5 = new TreeNode(2);
        TreeNode t6 = new TreeNode(0);
        TreeNode t7 = new TreeNode(8);
        TreeNode t8 = new TreeNode(7);
        TreeNode t9 = new TreeNode(4);
        t1.left = t2;
        t1.right = t3;
        t2.left = t4;
        t2.right = t5;
        t3.left = t6;
        t3.right = t7;
        t5.left = t8;
        t5.right = t9;
        TreeNode ans = new LowestCommonAncestor().lowestCommonAncestor(t1, t2, t9);
        System.out.println(ans);
    }

    /**
     * 思路：先序遍历递归
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
//        当前遍历到空节点或p，q节点时返回当前节点
        if (root == null || root == p || root == q)
            return root;
//        递归获取左子树和右子树
//        可能出现的情况有三种：
//        1 一直到叶子结点也没有找到p或q节点，返回了null
//        2 找到了p或q节点中的一个，那么返回找到的那个节点（注意返回的节点对象为p或q）
//        3 p和q节点都找到了，必然一个在左子树一个在右子树
//              哪个在左哪个在右不重要，重要的是第一次出现左右子树返回pq的这个根必然是pq的最近公共祖先
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
//        情况3
        if (left != null && right != null)
            return root;
        else
//            情况2（其中还包括返回最近公共祖先过程中另一侧的子树必然返回空的情况）
            return left == null ? right : left;
    }
}
