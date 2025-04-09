package io.github.llnancy.jingtian.algorithm.base.lru;

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

    private final DoubleList<K, V> dl = new DoubleList<>();

    private final int capacity;

    public LRUCache(int capacity) {
        this.capacity = capacity;
    }

    public void remove(K key) {
        dl.remove(map.get(key));
        map.remove(key);
    }

    public void addRecently(K key, V value) {
        DoubleList.Node<K, V> node = new DoubleList.Node<>(key, value);
        map.put(key, node);
        dl.addLast(node);
    }

    public void put(K key, V val) {
        if (map.containsKey(key)) {
            // 以前放过，提升为最近已使用：先从双向链表中删除，再添加至链表尾部
            remove(key);
            addRecently(key, val);
            return;
        }
        if (dl.size() == capacity) {
            // 容量满了，删除最久未使用
            DoubleList.Node<K, V> node = dl.removeFirst();
            map.remove(node.key);
        }
        // 新节点：直接放在链表尾部
        addRecently(key, val);
    }

    public V get(K key) {
        if (!map.containsKey(key)) {
            return null;
        }
        // 如果存在，则将其提升为最近已使用
        DoubleList.Node<K, V> node = map.get(key);
        dl.remove(node);
        dl.addLast(node);
        return node.val;
    }

    public static void main(String[] args) {
        LRUCache<Integer, Integer> lruCache = new LRUCache<>(2);
        lruCache.put(2, 1);
        lruCache.put(2, 2);
        Integer get1 = lruCache.get(2);
        lruCache.put(1, 1);
        lruCache.put(4, 1);
        Integer get2 = lruCache.get(2);
        System.out.println(get1);
        System.out.println(get2);
    }
}
