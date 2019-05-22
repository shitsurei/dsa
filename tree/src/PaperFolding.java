/**
 * 	折纸问题：求一张纸对折N次后展开，折痕的朝向顺序（设向上为1，向下为0） 归纳可得：
 * 1.折痕顺序为一棵根节点为0深度为N的满二叉树的中序遍历结果，树种的每个非叶子节点的左子树均为0，右子树均为1
 * 2.折痕的顺序结构类似一种特殊的回文结构，以0为对称轴，左右两侧对应位置取反
 * 
 * @author 张国荣
 *
 */
public class PaperFolding {
	public static void main(String[] args) {
		System.out.print("fold order:");
		foldOrder(1, 5, false);
		System.out.println();
		foldOrder2(5);
	}

	/**
	 * 折纸问题解法1 直接在递归中序遍历的过程中输出结果， 
	 * 终止条件为深度
	 * 
	 * @param level
	 */
	public static void foldOrder(int cur, int level, boolean direction) {
		if (cur > level)
			return;
		int dir = direction == true ? 1 : 0;
		foldOrder(cur + 1, level, false);
		System.out.print(dir + " ");
		foldOrder(cur + 1, level, true);
	}

	/**
	 * 折纸问题解法2
	 * 
	 * @param level
	 */
	public static void foldOrder2(int level) {
		Node head = generateFullTreeByLevel(level);
		if (head == null)
			System.out.println("no fold");
		else {
			System.out.print("fold order:");
			// 中序遍历该树的结果即为折痕的朝向顺序
			Traversal.inOrderUnrecursion(head);
		}
	}

	/**
	 * 由归纳规律，考虑使用层序遍历的反序列化和完全二叉树的层序序列化的特点生成该树
	 * 
	 * @param level
	 * @return
	 */
	public static Node generateFullTreeByLevel(int level) {
		// 处理深度不合法,深度为零和深度为一的特例
		if (level <= 0)
			return null;
		if (level == 1)
			return new Node(0);
		// 加减乘除的优先级高于位运算
		// 根据满二叉树的相关公式,计算层序遍历中左右子树出现的组数(除去根节点)
		int childs = ((1 << level) - 2) >> 1;
		// 计算层序遍历时所需的空值个数(即叶子结点下一层的节点个数)
		int nullNode = 1 << level;
		// 开始拼串
		StringBuilder tree = new StringBuilder();
		// 先拼根节点
		tree.append("0_");
		// 再拼左右子树对
		while (childs-- > 0)
			tree.append("0_1_");
		// 最后拼空节点
		while (nullNode-- > 0)
			tree.append("#_");
		// 返回层序反序列化生成的满二叉树
		return Serialization.levelUnserialize(tree.toString());
	}
}
