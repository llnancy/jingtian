package io.github.llnancy.jingtian.designpattern.gof.creational.singleton;

import org.springframework.objenesis.instantiator.util.UnsafeUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;

/**
 * 单例模式测试 Main
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2021/10/7
 */
public class Main {

    public static void main(String[] args) throws Exception {
        // 饿汉式单例模式
        HungrySingleton hs1 = HungrySingleton.getInstance();
        HungrySingleton hs2 = HungrySingleton.getInstance();
        System.out.println(hs1);
        System.out.println(hs2);
        // 反射破坏单例
        // reflection(HungrySingleton.class);
        // 反序列化破坏单例
        serializable(HungrySingleton.getInstance());
        // unsafe 破坏单例
        unsafe(HungrySingleton.class);

        // 枚举实现饿汉式单例模式
        EnumHungrySingleton ehs1 = EnumHungrySingleton.getInstance();
        EnumHungrySingleton ehs2 = EnumHungrySingleton.getInstance();
        System.out.println(ehs1);
        System.out.println(ehs2);
        // 反射破坏单例
        // 枚举对象没有无参构造函数
        // reflection(EnumHungrySingleton.class);
        // 不能反射调用枚举对象有参构造函数。Cannot reflectively create enum objects
        // reflectionEnum(EnumHungrySingleton.class);
        // 反序列化破坏单例
        serializable(EnumHungrySingleton.getInstance());
        // unsafe 破坏单例
        unsafe(EnumHungrySingleton.class);

        // DCL 双重检查锁实现懒汉式单例模式
        DoubleCheckLockLazySingleton dcl1 = DoubleCheckLockLazySingleton.getInstance();
        DoubleCheckLockLazySingleton dcl2 = DoubleCheckLockLazySingleton.getInstance();
        System.out.println(dcl1);
        System.out.println(dcl2);
        // 反射破坏单例
        // reflection(DoubleCheckLockLazySingleton.class);
        // 反序列化破坏单例
        serializable(DoubleCheckLockLazySingleton.getInstance());
        // unsafe 破坏单例
        unsafe(DoubleCheckLockLazySingleton.class);

        // 内部类实现懒汉式单例模式
        InnerClassLazySingleton ic1 = InnerClassLazySingleton.getInstance();
        InnerClassLazySingleton ic2 = InnerClassLazySingleton.getInstance();
        System.out.println(ic1);
        System.out.println(ic2);
        // 反射破坏单例
        // reflection(InnerClassLazySingleton.class);
        // 反序列化破坏单例
        serializable(InnerClassLazySingleton.getInstance());
        // unsafe 破坏单例
        unsafe(InnerClassLazySingleton.class);
    }

    private static void reflection(Class<?> clazz) throws Exception {
        Constructor<?> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        Object newInstance = constructor.newInstance();
        System.out.println("反射创建的对象：" + newInstance);
    }

    private static void reflectionEnum(Class<?> clazz) throws Exception {
        Constructor<?> constructor = clazz.getDeclaredConstructor(String.class, int.class);
        constructor.setAccessible(true);
        Object newInstance = constructor.newInstance("INSTANCE", 0);
        System.out.println("反射创建的枚举对象：" + newInstance);
    }

    private static void serializable(Object instance) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(instance);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
        System.out.println("反序列化创建的对象：" + ois.readObject());
    }

    private static void unsafe(Class<?> clazz) throws InstantiationException {
        Object o = UnsafeUtils.getUnsafe().allocateInstance(clazz);
        System.out.println("Unsafe 创建的对象：" + o);
    }
}
