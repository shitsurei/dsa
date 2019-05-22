import java.util.LinkedList;
import java.util.Queue;

/**
 * 	完全二叉树
 * 	判定方式：层序遍历的结果，总体顺序为空节点总在最后
 * 	二叉树建堆：节省空间（申请为空的数组空间），提升效率（数组扩容的拷贝成本）
 * @author 张国荣
 *
 */
public class Complete {
	public static void main(String[] args) {
		Node n  = Serialization.levelUnserialize("1_2_3_4_5_6_7_#_#_#_#_#_#_#_#_");
		System.out.println(nodeNum(n));
	}
	
	/**
	 * 	完全二叉树判定函数，利用二叉树层序遍历
	 * @param head
	 * @return
	 */
	public static boolean isComplete(Node head) {
		if(head==null)
			return true;
		Queue<Node> queue = new LinkedList<>();
		//设置开关，标记非空节点部分的结束和循环开关
		boolean valueEnd = false, isComplete = true;
		queue.add(head);
		while(!queue.isEmpty()) {
			Node cur = queue.poll();
			//当前节点为空时进行标记
			if(cur==null) {
				valueEnd = true;
				//当出现某一节点只有右子树没有左子树时直接判定
			}else if(cur.left==null&&cur.right!=null) {
				isComplete = false;
				break;
			}else {
				queue.add(cur.left);
				queue.add(cur.right);
			}
			//当空节点之后又出现非空节点时直接判定
			if(valueEnd&&cur!=null) {
				isComplete = false;
				break;
			}
		}
		return isComplete;
	}
	
	/**
	 * 	求完全二叉树的节点个数
	 * 	要求时间复杂度小于O(N)
	 * 	实际为O(log(N)^2)
	 * @param root
	 * @return
	 */
	public static int nodeNum(Node root) {
		if(root==null)
			return 0;
		return bs(root,deepNum(root, 1),1);
	}
	/**
	 * 	递归计算一棵树及其子树的节点个数
	 * 	公式：一颗满二叉树的深度为l时（根节点深度为1），节点个数为2^l-1
	 * @param cur	表示当前节点
	 * @param whole	常量，表示整颗二叉树的深度
	 * @param curLevel	当前节点的深度
	 * @return
	 */
	public static int bs(Node cur,int whole,int curLevel) {
		//如果当前节点的深度和整棵树的深度相等，说明该节点为叶子结点，以该节点为根的子树节点个数为1
		if(curLevel==whole)
			return 1;
		//如果当前节点的右子树的最底层和原树的最低层处于同一层，说明当前节点的左子树为满二叉树，可以利用公式直接计算左子树节点数
		if(deepNum(cur.right, curLevel+1)==whole) {
			//此处 1<<x 等价于 2^x 
			//此时该子树的总节点数等于其左子树（满二叉树）的节点数（公式计算）加上右子树的总节点数（递归调用）再加上自己
			return (1<<(whole-curLevel)-1+1) + bs(cur.right,whole,curLevel+1);
		//如果当前节点的右子树的最底层和原树的最低层不处于同一层，说明当前节点的右子树为满二叉树，可以利用公式直接右子树计算节点数
		}else {
			//此时该子树的总节点数等于其右子树（满二叉树，由于最底层缺失，深度多减一）的节点数（公式计算）加上左子树的总节点数（递归调用）再加上自己
			return (1<<(whole-curLevel-1)-1+1) + bs(cur.left,whole,curLevel+1);
		}
	}
	
	/**
	 * 	计算完全二叉树中某一节点为根节点的子树深度和该节点在原树中的层数之和（完全二叉树中的子树也都是完全二叉树）
	 * 	这个函数返回值用于判定某一子树的深度是否到达原树的最底层
	 * @param cur	当前节点
	 * @param curLevel	当前节点所在层数（根节点为1）
	 * @return
	 */
	public static int deepNum(Node cur,int curLevel) {
		//从根节点不断向左移动累加，直到叶结点
		int curDeep = 1;
		while(cur.left!=null) {
			curDeep++;
			cur = cur.left;
		}
		return curDeep+curLevel-1;
	}
}
