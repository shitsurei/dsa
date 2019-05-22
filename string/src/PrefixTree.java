/**
 * 前缀树
 * 其操作的时间复杂度和数据量无关，效率高，结构精简，便于扩展功能（前缀计算相关）
 * 插入字符串的时间复杂度只和字符串的长度有关
 * @author 张国荣
 *
 */
public class PrefixTree {
	public static void main(String[] args) {
		Node head = new Node();
		head.insert("ab");
		head.insert("abcd");
		head.insert("abc");
		head.insert("bcd");
		head.insert("bd");
		System.out.println(head.isInsert("ab"));
		System.out.println(head.isInsert("b"));
		System.out.println(head.isPrefix("a"));
		System.out.println(head.isPrefix("c"));
		System.out.println(head.prefixWith("a"));
		System.out.println(head.prefixWith("c"));
	}

	/**
	 * 前缀树节点类
	 * @author 张国荣
	 *
	 */
	public static class Node {
		//表示每个节点可以到达的路径
		Node[] path;
		//以当前节点为结尾的字符串个数
		int end;
		//以当前节点结尾的字符串为前缀的字符串个数
		int through;

		public Node() {
			//这里只对应26个小写字母
			this.path = new Node[26];
			this.end = 0;
			this.through = 0;
		}

		/**
		 * 插入字符串到前缀树中
		 * @param str
		 */
		public void insert(String str) {
			char[] letters = str.toCharArray();
			Node cur = this;
			for (int i = 0; i < letters.length; i++) {
				Node next = cur.path[letters[i] - 'a'];
				if (next == null) {
					next = new Node();
					//注意，判断后建立的新节点要重新挂回原位置
					cur.path[letters[i] - 'a'] = next;
				}
				cur = next;
				cur.through++;
			}
			cur.end++;
		}

		/**
		 * 前缀树中是否含有字符串以某一字符串为前缀
		 * 
		 * @param prefix
		 * @return
		 */
		public boolean isPrefix(String prefix) {
			char[] letters = prefix.toCharArray();
			boolean exist = true;
			Node cur = this;
			for (int i = 0; i < letters.length; i++) {
				Node next = cur.path[letters[i] - 'a'];
				if (next == null) {
					exist = false;
					break;
				} else {
					cur = next;
				}
			}
			return exist;
		}

		/**
		 * 是否添加过某一字符串
		 * @param str
		 * @return
		 */
		public boolean isInsert(String str) {
			char[] letters = str.toCharArray();
			boolean inserted = true;
			Node cur = this;
			for (int i = 0; i < letters.length; i++) {
				Node next = cur.path[letters[i] - 'a'];
				if (next == null) {
					inserted = false;
					break;
				} else {
					cur = next;
				}
			}
			if (cur.end == 0)
				inserted = false;
			return inserted;
		}
		/**
		 * 前缀词频统计
		 * 有多少个字符串以某一字符串为前缀
		 * @param str
		 * @return
		 */
		public int prefixWith(String str) {
			char[] letters = str.toCharArray();
			boolean exist = true;
			Node cur = this;
			for (int i = 0; i < letters.length; i++) {
				Node next = cur.path[letters[i] - 'a'];
				if (next == null) {
					exist = false;
					break;
				} else {
					cur = next;
				}
			}
			if(exist)
				return cur.through;
			else
				return 0;
		}
	}
}
