package foundation;

/**
 * 小和问题，运用归并排序的思路提高
 * 
 * @author 张国荣
 *
 */
public class SmallSum {
	public static void main(String[] args) {
		int[] arr = { 1, 2, 3, 4 , 5, 4, 3, 2, 1};
		System.out.println(validate(arr));
		System.out.println(smallSum(arr));
		FoundSort.show(arr, false);
	}

	public static int smallSum(int[] arr) {
		if (arr == null || arr.length < 2)
			return 0;
		else
			return divide(arr, 0, arr.length - 1);
	}

	public static int divide(int[] arr, int left, int right) {
		if (left == right)
			return 0;
		else {
			int middle = left + ((right - left) >> 1);
			return divide(arr, left, middle) + divide(arr, middle + 1, right) + merge(arr, left, right, middle);
		}
	}

	public static int merge(int[] arr, int left, int right, int middle) {
		int[] help = new int[right - left + 1];
		int i = 0;
		int p = left;
		int q = middle + 1;
		int sum = 0;
		while (p <= middle && q <= right) {
			//榨取每个小组中的所有小和
			sum += arr[p] < arr[q] ? (right - q + 1) * arr[p] : 0;
			//先榨取后拷贝（指针后移）
			help[i++] = arr[p] < arr[q] ? arr[p++] : arr[q++];
		}
		while (p <= middle)
			help[i++] = arr[p++];
		while (q <= right)
			help[i++] = arr[q++];
		for (i = 0; i < help.length; i++) {
			arr[left + i] = help[i];
		}
		return sum;
	}

	public static int validate(int[] arr) {
		int sum = 0;
		if(arr!=null&&arr.length>1) {
			for (int i = 1; i < arr.length; i++) {
				for (int j = 0; j < i; j++) {
					if (arr[j] < arr[i])
						sum += arr[j];
				}
			}
		}
		return sum;
	}
}
