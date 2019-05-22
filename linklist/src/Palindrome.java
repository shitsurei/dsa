/**
 * @author 张国荣
 *
 *	链表数据下的荷兰国旗问题
 */
public class Palindrome {
	public static void main(String[] args) {
		Node n1 = Generate.generateSinglyLink(new int[] {1,2,3,6,2,1},6);
		Node n2 = Generate.generateSinglyLink(new int[] {0,1,2,6,2,1,0},7);
		//printCommon(n1, n2);
		System.out.println(isPalindrome(n1));
		System.out.println(isPalindrome(n2));
	}
	/**
	 * 	打印两个有序链表的公共部分
	 * 	方法类似外排的归并过程
	 * @param n1
	 * @param n2
	 */
	public static void printCommon(Node n1,Node n2) {
		while(n1!=null&&n2!=null) {
			if(n1.value<n2.value)
				n1 = n1.next;
			else if(n1.value>n2.value)
				n2 = n2.next;
			else {
				System.out.print(n1.value+" ");
				n1 = n1.next;
				n2 = n2.next;
			}
		}
	}
	/**
	 * 	判断一个链表是否为回文结构
	 *	借助辅助存储空间的做法：遍历时压栈，可以用快指针节省一半的存储
	 *	不借助，需要先修改原始数据项或数据结构，将后半部分逆序，头指针和尾指针一起向中间遍历比较
	 * @param n
	 * @return
	 */
	public static boolean isPalindrome(Node n) {
		if(n==null||n.next==null)
			return true;
		//设置快慢指针,快指针走到表尾时慢指针走到回文结构的中间位置
		Node fast = n,slow = n,reverse;
		while(fast.next!=null&&fast.next.next!=null) {
			fast = fast.next.next;
			slow = slow.next;
		}
		//不论节点个数为奇偶,反转链表的后半段都是从慢指针指向的下一个节点开始
		reverse = Reverse.reverseSinglyLink(slow.next);
		boolean isPalindrome = true;
		fast = n;
		slow = reverse;
		while(fast!=null&&slow!=null) {
			if(fast.value!=slow.value) {
				isPalindrome = false;
				break;
			}
			fast = fast.next;
			slow = slow.next;
		}
		//对改变的原始数据结构进行还原
		reverse = Reverse.reverseSinglyLink(reverse);
		return isPalindrome;
	}
}
