package LeetCode;

import java.util.HashMap;
import java.util.Map;

/**
 * 运用你所掌握的数据结构，设计和实现一个  LRU (最近最少使用) 缓存机制。
 * 它应该支持以下操作： 获取数据 get 和 写入数据 put 。
 * 获取数据 get(key) - 如果密钥 (key) 存在于缓存中，则获取密钥的值（总是正数），否则返回 -1。
 * 写入数据 put(key, value) - 如果密钥不存在，则写入其数据值。
 * 当缓存容量达到上限时，它应该在写入新数据之前删除最久未使用的数据值，从而为新的数据值留出空间。
 * <p>
 * 进阶:
 * 你是否可以在 O(1) 时间复杂度内完成这两种操作？
 * <p>
 * 示例:
 * LRUCache cache = new LRUCache(2);
 * cache.put(1, 1);
 * cache.put(2, 2);
 * cache.get(1);       // 返回  1
 * cache.put(3, 3);    // 该操作会使得密钥 2 作废
 * cache.get(2);       // 返回 -1 (未找到)
 * cache.put(4, 4);    // 该操作会使得密钥 1 作废
 * cache.get(1);       // 返回 -1 (未找到)
 * cache.get(3);       // 返回  3
 * cache.get(4);       // 返回  4
 */
public class LRUCache {
    public static void main(String[] args) {
        LRUCache lruCache = new LRUCache(2);
        lruCache.put(1, 1);
        lruCache.put(2, 2);
        System.out.println(lruCache.get(1));
        lruCache.put(3, 3);
        System.out.println(lruCache.get(2));
        lruCache.put(4, 4);
        System.out.println(lruCache.get(1));
        System.out.println(lruCache.get(3));
        System.out.println(lruCache.get(4));
    }

    private class Node {
        Node pre;
        Node next;
        int key;
        int value;
    }

    private Node head;
    private Node tail;
    private Map<Integer, Node> hash;

    public LRUCache(int capacity) {
        this.head = new Node();
        this.head.value = capacity;
        this.tail = this.head;
        hash = new HashMap<>();
    }

    public int get(int key) {
        if (!this.hash.containsKey(key))
            return -1;
        Node node = this.hash.get(key);
        moveToHead(node);
        return node.value;
    }

    public void put(int key, int value) {
        if (this.hash.containsKey(key)) {
            Node node = this.hash.get(key);
            node.value = value;
            moveToHead(node);
            return;
        }
        if (this.head.value == this.hash.size())
            removeTail();
        Node node = new Node();
        node.key = key;
        node.value = value;
        this.hash.put(key,node);
        if (this.tail == this.head)
            this.tail = node;
        else
            this.head.next.pre = node;
        node.next = this.head.next;
        node.pre = head;
        this.head.next = node;
    }

    /**
     * 移除链表尾部的节点
     */
    private void removeTail() {
        if (this.head == this.tail || this.hash.isEmpty())
            return;
        this.hash.remove(this.tail.key);
        this.tail = this.tail.pre;
        this.tail.next = null;
    }

    /**
     * 将链表中的节点移动到链表头部
     *
     * @param node
     */
    private void moveToHead(Node node) {
//        1 hash表中无节点 2 链表为空 3 当前节点本身位于头结点之后
        if (this.hash.isEmpty() || this.head.next == null || this.head.next == node)
            return;
//        将node节点从原本的位置删除
        if (this.tail == node)
//        1 当前节点位于链表尾部的处理方式
            this.tail = node.pre;
        else
//        2 当前节点位于链表中部的处理方式
            node.next.pre = node.pre;
        node.pre.next = node.next;
//        node节点插入到头结点之后
        node.pre = head;
        node.next = head.next;
        head.next.pre = node;
        head.next = node;
    }
}
