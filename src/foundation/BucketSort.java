package foundation;

/**
 * 桶排序，非基于比较，基于词频
 *
 * @author 张国荣
 * <p>
 * 分为基数排序和计数排序
 * 例题：求排序后相邻两字段的最大差值
 * 解：遍历一遍求出最大值max和最小值min，准备(max-min)/n+1个桶，其中n是要划分的范围
 * 每个桶只存最小值和最大值，统计完成后空桶两侧的桶中最小最大值之差就是最大相邻字段差值
 */
public class BucketSort {

    public static void main(String[] args) {
        int[] arr = {1, 2, 6, 15, 3, 7};
        System.out.println(maxMinus(arr, arr.length));
    }

    public static int maxMinus(int[] arr, int len) {
        int[] values = absoluteValue(arr);
        int minus = values[0] - values[1];
        if (minus == 0 || minus == 1)
            return minus;
        int[][] maxMins = new int[len + 1][2];
        boolean[] enter = new boolean[len + 1];
        for (int i = 0; i < arr.length; i++) {
            int bucket = bucket(arr[i], len, values[0], values[1]);
            maxMins[bucket][0] = enter[bucket] ? Math.max(maxMins[bucket][0], arr[i]) : arr[i];
            maxMins[bucket][1] = enter[bucket] ? Math.min(maxMins[bucket][1], arr[i]) : arr[i];
            enter[bucket] = true;
        }
        int res = 0, lastMax = maxMins[0][0];
        for (int i = 1; i <= len; i++) {
            if (enter[i]) {
                res = Math.max(res, maxMins[i][1] - lastMax);
                lastMax = maxMins[i][0];
            }
        }
        return res;
    }

    /**
     * 确定该数应该放入哪个桶
     *
     * @param num
     * @param len
     * @param max
     * @param min
     * @return
     */
    public static int bucket(int num, int len, int max, int min) {
        return (int) ((num - min) * len / (max - min));
    }

    /**
     * 求数组中的最大值和最小值
     *
     * @param arr
     * @return
     */
    public static int[] absoluteValue(int[] arr) {
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
