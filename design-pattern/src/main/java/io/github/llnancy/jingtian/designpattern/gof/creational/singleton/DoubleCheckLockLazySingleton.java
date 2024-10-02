package io.github.llnancy.jingtian.designpattern.gof.creational.singleton;

import java.io.Serial;
import java.io.Serializable;

/**
 * double-check-lock 双重检查锁（DCL）实现懒汉式单例模式
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2024/10/2
 */
public class DoubleCheckLockLazySingleton implements Serializable {

    @Serial
    private static final long serialVersionUID = -8781815743657385934L;

    private DoubleCheckLockLazySingleton() {
        if (instance != null) {
            // 防止反射创建新对象破坏单例
            throw new RuntimeException("重复创建单例对象");
        }
    }

    /**
     * 加 volatile 防止指令重排，保证可见性和有序性
     */
    private static volatile DoubleCheckLockLazySingleton instance = null;

    public static DoubleCheckLockLazySingleton getInstance() {
        if (instance == null) {
            synchronized (DoubleCheckLockLazySingleton.class) {
                if (instance == null) {
                    instance = new DoubleCheckLockLazySingleton();
                }
            }
        }
        return instance;
    }

    @Serial
    public Object readResolve() {
        return instance;
    }
}
