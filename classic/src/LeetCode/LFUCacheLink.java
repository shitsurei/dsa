package LeetCode;

import java.util.HashMap;
import java.util.Map;

public class LFUCacheLink {
    public static void main(String[] args) {
        LFUCacheLink lruCacheHeap = new LFUCacheLink(2);
        lruCacheHeap.put(1, 1);
        lruCacheHeap.put(2, 2);
        System.out.println(lruCacheHeap.get(1));
        lruCacheHeap.put(3, 3);
        System.out.println(lruCacheHeap.get(2));
        lruCacheHeap.put(4, 4);
        System.out.println(lruCacheHeap.get(1));
        System.out.println(lruCacheHeap.get(3));
        System.out.println(lruCacheHeap.get(4));
    }

    /**
     * 双向链表结点
     */
    private class Node {
        Node prev;
        Node next;
        int freq;
        int key;
        int value;

        public Node(int key, int value) {
            this.freq = 1;
            this.key = key;
            this.value = value;
        }
    }

    /**
     * 带头节点和尾节点的双向链表
     */
    private class DoubleList {
        Node head;
        Node tail;
        int len;

        public DoubleList() {
//            初始化头尾节点和关联关系
            this.head = new Node(0, 0);
            this.tail = new Node(0, 0);
            head.next = tail;
            tail.prev = head;
            len = 0;
        }

        public boolean isEmpty() {
            return len == 0;
        }

        /**
         * 每次新加入元素加入表头位置
         * 保持同访问频率元素的先后关系
         * @param node
         */
        public void addHead(Node node) {
            node.next = head.next;
            head.next.prev = node;
            node.prev = head;
            head.next = node;
            len++;
        }

        /**
         * 移除链表中的某个节点（增加头尾节点的好处在此方法中无需对首位元素出链表做额外处理）
         * 用于节点访问频次增加更换存储链表时使用
         * @param node
         */
        public void remove(Node node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            len--;
        }

        /**
         * 移除链表尾部的节点，并返回该节点的键值
         * 用于容量达到上限时移除访问频率最小的键值对中最久访问的一个键值对
         * @return
         */
        public int removeTail() {
            int removeKey = tail.prev.key;
            tail.prev = tail.prev.prev;
            tail.prev.next = tail;
            len--;
            return removeKey;
        }
    }

//    hash表存键值对（此处的值为多级链表中的节点）
    Map<Integer, Node> hash;
//    按访问频次划分的多级链表，可以在O(1)时间获取访问频次为n的键值对链表
    Map<Integer, DoubleList> freqList;
//    记录当前最小访问频次（用于O(1)时间找到访问频次最低的链表）和容量上限
    int minFreq, capacity;

    public LFUCacheLink(int capacity) {
        hash = new HashMap<>();
        freqList = new HashMap<>();
//        注意初始化最低频次需要为1
        minFreq = 1;
        this.capacity = capacity;
    }

    public int get(int key) {
        if (!hash.containsKey(key))
            return -1;
        Node node = hash.get(key);
        DoubleList preList = freqList.get(node.freq);
        preList.remove(node);
        freqList.put(node.freq, preList);
        node.freq++;
//        注意getOrDefault返回的新实例并不会直接加入到原hash表中
        DoubleList newList = freqList.getOrDefault(node.freq, new DoubleList());
        newList.addHead(node);
//        需要手动加入
        freqList.put(node.freq, newList);
//        小频率链表为空时，minFreq需要同步
        if (preList.isEmpty() && node.freq - 1 == minFreq)
            minFreq++;
        return node.value;
    }

    public void put(int key, int value) {
        if (capacity == 0)
            return;
        if (hash.containsKey(key)) {
            hash.get(key).value = value;
            get(key);
        } else {
            if (hash.size() == capacity) {
                int removeKey = freqList.get(minFreq).removeTail();
                hash.remove(removeKey);
            }
            Node node = new Node(key, value);
            hash.put(key, node);
            DoubleList list = freqList.getOrDefault(node.freq, new DoubleList());
            freqList.put(node.freq, list);
            list.addHead(node);
//            新产生的元素访问频率为1，可能会重置minFreq
            if (minFreq > node.freq)
                minFreq = node.freq;
        }
    }
}
