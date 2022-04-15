## 前言
什么是序列化和反序列化？

- 序列化：将对象的二进制数据流写入硬盘（或用于网络传输）；
- 反序列化：从硬盘（网络）中将对象的二进制数据流读取出来转化成对象。

## 简介
实现了`java.io.serializable`标记性接口的类是可序列化的，可序列化类的所有子类型都是可序列化的。

## 源码
```java
public interface Serializable {
}
```

在`Java`中序列化接口是一个空接口，仅用于标记可序列化的语义。

## 序列化`UID`字段：`serialVersionUID`
实现了`java.io.Serializable`接口的类，如果未显式进行声明，在编译期`JVM`将使用自己的算法生成默认的`serialVersionUID`字段。

> 默认的`serialVersionUID`生成算法对类的详细信息非常敏感，因不同的`JVM`实现而异，并且在反序列化过程中会可能会导致意外的`java.io.InvalidClassException`异常。

当进行序列化操作时，会将此`serialVersionUID`序列化进二进制流中；

当进行反序列化操作时，如果用来接收对象的类中的`serialVersionUID`字段值与序列化时的值不一致，会导致反序列化失败。

## 代码演示
首先创建一个用来序列化测试的类`SerializableClass`，并实现序列化接口。
```java
package com.sunchaser.javase.base.serizlizable;

import java.io.Serializable;

/**
 * 序列化类
 * @author sunchaser
 * @since JDK8 2020/3/19
 * 
 */
public class SerializableClass implements Serializable {
    private static final long serialVersionUID = 5135631042912401553L;
    private String name;
    private Integer age;

    public String getName() {
        return name;
    }

    public SerializableClass setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public SerializableClass setAge(Integer age) {
        this.age = age;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SerializableClass{");
        sb.append("name='").append(name).append('\'');
        sb.append(", age=").append(age);
        sb.append('}');
        return sb.toString();
    }
}
```

接下来创建测试类来进行序列化。首先在当前类所在包中创建`serializableClass.txt`文件。之后我们序列化写对象写入该文件中，反序列化从该文件中读对象。

使用以下代码即可获取`txt`文件的绝对路径：

```java
// 获取当前类所在包中的serializableClass.txt文件路径
String path = SerializableTest.class.getResource("").getPath();
path = path.replace("target/classes","src/main/java") + "serializableClass.txt";
```

接下来创建用来序列化的对象：

```java
SerializableClass sc = new SerializableClass().setName("序列化").setAge(10);
```

然后我们创建序列化的方法`writeObject`，将对象和`txt`文件绝对路径传入进行写对象操作：

```java
private static void writeObject(SerializableClass sc,String path) {
    FileOutputStream fos = null;
    ObjectOutputStream ops = null;
    try {
        fos = new FileOutputStream(path);
        ops = new ObjectOutputStream(fos);
        ops.writeObject(sc);
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        try {
            if (ops != null) {
                ops.close();
            }
            if (fos != null) {
                fos.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

在`main`方法中调用即可将对象写入文本文件中，可打开文本文件查看（有点乱码）。

下面我们来创建反序列化的方法`readObject`，将文本文件路径传入进行读对象操作：
```java
private static void readObject(String path) {
    FileInputStream fis = null;
    ObjectInputStream ois = null;
    try {
        fis = new FileInputStream(path);
        ois = new ObjectInputStream(fis);
        SerializableClass sc = (SerializableClass) ois.readObject();
        System.out.println(sc);
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        try {
            if (ois != null) {
                ois.close();
            }
            if (fis != null) {
                fis.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

调用该方法后可在控制台看到对象打印：
```
SerializableClass{name='序列化', age=10}
```

### 反序列化失败演示
先调用`writeObject`方法将对象序列化写入文本文件，然后去修改`SerializableClass`的`serialVersionUID`字段的值，再去调用`readObject`方法从文本文件中反序列化读对象。这时反序列化就会失败，并抛出`java.io.InvalidClassException`异常。

主要异常堆栈信息如下：

```
java.io.InvalidClassException: SerializableClass; 

local class incompatible: stream classdesc serialVersionUID = 5135632042912401553, local class serialVersionUID = 5135631042912401553
```

## `IDEA`中生成`serialVersionUID`
打开`IDEA`，选择`File`->`Settings`->`Editor`->`Inspections`，在搜索框中输入`serialVersionUID`，找到`Serializable class without 'serialVersionUID'`，进行勾选，点击`apply`->`OK`进行保存。

设置之后如果实现了`Serializable`接口的类未定义`serialVersionUID`字段，则类名处会有黄色警告，输入光标移动至类名处按下快捷键`alt+enter`会有生成`serialVersionUID`的快捷方式。

## 总结
本文完整代码地址：[传送门](https://github.com/sunchaser-lilu/gold-road-to-Java/tree/master/java-se/src/main/java/com/sunchaser/javase/base/serizlizable)

`Serializable`接口实际上就是一个标记，实现了该接口的类才允许被序列化和反序列化，而`serialVersionUID`字段则像是一个“版本号”，序列化时将该字段一起存至二进制流中，反序列化时用该字段来判断是否是存进去时的状态。它的作用其实就是判断反序列化出来的对象是不是原来序列化的对象。