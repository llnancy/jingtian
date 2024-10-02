package io.github.llnancy.jingtian.designpattern.gof.creational.singleton;

/**
 * 枚举实现饿汉式单例模式
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2021/10/8
 */
public enum EnumHungrySingleton {

    INSTANCE,
    ;

    public static EnumHungrySingleton getInstance() {
        return INSTANCE;
    }

    /**
     * 默认是 private 构造函数
     */
    EnumHungrySingleton() {
    }

    @Override
    public String toString() {
        // 重写 toString 方便看对象地址
        return getClass().getName() + "@" + Integer.toHexString(hashCode());
    }
}
