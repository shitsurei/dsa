public class Reverse {
	public static void main(String[] args) {
		int[] arr = {1,2,3,4,5,6,7};
		Node n1 = Generate.generateSinglyLink(arr,arr.length);
		Node p = reverseSinglyLink(n1.next.next.next);
		reverseSinglyLink(p);
		Generate.printSinglyLinkList(p);
		Node n2 = Generate.generateDoublyLink(arr,arr.length);
		Node q = reverseDoublyLink(n2);
		Generate.printDoublyLinkList(q);
	}
	/**
	 * 反转单链表，原地反转
	 * @param n
	 * @return
	 */
	public static Node reverseSinglyLink(Node n) {
		if(n==null||n.next==null)
			return n;
		//同时记录相邻的三个节点的位置
		Node prev = null,next = n.next;
		while(next.next!=null) {
			//逐个改变节点的指向关系
			n.next = prev;
			//依次后移
			prev = n;
			n = next;
			next = next.next;
		}
		//处理最后两个节点之间的顺序
		n.next = prev;
		next.next = n;
		return next;
	}
	/**
	 * 反转双向链表，原地反转
	 * @param n
	 * @return
	 */
	public static Node reverseDoublyLink(Node n) {
		if(n==null||n.next==null)
			return n;
		//同时记录相邻的三个节点的位置
		Node prev = null, next = n.next;
		while(next.next!=null) {
			//逐个改变节点的指向关系
			n.next = prev;
			//头结点时不处理
			if(prev!=null)
				prev.prev = n;
			//依次后移
			prev = n;
			n = next;
			next = next.next;
		}
		//处理最后两个节点之间的顺序
		if(prev!=null)
			prev.prev = n;
		
		n.next = prev;
		n.prev = next;
		
		next.next = n;
		next.prev = null;
		return next;
	}
}
