package foundation;

import java.util.Scanner;

/**
 * 	数组实现大小固定的栈结构
 * @author 张国荣
 */
public class Stack {
	public int[] arr;
	public int[] mins;
	public int index;
	
	
	//单元测试用
	private static Scanner sc;
	public static void main(String[] args) {
		sc = new Scanner(System.in);
		System.out.println("input stack content length");
		int content = Integer.parseInt(sc.nextLine());
		Stack s = new Stack(content);
		System.out.println("init complete");
		while(true) {
			System.out.println("================");
			System.out.println("input push to push");
			System.out.println("input pop to pop top elem");
			System.out.println("input len to get length");
			System.out.println("input min to get minElem");
			System.out.println("input exit to exit");
			System.out.println("================");
			switch (sc.nextLine()) {
			case "push":
				System.out.println("input elem");
				s.push(Integer.parseInt(sc.nextLine()));
				break;
			case "pop":
				System.out.println("pop:"+s.pop());
				break;
			case "len":
				System.out.println("len="+s.len());
				break;
			case "min":
				System.out.println("minElem:"+s.minElem());
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
	public Stack() {
		arr = new int[5];
		mins = new int[5];
		index = -1;
	}
	
	public Stack(int num) {
		arr = new int[num];
		mins = new int[num];
		index = -1;
	}
	
	/**
	 * 返回栈中最小元素，时间复杂度O(1)
	 * @return
	 */
	private int minElem() {
		if(index==-1) {
			error();
			return 0;
		}
		else
			return mins[index];
	}

	public void push(int num) {
		if(index==arr.length-1)
			error();
		else {
			arr[++index] = num;
			if(index<1||num<mins[index-1])
				mins[index] = num;
			else
				mins[index] = mins[index-1];
		}
	}
	
	public int pop() {
		if(index==-1) {
			error();
			return 0;
		}
		else
			return arr[index--];
	}
	
	public int peek() {
		if(index==-1) {
			error();
			return 0;
		}
		else
			return arr[index];
	}
	
	public int len() {
		return index+1;
	}
	
	public void error() {
		System.out.println("stack Overflow");
	}
}
