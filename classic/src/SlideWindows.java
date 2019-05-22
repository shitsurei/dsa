import java.util.LinkedList;

/**
 * 双端队列结构（双向链表） 
 * 1.实时记录窗口中的最值 
 * 2.进队列永远是从队尾进入，出队列永远从队头出去 
 * 3.每个数都只会进队列一次，出队列一次，时间复杂度O(N)
 * 4.不仅要记录进入窗口的数，还要记录相应的下标，以便随时处理掉过期的数 
 * 5.在数组结构中只需记录下表，用下标寻址即可
 * 
 * @author 张国荣
 *
 */
public class SlideWindows {
	public static void main(String[] args) {
		
	}

	/**
	 * 获取窗口变化过程中的最大值数组
	 * 
	 * @param arr 原数组，依次进窗口出窗口
	 * @param w   窗口大小
	 * @return 每个形成的窗口内最大值组成的数组
	 */
	public static int[] getMaxWindow(int[] arr, int w) {
		if (arr.length < w || arr.length == 0 || w == 0)
			return null;
		// 生成窗口的个数=原数组总长度-窗口大小+1
		int[] maxArr = new int[arr.length - w + 1];
		int index = 0;
		// java中LinkedList的底层结构即为一个纯净的双端列表
		LinkedList<Integer> qMax = new LinkedList<>();
		// 依次遍历原数组
		for (int i = 0; i < arr.length; i++) {
			// 队列中不为空且当前遍历的数大于等于队列尾部的数时
			while (!qMax.isEmpty() && arr[qMax.peekLast()] <= arr[i])
				// 队列尾部的数不断弹出，直到队列为空或当前数小于队列尾部的数
				qMax.pollLast();
			// 当前数进队尾
			qMax.addLast(i);
			// 当队头的数因窗口向后移动而过期时队头的数移出队列，但不一定每一次都触发，过期的数也有可能在之前就已经弹出
			if (qMax.peekFirst() == i - w)
				qMax.pollFirst();
			// 当窗口已经形成时每次记录一个窗口中的最大值
			if (i >= w - 1)
				maxArr[index++] = arr[qMax.peekFirst()];
		}
		return maxArr;
	}

	/**
	 * 获取一个大数组中所有子数组中满足最大最小值之差小于等于num的个数
	 * 暴力遍历解决需要O(N^3)：子数组的个数为等差数列求和，时间复杂度O(N^2)，其中每一个子数组需要验证是否满足最大最小值之差条件，时间复杂度O(N)
	 * 利用双端队列可做到时间复杂度：O(N)
	 * 推论1：如果一个子数组达标，其内部的任意个子数组达标（内部数组的最大值只会变小，最小值只会变大）
	 * 推论2：如果一个子数组不达标，其延伸出来外部的任意个子数组不达标（外部数组的最大值只会变大，最小值只会变小）
	 * @param arr 原数组
	 * @param num 衡量子数组是否达标的数
	 * @return 满足调节的子数组个数
	 */
	public static int getSubArrNum(int[] arr, int num) {
		if (arr.length == 0 || num < 0)
			return 0;
		//创建两个双端队列记录当前子数组的最大值和最小值
		LinkedList<Integer> qMax = new LinkedList<>();
		LinkedList<Integer> qMin = new LinkedList<>();
		//用变量记录子数组的起始和终止位置的下标
		int start = 0, end = 0, res = 0;
		//外层循环用于遍历每一个下标作为开始的子数组是否符合条件
		while (start < arr.length) {
			//内层循环用于遍历每个下标作为终止的子数组是否符合条件
			while (end < arr.length) {
				//实时更新当前子数组的最大最小值
				while (!qMin.isEmpty() && arr[qMin.peekLast()] >= arr[end])
					qMin.pollLast();
				qMin.addLast(end);
				while (!qMax.isEmpty() && arr[qMax.peekLast()] <= arr[end])
					qMax.pollLast();
				qMax.addLast(end);
				//判断当前子数组是否满足条件,如果满足尾部向后延伸继续尝试;如果不满足终止内部循环,队头元素后移
				if (arr[qMax.peekFirst()] - arr[qMin.peekFirst()] > num)
					break;
				end++;
			}
			//判断当前子数组的最大最小值是否位于数组头部,如果是则需要随着头部后移将原本位于双端队列中的最大最小值移除
			if (qMax.peekFirst() == start)
				qMax.pollFirst();
			if (qMin.peekFirst() == start)
				qMin.pollFirst();
			//以当前队头元素为初始元素的满足条件的子数组个数(即start开头end结尾的所有子数组个数)
			res += (end - start);
			//队头后移
			start++;
		}
		return res;
	}
	
	/**
	 * 子数组个数求解对数器
	 * @param arr
	 * @param num
	 * @return
	 */
	public static int validateSubArr(int[] arr,int num) {
		int res = 0;
		for (int i = 0; i < arr.length; i++) {
			for (int j = i; j < arr.length; j++) {
				if(validate(arr, i, j, num))
					res++;
			}
		}
		return res;
	}
	
	public static boolean validate(int[] arr, int start,int end,int num) {
		int min = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;
		for (int i = start; i <= end; i++) {
			min = Math.min(min, arr[i]);
			max = Math.max(max, arr[i]);
		}
		return max-min<=num;
	}
}
