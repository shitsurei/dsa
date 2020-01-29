package foundation;

/**
 * 基数排序:改进版，使用指向桶底的结点数组节省遍历链表的时间
 */
public class RadixSort2 {
    public static void main(String[] args) {
        int[] arr = new int[]{996, 354, 227, 56, 12, 335, 443, 2, 8, 2009};
        radix(arr);
        FoundSort.show(arr, false);
    }

    /**
     * 桶内链表节点
     */
    public static class Elem {
        int value;
        Elem next;

        public Elem(int value, Elem next) {
            this.value = value;
            this.next = next;
        }
    }

    /**
     * 桶集合
     */
    public static class Buckets {
        Elem[] heads;
        Elem[] tails;

        public Buckets(int system){
            this.heads = new Elem[system];
            this.tails = new Elem[system];
            for (int i = 0; i < heads.length; i++) {
                heads[i] = new Elem(Integer.MIN_VALUE, null);
                tails[i] = heads[i];
            }
        }
    }

    /**
     * 基数排序主函数
     *
     * @param arr
     */
    public static void radix(int[] arr) {
        //1.求最大值元素的位数
        int maxIndex = maxIndex(arr);
        //2.准备桶，初始化
        Buckets buckets = new Buckets(10);
        //3.按位循环，由低到高
        for (int i = 0; i < maxIndex; i++) {
            //4.遍历序列
            for (int j = 0; j < arr.length; j++) {
                //5.元素入桶（注意找桶的逻辑）
                insertBucket(arr[j], buckets, arr[j] / (int) Math.pow(10, i) % 10 );
            }
            //6.生成新的序列
            generateNewArray(arr, buckets.heads);
            //7.清空桶
            cleanBucket(buckets.heads,buckets.tails);
        }

    }

    /**
     * 将桶中的数据覆盖回原序列
     *
     * @param arr
     * @param buckets
     * @return
     */
    public static void generateNewArray(int[] arr, Elem[] buckets) {
        int index = 0;
        for (int i = 0; i < buckets.length; i++) {
            Elem temp = buckets[i].next;
            while (temp != null) {
                arr[index++] = temp.value;
                temp = temp.next;
            }
        }
    }

    /**
     * 清空所有桶
     *
     * @param heads
     */
    public static void cleanBucket(Elem[] heads, Elem[] tail) {
        for (int i = 0; i < heads.length; i++) {
            heads[i].next = null;
            tail[i] = heads[i];

        }
    }

    /**
     * 元素入桶
     *
     * @param value
     * @param buckets
     * @param index
     */
    public static void insertBucket(int value, Buckets buckets, int index) {
        buckets.tails[index].next = new Elem(value, null);
        buckets.tails[index] = buckets.tails[index].next;
    }

    /**
     * 求数组中最大元素的位数
     *
     * @param arr
     * @return
     */
    public static int maxIndex(int[] arr) {
        int maxIndex = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > arr[maxIndex]) {
                maxIndex = i;
            }
        }
        int temp = arr[maxIndex];
        maxIndex = 1;
        while (temp / 10 != 0) {
            maxIndex++;
            temp /= 10;
        }
        return maxIndex;
    }

}
