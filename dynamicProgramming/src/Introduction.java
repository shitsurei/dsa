import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author 张国荣
 * 多项式：p（知道怎么算）和np（知道怎么试）
 * 一：如果一个问题可以找到一个能在多项式的时间（O(1),O(log(n)),O(n^a)等）里解决它的算法，那么这个问题就属于P问题
 * 二：NP问题是指可以在多项式的时间里验证一个解的问题，即可以在多项式的时间里猜出一个解的问题。
 * 验证一个解只需要O(n)的时间复杂度，也就是说我可以花O(n)的时间把我猜的路径的长度加出来。那么，只要我RP好，猜得准，我一定能在多项式的时间里解决这个问题。
 * 
 * 暴力递归：
 * 1.把问题转化为规模缩小了的同类问题的子问题
 * 2.有明确的不需要继续进行递归的条件（base case）
 * 3.有当得到了子问题的结果之后的决策过程4，不记录每一个子问题的解
 * 
 * 动态规划：
 * 1.为了优化暴力递归
 * 2.将每一个子问题的解记录下来，避免重复计算
 * 3.把暴力递归的过程，抽象成状态表达
 * 4.并且存在化简状态表达，使其更加简洁的可能
 *
 */

public class Introduction {
	public static void main(String[] args) {
		/*Hanoi tower = new Hanoi(7);
		tower.result();*/
		//String[] subs = allSubSquence2("abc");
		/*List<String> subs = allSquence("abcd");
		for(String e : subs)
			System.out.println(e);*/
		System.out.println(cow(6));
	}
	/**
	 * 求n的阶乘
	 * @param n
	 * @return
	 */
	public static int factorial(int n) {
		if(n<1)
			throw new NumberFormatException("1以下没有阶乘");
		if(n==1)
			return 1;
		return n*factorial(n-1);
	}
	/**
	 * 汉诺塔问题
	 * 一个n规模的问题
	 * T(N)=2T(N-1)+1
	 * 可化简为等比数列，最终时间复杂度为2^N
	 * @author 张国荣
	 *
	 */
	public static class Hanoi{
		//三条轴
		int[] left;
		int[] middle;
		int[] right;
		int length;
		public Hanoi(int num) {
			length = num;
			middle = new int[length];
			right = new int[length];
			left = new int[length];
			for (int i = 0; i < left.length; i++) {
				left[i] = i + 1;
			}
		}
		/**
		 * 汉诺塔的解法
		 */
		public void result() {
			process(left, right, middle, length);
		}
		
		/**
		 * 递归求解汉诺塔的移动步骤
		 * @param from	从哪一个轴上取
		 * @param to	放到哪个轴的顶部
		 * @param help	辅助轴
		 * @param index	要移动的圆盘号
		 */
		public void process(int[] from,int[] to,int[] help,int index) {
			//base case
			if(index==1) {
				move(from, to, index);
			}else {
				//先递归移动n-1个从初始轴到辅助轴
				process(from, help, to, index - 1);
				//再移动第n个从初始轴到目的地轴
				move(from, to, index);
				//最后递归移动n-1个从辅助轴到目的地轴
				process(help, to, from, index - 1);
			}
		}
		/**
		 * 移动圆盘操作
		 * @param from
		 * @param to
		 * @param index
		 */
		public void move(int[] from,int[] to,int index) {
			to[index-1] = index;
			from[index-1] = 0;
			show();
		}
		/**
		 * 打印当前的三根轴的状态，0表示空，大于0表示第几号圆盘在该轴上
		 */
		public void show() {
			System.out.println("------------------");
			for (int i = 0; i < left.length; i++) {
				System.out.print(left[i]+" ");
			}
			System.out.println();
			for (int i = 0; i < middle.length; i++) {
				System.out.print(middle[i]+" ");
			}
			System.out.println();
			for (int i = 0; i < right.length; i++) {
				System.out.print(right[i]+" ");
			}
			System.out.println();
			System.out.println("------------------");
		}
	}

	/**
	 * 打印一个字符串的所有子序列（非递归）
	 * @param str
	 * @return
	 */
	public static String[] allSubSquence(String str) {
		char[] letters = str.toCharArray();
		if(letters.length==0)
			return new String[] {"*"};
		int max = 1 << letters.length;
		String[] subSquence = new String[max];
		for (int i = 0; i < subSquence.length; i++) {
			StringBuilder sub = new StringBuilder();
			int cur = i;
			for (int j = 0; j < letters.length; j++) {
				if(cur%2==1)
					sub.append(letters[j]);
				cur/=2;
			}
			if(sub.length()==0)
				subSquence[i] = "*";
			else
				subSquence[i] = sub.toString();
		}
		return subSquence;
	}
	
	/**
	 * 打印一个字符串的所有子序列（递归）
	 * @param str
	 * @return
	 */
	public static List<String> allSubSquence2(String str) {
		char[] letters = str.toCharArray();
		//设置收集器用于递归的过程中收集最终返回值
		List<String> subs = new ArrayList<>();
		//进入递归，初始值为空串，索引为0
		subSquence(letters, 0, subs, "");
		return subs;
	}
	
	public static void subSquence(char[] letters, int index,List<String> subs,String sub) {
		//当索引来到字符数组的最后一位时可以进行收集
		if(index==letters.length) {
			subs.add(sub);
			return;
		}else {
			//否则递归分支，对每一个字符都进行拼串和不拼串的操作，同时索引加一
			subSquence(letters, index+1, subs, sub); 
			subSquence(letters, index+1, subs, sub+letters[index]); 
		}
	}
	
	/**
	 * 全排列（非递归）
	 */
	public static List<String> allSquence(String str) {
		char[] letters = str.toCharArray();
		if (letters.length==0) {
			return null;
		}
		List<String> squs = new LinkedList<>();
		//外层循环将每个位置的字母都进行一次从头到尾的冒泡
		for (int j = 0; j < letters.length; j++) {
			//内层循环每次将首个字母逐个冒泡到最后一位
			for (int i = 0; i < letters.length-1; i++) {
				//每次冒泡形成一个全排列顺序
				squs.add(new String(letters));
				char temp = letters[i];
				letters[i] = letters[i+1];
				letters[i+1] = temp;
			}
		}
		return squs;
	}
	
	/**
	 * 全排列（非递归）
	 */
	public static List<String> allSequence2(String str){
		return null;
	}
	
	
	/**
	 * 顺序计算，斐波那契
	 * @param year
	 * @return
	 */
	public static int cow(int year) {
		if(year<4)
			return year;
		return cow(year-1) + cow(year-3);
	}
}
