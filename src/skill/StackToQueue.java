package skill;

import foundation.Stack;
/**
 * 用两个栈实现一个队列：一个存数据，一个出数据，倒数据有两个条件：1.出数据栈必须为空2.存数据栈必须一次性倒完（满足两个条件可以发生在任意时刻）
 * @author 张国荣
 *
 */
public class StackToQueue {
	private Stack en,de;
	public static void main(String[] args) {
		StackToQueue stq = new StackToQueue();
		stq.enQueue(1);
		stq.enQueue(2);
		System.out.println(stq.deQueue());
		stq.enQueue(3);
		stq.enQueue(4);
		stq.enQueue(9);
		stq.enQueue(4);
		stq.enQueue(9);
		stq.enQueue(4);
		System.out.println(stq.deQueue());
		System.out.println(stq.deQueue());
		System.out.println(stq.deQueue());
		System.out.println(stq.deQueue());
		System.out.println(stq.deQueue());
		System.out.println(stq.deQueue());
		System.out.println(stq.deQueue());
	}
	
	public StackToQueue() {
		en = new Stack(5);
		de = new Stack(5);
	}
	
	public StackToQueue(int num) {
		en = new Stack(num/2);
		de = new Stack(num/2);
	}
	
	public void enQueue(int elem) {
		//最大程度使用两个空间的容量
		if(en.len()==5)
			transfer();
		en.push(elem);
	}
	
	public int deQueue() {
		//出数据栈必须为空
		if(de.len()==0)
			transfer();
		return de.pop();
	}
	
	public int len() {
		return en.len()+de.len();
	}
	
	public void transfer() {
		//出数据栈必须为空
		if(de.len()!=0)
			return;
		//存数据栈必须一次性倒完
		while(en.len()>0) {
			de.push(en.pop());
		}
	}
}
