package LeetCode;

import java.util.HashMap;
import java.util.Map;

/**
 * 设计并实现最不经常使用（LFU）缓存的数据结构。它应该支持以下操作：get 和 put。
 * <p>
 * get(key) - 如果键存在于缓存中，则获取键的值（总是正数），否则返回 -1。
 * put(key, value) - 如果键不存在，请设置或插入值。当缓存达到其容量时，它应该在插入新项目之前，使最不经常使用的项目无效。
 * 在此问题中，当存在平局（即两个或更多个键具有相同使用频率）时，最近最少使用的键将被去除。
 * <p>
 * 进阶：
 * 你是否可以在 O(1) 时间复杂度内执行两项操作？
 * <p>
 * 示例：
 * //capacity (缓存容量)
 * LFUCache cache = new LFUCache(2);
 * cache.put(1,1);
 * cache.put(2,2);
 * cache.get(1);       // 返回 1
 * cache.put(3,3);    // 去除 key 2
 * cache.get(2);       // 返回 -1 (未找到key 2)
 * cache.get(3);       // 返回 3
 * cache.put(4,4);    // 去除 key 1
 * cache.get(1);       // 返回 -1 (未找到 key 1)
 * cache.get(3);       // 返回 3
 * cache.get(4);       // 返回 4
 */
public class LFUCache {
    public static void main(String[] args) {
        LFUCache lfuCache = new LFUCache(3);
        lfuCache.put(1, 1);
        lfuCache.put(2, 2);
        lfuCache.put(3, 3);
        lfuCache.put(4, 4);
        System.out.println(lfuCache.get(4));
        System.out.println(lfuCache.get(3));
        System.out.println(lfuCache.get(2));
        System.out.println(lfuCache.get(1));
        lfuCache.put(5, 5);
        System.out.println(lfuCache.get(1));
        System.out.println(lfuCache.get(2));
        System.out.println(lfuCache.get(3));
        System.out.println(lfuCache.get(4));
        System.out.println(lfuCache.get(5));
    }

    private class Node {
        int key;
        int freq;
        int value;
        int index;

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
            this.freq = 1;
        }
    }

    private Node[] heap;
    private int len;
    private Map<Integer, Node> hash;

    public LFUCache(int capacity) {
        this.heap = new Node[capacity];
        this.len = 0;
        this.hash = new HashMap<>();
    }

    public int get(int key) {
        if (!this.hash.containsKey(key))
            return -1;
        Node node = this.hash.get(key);
        node.freq++;
        adjust(node);
        return node.value;
    }

    public void put(int key, int value) {
        if (this.hash.containsKey(key)) {
            Node node = this.hash.get(key);
            node.value = value;
            node.freq++;
            adjust(node);
        } else {
            if (this.heap.length == 0)
                return;
            Node node = new Node(key, value);
            insertHeap(node);
            this.hash.put(key, node);
        }
    }

    /**
     * 插入新元素入堆并调整位置
     *
     * @param node
     * @return
     */
    private void insertHeap(Node node) {
        if (len == this.heap.length)
            removeHeapTop();
        node.index = len;
        this.heap[len++] = node;
        while (node.index != (node.index - 1) / 2 &&
                this.heap[node.index].freq < this.heap[(node.index - 1) / 2].freq)
            swap(node.index, (node.index - 1) / 2);
    }

    /**
     * 移除堆顶元素
     * 实际操作是交换数组首尾元素，并将数组长度-1，调整新的堆顶元素在堆中的位置（下沉过程）
     */
    private void removeHeapTop() {
        if (len == 0)
            return;
        this.hash.remove(this.heap[0].key);
        swap(0, len - 1);
        len--;
        adjust(this.heap[0]);
    }

    /**
     * 将堆中某个位置的元素进行修改之后重新调整该元素在堆中的位置
     *
     * @param node
     * @return
     */
    private void adjust(Node node) {
        while ((node.index * 2 + 1) < len) {
            int leftChildIndex = node.index * 2 + 1;
            int rightChildIndex = node.index * 2 + 2;
            int minIndex = rightChildIndex < len && this.heap[rightChildIndex].freq <= heap[leftChildIndex].freq ? rightChildIndex : leftChildIndex;
            if (this.heap[minIndex].freq <= node.freq) {
                swap(minIndex, node.index);
            } else
                break;
        }
    }

    /**
     * 交换堆中两个位置的元素
     *
     * @param index1
     * @param index2
     * @return
     */
    private boolean swap(int index1, int index2) {
        if (index1 < 0 || index1 >= len || index2 < 0 || index2 >= len)
            return false;
        if (index1 == index2)
            return true;
        this.heap[index1].index = index2;
        this.heap[index2].index = index1;
        Node temp = this.heap[index1];
        this.heap[index1] = this.heap[index2];
        this.heap[index2] = temp;
        return true;
    }
}
