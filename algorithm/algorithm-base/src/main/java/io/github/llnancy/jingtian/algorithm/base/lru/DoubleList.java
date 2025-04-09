package io.github.llnancy.jingtian.algorithm.base.lru;

/**
 * 双向链表
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/11/8
 */
public class DoubleList<K, V> {

    private final Node<K, V> head;

    private final Node<K, V> tail;

    private int size;

    public DoubleList() {
        head = new Node<>(null, null);
        tail = new Node<>(null, null);
        head.next = tail;
        tail.prev = head;
        size = 0;
    }

    /**
     * 用于每次往双向链表尾部添加节点
     */
    public void addLast(Node<K, V> node) {
        node.next = tail;
        node.prev = tail.prev;
        tail.prev.next = node;
        tail.prev = node;
        size++;
    }

    /**
     * 删除指定节点：用于将某个节点提升为最近已使用时先从双向链表中删除
     */
    public void remove(Node<K, V> node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
        size--;
    }

    /**
     * 删除链表头节点：用于删除最近最久未使用的节点
     */
    public Node<K, V> removeFirst() {
        if (head.next == tail) {
            return null;
        }
        Node<K, V> first = head.next;
        remove(first);
        return first;
    }

    public int size() {
        return size;
    }

    public static class Node<K, V> {

        K key;

        V val;

        Node<K, V> prev;

        Node<K, V> next;

        public Node(K key, V val) {
            this.key = key;
            this.val = val;
        }
    }
}
