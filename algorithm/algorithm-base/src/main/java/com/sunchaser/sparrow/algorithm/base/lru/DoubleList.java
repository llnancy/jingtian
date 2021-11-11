package com.sunchaser.sparrow.algorithm.base.lru;

/**
 * 双向链表
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/11/8
 */
public class DoubleList<K, V> {
    private Node<K, V> head;
    private Node<K, V> tail;
    private int size;

    public int size() {
        return size;
    }

    public DoubleList() {
        head = new Node<>(null, null);
        tail = new Node<>(null, null);
        head.next = tail;
        tail.prev = head;
        size = 0;
    }

    public void remove(Node<K, V> exist) {
        exist.prev.next = exist.next;
        exist.next.prev = exist.prev;
        size--;
    }

    public void addLast(Node<K, V> newNode) {
        newNode.prev = tail.prev;
        newNode.next = tail;
        tail.prev.next = newNode;
        tail.prev = newNode;
        size++;
    }

    public Node<K, V> removeFirst() {
        Node<K, V> first = head.next;
        remove(first);
        return first;
    }

    static class Node<K, V> {
        private K key;
        private V val;
        private Node<K, V> prev;
        private Node<K, V> next;

        public Node(K key, V val) {
            this.key = key;
            this.val = val;
        }

        public K getKey() {
            return key;
        }

        public V getVal() {
            return val;
        }
    }
}
