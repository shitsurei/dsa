package skill;

/**
 * @Description 使用桶求序列的最大差值
 */
public class BucketForMaxMinus {
    public static void main(String[] args) {
        int[] arr = {1,2,6,3,7};
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
     * @Description 初始化桶集合
     * @param buckets
     */
    public static void initBucket(Bucket[] buckets){
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new Bucket();
        }
    }

    /**
     * @Description 主函数
     * @param arr
     * @return
     */
    public static int maxMinus(int[] arr){
        if (arr == null || arr.length <2){
            return -1;
        }
        int length = arr.length;
        Bucket[] buckets = new Bucket[length + 1];
        initBucket(buckets);
        int maxMin[] = maxMinElem(arr);
        int min = maxMin[0];
        int max = maxMin[1];
        if (max == min){
            return 0;
        }
        for (int i = 0; i < arr.length; i++) {
            int index = determainIndex(arr[i],length,min,max);
            insertBucket(buckets[index],arr[i]);
        }
        int maxMinus = calMaxMinus(buckets);
        return maxMinus;
    }

    public static int determainIndex(long num, long len, long min, long max){
        return (int) ((num -min) * len / (max - min));
    }

    public static int calMaxMinus(Bucket[] buckets){
        int max = Integer.MIN_VALUE;
        int i = 0;
        int temp = buckets[i].maxValue;
        for (; i < buckets.length; ++i) {
            if (buckets[i].enter && max < (buckets[i].minValue - temp)){
                max = buckets[i].minValue - temp;
            }else{
                temp = buckets[i].maxValue;
            }
        }
        return max;
    }

    public static void insertBucket(Bucket bucket, int elemValue){
        if (bucket.enter){
            if(bucket.minValue > elemValue){
                bucket.minValue = elemValue;
            }
            if (bucket.maxValue < elemValue){
                bucket.maxValue = elemValue;
            }
        }else{
            bucket.enter = true;
            bucket.maxValue = elemValue;
            bucket.minValue = elemValue;
        }
    }


    /**
     * @Description 求数组中的最大值和最小值
     * @param arr
     * @return
     */
    public static int[] maxMinElem(int[] arr){
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
