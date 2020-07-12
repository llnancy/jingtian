## 简介
该接口是`List`实现使用的标记型接口，表明它们支持快速（通常是恒定时间）随机访问。此接口的主要目的是允许通用算法更改其行为，以应用于随机访问列表或顺序访问列表时提供良好的性能。

当应用于顺序访问列表（例如`LinkedList`）时，用于操纵随机访问列表（例如`ArrayList`）的最佳算法会产生二次行为。鼓励使用通用列表算法，先检查给定列表是否为该接口的实例，然后再应用一种算法（如果将其应用于顺序访问列表则性能较差），并在必要时更改其行为以保证可接受的性能。

公认的是，随机访问和顺序访问之间的区别通常是模糊的。例如，某些`List`实现在变得庞大的情况下提供渐进线性的访问时间，但实际上却是恒定的访问时间。这样的`List`实现通常应该实现此接口。根据经验，如果满足以下条件，则`List`实现应实现此接口：

对于类的典型实例，此循环：

```java
for (int i=0, n=list.size(); i < n; i++)
    list.get(i);
```

比这个循环运行更快：

```java
for (Iterator i=list.iterator(); i.hasNext(); )
    i.next();
```

## `ArrayList`效率比较
`ArrayList`实现了`RandomAccess`接口，根据`RandomAccess`接口定义，使用`fori`循环遍历比迭代器遍历更快。下面我们来进行验证。

创建一个`ArrayList`集合并填充一千万个元素，分别使用`fori`循环和迭代器进行遍历，计算其运行时间。完整代码如下：

```java
package com.sunchaser.javase.base.randomaccess;

import java.util.*;

/**
 * 随机访问和迭代器访问效率比较
 * @author sunchaser
 * @date 2020/4/26
 * @since 1.0
 */
public class RandomIteratorCompare {
    public static void main(String[] args) {
        // init list data
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10000000; i++) {
            list.add(String.valueOf(i));
        }
        // random access
        long randomStartTime = System.currentTimeMillis();
        for (int i = 0,size = list.size(); i < size; i++) {
            list.get(i);
        }
        long randomEndTime = System.currentTimeMillis();
        System.out.println("random access:" + (randomEndTime - randomStartTime));
        // sequential access
        long sequentialStartTime = System.currentTimeMillis();
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            iterator.next();
        }
        long sequentialEndTime = System.currentTimeMillis();
        System.out.println("sequential access:" + (sequentialEndTime - sequentialStartTime));
    }
}
```

运行输入：

```
random access:6
sequential access:11
```

结果显而易见，`ArrayList`使用迭代器遍历效率略低于`fori`遍历。

## `LinkedList`效率比较
当应用于`LinkedList`时，用于操纵`ArrayList`的最佳算法（`fori`循环遍历）会产生二次行为。`ListedList`未实现`RandomAccess`接口。

我们来创建一个`LinkedList`，对其填充元素并分别使用`fori`循环和迭代器进行遍历。代码如下：

```java
        // init LinkedList data
        List<String> linkedList = new LinkedList<>();
        for (int i = 0; i < 100000; i++) {
            linkedList.add(String.valueOf(i));
        }
        // random access
        long linkedListRandomStartTime = System.currentTimeMillis();
        for (int i = 0,size = linkedList.size(); i < size; i++) {
            linkedList.get(i);
        }
        long linkedListRandomEndTime = System.currentTimeMillis();
        System.out.println("random access:" + (linkedListRandomEndTime - linkedListRandomStartTime));
        // sequential access
        long linkedListSequentialStartTime = System.currentTimeMillis();
        Iterator<String> linkedListIterator = arrayList.iterator();
        while (linkedListIterator.hasNext()) {
            linkedListIterator.next();
        }
        long linkedListSequentialEndTime = System.currentTimeMillis();
        System.out.println("sequential access:" + (linkedListSequentialEndTime - linkedListSequentialStartTime));
```

运行后控制台输出：

```
random access:11031
sequential access:10
```

可以看到，`fori`循环遍历所耗费的时间已经远远超过了迭代器遍历，这说明适用于`ArrayList`的最佳算法不再适用于`LinkedList`，这与集合的内部数据结构和`get`方法实现有关。

## 总结
实现了`RandomAccess`接口的`ArrayList`使用`fori`循环遍历效率更高，而`LinkedList`使用迭代器遍历效率极高。