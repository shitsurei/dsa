package skill;

import foundation.Queue;

/**
 * 用两个队列实现一个栈：用一个存数据，另一个倒数据，倒到原队列里就剩一个时返回；为了方便一次“弹栈”后交换两队列的引用
 * @author 张国荣
 *
 */
public class QueueToStack {
	private Queue queue,trans;
	public static void main(String[] args) {
		QueueToStack qts = new QueueToStack();
		qts.push(1);
		qts.push(2);
		System.out.println(qts.pop());
		qts.push(3);
		qts.push(4);
		qts.push(5);
		qts.push(6);
		System.out.println(qts.pop());
		System.out.println(qts.pop());
		System.out.println(qts.pop());
		System.out.println(qts.pop());
		System.out.println(qts.pop());
	}
	
	public QueueToStack() {
		queue = new Queue();
		trans = new Queue();
	}
	
	public QueueToStack(int num) {
		queue = new Queue(num/2);
		trans = new Queue(num/2);
	}
	
	public int pop() {
		transfer();
		int num = queue.deQueue();
		
		Queue temp;
		temp = queue;
		queue = trans;
		trans = temp;
		
		return num;
	}
	
	public void push(int elem) {
		queue.inQueue(elem);
	}
	
	public void transfer() {
		while(queue.len()>1) {
			trans.inQueue(queue.deQueue());
		}
	}
	
	public int len() {
		return queue.len()+queue.deQueue();
	}
}
