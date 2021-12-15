package com.sunchaser.sparrow.designpatterns.gof.creational.singleton;

import java.io.Serializable;

/**
 * 饿汉式单例模式
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/10/7
 */
public class HungrySingleton implements Serializable {
    private static final long serialVersionUID = -1406126516096410809L;

    private HungrySingleton() {
        if (instance != null) {
            throw new RuntimeException("重复创建单例对象");
        }
    }

    private static final HungrySingleton instance = new HungrySingleton();

    public static HungrySingleton getInstance() {
        return instance;
    }

    /**
     * 对象在被反序列化时会自动调用，并且该方法的返回值会作为反序列化的结果。
     * @return 反序列化后的对象
     */
    public Object readResolve() {
        return instance;
    }
}
