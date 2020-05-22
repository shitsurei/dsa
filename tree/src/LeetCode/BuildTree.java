package LeetCode;

/**
 * 根据一棵树的前序遍历与中序遍历构造二叉树。
 * <p>
 * 注意:
 * 你可以假设树中没有重复的元素。
 * <p>
 * 例如，给出
 * 前序遍历 preorder = [3,9,20,15,7]
 * 中序遍历 inorder = [9,3,15,20,7]
 * 返回如下的二叉树：
 * 3
 * / \
 * 9  20
 * /  \
 * 15   7
 */
public class BuildTree {
    public static void main(String[] args) {
        System.out.println(new BuildTree().buildTree(new int[0], new int[0]));
    }

    private int preIndex = 0;

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        TreeNode help = new TreeNode(0);
        help.left = buildTree(preorder, inorder, 0, inorder.length - 1);
        return help.left;
    }

    /**
     * 思路：优化后的递归写法
     * 1 建立全局变量记录递归栈访问到的前序遍历数组中的索引下标
     * 2 每次递归调用构建左右子树的方法时，限定中序遍历数组中的搜索范围
     */
    public TreeNode buildTree(int[] preorder, int[] inorder, int start, int end) {
        if (preIndex == preorder.length)
            return null;
        TreeNode root = new TreeNode(preorder[preIndex++]);
        for (int i = start; i <= end; i++) {
            if (root.val == inorder[i]) {
                if (i > start)
                    root.left = buildTree(preorder, inorder, start, i - 1);
                if (i < end)
                    root.right = buildTree(preorder, inorder, i + 1, end);
                break;
            }
        }
        return root;
    }
}
