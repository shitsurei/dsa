package skill;

/**
 * @Description 使用桶求序列的最大差值
 */
public class BucketForMaxMinus {
    public static void main(String[] args) {
        int[] arr = {1, 2, 6, 15, 3, 25, 7};
        System.out.println(maxMinus(arr));
    }

    /**
     * @Description 桶结构
     */
    public static class Bucket {
        int maxValue;
        int minValue;
        boolean enter;
    }

    /**
     * @param buckets
     * @Description 初始化桶集合
     */
    public static void initBucket(Bucket[] buckets) {
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new Bucket();
        }
    }

    /**
     * @param arr
     * @return
     * @Description 主函数
     */
    public static int maxMinus(int[] arr) {
        if (arr == null || arr.length < 2) {
            return -1;
        }
        int length = arr.length;
        Bucket[] buckets = new Bucket[length + 1];
        initBucket(buckets);
        int maxMin[] = maxMinElem(arr);
        int max = maxMin[0];
        int min = maxMin[1];
        if (max == min) {
            return 0;
        }
        for (int i = 0; i < arr.length; i++) {
            int index = determainIndex(arr[i], length, min, max);
            insertBucket(buckets[index], arr[i]);
        }
        int maxMinus = calMaxMinus(buckets);
        return maxMinus;
    }

    public static int determainIndex(int num, int len, int min, int max) {
        return (int) ((num - min) * len / (max - min));
    }

    /**
     * @Description 遍历桶集合寻找最大差值
     * @param buckets
     * @return
     */
    public static int calMaxMinus(Bucket[] buckets) {
        int max = Integer.MIN_VALUE;
        int temp = buckets[0].maxValue;
        for (int i = 1; i < buckets.length; i++) {
            if (buckets[i].enter) {
                max = Math.max(max, buckets[i].minValue - temp);
                temp = buckets[i].maxValue;
            }
        }
        return max;
    }

    /**
     * @Description 元素入桶
     * @param bucket
     * @param elemValue
     */
    public static void insertBucket(Bucket bucket, int elemValue) {
        if (bucket.enter) {
            bucket.minValue = Math.min(bucket.minValue, elemValue);
            bucket.maxValue = Math.max(bucket.maxValue, elemValue);
        } else {
            bucket.enter = true;
            bucket.maxValue = elemValue;
            bucket.minValue = elemValue;
        }
    }


    /**
     * @param arr
     * @return
     * @Description 求数组中的最大值和最小值
     */
    public static int[] maxMinElem(int[] arr) {
        if (arr == null || arr.length < 2)
            return new int[]{0, 0};
        int max = Integer.MIN_VALUE, min = Integer.MAX_VALUE;
        for (int i = 0; i < arr.length; i++) {
            max = Math.max(max, arr[i]);
            min = Math.min(min, arr[i]);
        }
        return new int[]{max, min};
    }
}
