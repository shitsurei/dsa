package foundation;

import java.util.Scanner;

/**
 * 	固定数组实现队列：循环队列
 * @author 张国荣
 *	用两个队列实现一个栈：用一个存数据，另一个倒数据，倒到原队列里就剩一个时返回；为了方便一次“弹栈”后交换两队列的引用
 */
public class Queue {
	
	int[] arr;
	int head,tail,content;
	
	//单元测试用
	private static Scanner sc;
	public static void main(String[] args) {
		sc = new Scanner(System.in);
		System.out.println("input queue content length");
		Queue q = new Queue(Integer.parseInt(sc.nextLine()));
		System.out.println("init complete");
		while(true) {
			System.out.println("================");
			System.out.println("input in to inQueue");
			System.out.println("input de to deQueue head elem");
			System.out.println("input len to get length");
			System.out.println("input exit to exit");
			System.out.println("================");
			switch (sc.nextLine()) {
			case "in":
				System.out.println("input elem");
				q.inQueue(Integer.parseInt(sc.nextLine()));
				break;
			case "de":
				System.out.println("deQueue:"+q.deQueue());
				break;
			case "len":
				System.out.println("len="+q.len());
				break;
			case "exit":
				System.exit(0);
				break;

			default:
				System.out.println("wrong input");
				break;
			}
		}
	}
	
	
	/**
	 * 无参构造函数，长度为5
	 */
	public Queue() {
		content = 5;
		arr = new int[content];
		head = tail = 0;
	}
	
	public Queue(int num) {
		//实际长度要减1，空出一个索引位置用于区分空队列满队列
		content = num+1;
		arr = new int[content];
		head = tail = 0;
	}
	
	public void inQueue(int num) {
		if(((tail+1)%content)==head)
			error();
		else {
			arr[tail] = num;
			tail = (tail+1)%content;
		}
	}
	
	public int deQueue() {
		if(head==tail) {
			error();
			return 0;
		}else {
			int num = arr[head];
			head = (head+1)%content;
			return num;
		}
	}
	
	public int len() {
		return (tail+content-head)%content;
	}
	
	public void error() {
		System.out.println("queue Overflow");
	}
}
