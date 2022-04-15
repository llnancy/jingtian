package com.sunchaser.sparrow.algorithm.base.lru;

import java.util.HashMap;
import java.util.Map;

/**
 * LRU：最近最久访问
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/11/8
 */
public class LRUCache<K, V> {

    private final Map<K, DoubleList.Node<K, V>> map = new HashMap<>();
    private final DoubleList<K, V> cache = new DoubleList<>();
    private final int capacity;

    public LRUCache(int capacity) {
        this.capacity = capacity;
    }

    public void put(K key, V val) {
        if (map.containsKey(key)) {
            // 存在
            // map中进行值覆盖
            DoubleList.Node<K, V> exist = map.get(key);
            DoubleList.Node<K, V> newNode = new DoubleList.Node<>(key, val);
            map.put(key, newNode);
            // cache中删除原来的旧节点，新节点添加到链表尾部
            cache.remove(exist);
            cache.addLast(newNode);
            return;
        }
        // 不存在，判断是否达到容量
        if (capacity == cache.size()) {
            // 达到容量
            // 删除链表头节点，并从map中删除
            DoubleList.Node<K, V> first = cache.removeFirst();
            // 新节点添加到链表尾部，并添加到map中
            map.remove(first.getKey());
        }
        // 未达到容量，新节点添加到链表尾部，并添加到map中
        DoubleList.Node<K, V> newNode = new DoubleList.Node<>(key, val);
        cache.addLast(newNode);
        map.put(key, newNode);
    }

    public V get(K key) {
        if (!map.containsKey(key)) {
            return null;
        }
        DoubleList.Node<K, V> node = map.get(key);
        // 删除存在的，添加到链表尾部
        cache.remove(node);
        cache.addLast(node);
        return node.getVal();
    }

    public static void main(String[] args) {
        LRUCache<Integer, Integer> lruCache = new LRUCache<>(2);
        lruCache.put(2, 1);
        lruCache.put(1, 1);
        lruCache.put(2, 3);
        lruCache.put(4, 1);
        Integer get1 = lruCache.get(1);
        Integer get2 = lruCache.get(2);
        System.out.println(get1);
        System.out.println(get2);
    }
}
