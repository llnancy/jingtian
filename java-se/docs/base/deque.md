## 双端队列简述

双端队列是一种更特殊的线性表。普通队列`Queue`只允许在队首删除元素，在队尾插入元素。而双端队列同时允许在队首和队尾插入和删除元素。

## `Deque`接口简述

`Deque`接口是`Java`中的双端队列定义，继承`java.util.Queue`接口，它提供了操作队首和队尾的六组方法：插入队首/队尾元素、删除队首/队尾元素、提取队首/队尾元素。同`Queue`接口一样，每组操作都包含两种形式：一种在操作失败时抛出异常；另一种返回特殊的值（`null`或`false`）。

当把双端队列当做普通队列使用时，将出现`FIFO`（先进先出）行为。继承自`Queue`接口的方法等同于`Deque`接口中的特定方法。

双端队列也可用做`LIFO`（后进先出）堆栈。应该优先选用此接口，尽量不要使用旧版`java.util.Stack`类（继承自同步集合`Vector`）。

## 接口详解

双端队列操作接口：

<table style="vertical-align:middle; text-align:center;">
	<tr>
	    <th>操作位置</th>
		<th colspan="2">队首（Head）</th>
		<th colspan="2">队尾（Tail）</th>
	</tr>
	<tr>
	    <th>响应</th>
		<th>throws exception</th>
		<th>special value</th>
		<th>throws exception</th>
		<th>special value</th>
	</tr>
	<tr>
	    <td>insert</td>
		<td>addFirst(e)</td>
		<td>offerFirst(e)</td>
		<td>addLast(e)</td>
		<td>offerLast(e)</td>
	</tr>
	<tr>
	    <td>remove</td>
		<td>removeFirst()</td>
		<td>pollFirst()</td>
		<td>removeLast()</td>
		<td>pollLast()</td>
	</tr>
	<tr>
	    <td>get element</td>
		<td>getFirst()</td>
		<td>peekFirst()</td>
		<td>getLast()</td>
		<td>peekLast()</td>
	</tr>
</table>

### 入队列

#### 入队首

```java
    void addFirst(E e);
```

插入指定元素至队首，如果成功则返回`true`，如果达到了当前队列的最大容量限制，则抛出`IllegalStateException`异常。

```java
    boolean offerFirst(E e);
```

同样是插入指定元素至队首，如果成功则返回`true`，如果达到了当前队列的最大容量限制，则返回`false`。不会抛出异常。

所以，当使用容量受限的队列时，通常使用`offerFirst`方法插入指定元素至队首。

#### 入队尾

```java
    void addLast(E e);
```

插入指定元素至队尾，如果成功则返回`true`，如果达到了当前队列的最大容量限制，则抛出`IllegalStateException`异常。

```java
    boolean offerLast(E e);
```

同样是插入指定元素至队尾，如果成功则返回`true`，如果达到了当前队列的最大容量限制，则返回`false`。不会抛出异常。

所以，当使用容量受限的队列时，通常使用`offerLast`方法插入指定元素至队尾。

### 出队列

#### 从队首出队列

```java
    E removeFirst();
```

删除队首第一个元素，如果为空队列，则抛出NoSuchElementException异常。

```java
    E pollFirst();
```

同样是删除队首第一个元素，如果为空队列，则返回`null`。不会抛出异常。

### 从队尾出队列

```java
    E removeLast();
```

删除队尾第一个元素，如果为空队列，则抛出NoSuchElementException异常。

```java
    E pollLast();
```

同样是删除队尾第一个元素，如果为空队列，则返回`null`。不会抛出异常。

### 查询队列元素

#### 查询队首元素

```java
    E getFirst();
```

获取队首元素，如果为空队列，则抛出`NoSuchElementException`异常。

```java
    E peekFirst();
```

同样是获取队首元素，如果为空队列，则返回`null`。不会抛出异常。

#### 查询队尾元素

```java
    E getLast();
```

获取队尾元素，如果为空队列，则抛出`NoSuchElementException`异常。

```java
    E peekLast();
```

同样是获取队尾元素，如果为空队列，则返回`null`。不会抛出异常。

### 删除指定元素

#### 删除第一次出现的指定元素

```java
    boolean removeFirstOccurrence(Object o);
```

从此双端队列中删除第一次出现的指定元素`e`。如果从未出现，则队列保持不变。判断队列中的元素与指定元素相等的条件为：`o == null ? e == null : o.equals(e)`。

#### 删除最后一次出现的指定元素

```java
    boolean removeLastOccurrence(Object o);
```

从此双端队列中删除最后一次出现的指定元素`e`。如果从未出现，则队列保持不变。判断队列中的元素与指定元素相等的条件为：`o == null ? e == null : o.equals(e)`。

### 作为普通队列

该双端队列`Deque`接口同样提供了普通队列`Queue`的六个操作方法。用作普通队列时，产生`FIFO`（先进先出）行为，其方法等同于上述双端队列的特定方法。下面是它们的对应关系：


Queue Method | Equivalent Deque Method
:--- | :---
add(e) | addLast(e)
offer(e) | offerLast(e)
remove() | removeFirst()
poll() | pollFirst()
element() | getFirst()
peek() | peekFirst()

虽然`Deque`接口也提供了普通队列的六个操作方法，但我们尽量不要去使用它们，而是使用其对应的方法。

例如入队列，最好不要调用`offer(e)`，而是调用`offerLast(e)`。

因为当使用`offer(e)`时，我们还需要思考，`offer(e)`实际上就是`offerLast(e)`：从队尾入队列。如果我们直接调用`offerLast(e)`，一眼就能看出是从队尾入队列。

所以，使用`Deque`接口时，推荐明确使用`xxxLast`/`xxxFirst`这类方法。

### 作为栈

双端队列可以当做栈（`LIFO`后进先出）来使用。它提供了遗留类`Stack`中对栈的基本操作方法。这些方法等同于上述双端队列的特定方法。下面是它们的对应关系：

Stack Method | Equivalent Deque Method
:--- | :---
push(e) | addFirst(e)
pop() | removeFirst()
peek() | peekFirst()

当做栈使用时，尽量使用栈的方法：`push(e)`/`pop`/`peek`，不要调用其对应的方法。

因为遗留类`Stack`已经存在了，考虑到类库兼容性问题无法将其覆盖，所以在`Deque`接口中定义了栈的方法，当我们把`Deque`当做栈使用时，直接调用栈的方法会使语义更加明确。

### 作为集合

由于`Deque`接口继承了`Queue`接口，而`Queue`接口又继承了`Collection`接口，所以`Deque`也可作为集合使用。它也定义了一些集合的基本操作。

判断指定元素`o`与双端队列中元素`e`相等的条件为：`o == null ? e == null : o.equals(e)`。

#### 删除第一次出现的指定元素

```java
    boolean remove(Object o);
```

与`Collection`接口中定义的类似，删除指定元素在集合中的第一次出现。实际上，该方法等同与`removeFirstOccurrence(o)`方法。

#### 判断是否包含指定元素

```java
    boolean contains(Object o);
```

该双端队列中至少包含一个指定的元素`o`。

#### 查询元素个数

```java
    public int size();
```

返回该双端队列中的元素个数。

#### 迭代器

提供了两个不同元素顺序的迭代器。

##### 从队首到队尾的迭代器

```java
    Iterator<E> iterator();
```

迭代器中的元素顺序为双端队列从队首到队尾的元素顺序。

##### 从队尾到队首的迭代器

```java
    Iterator<E> descendingIterator();
```

以相反的顺序返回迭代器。

## 总结

`Deque`接口定义了一个双端队列（`Double Ended Queue`），它可以从队首或队尾操作元素；也可以当做普通队列`Queue`使用；同时还可以当做栈`Stack`来使用。