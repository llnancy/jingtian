package io.github.llnancy.jingtian.designpattern.gof.creational.singleton;

import java.io.Serial;
import java.io.Serializable;

/**
 * 内部类实现懒汉式单例模式
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2024/10/2
 */
public class InnerClassLazySingleton implements Serializable {

    @Serial
    private static final long serialVersionUID = 6904297552615649688L;

    private InnerClassLazySingleton() {
    }

    private static class SingletonHolder {

        private static final InnerClassLazySingleton INSTANCE = new InnerClassLazySingleton();
    }

    public static InnerClassLazySingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Serial
    public Object readResolve() {
        return InnerClassLazySingleton.getInstance();
    }
}
