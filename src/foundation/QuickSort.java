package foundation;

/**
 * 快排：常数项较低的O(N*log(N))时间复杂度排序算法，空间（用于记录左右的断点位置）复杂度长期期望为O(log(N))，最差为O(N)
 * 经典快排：一次只搞定一个数字的位置
 * 问题：数据趋于有序时，等于区域会靠近边界，时间复杂度退化到O(N^2)
 * 改进快排：使用荷兰国旗问题进行划分
 * @author 张国荣
 * 快排可以实现稳定，看01 stable sort
 * 01标准，快排中的划分、奇偶都是，很难做到稳定
 */
public class QuickSort {
	public static void main(String[] args) {
		int[] arr = {9,1,2,8,4,5,2,8,4,4};
		quickSort2(arr);
		FoundSort.show(arr, false);
	}
	
	public static void quickSort(int[] arr) {
		if(arr==null||arr.length<2)
			return;
		quickSort(arr, 0, arr.length-1);
	}
	public static void quickSort(int[] arr,int left,int right) {
		if(left<right) {
			//随机快排：划分值随机选择，其复杂度是一个长期期望的概率O(N*log(N))
			FoundSort.swap(arr, left+(int)(Math.random()*(right-left+1)), right);
			int[] equal = partition(arr, left, right);
			quickSort(arr, left, equal[0]-1);
			quickSort(arr, equal[1]+1, right);
		}
	}
	/**
	 * 默认以最后一个数作为划分标准
	 * @param arr
	 * @param left
	 * @param right
	 * @return
	 */
	public static int[] partition(int[] arr,int left,int right) {
		int less = left - 1;
		//划分初始，默认的区域为arr【right】之前的所有范围，即划分完后整个区域分为四个部分
		//【小于arr[right]】【等于arr[right]】【大于arr[right]】【arr[right]】
		int more = right;
		while(left<more) {
			if(arr[left]<arr[right])
				FoundSort.swap(arr, left++, ++less);
			else if(arr[left]>arr[right])
				FoundSort.swap(arr, left, --more);
			else
				left++;
		}
		//收尾工作，将最后一位arr[right]纳入整个序列
		FoundSort.swap(arr, more, right);
		return new int[] {less + 1,more};
	}
	
	
	
	/**
	 * 	经典快排
	 * @param arr
	 */
	public static void quickSort2(int[] arr) {
		if(arr==null||arr.length<2)
			return;
		quickSort(arr, 0, arr.length-1);
	}
	public static void quickSort2(int[] arr,int left,int right) {
		if(left<right) {
			int pivot = partition2(arr, left, right);
			quickSort(arr, left, pivot);
			quickSort(arr, pivot+1, right);
		}
	}
	
	public static int partition2(int[] arr,int left,int right) {
		int current = left;
		int more = right;
		while(current < more) {
			if(arr[current] > arr[right])
				FoundSort.swap(arr, current, --more);
			else
				current++;
		}
		FoundSort.swap(arr, more, right);
		return more;
	}
}
