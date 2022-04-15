package com.sunchaser.sparrow.designpatterns.gof.creational.singleton;

/**
 * 枚举实现饿汉式单例模式
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/10/8
 */
public enum EnumHungrySingleton {
    INSTANCE,
    ;
    public static EnumHungrySingleton getInstance() {
        return INSTANCE;
    }

    EnumHungrySingleton() {
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode());
    }
}
