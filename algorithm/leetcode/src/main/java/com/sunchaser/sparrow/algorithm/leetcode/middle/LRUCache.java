package com.sunchaser.sparrow.algorithm.leetcode.middle;

import java.util.HashMap;
import java.util.Map;

/**
 * 146. LRU 缓存机制
 * <p>
 * 最近最久未使用：哈希链表
 * <p>
 * https://leetcode-cn.com/problems/lru-cache/
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/11/14
 */
public class LRUCache {
    private final Map<Integer, DoubleList.Node> map = new HashMap<>();
    private final DoubleList cache = new DoubleList();
    private final int capacity;

    public LRUCache(int capacity) {
        this.capacity = capacity;
    }

    public int get(int key) {
        if (!map.containsKey(key)) {
            return -1;
        }
        DoubleList.Node exist = map.get(key);
        cache.remove(exist);
        cache.addLast(exist);
        return exist.getVal();
    }

    public void put(int key, int val) {
        if (map.containsKey(key)) {
            DoubleList.Node exist = map.get(key);
            cache.remove(exist);

            DoubleList.Node newNode = new DoubleList.Node(key, val);
            map.put(key, newNode);
            cache.addLast(newNode);
            return;
        }
        if (capacity == cache.size()) {
            DoubleList.Node first = cache.removeFirst();
            map.remove(first.getKey());
        }
        DoubleList.Node newNode = new DoubleList.Node(key, val);
        map.put(key, newNode);
        cache.addLast(newNode);
    }

    static class DoubleList {
        private final Node head;
        private final Node tail;
        private int size;

        public DoubleList() {
            head = new Node(-1, -1);
            tail = new Node(-1, -1);
            head.next = tail;
            tail.prev = head;
            size = 0;
        }

        public int size() {
            return size;
        }

        public void remove(Node x) {
            x.prev.next = x.next;
            x.next.prev = x.prev;
            size--;
        }

        public void addLast(Node x) {
            x.prev = tail.prev;
            x.next = tail;
            tail.prev.next = x;
            tail.prev = x;
            size++;
        }

        public Node removeFirst() {
            Node first = head.next;
            remove(first);
            return first;
        }

        static class Node {
            private final int key;
            private final int val;
            private Node prev;
            private Node next;

            public Node(int key, int val) {
                this.key = key;
                this.val = val;
            }

            public int getKey() {
                return key;
            }

            public int getVal() {
                return val;
            }
        }
    }
}

