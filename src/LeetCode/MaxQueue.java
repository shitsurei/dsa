package LeetCode;


import java.util.LinkedList;
import java.util.Queue;

/**
 * 请定义一个队列并实现函数 max_value 得到队列里的最大值
 * 要求函数max_value、push_back 和 pop_front 的均摊时间复杂度都是O(1)
 * 若队列为空，pop_front 和 max_value 需要返回 -1
 * <p>
 * 类比求 滑动窗口中的最大值 使用双端队列解决
 */
public class MaxQueue {
    public static void main(String[] args) {
        MaxQueue mq = new MaxQueue();
        mq.push_back(1);
        mq.push_back(2);
        System.out.println(mq.max_value());
        System.out.println(mq.pop_front());
        System.out.println(mq.max_value());
    }

    private Queue<Integer> queue;
    private LinkedList<Integer> deque;

    public MaxQueue() {
        queue = new LinkedList<>();
        deque = new LinkedList<>();
    }

    public int max_value() {
        if (deque.isEmpty())
            return -1;
        else
            return deque.peekFirst();
    }

    public void push_back(int value) {
        queue.add(value);
        while (!deque.isEmpty() && deque.peekLast() < value)
            deque.pollLast();
        deque.add(value);
    }

    public int pop_front() {
        if (queue.isEmpty())
            return -1;
        if (queue.peek() >= deque.peekFirst())
            deque.poll();
        return queue.poll();
    }
}
