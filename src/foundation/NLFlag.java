package foundation;

public class NLFlag {
	public static void main(String[] args) {
		int[] arr = {9,1,2,8,4,5,2,8,4,4};
		int[] arr2 = {9,1,2,8,4,5,2,8,4,4};
		validate(arr, 4);
		partiton(arr2, 4);
		FoundSort.show(arr, false);
		FoundSort.show(arr2, false);
	}
	
	public static void partiton(int[] arr,int num) {
		int less = -1 , more = arr.length;
		boolean stay = false;
		for(int current = 0 ; current < arr.length ; current ++) {
			//情况三
			if(arr[current]>num) {
				more--;
				FoundSort.swap(arr, current, more);
				stay = true;
			//情况一
			}else if(arr[current]<num) {
				less++;
				FoundSort.swap(arr, less, current);
			}
			//情况三的current指针移动逻辑
			if(stay) {
				current--;
				stay = false;
			}
			//循环终止条件
			if(current==more-1)
				break;
		}
	}
	
	public static void validate(int[] arr,int num) {
		if(arr==null||arr.length<2)
			return;
		int[] small = new int[arr.length];
		int[] big = new int[arr.length];
		int s = 0 , b = 0;
		for(int e : arr) {
			if(e>num)
				big[b++] = e;
			if(e<num)
				small[s++] = e;
		}
		int equal = arr.length - s - b;
		for(int i = 0 ; i < arr.length ; i++) {
			if(s-->0) {
				arr[i] = small[s];
			}else if(equal-->0) {
				arr[i] = num;
			}else {
				arr[i] = big[--b];
			}
		}
	}
}
