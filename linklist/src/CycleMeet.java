/**
 * @author 张国荣
 * 
 *         讨论单链表的有环无环及相交与否的问题
 * 
 *         判断有环无环的方法： 第一种：借助辅助结构HashSet进行记录，重复时直接判断 第二种：利用快慢指针（原理用归纳法证明）
 * 
 *         判断相交与否的方法（相交指链表中的节点共用相同的内存地址）：
 *         情况一：两个无环链表相交，某一节点开始，两个链表共用剩余的节点，即如果两个链表相交，其最后一个节点必然相同
 *         情况二：两个有环链表相交，交于环内（此时入环节点必定相同）或交于环外（此时入环节点不相同） 情况三：一个有环链表和一个无环链表，不可能相交
 */
public class CycleMeet {
	public static void main(String[] args) {
		Node head = Generate.generateSinglyLink(new int[] { 0, 1, 2, 3, 4 }, 5);
		Node head2 = Generate.generateSinglyLink(new int[] { 0, 1, 2, 3, 4 }, 5);
		head.next.next.next.next.next = head.next.next;
		head2.next.next.next.next.next = head2.next.next.next;
		head.next.next = head2.next.next;
		System.out.println(isMeetAll(head, head2));

	}

	/**
	 * 判断两个链表是否相交的综合方法
	 * 
	 * @param n1
	 * @param n2
	 * @return
	 */
	public static boolean isMeetAll(Node n1, Node n2) {
		if(n1==null||n2==null)
			return false;
		Node c1 = isCycle(n1);
		Node c2 = isCycle(n2);
		//无环单链表相交问题
		if (c1 == null && c2 == null)
			return isMeet(n1, n2) != null;
		//有环单链表相交问题
		if (c1 != null && c2 != null)
			return isMeet2(n1, n2) != null;
		return false;
	}

	/**
	 * 判断单链表是否有环 
	 * 如果有环，返回第一个入环节点 
	 * 如果无环，返回null
	 * 
	 * @param head
	 * @return
	 */
	public static Node isCycle(Node head) {
		if (head == null)
			return null;
		// 快指针一次走两步,慢指针一次走一步
		Node fast = head, slow = head, enter = null;
		boolean find = false;
		// 快指针遇到空直接退出说明无环
		while (fast.next != null && fast.next.next != null) {
			fast = fast.next.next;
			slow = slow.next;
			if (fast == slow) {
				find = true;
				break;
			}
		}
		if (find) {
			// 相遇时其中一个指针回到表头
			fast = head;
			// 两个指针都以一次一步的速度向下走
			while (fast != slow) {
				fast = fast.next;
				slow = slow.next;
			}
			// 相遇时即为第一个入环节点
			enter = fast;
		}
		return enter;
	}

	/**
	 * 判断两个无环单链表是否相交 
	 * 如果相交，返回相交的第一个节点 
	 * 如果不想交，返回null
	 * 
	 * @param n1
	 * @param n2
	 * @return
	 */
	public static Node isMeet(Node n1, Node n2) {
		if (n1==null||n2==null) 
			return null;
		Node p = n1, q = n2;
		// 记录两个链表的长度
		int len1 = 0, len2 = 0;
		while (p != null) {
			len1++;
			p = p.next;
		}
		while (q != null) {
			len2++;
			q = q.next;
		}
		p = n1;
		q = n2;
		// 长的链表先走直到两个链表剩余长度相等
		int minus;
		if (len1 > len2) {
			minus = len1 - len2;
			while (minus-- > 0)
				p = p.next;
		}
		if (len1 < len2) {
			minus = len2 - len1;
			while (minus-- > 0)
				q = q.next;
		}
		// 两个链表同时开始遍历,比较是否相交
		Node meet = null;
		while (p != null && q != null) {
			if (p == q) {
				meet = p;
				break;
			}
			p = p.next;
			q = q.next;
		}
		return meet;
	}

	/**
	 * 判断两个有环单链表是否相交 
	 * 如果环外相交，返回两个链表的相交节点 
	 * 如果环内相交，返回任一链表的入环节点均可 
	 * 如果不想交，返回null
	 * 
	 * @param n1
	 * @param n2
	 * @return
	 */
	public static Node isMeet2(Node n1, Node n2) {
		Node meet = null;
		// 先找出两个链表的入环节点
		Node c1 = isCycle(n1);
		Node c2 = isCycle(n2);
		// 如果同一节点入环,说明两链表环外相交或入环节点相交,此时可采用无环单链表相交的思路解决
		if (c1 == c2) {
			// 先将两个链表的环部分去掉
			Node cycleHead = c1.next;
			c1.next = null;
			c2.next = null;
			// 求出相交节点
			meet = isMeet(n1, n2);
			// 再将环拼接回原链表中
			c1.next = cycleHead;
			c2.next = cycleHead;
			// 如果不是同一节点入环,则有可能是环内相交或不想交的情况,需要遍历其中一个链表的环来查看另一链表的入环节点是否在其中
		} else {
			Node p = c1.next;
			boolean isMeet = false;
			while (p != c1) {
				// 如果在其中,说明两个链表环内相交,否则两链表不想交
				if (p == c2) {
					isMeet = true;
					break;
				}
			}
			if (isMeet) {
				meet = c1;
				// meet = c2;
			}
		}
		return meet;
	}
}
