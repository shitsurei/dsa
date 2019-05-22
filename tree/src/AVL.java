/**
 * 	平衡二叉树
 * 	递归函数的优势
 * 	考察每棵子树是否平衡
 * @author 张国荣
 * 	递归函数设计
 *	1.左子树是否平衡
 *	2.右子树是否平衡
 *	3.左右子树高度差（树高度为左右子树较大者加一）
 */
public class AVL {
	public static void main(String[] args) {
		Node n1 = TestData.generateTestData();
		System.out.println(isBalance(n1));
		
	}
	/**
	 * 	平衡二叉树判定返回值内部类
	 * 	通用套路类型
	 * @author 张国荣
	 *
	 */
	public static class ReturnData{
		boolean isBalance;
		int height;
		public ReturnData(boolean isBalance,int height) {
			this.isBalance = isBalance;
			this.height = height;
		}
	}
	/**
	 * 	平衡二叉树判定函数
	 * @param head
	 * @return
	 */
	public static boolean isBalance(Node head) {
		return process(head).isBalance;
	}
	
	/**
	 * 	平衡二叉树递归判定过程函数
	 * @param cur
	 * @return
	 */
	public static ReturnData process(Node cur) {
		//空树平衡，高度为1
		if(cur==null)
			return new ReturnData(true, 0);
		//递归返回左子树的平衡信息
		ReturnData left = process(cur.left);
		//左子树不平衡时二叉树不平衡，此时高度信息无意义，可返回0
		if(!left.isBalance)
			return new ReturnData(false, 0);
		//递归返回右子树的平衡信息
		ReturnData right = process(cur.right);
		//右子树不平衡时二叉树不平衡
		if(!right.isBalance)
			return new ReturnData(false, 0);
		//左子树右子树高度差大于1时二叉树不平衡
		if(Math.abs(left.height-right.height)>1)
			return new ReturnData(false, 0);
		//左子树右子树高度差小于1时二叉树平衡，同时返回当前子树的高度
		return new ReturnData(true, Math.max(left.height, right.height)+1);
	}
}
