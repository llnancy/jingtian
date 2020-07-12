## 队列简述

队列是一种特殊的线性表，它具有先进先出（`FIFO`）的特点，只允许在表的前端（`front`）进行删除操作，而在表的后端（`rear`）进行插入操作。进行插入操作的端称为队尾，进行删除操作的端称为队头。队列中没有元素时，称为空队列。

## `Queue`接口简述

`Queue`接口是`Java`中的队列定义，它也是`Java`容器中的一员，继承自`java.util.Collection`接口。`Queue`接口在`Collection`接口的基础上定义了三组操作：插入、删除和提取。每组操作都包含两种形式：一种在操作失败时抛出异常；另一种返回特殊的值（`null`或`false`）。

## 接口详解


action | throws exception | returns special value
:--- | :---| :---
insert | add(e) | offer(e)
remove | remove() | poll()
get head | element() | peek()

### 入队列

```java
    boolean add(E e);
```

插入指定元素至队尾，如果成功则返回`true`，如果达到了当前队列的最大容量限制，则抛出`IllegalStateException`异常。

```java
    boolean offer(E e);
```

同样是插入指定元素至队尾，如果成功则返回`true`，如果达到了当前队列的最大容量限制，则返回`false`。不会抛出异常。

所以，当使用容量受限的队列时，通常使用`offer`方法插入元素。

### 出队列

```java
    E remove();
```

删除队首第一个元素，如果为空队列，则抛出`NoSuchElementException`异常。

```java
    E poll();
```

同样是删除队首第一个元素，如果为空队列，则返回`null`。不会抛出异常。

### 查询队首元素

```java
    E element();
```

获取队首元素，如果为空队列，则抛出`NoSuchElementException`异常。

```java
    E peek();
```

同样是获取队首元素，如果为空队列，则返回`null`。不会抛出异常。

## 总结

`Queue`接口实现通常不允许插入`null`元素，尽管某些实现（例如`LinkedList`）可以插入`null`元素，也不应将`null`插入队列中，因为`poll`和`peek`方法会在队列为空队列时返回特殊值`null`，会产生歧义。