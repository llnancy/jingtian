## `Cloneable`接口简介
1、一个类实现`java.lang.Cloneable`标记性接口以向`java.lang.Object#clone()`方法指示该方法为该类的实例进行逐域复制是合法的。

2、在未实现`java.lang.Cloneable`接口的实例上调用`java.lang.Object#clone()`方法会导致抛出`java.lang.CloneNotSupportedException`异常。

3、按照约定，实现了`java.lang.Cloneable`接口的类应使用`public`公共方法重写`java.lang.Object#clone()`方法（`protected`受保护的）。

## 克隆的基本使用
由于`java.util.ArrayList`类实现了`Cloneable`接口，我们可以将一个`ArrayList`集合的数据克隆至另一个新的集合中。

代码如下：
```java
package com.sunchaser.javase.base.cloneable;

import com.google.common.collect.Lists;

import java.util.ArrayList;

/**
 * 将一个ArrayList集合的数据克隆至另一个新的集合
 * @author sunchaser
 * @date 2020/4/1
 * @since 1.0
 */
public class ArrayListClone {
    public static void main(String[] args) {
        ArrayList<String> list = Lists.newArrayList();
        list.add("Java大法好");
        list.add("PHP是世界上最好的语言");
        list.add("向日葵的自我修养");
        Object clone = list.clone();
        System.out.println(clone == list);
        System.out.println(clone);
        System.out.println(list);
    }
}
```

代码说明：
1. 创建一个`ArrayList`集合对象；
2. 往其中添加三个元素；
3. 调用集合对象的`clone()`方法；
4. 判断克隆后的集合地址与原集合地址是否相等；
5. 分别打印两个集合的内容。

运行程序，可以看到，克隆后的`ArrayList`集合与旧集合的地址不一样，但其内容相同。

## `ArrayList#clone()`方法源码分析
调用了`list`对象的`clone()`方法，我们来看下其实现：
```java
    public Object clone() {
        try {
            ArrayList<?> v = (ArrayList<?>) super.clone();
            v.elementData = Arrays.copyOf(elementData, size);
            v.modCount = 0;
            return v;
        } catch (CloneNotSupportedException e) {
            // this shouldn't happen, since we are Cloneable
            throw new InternalError(e);
        }
    }
```

可以发现其调用的是`super.clone()`方法得到的一个新的集合。

那么该`super`指代的是哪个父类呢？

- `java.util.ArrayList`的直接父类是`java.util.AbstractList`；
- `java.util.AbstractList`的直接父类是`java.util.AbstractCollection`；
- `java.util.AbstractCollection`是一个抽象类，实现了`java.util.Collection`接口，没有继承任何父类。

但是我们却没有在`java.util.AbstractList`和`java.util.AbstractCollection`类中找到`clone()`方法。

事实上可以直接借助`IDEA`的快捷键得到其是`Object`类的`clone()`方法。

只有`java.util.ArrayList`类中有`clone()`方法，这也是为什么在基本使用的代码中创建的集合，使用`java.util.ArrayList`接收而不用顶层接口`java.util.List`接收的原因。

我们来看下`Object#clone()`方法的源码：

```java
protected native Object clone() throws CloneNotSupportedException;
```

这是一个本地`navive`方法，使用了`protected`访问修饰符，并显式抛出了`java.lang.CloneNotSupportedException`异常。其实现是由底层的`c/c++`语言进行实现的，我们无法从`Java`语言层面看到。

克隆出一个`ArrayList`对象`v`之后，调用工具类`java.util.Arrays`的`copyOf`方法，将原集合的数据`elementData`和集合大小`size`传入，拷贝集合元素至克隆集合`v`的`elementData`域中。

我们来看下`java.util.Arrays#copyOf()`方法的实现：

```java
    public static <T> T[] copyOf(T[] original, int newLength) {
        return (T[]) copyOf(original, newLength, original.getClass());
    }
    
    public static <T,U> T[] copyOf(U[] original, int newLength, Class<? extends T[]> newType) {
        @SuppressWarnings("unchecked")
        T[] copy = ((Object)newType == (Object)Object[].class)
            ? (T[]) new Object[newLength]
            : (T[]) Array.newInstance(newType.getComponentType(), newLength);
        System.arraycopy(original, 0, copy, 0,
                         Math.min(original.length, newLength));
        return copy;
    }
```

`copyOf`方法内部调用的是另一个重载的`copyOf`方法，判断传入的集合数据`elementData`类型是不是`Object[]`数组类型，如果是`Object`类型则使用`new`关键字创建一个新的`Object[]`数组，否则创建其它类型的数组，最后调用`System.arraycopy`方法将传入的数组元素拷贝至新建的数组中返回。

返回了新的数组之后，将克隆出来的集合`v`的`elementData`域引用指向新的数组地址，然后设置修改次数`modCount`字段为`0`，得到一个全新的克隆集合返回。

## 总结
`Java`中所有类都是从`java.lang.Object`类继承而来的，而`Object`类提供`protected Object clone()`方法对对象进行克隆，子类必须实现标记型接口`java.lang.Cloneable`才支持克隆，当然子类也可以重写`Object#clone()`方法来实现自己的克隆方式。

对象的克隆有一个基本问题：对象可能包含对其它对象的引用，当使用`Object#clone()`方法来克隆对象时，此对象对其它对象的引用也被克隆了一份（注意：这里克隆的是引用）。

`java.lang.Cloneable`接口只起一个作用，就是在运行期指示虚拟机在当前类使用`Object#clone()`方法是合法的。通过克隆可以得到一个对象的复制。但由于`Object`类并未实现`java.lang.Cloneable`接口，所以被克隆的类如果未实现`java.lang.Cloneable`接口，在调用`java.lang.Object#clone()`方法时会抛出`java.lang.CloneNotSupportedException`异常。