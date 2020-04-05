package foundation;

/**
 * 堆排序
 * 堆结构的优点在于调成代价是O(log(N))，适用于实时的对数据进行调整统计
 * 例题：数据流求实时求中位数，用一个大根堆和一个小根堆解决，实时保证两个堆得平衡
 *
 * @author 张国荣
 * 完全二叉树的数组表示： 左孩子：2*i+1 右孩子：2*i+2 父节点：(i-1)/2
 * 堆：
 * 大根堆：整棵树的最大值位于根节点
 * 小根堆：整棵树的最小值位于根节点
 */
public class HeapSort {
    public static void main(String[] args) {
        int[] arr = {8, 2, 4, 3, 6, 7, 3, 6, 5};
        heapSort(arr);
        FoundSort.show(arr, false);
    }

    public static void heapSort(int[] arr) {
        if (arr == null || arr.length < 2)
            return;
        //建堆过程
        //时间复杂度：O(log(1))+O(log(2))+O(log(3))+……+O(log(N-1))收敛于O(N)
        for (int i = 0; i < arr.length; i++) {
            heapInsert(arr, i);
        }
        //减堆过程
        int heapSize = arr.length;
        while (heapSize-- > 0) {
            FoundSort.swap(arr, 0, heapSize);
            heapify(arr, 0, heapSize);
        }
    }

    /**
     * 建立大根堆，将新加入的节点逐步向上调整，形成大根堆
     *
     * @param arr
     * @param index
     */
    public static void heapInsert(int[] arr, int index) {
        // 兼顾根节点和自身的比较
        while (arr[index] > arr[(index - 1) / 2]) {
            FoundSort.swap(arr, index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }

    /**
     * 已形成的大根堆在某个节点发生变化后如何进行调整，调整节点逐步下沉
     *
     * @param arr
     * @param index
     * @param heapSize 要调整的堆的范围
     */
    public static void heapify(int[] arr, int index, int heapSize) {
        while ((index * 2 + 1) < heapSize) {
            int leftChild = index * 2 + 1;
            int rightChild = index * 2 + 2;
            int large = rightChild < heapSize && arr[rightChild] > arr[leftChild] ? rightChild : leftChild;
            large = arr[index] < arr[large] ? large : index;
            FoundSort.swap(arr, index, large);
            //当前数比左右孩子都大，不用再进行交换
            if (index == large)
                break;
            index = large;
        }


        //以下代码当左孩子未越界且大于父节点时，无法进行调整
		/*while((index*2+2)<heapSize) {
			index = arr[index*2+2]>arr[index*2+1]?index*2+2:index*2+1;
			FoundSort.swap(arr, index, (index-1)/2);
		}*/
    }
}
