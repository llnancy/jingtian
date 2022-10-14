package com.sunchaser.sparrow.javase.concurrency;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁：适用于读多写少的场景
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/10/13
 */
public class ReadWriteLockMap {

    /**
     * 使用 ReadWriteLock 读写锁保证 HashMap 线程安全
     */
    private static final Map<String, Object> CACHE = new HashMap<>();

    /**
     * 读写锁
     */
    private static final ReadWriteLock RWL = new ReentrantReadWriteLock();

    /**
     * 读锁
     */
    private static final Lock R = RWL.readLock();

    /**
     * 写锁
     */
    private static final Lock W = RWL.writeLock();

    public static Object get(String key) {
        R.lock();
        try {
            return CACHE.get(key);
        } finally {
            R.unlock();
        }
    }

    public static Object put(String key, Object value) {
        W.lock();
        try {
            return CACHE.put(key, value);
        } finally {
            W.unlock();
        }
    }

    public static void clear() {
        W.lock();
        try {
            CACHE.clear();
        } finally {
            W.unlock();
        }
    }
}
