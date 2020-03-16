import java.util.PriorityQueue;

/**
 * 双堆法求数据流中的中位数
 * 大佬的左右横跳法看呆我了Σ(⊙▽⊙"a
 */
public class MedianFinder {
    PriorityQueue<Integer> big;
    PriorityQueue<Integer> small;

    public static void main(String[] args) {
        MedianFinder medianFinder = new MedianFinder();
        medianFinder.addNum(-1);
        medianFinder.addNum(-2);
        System.out.println(medianFinder.findMedian());
    }

    public MedianFinder() {
        big = new PriorityQueue<>();
        small = new PriorityQueue<>((a1, a2) -> a2 - a1);
    }

    public void addNum(int num) {
        if (big.isEmpty() && small.isEmpty())
            big.add(num);
        else {
            if (num<big.peek())
                small.add(num);
            else
                big.add(num);
            if (small.size() - big.size() > 1)
                big.add(small.poll());
            if (big.size() - small.size() > 1)
                small.add(big.poll());
        }
    }

    public double findMedian() {
        if (big.size() > small.size())
            return big.peek();
        else if (big.size() < small.size())
            return small.peek();
        else
            return (big.peek() + small.peek()) / 2.0;
    }
}
