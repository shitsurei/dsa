/**
 * 链表数据下的荷兰国旗问题
 * 
 * @author 张国荣
 *
 */
public class ListFlag {
	public static void main(String[] args) {
		Node n = Generate.generateSinglyLink(new int[] { 3, 3, 3, 9, 0, 4, 5, 1, 2, 3, 4, 3, 2 }, 13);
		Node n1 = validate(n, 3);
		while (n1 != null) {
			System.out.print(n1.value);
			n1 = n1.next;
		}
	}

	/**
	 * 将单向链表按某值划分为左边小中间相等右边大的形式
	 * 要求时间复杂度为O(N)，空间复杂度为O(1)，每个部分里的节点从左到右的顺序与原链表中节点的先后次序一致
	 * 
	 * @param n
	 */
	public static Node partition(Node n, int flag) {
		if (n == null || n.next == null)
			return n;
		// 设置三个指针分别用于挂载对应区间的节点,由于是头指针和头插法
		Node small = null, equal = null, big = null, next = n.next;
		while (n != null) {
			if (n.value > flag) {
				n.next = big;
				big = n;
			} else if (n.value < flag) {
				n.next = small;
				small = n;
			} else {
				n.next = equal;
				equal = n;
			}
			n = next;
			next = next == null ? null : next.next;
		}
		// 其顺序和原来链表中的顺序相反,挂载完成后要进行逆序操作
		small = Reverse.reverseSinglyLink(small);
		big = Reverse.reverseSinglyLink(big);
		// 最后将三段链表进行连接,考虑不存在部分区间的情况
		n = small == null ? equal == null ? big : equal : small;
		if (small != null) {
			while (small.next != null)
				small = small.next;
			small.next = equal;
			while (small.next != null)
				small = small.next;
			small.next = big;
		} else if (equal != null) {
			while (equal.next != null)
				equal = equal.next;
			equal.next = big;
		}
		return n;
	}

	/**
	 * 链表荷兰国旗问题对数器
	 * 
	 * @param n
	 * @param flag
	 * @return
	 */
	public static Node validate(Node n, int flag) {
		int num = 0;
		Node p = n, q = n;
		while (p != null) {
			num++;
			p = p.next;
		}
		int[] small = new int[num];
		int[] equal = new int[num];
		int[] big = new int[num];
		int s = 0, e = 0, b = 0;
		while (q != null) {
			if (q.value > flag) {
				big[b++] = q.value;
			} else if (q.value < flag) {
				small[s++] = q.value;
			} else {
				equal[e++] = flag;
			}
			q = q.next;
		}
		Node bigNode = Generate.generateSinglyLink(big, b);
		Node smallNode = Generate.generateSinglyLink(small, s);
		Node equalNode = Generate.generateSinglyLink(equal, e);
		if (smallNode != null) {
			n = smallNode;
			while (smallNode.next != null)
				smallNode = smallNode.next;
			smallNode.next = equalNode;
			while (smallNode.next != null)
				smallNode = smallNode.next;
			smallNode.next = bigNode;
		} else if (equalNode != null) {
			n = equalNode;
			while (equalNode.next != null)
				equalNode = equalNode.next;
			equalNode.next = bigNode;
		} else {
			n = bigNode;
		}
		return n;
	}
}
