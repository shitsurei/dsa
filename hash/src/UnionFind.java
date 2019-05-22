import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;

/**
 * 并查集
 * 查询次数+合并次数逼近O(N)时，单次操作平均时间复杂度O(1)
 * @author 张国荣
 *
 */
public class UnionFind {
	public static void main(String[] args) {
		
	}
	public static class Node{
		// whatever
		int x;
		int y;
		public Node(int x,int y) {
			this.x = x;
			this.y = y;
		}
	}
	public static class UnionFindSet{
		/**
		 * 通过不断查hash表，找到某一节点的代表结点
		 */
		public Map<Node, Node> fatherNode;
		/**
		 * 某一个节点所在的集合，一共有多少个节点
		 */
		public Map<Node, Integer> sizeMap;
		/**
		 * 初始化函数，并查集只在初始化时接受节点，无法处理流数据
		 * @param nodes
		 */
		public UnionFindSet(List<Node> nodes) {
			fatherNode = new HashMap<>();
			sizeMap = new HashMap<>();
			for(Node e : nodes) {
				//初始情况下,每个节点的代表结点是自己,每个集合中只有一个节点
				fatherNode.put(e, e);
				sizeMap.put(e, 1);
			}
		}
		/**
		 * 寻找代表结点函数
		 * 同时将寻找路径上的节点都挂在代表结点之后（非递归）
		 * @param n
		 * @return
		 */
		private Node findHead(Node n) {
			if(n==null)
				return null;
			Node father = fatherNode.get(n);
			Stack<Node> stack = new Stack<>();
			while(father!=fatherNode.get(father)) {
				stack.push(father);
				father = fatherNode.get(father);
			}
			while(!stack.isEmpty()) {
				Node cur = stack.pop();
				fatherNode.put(cur, fatherNode.get(fatherNode.get(cur)));
			}
			return fatherNode.get(father);
		}
		
		/**
		 * 两节点是否属于同一集合
		 * @param n1
		 * @param n2
		 * @return
		 */
		public boolean isSameSet(Node n1,Node n2) {
			return findHead(n1)==findHead(n2);
		}
		/**
		 * 合并两个节点
		 * @param n1
		 * @param n2
		 */
		public void union(Node n1,Node n2) {
			if(n1==null||n2==null)
				return;
			Node head1 = findHead(n1);
			Node head2 = findHead(n2);
			//代表结点相同时即为同一集合
			if(head1!=head2) {
				int size1 = sizeMap.get(head1);
				int size2 = sizeMap.get(head2);
				if(size1>=size2) {
					fatherNode.put(head2, head1);
					sizeMap.put(head1, size1+size2);
				}else {
					fatherNode.put(head1, head2);
					sizeMap.put(head2, size1+size2);
				}
			}
		}
		/**
		 * 显示并查集中的接续关系
		 */
		public void showMap() {
			for(Entry<Node, Node> e : fatherNode.entrySet()) {
				System.out.println(e.getKey()+"->father->"+e.getValue());
			}
		}
	}
}
