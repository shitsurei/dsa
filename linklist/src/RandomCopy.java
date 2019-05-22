import java.util.HashMap;
import java.util.Map;

public class RandomCopy {
	/**
	 * 	一种特殊的链表节点，包含指向下一个节点的next指针和指向随机节点或null的rand指针
	 * @author 张国荣
	 *
	 */
	public static class RandomNode{
		int value;
		RandomNode next;
		RandomNode rand;
		public RandomNode(int value) {
			this.value = value;
		}
	}
	public static void main(String[] args) {
		
	}
	/**
	 * 	随机节点链表深拷贝函数
	 * 	要求时间复杂度为O(N)，空间复杂度为O(N)
	 * 	使用hash表解决，hash函数的put和get操作都是常数级，但是常数项很大
	 * @return
	 */
	public static RandomNode copy(RandomNode head) {
		Map<RandomNode,RandomNode> map = new HashMap<>();
		RandomNode cur = head;
		while(cur!=null) {
			map.put(cur, new RandomNode(cur.value));
			cur = cur.next;
		}
		cur = head;
		//两个链表之间的节点不发生关系,只通过map串起来
		while(cur!=null) {
			map.get(cur).next = map.get(cur.next);
			map.get(cur).rand = map.get(cur.rand);
			cur = cur.next;
		}
		return map.get(head);
	}
	/**
	 * 	随机节点链表深拷贝函数
	 * 	要求时间复杂度为O(N)，空间复杂度为O(1)
	 * 	用结构关系替代hash表
	 * @return
	 */
	public static RandomNode copy2(RandomNode head) {
		RandomNode cur = head,re;
		//在原链表中每一个节点之后都插入一个相同值的拷贝节点
		while(cur!=null) {
			RandomNode copy = new RandomNode(cur.value);
			copy.next = cur.next;
			cur.next = copy;
			cur = cur.next;
		}
		cur = head;
		//每一个拷贝节点的随机指针就应当指向其前一个节点的随机指针指向节点的后一个节点(即其拷贝节点)
		while(cur!=null) {
			cur.next.rand = cur.rand.next;
		}
		re = cur = head.next;
		//在把原链表中非拷贝节点删除
		while(cur!=null) {
			cur.next = cur.next.next;
			cur = cur.next;
		}
		return re;
	}
}
