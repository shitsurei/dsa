import java.util.Stack;

/**
 * 	搜索二叉树：任何一个节点，左子树都比他小，右子树都比他大
 * 	通常而言，搜索二叉树不包含重复值节点，重复值可以合并
 * 	判定方式：中序遍历结果升序即可
 * @author 张国荣
 *
 */
public class Search {
	public static void main(String[] args) {
		Node head = Serialization.levelUnserialize("4_2_6_1_3_5_7_#_#_#_#_#_#_#_#_");
		System.out.println(isSearch(head));
	}
	
	/**
	 * 	平衡二叉树的判定，利用中序遍历的非递归函数，每次弹栈时与前一次弹栈的数值作比较
	 * @param root
	 * @return
	 */
	public static boolean isSearch(Node root) {
		if(root==null)
			return true;
		Stack<Node> stack = new Stack<>();
		Node cur = root;
		//初始值设定为系统最小整型值
		int val = Integer.MIN_VALUE;
		//设置循环开关
		boolean isSearch = true;
		while(!stack.isEmpty()||cur!=null) {
			//中序遍历：如果当前节点不为空,当前节点压栈,向左移动(往左一压压一溜)
			if(cur!=null) {
				stack.push(cur);
				cur = cur.left;
			//中序遍历：如果当前节点为空,弹栈,向右移动
			}else {
				cur = stack.pop();
				//弹栈操作为弹栈节点数值与前一个弹栈数值作比较
				if(cur.value<=val) {
					isSearch = false;
					break;
				}else {
					val = cur.value;
				}
				cur = cur.right;
			}
		}
		return isSearch;
	}
}
