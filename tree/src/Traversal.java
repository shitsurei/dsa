import java.util.Stack;

/**
 * 	二叉树的遍历（以三层完全二叉树为例）：
 * 					1
 * 
 * 			2				3
 * 
 * 		4		5		6		7
 * 	如果不考虑遍历时的节点操作，函数在压栈和弹栈的过程中依次来到各个节点的顺序如下：
 * 	1 2 4 4 4 2 5 5 5 2 1 3 6 6 6 3 7 7 7 3 1
 * 	先中后序遍历即分别在第一二三次到达节点时对其进行操作
 * 	先序遍历（第一次到达节点时进行操作）：1 2 4 5 3 6 7
 * 	中序遍历（第二次到达节点时进行操作）：4 2 5 1 6 3 7
 * 	后序遍历（第三次到达节点时进行操作）：4 5 2 6 7 3 1
 * @author 张国荣
 *
 */
public class Traversal {
	
	public static void main(String[] args) {
		Node n1 = TestData.generateTestData();
		postOrder(n1);
		System.out.println();
		postOrderUnrecursion(n1);
	}
	/**
	 * 	先序遍历递归版
	 * @param root
	 */
	public static void preOrder(Node root) {
		System.out.print(root.value+" ");
		if(root.left!=null)
			preOrder(root.left);
		if(root.right!=null)
			preOrder(root.right);
	}
	/**
	 * 	中序遍历递归版
	 * @param root
	 */
	public static void inOrder(Node root) {
		if(root.left!=null)
			inOrder(root.left);
		System.out.print(root.value+" ");
		if(root.right!=null)
			inOrder(root.right);
	}
	/**
	 * 	后序遍历递归版
	 * @param root
	 */
	public static void postOrder(Node root) {
		if(root.left!=null)
			postOrder(root.left);
		if(root.right!=null)
			postOrder(root.right);
		System.out.print(root.value+" ");
	}
	/**
	 * 	先序遍历非递归版
	 * @param root
	 */
	public static void preOrderUnrecursion(Node root) {
		if(root!=null) {
			Stack<Node> stack = new Stack<>();
			Node cur = root;
			stack.push(cur);
			//有右先压右,再压左
			while(!stack.isEmpty()) {
				cur = stack.pop();
				System.out.print(cur.value+" ");
				if(cur.right!=null)
					stack.push(cur.right);
				if(cur.left!=null)
					stack.push(cur.left);
			}
		}
		System.out.println();
	}
	
	/**
	 * 	中序遍历非递归版
	 * @param root
	 */
	public static void inOrderUnrecursion(Node root) {
		if(root==null)
			return;
		Stack<Node> stack = new Stack<>();
		Node cur = root;
		while(!stack.isEmpty()||cur!=null) {
			//如果当前节点不为空,当前节点压栈,向左移动(往左一压压一溜)
			if(cur!=null) {
				stack.push(cur);
				cur = cur.left;
			//如果当前节点为空,弹栈,向右移动
			}else {
				cur = stack.pop();
				System.out.print(cur.value+" ");
				cur = cur.right;
			}
		}
		System.out.println();
	}
	
	/**
	 * 	后序遍历非递归版
	 * @param root
	 */
	public static void postOrderUnrecursion(Node root) {
		if(root!=null) {
			//准备两个栈,一个用来以中右左的顺序弹栈,其弹栈顺序正好是后序遍历的逆序(即左右中)
			//第二个栈用于将第一个栈的弹栈节点压栈,最终统一弹栈
			Stack<Node> stack1 = new Stack<>(),stack2 = new Stack<>();
			Node cur = root;
			stack1.push(cur);
			//有左先压左,再压右
			while(!stack1.isEmpty()) {
				cur = stack1.pop();
				stack2.push(cur);
				if(cur.left!=null)
					stack1.push(cur.left);
				if(cur.right!=null)
					stack1.push(cur.right);
			}
			while(!stack2.isEmpty())
				System.out.print(stack2.pop().value+" ");
			System.out.println();
		}
	}
	
}
