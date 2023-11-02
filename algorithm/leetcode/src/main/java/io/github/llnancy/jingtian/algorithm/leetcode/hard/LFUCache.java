package io.github.llnancy.jingtian.algorithm.leetcode.hard;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

/**
 * 460. LFU 缓存
 * <p>
 * 最近最少次数使用
 * <p>
 * https://leetcode-cn.com/problems/lfu-cache/
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/11/14
 */
public class LFUCache {
    private final Map<Integer, Integer> keyToVal = new HashMap<>();
    private final Map<Integer, Integer> keyToFreq = new HashMap<>();
    private final Map<Integer, LinkedHashSet<Integer>> freqToKeys = new HashMap<>();
    private int minFreq;
    private final int capacity;

    public LFUCache(int capacity) {
        this.capacity = capacity;
    }

    public int get(int key) {
        if (!keyToVal.containsKey(key)) {
            return -1;
        }
        increaseFreq(key);
        return keyToVal.get(key);
    }

    public void put(int key, int val) {
        if (capacity == 0) {
            return;
        }
        if (keyToVal.containsKey(key)) {
            keyToVal.put(key, val);
            increaseFreq(key);
            return;
        }
        if (capacity == keyToVal.size()) {
            LinkedHashSet<Integer> keyList = freqToKeys.get(this.minFreq);
            Integer deletedKey = keyList.iterator().next();
            keyList.remove(deletedKey);
            if (keyList.isEmpty()) {
                freqToKeys.remove(this.minFreq);
            }
            keyToFreq.remove(deletedKey);
            keyToVal.remove(deletedKey);
        }
        keyToVal.put(key, val);
        keyToFreq.put(key, 1);
        freqToKeys.putIfAbsent(1, new LinkedHashSet<>());
        freqToKeys.get(1).add(key);
        this.minFreq = 1;
    }

    private void increaseFreq(int key) {
        Integer freq = keyToFreq.get(key);
        keyToFreq.put(key, freq + 1);
        LinkedHashSet<Integer> keyList = freqToKeys.get(freq);
        keyList.remove(key);
        if (keyList.isEmpty()) {
            freqToKeys.remove(freq);
            if (this.minFreq == freq) {
                this.minFreq++;
            }
        }
        freqToKeys.putIfAbsent(freq + 1, new LinkedHashSet<>());
        freqToKeys.get(freq + 1).add(key);
    }
}
