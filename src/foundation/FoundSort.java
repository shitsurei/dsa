package foundation;

public class FoundSort {
	public static enum Sort {BUBBLE,SELECT,INSERT};
	public static void main(String[] args) {
		int[] arr = {1,5,3,2,7,4,6,3};
		sort(arr,Sort.SELECT);
	}
	public static void sort(int[] arr, Sort s) {
		System.out.print("origin data:\t");
		show(arr,false);
		if(arr!=null&&arr.length>1)
			switch (s) {
			case BUBBLE:
				bubble(arr);
				break;
			case SELECT:
				select(arr);
				break;
			case INSERT:
				insert(arr);
				break;
			default:
				break;
			}
		System.out.print("sorted data:\t");
		show(arr,false);
	}
	/**
	 * 冒泡排序：大数向后冒泡
	 * @param arr
	 */
	public static void bubble(int[] arr) {
		for(int i = arr.length; i > 0 ; i--) {
			for(int j = 0;  j < i-1 ; j++) {
				if(arr[j]>arr[j+1])
					swap(arr, j, j+1);
			}
			//show(arr,true);
		}
	}
	/**
	 * 选择排序：选小数往前放
	 * @param arr
	 */
	public static void select(int[] arr) {
		for(int i = 0 ; i < arr.length ; i++) {
			int min_index = i;
			for(int j = i+1 ; j < arr.length ; j++) {
				if(arr[j]<arr[min_index])
					min_index = j;
			}
			if(min_index!=i)
				swap(arr, i, min_index);
			show(arr,true);
		}
	}
	/**
	 * 插入排序：从前往后选数字顺序插入
	 * @param arr
	 */
	public static void insert(int[] arr) {
		for(int i = 1 ; i < arr.length ; i++) {
			int index = i;
			for(int j = 0 ; j < i ; j++) {
				if(arr[i]<arr[j]) {
					index = j;
					break;
				}
			}
			if(index!=i) {
				int temp = arr[i];
				for(int j = i ; j > index ; j--)
					swap(arr, j-1, j);
				arr[index] = temp;
			}
			//show(arr,true);
		}
	}
	/**
	 * 交换函数
	 * @param arr
	 * @param i
	 * @param j
	 */
	public static void swap(int[] arr,int i, int j) {
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}
	/**
	 * 输出数组
	 * @param arr
	 */
	public static void show(int[] arr,boolean sorting) {
		if(sorting)
			System.out.print("sorting data:\t");
		if(arr==null)
			System.out.println("no numbers");
		else {
			for(int e : arr)
				System.out.print(e+" ");
			System.out.println();
		}
	}
}
