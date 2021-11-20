package com.sunchaser.sparrow.algorithm.base.lfu;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

/**
 * LFU：最近最少访问
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/11/10
 */
public class LFUCache<K, V> {
    // key => val 键到值的映射
    private final Map<K, V> keyToVal = new HashMap<>();

    // key => freq 键到访问次数的映射
    private final Map<K, Integer> keyToFreq = new HashMap<>();

    // freq => keys 访问次数到有序键列表的映射
    private final Map<Integer, LinkedHashSet<K>> freqToKeys = new HashMap<>();

    // 最小访问次数
    private int minFreq;

    // 容量
    private final int capacity;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.minFreq = 0;
    }

    public void put(K key, V val) {
        if (keyToVal.containsKey(key)) {
            // key存在，覆盖其val，对应freq+1
            keyToVal.put(key, val);
            increaseFreq(key);
            return;
        }
        // key不存在
        if (capacity == keyToVal.size()) {
            // 满了，淘汰掉访问次数最少（freq最小）的key
            LinkedHashSet<K> keyList = freqToKeys.get(this.minFreq);
            // 最先被插入的key，是需要淘汰掉的
            K deletedKey = keyList.iterator().next();
            keyList.remove(deletedKey);
            if (keyList.isEmpty()) {
                freqToKeys.remove(this.minFreq);
            }
            keyToVal.remove(deletedKey);
            keyToFreq.remove(deletedKey);
        }
        // 插入新key
        keyToVal.put(key, val);
        keyToFreq.put(key, 1);
        freqToKeys.putIfAbsent(1, new LinkedHashSet<>());
        freqToKeys.get(1).add(key);
        this.minFreq = 1;
    }

    public V get(K key) {
        if (!keyToVal.containsKey(key)) {
            return null;
        }
        // key的使用次数freq加一
        increaseFreq(key);
        return keyToVal.get(key);
    }

    /**
     * 将指定key的使用次数加一
     *
     * @param key
     */
    public void increaseFreq(K key) {
        Integer freq = keyToFreq.get(key);
        // KF加一
        keyToFreq.put(key, freq + 1);
        // FK中freq删除对应key
        LinkedHashSet<K> keyList = freqToKeys.get(freq);
        keyList.remove(key);
        if (keyList.isEmpty()) {
            freqToKeys.remove(freq);
            if (freq == this.minFreq) {
                this.minFreq++;
            }
        }
        // FK中freq+1添加对应key
        freqToKeys.putIfAbsent(freq + 1, new LinkedHashSet<>());
        freqToKeys.get(freq + 1).add(key);
    }

    public static void main(String[] args) {
        LFUCache<Integer, Integer> lfuCache = new LFUCache<>(2);
        lfuCache.put(1, 1);   // cache=[1,_], cnt(1)=1
        lfuCache.put(2, 2);   // cache=[2,1], cnt(2)=1, cnt(1)=1
        System.out.println(lfuCache.get(1));
        // 返回 1
        // cache=[1,2], cnt(2)=1, cnt(1)=2
        lfuCache.put(3, 3);   // 去除键 2 ，因为 cnt(2)=1 ，使用计数最小
        // cache=[3,1], cnt(3)=1, cnt(1)=2
        System.out.println(lfuCache.get(2));
        // 返回 -1（未找到）
        System.out.println(lfuCache.get(3));
        // 返回 3
        // cache=[3,1], cnt(3)=2, cnt(1)=2
        lfuCache.put(4, 4);   // 去除键 1 ，1 和 3 的 cnt 相同，但 1 最久未使用
        // cache=[4,3], cnt(4)=1, cnt(3)=2
        System.out.println(lfuCache.get(1));
        // 返回 -1（未找到）
        System.out.println(lfuCache.get(3));
        // 返回 3
        // cache=[3,4], cnt(4)=1, cnt(3)=3
        System.out.println(lfuCache.get(4));
        // 返回 4
        // cache=[3,4], cnt(4)=2, cnt(3)=3

    }
}
