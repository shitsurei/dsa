/**
 * 	讨论线索二叉树的相关问题
 * @author 张国荣
 *	前驱节点：按中序遍历的顺序某个节点的前一个节点
 *	后继节点：按中序遍历的顺序某个节点的后一个节点
 *
 *	查询某个节点的后继节点取决于当前节点是否有右孩子：
 *	1.如果有右孩子，当前节点的后继为右子树中最左的节点
 *	2.如果没有右孩子，不断向父节点回溯，直到回溯到某一层其通过的是父节点的左孩子分支路径，此时原当前节点的后继节点为该父节点
 *
 *	查询某个节点的前驱节点取决于当前节点是否有左孩子：
 *	1.如果有左孩子，当前节点的前驱为左子树中最右的节点
 *	2.如果没有左孩子，不断向父节点回溯，直到回溯到某一层其通过的是父节点的右孩子分支路径，此时原当前节点的前驱节点为该父节点
 */
public class Thread {
	/**
	 * 	带父节点指针的二叉树节点内部类
	 * @author 张国荣
	 *
	 */
	public static class BitNode{
		int value;
		BitNode left;
		BitNode right;
		BitNode parent;
		public BitNode(int value) {
			this.value = value;
		}
	}
	
	public static void main(String[] args) {
		BitNode bn1 = new BitNode(1);
		BitNode bn2 = new BitNode(2);
		BitNode bn3 = new BitNode(3);
		BitNode bn4 = new BitNode(4);
		BitNode bn5 = new BitNode(5);
		BitNode bn6 = new BitNode(6);
		BitNode bn7 = new BitNode(7);
		bn1.left = bn2;
		bn1.right = bn3;
		bn1.parent = null;
		bn2.left = bn4;
		bn2.right = bn5;
		bn2.parent = bn1;
		bn3.left = bn6;
		bn3.right = bn7;
		bn3.parent = bn1;
		bn4.parent = bn2;
		bn5.parent = bn2;
		bn6.parent = bn3;
		bn7.parent = bn3;
		
		System.out.println(sequenceBitNode(bn7).value);
	}
	/**
	 * 	查找二叉树中某一节点的后继节点
	 * @param bn
	 * @return
	 */
	public static BitNode sequenceBitNode(BitNode bn) {
		BitNode sequence = null;
		if(bn==null)
			return sequence;
		//当前节点没有右孩子,其后继为右子树中最左的节点
		if(bn.right==null) {
			sequence = bn.parent;
			//不断向父节点回溯,直到回溯到某一层其通过的是父节点的左孩子分支路径
			while(bn!=null&&sequence.left!=bn) {
				bn = sequence;
				//原当前节点的后继为该父节点
				sequence = sequence.parent;
			}
			return sequence;
		}else {
			//当前节点没有右孩子,其后继为右子树中最左的节点
			sequence = bn.right;
			while(sequence.left!=null)
				sequence = sequence.left;
			return sequence;
		}
	}
	
	/**
	 * 	查找二叉树中某一节点的前驱节点
	 * @param bn
	 * @return
	 */
	public static BitNode precursorBitNode(BitNode bn) {
		BitNode precursor = null;
		if(bn==null)
			return precursor;
		if(bn.left==null) {
			precursor = bn.parent;
			while(bn!=null&&precursor.right!=bn) {
				bn = precursor;
				precursor = precursor.parent;
			}
			return precursor;
		}else {
			precursor = bn.left;
			while(precursor.right!=null)
				precursor = precursor.right;
			return precursor;
		}
	}
}
