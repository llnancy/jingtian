package io.github.llnancy.jingtian.algorithm.leetcode.hot100;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 146. LRU 缓存
 * <a href="https://leetcode.cn/problems/lru-cache/">https://leetcode.cn/problems/lru-cache/</a>
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2023/11/15
 */
public class LruCache<K, V> {

    private final Map<K, Node<K, V>> map;

    private final DoubleList<K, V> cache;

    private final int capacity;

    public LruCache(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>();
        cache = new DoubleList<>();
    }

    /**
     * 将指定 key 提升为最近使用过的
     *
     * @param key 指定 key
     */
    private void makeRecently(K key) {
        Node<K, V> node = map.get(key);
        // 先从双向链表中删除该节点
        cache.remove(node);
        // 再将该节点添加到尾部
        cache.addLast(node);
    }

    /**
     * 添加最近使用的元素
     *
     * @param key k
     * @param val v
     */
    private void addRecently(K key, V val) {
        Node<K, V> node = new Node<>(key, val);
        cache.addLast(node);
        map.put(key, node);
    }

    /**
     * 删除指定 key
     *
     * @param key k
     */
    private void deleteKey(K key) {
        cache.remove(map.get(key));
        map.remove(key);
    }

    /**
     * 删除最久未使用
     */
    private void removeLeastRecently() {
        Node<K, V> node = cache.removeFirst();
        map.remove(node.key);
    }

    public V get(K key) {
        if (!map.containsKey(key)) {
            return null;
        }
        makeRecently(key);
        return map.get(key).val;
    }

    public void put(K key, V val) {
        if (map.containsKey(key)) {
            deleteKey(key);
            addRecently(key, val);
            return;
        }
        if (cache.size() == capacity) {
            removeLeastRecently();
        }
        addRecently(key, val);
    }
}

/**
 * 双向链表：只能从尾部插入。即尾部的节点是最近使用的，头部的节点是最久未使用的。
 *
 * @param <K>
 * @param <V>
 */
class DoubleList<K, V> {

    private final Node<K, V> head;

    private final Node<K, V> tail;

    private int size;

    public DoubleList() {
        head = new Node<>();
        tail = new Node<>();
        head.next = tail;
        tail.prev = head;
        size = 0;
    }

    /**
     * 添加指定节点到尾部
     *
     * @param node 待添加节点
     */
    public void addLast(Node<K, V> node) {
        node.prev = tail.prev;
        node.next = tail;
        tail.prev.next = node;
        tail.prev = node;
        size++;
    }

    /**
     * 删除链表中的指定节点
     *
     * @param node 待删除节点
     */
    public void remove(Node<K, V> node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
        size--;
    }

    /**
     * 删除链表中的第一个节点
     *
     * @return 链表中的第一个节点
     */
    public Node<K, V> removeFirst() {
        if (head.next == tail) {
            return null;
        }
        Node<K, V> first = head.next;
        remove(first);
        return first;
    }

    /**
     * 返回链表长度
     *
     * @return 链表长度
     */
    public int size() {
        return size;
    }
}

class Node<K, V> {

    K key;

    V val;

    Node<K, V> next;

    Node<K, V> prev;

    public Node() {
    }

    public Node(K key, V val) {
        this.key = key;
        this.val = val;
    }

}

/**
 * 基于 JDK 的 {@link LinkedHashMap} 实现
 *
 * @param <K> k
 * @param <V> v
 */
class LruCacheBasedOnJDK<K, V> {

    private final int capacity;

    private final Map<K, V> cache = new LinkedHashMap<>();

    public LruCacheBasedOnJDK(int capacity) {
        this.capacity = capacity;
    }

    public V get(K k) {
        if (!cache.containsKey(k)) {
            return null;
        }
        makeRecently(k);
        return cache.get(k);
    }

    public void put(K k, V v) {
        if (cache.containsKey(k)) {
            cache.remove(k);
            cache.put(k, v);
            return;
        }
        if (cache.size() == capacity) {
            K first = cache.keySet().iterator().next();
            cache.remove(first);
        }
        cache.put(k, v);
    }

    private void makeRecently(K k) {
        V v = cache.get(k);
        cache.remove(k);
        cache.put(k, v);
    }
}
