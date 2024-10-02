package io.github.llnancy.jingtian.designpattern.gof.creational.singleton;

import java.io.Serial;
import java.io.Serializable;

/**
 * 饿汉式单例模式
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2021/10/7
 */
public class HungrySingleton implements Serializable {

    @Serial
    private static final long serialVersionUID = -1406126516096410809L;

    private HungrySingleton() {
        if (instance != null) {
            // 防止反射创建新对象破坏单例
            throw new RuntimeException("重复创建单例对象");
        }
    }

    private static final HungrySingleton instance = new HungrySingleton();

    public static HungrySingleton getInstance() {
        return instance;
    }

    /**
     * 对象在反序列化时会自动调用，并且该方法的返回值会作为反序列化的结果。
     * 重写该方法，返回单例 instance，防止反序列化破坏单例
     *
     * @return 反序列化后的对象
     */
    @Serial
    public Object readResolve() {
        return instance;
    }
}
