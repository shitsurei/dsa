package foundation;
/**
 * 涉及递归函数，其时间复杂度为T(N)=aO(n/b)+O(n^d)
 * 其中a为2，b为2，d为1，log(b,a)=1=d   ---->   该算法的时间复杂度为O(N)=N*log(N)
 * @author 张国荣
 * 归并排序内部缓存法，可以使其空间复杂度降低至O(1)
 *
 */
public class MergeSort {
	public static void main(String[] args) {
		int[] arr = {5,1,3,2,8,7};
		mergeSort(arr);
		FoundSort.show(arr, false);
	}
	/**
	 * 归并排序算法
	 * @param arr
	 */
	public static void mergeSort(int[] arr) {
		if(arr==null||arr.length<2)
			return;
		sortProcess(arr, 0, arr.length-1);
	}
	/**
	 * 划分函数，二分
	 * @param arr
	 * @param left
	 * @param right
	 */
	public static void sortProcess(int[] arr,int left,int right) {
		if(left==right)
			return;
		int middle = (left+right)/2;
		sortProcess(arr, left, middle);
		sortProcess(arr, middle+1, right);
		merge(arr, left, middle, right);
	}
	/**
	 * 归并函数，复杂度为O(N)
	 * @param arr
	 * @param left
	 * @param middle
	 * @param right
	 */
	public static void merge(int[] arr, int left, int middle , int right) {
		int[] help = new int[right-left+1];
		int p = left, q = middle+1 , i = 0;
		//比较拷贝
		while(p<=middle&&q<=right) {
			//打印逆序对
			/*if(arr[p]>arr[q]) {
				for (int j = p; j <= middle; j++) {
					System.out.println("逆序对["+arr[j]+","+arr[q]+"]");
				}
			}*/
			help[i++] = arr[p] < arr[q] ? arr[p++] : arr[q++];
		}
		//补全拷贝
		//右子部分有剩余元素未拷贝结束
		while(q<=right)
			help[i++] = arr[q++];
		//左子部分有剩余元素未拷贝结束
		while(p<=middle)
			help[i++] = arr[p++];
		//拷回原序列
		for(i = 0 ; i < help.length ; i++)
			arr[left++] = help[i];
	}
}
