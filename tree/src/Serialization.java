import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * @author 张国荣
 * 	二叉树的序列化和反序列化（以三层完全二叉树为例），用于二叉树的持久化存储
 * 					1
 * 
 * 			2				3
 * 
 * 		4		5		6		7
 * 	按照不同的顺序遍历并序列化，每个值的结尾加下划线 _ ，null值用符号 # 代替
 * 	先序序列化：	1_2_4_#_#_5_#_#_3_6_#_#_7_#_#_
 * 	中序序列化：	#_4_#_2_#_5_#_1_#_6_#_3_#_7_#_
 * 	后序序列化：	#_#_4_#_#_5_2_#_#_6_#_#_7_3_1_
 * 	层序序列化：	1_2_3_4_5_6_7_#_#_#_#_#_#_#_#_
 */
public class Serialization {
	public static void main(String[] args) {
		Node n1 = new Node(2);
		Node n2 = new Node(4);
		Node n3 = TestData.generateTestData();
		n3.left.right = null;
		n1.left = n2;
		System.out.println(preSerialize(n3));
		System.out.println(preSerialize(n1));
	}
	
	/**
	 * 	先序序列化递归版本
	 * @param root
	 * @return
	 */
	public static String preSerialize(Node root) {
		if(root==null)
			return "#_";
		String res = root.value+"_";
		res+= preSerialize(root.left);
		res+= preSerialize(root.right);
		return res;
	}
	
	/**
	 * 	先序序列化非递归版本
	 * @param root
	 * @return
	 */
	public static String preSerializeUnrecursion(Node root) {
		StringBuilder str = new StringBuilder();
		if(root==null) {
			str.append("#_");
			return str.toString();
		}
		Stack<Node> stack = new Stack<>();
		stack.push(root);
		while(!stack.isEmpty()) {
			Node cur = stack.pop();
			str.append(String.valueOf(cur.value)).append('_');
			if(cur.right==null)
				str.append("#_");
			else
				stack.push(cur.right);
			if(cur.left==null)
				str.append("#_");
			else
				stack.push(cur.left);
		}
		return str.toString();
	}
	
	/**
	 * 	先序反序列化递归版
	 * @param tree
	 * @return
	 */
	public static Node preUnserialize(String tree) {
		String[] values = tree.split("_");
		//借助辅助结构队列
		Queue<String> queue = new LinkedList<>();
		for(String e : values)
			queue.add(e);
		return reconPreOrder(queue);
	}
	/**
	 * 	每一次从队列中消费一个先序序列化的值
	 * @param queue
	 * @return
	 */
	public static Node reconPreOrder(Queue<String> queue) {
		String value = queue.poll();
		Node head = null;
		if("#".equals(value))
			return head;
		//先中再左再右
		head = new Node(Integer.valueOf(value));
		head.left = reconPreOrder(queue);
		head.right = reconPreOrder(queue);
		return head;
	}
	
	/**
	 * 	层序序列化（遍历）
	 * @param head
	 * @return
	 */
	public static String levelSerialize(Node head) {
		if(head==null)
			return "#_";
		//借助队列先进先出的特点，在每个节点出队时将其左右节点入队
		Queue<Node> queue = new LinkedList<>();
		StringBuilder str = new StringBuilder();
		queue.add(head);
		while(!queue.isEmpty()) {
			Node cur = queue.poll();
			if(cur==null) {
				str.append("#_");
				continue;
			}
			str.append(cur.value).append("_");
			queue.add(cur.left);
			queue.add(cur.right);
		}
		return str.toString();
	}
	
	/**
	 * 	层序反序列化
	 * @param tree
	 * @return
	 */
	public static Node levelUnserialize(String tree) {
		if(tree==null||"#_".equals(tree))
			return null;
		String[] values = tree.split("_");
		int index = 0;
		//借助队列先进先出的特点，在每个节点出队时将其左右子树连接到根节点上
		Queue<Node> queue = new LinkedList<>();
		Node head = generateNode(values[index++]);
		queue.add(head);
		while(!queue.isEmpty()) {
			Node cur = queue.poll();
			cur.left = generateNode(values[index++]);
			if(cur.left!=null)
				queue.add(cur.left);
			cur.right = generateNode(values[index++]);
			if(cur.right!=null)
				queue.add(cur.right);
		}
		return head;
	}
	
	/**
	 * 	根据层序序列化的给定值来生成一个节点
	 * @param value
	 * @return
	 */
	public static Node generateNode(String value) {
		Node cur = null;
		if(value.equals("#"))
			return cur;
		cur = new Node(Integer.valueOf(value));
		return cur;
	}
}
