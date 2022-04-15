package com.sunchaser.sparrow.designpatterns.gof.creational.singleton;

import org.springframework.objenesis.instantiator.util.UnsafeUtils;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 单例模式客户端Client
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/10/7
 */
public class Client {
    public static void main(String[] args) throws Exception {
        // System.out.println(HungrySingleton.getInstance());
        // System.out.println(HungrySingleton.getInstance());
        // reflection(EnumHungrySingleton.class);
        // serializable(HungrySingleton.getInstance());
        // unsafe(HungrySingleton.class);

        System.out.println(EnumHungrySingleton.getInstance());
        System.out.println(EnumHungrySingleton.getInstance());
        reflectionEnum(EnumHungrySingleton.class);
        // serializable(EnumHungrySingleton.getInstance());
        // unsafe(EnumHungrySingleton.class);
    }

    private static void reflection(Class<?> clazz) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<?> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        Object newInstance = constructor.newInstance();
        System.out.println("反射创建的对象：" + newInstance);
    }

    private static void reflectionEnum(Class<?> clazz) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<?> constructor = clazz.getDeclaredConstructor(String.class, int.class);
        constructor.setAccessible(true);
        Object newInstance = constructor.newInstance("INSTANCE", 0);
        System.out.println("反射创建的枚举对象：" + newInstance);
    }

    private static void serializable(Object instance) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(instance);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
        System.out.println("反序列化创建的对象：" + ois.readObject());
    }

    private static void unsafe(Class<?> clazz) throws InstantiationException {
        Object o = UnsafeUtils.getUnsafe().allocateInstance(clazz);
        System.out.println("Unsafe创建的对象：" + o);
    }
}
