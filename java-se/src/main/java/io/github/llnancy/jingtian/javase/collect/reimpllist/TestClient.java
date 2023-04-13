package io.github.llnancy.jingtian.javase.collect.reimpllist;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * test client
 *
 * @author sunchaser
 * @since JDK8 2020/4/27
 */
public class TestClient {

    public static void main(String[] args) {
        // [interface MyList]
        showInstance(MyAbstractList.class);
        // [interface java.lang.Cloneable, interface java.io.Serializable]
        showInstance(MyArrayListNoImpl.class);
        // [interface MyList, interface java.lang.Cloneable, interface java.io.Serializable]
        showInstance(MyArrayListImpl.class);

        // proxy
        MyList myList1 = new MyArrayListNoImpl();
        MyList myList2 = new MyArrayListImpl();
        MyList proxy2 = createProxy(myList2);
        // MyArrayListImpl#foo
        proxy2.foo();
        // Exception in thread "main" java.lang.ClassCastException: com.sun.proxy.$Proxy1 cannot be cast to MyList
        MyList proxy1 = createProxy(myList1);
        proxy1.foo();
    }

    private static void showInstance(Class<?> clazz) {
        System.out.printf("%s --- %s\n", clazz, Arrays.toString(clazz.getInterfaces()));
    }

    @SuppressWarnings("unchecked")
    private static <T> T createProxy(final T obj) {
        final InvocationHandler invocationHandler = (proxy, method, args) -> {
            System.out.printf("call %s method on %s object\n", method.getName(), obj);
            return method.invoke(obj, args);
        };
        return (T) Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), invocationHandler);
    }
}
