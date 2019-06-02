package foundation;

import java.util.Arrays;

public class AlgorithmDevice {
	public static void main(String[] args) {
		int textTime = 500000;
		int maxSize = 20;
		int maxValue = 50;
		boolean ac = true;
		for(int i = 0 ; i < textTime ; i++) {
			int[] ran = geneRandomArray(maxSize, maxValue,false,false);
			int[] arr1 = ran.clone();
			int[] arr2 = ran.clone();
			
			//验证切金条贪心策略
			/*int cost1 = CutGold.minCost(arr1);
			int cost2 = CutGold.minCost(arr2);
			if(cost1!=cost2) {
				ac = false;
				break;
			}*/
			
			//验证子数组个数问题
			/*int num = (int)(Math.random()*(maxValue+1));
			int num1 = SlideWindows.getSubArrNum(arr1, num);
			int num2 = SlideWindows.validateSubArr(arr2, num);
			if(num1!=num2) {
				ac = false;
				break;
			}*/
			
			//验证链表荷兰国旗问题
			/*Node n1 = Generate.generateSinglyLink(arr1,arr1.length);
			Node n2 = Generate.generateSinglyLink(arr2,arr2.length);
			int flag = (int)(Math.random()*(maxValue+1))-(int)(Math.random()*(maxValue+1));
			n1 = ListFlag.partition(n1, flag);
			n2 = ListFlag.validate(n2, flag);
			ac = validateLinkList(n1, n2);
			
			if(!ac) {
				System.out.println(flag);
				FoundSort.show(ran, false);
				Generate.printSinglyLinkList(n1);
				Generate.printSinglyLinkList(n2);
				break;
			}*/
			
			//验证小和是否一致
			/*if(answer1!=answer2) {
				ac = false;
				System.out.println(answer1+"---"+answer2);
				break;
			}*/
			
			Arrays.sort(arr1);
			RadixSort.radix(arr2);
			
			//验证数组是否完全相等
			if(!validate(arr1, arr2)) {
				ac = false;
				FoundSort.show(ran, false);
				FoundSort.show(arr1, false);
				FoundSort.show(arr2, false);
				break;
			}
		}
		if(ac)
			System.out.println("Nice!");
		else
			System.out.println("Fucking wrong!");
	}
	/**
	 * 生成随机数组
	 * @param maxSize	数组最大长度
	 * @param maxValue	数组元素最大值
	 * @param hasMinus	元素是否包含负数
	 * @param withEmpty	数组是否可以为空
	 * @return
	 */
	public static int[] geneRandomArray(int maxSize,int maxValue,boolean hasMinus,boolean withEmpty) {
		int[] arr;
		if(withEmpty)
			arr = new int[(int)(Math.random()*(maxSize+1))];
		else
			arr = new int[(int) (Math.random() * (maxSize - 1 + 1)) + 1];
		
		for(int i = 0 ; i < arr.length ; i++) {
			if (hasMinus)
				arr[i] = (int)(Math.random()*(maxValue+1))-(int)(Math.random()*(maxValue+1));
			else
				arr[i] = (int)(Math.random()*(maxValue+1));
		}
		return arr;
	}
	/**
	 * 验证两个数组是否完全相等
	 * @param arr1
	 * @param arr2
	 * @return
	 */
	public static boolean validate(int[] arr1, int[] arr2) {
		if((arr1==null&&arr2!=null)||(arr2==null&&arr1!=null))
			return false;
		if(arr1==null&&arr2==null)
			return true;
		if(arr1.length!=arr2.length)
			return false;
		boolean equal = true;
		for(int i = 0 ; i < arr1.length ; i++) {
			if(arr1[i]!=arr2[i]) {
				equal = false;
				break;
			}
		}
		return equal;
	}
	/**
	 * 	验证荷兰国旗问题中两个数组是否符合要求
	 * @param arr1
	 * @param arr2
	 * @param num
	 * @return
	 */
	public static boolean validateFlag(int[] arr1, int[] arr2,int num) {
		if((arr1==null&&arr2!=null)||(arr2==null&&arr1!=null))
			return false;
		if(arr1==null&&arr2==null)
			return true;
		if(arr1.length<2&&arr2.length<2)
			return true;
		if(arr1.length!=arr2.length)
			return false;
		boolean success = true;
		//state用于验证初始区域为大于，等于或小于给定数的部分
		int state;
		if(arr1[0]<num&&arr2[0]<num)
			state = 1;
		else if(arr1[0]==num&&arr2[0]==num)
			state = 2;
		else if(arr1[0]>num&&arr2[0]>num)
			state = 3;
		else
			return false;
		for(int i = 0 ; i < arr1.length ; i++) {
			switch (state) {
			case 1:
				if(arr1[i]<num&&arr2[i]<num)
					continue;
				else if(arr1[i]==num&&arr2[i]==num)
					state = 2;
				else if(arr1[i]>num&&arr2[i]>num)
					state = 3;
				else
					success = false;
				break;
			case 2:
				if(state==1||arr1[i]<num||arr2[i]<num)
					success = false;
				else if(arr1[i]>num&&arr2[i]>num)
					state = 3;
				else if(arr1[i]==num&&arr2[i]==num)
					continue;
				else
					success = false;
				break;
			case 3:
				if(state==1||state==2||arr1[i]<=num||arr2[i]<=num)
					success = false;
				break;
			}
		}
		return success;
	}
	/**
	 * 	验证两个链表是否完全相等
	 * @param n1
	 * @param n2
	 * @return
	 */
	/*public static boolean validateLinkList(Node n1,Node n2) {
		if(n1==null&&n2==null)
			return true;
		boolean equal = true;
		while(n1!=null&&n2!=null) {
			if(n1.value!=n2.value) {
				equal = false;
				break;
			}
			n1 = n1.next;
			n2 = n2.next;
		}
		if((n1==null&&n2!=null)||(n1!=null&&n2==null))
			equal = false;
		return equal;
	}*/
}
