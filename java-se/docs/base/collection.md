## 简介
`Collection`接口是`Java`容器类库中的顶层接口之一。它概括了序列的概念：一种存放一组对象的方式。

`JDK`不提供`Collection`接口的直接实现，而是提供更特殊的子接口的实现，比如`Set`和`List`。该接口通常用于传递集合并在需要最大通用性的地方操纵它们。

## 特性
所有通用的`Collection`接口实现类（通常通过其子接口之一来间接实现）应该提供两个标准构造器：

- `void`无参构造器：创建一个空集合。
- 带有`Collection`类型的单个参数的构造器：创建一个新集合，该集合具有与参数相同的元素。

事实上，后一个构造器允许我们复制任何集合，从而产生所需实现类型的等效集合。

由于接口无法包含构造器，所以无法强制执行此规范，但是`Java`平台库中的所有通用`Collection`实现都遵循了该规范。

此接口中包含一些“破坏性”方法：修改集合的操作。如果实现类不支持，将抛出`UnsupportedOperationException`异常。在这种情况下，如果调用对集合没有影响，则可以但不强制要求这些方法抛出`UnsupportedOperationException`异常。例如，对不可修改的集合调用`addAll(Collection c)`方法时，如果待添加的集合`c`为空，则可以但不是必须抛出异常。

有些集合实现对它们可能包含的元素有限制。例如，有些实现禁止`null`元素，而有些实现对其元素类型有限制。尝试添加不合法元素会引发未经检查的异常，通常为`NullPointerException`或`ClassCastException`。尝试查询不合法元素的存在可能会引发异常，或者可能仅返回`false`。一些实现类将表现出前者的行为，而某些实现类则表现出后者的行为。更一般的，尝试对不合法元素进行操作，该操作的完成不会导致不合法元素插入集合中，这可能会抛出异常，或者可能会成功，一切都取决于实现类。对于`Collection`接口的规范来说，这些异常是可选的，并未显式抛出。

该接口中的许多方法都是根据`Object#equals(Object o)`方法的术语定义的。例如以下方法：

```java
boolean contains(Object o);
```

此接口的定义为：当且仅当此集合中至少包含一个元素`e`，使得`o == null ? e == null : o.equals(e)`成立。

不应理解为对集合中所有元素都调用`equals`方法，找到一个满足条件的就可以返回。实现类可以自由进行优化，从而避免`equals`频繁调用。例如，可以先比较两个元素的哈希码（`Object#hashCode()`方法的规范保证了具有不相等哈希码的两个对象一定不相等。）

简单的说，各种`Collection`接口实现类都可以自由利用基础方法（`Object`类的方法），只要实现类认为合适即可。

## 接口签名
### 查询操作

- `int size()`：返回集合中的元素个数，最多为`Integer.MAX_VALUE`。
- `boolean isEmpty()`：如果集合中不包含任何元素则返回`true`。
- `boolean contains(Object o)`：当且仅当此集合中至少包含一个元素`e`使得`o == null ? e == null : o.equals(e)`成立时返回`true`。
- `Iterator<E> iterator()`：返回此集合中元素的迭代器。
- `Object[] toArray()`：返回包含此集合中所有元素的数组。如果此集合对由其迭代器返回元素的顺序进行了任何保证，则此方法必须按相同的顺序返回元素。操作返回的数组不会对原集合产生任何影响（安全）。此方法充当基于数组的`API`和基于集合的`API`之间的桥梁。

### 修改操作

- `boolean add(E e)`：插入元素`e`至集合中，如果此集合由于调用该方法发生更改，则返回`true`；如果集合不允许重复元素，且已经包含`e`，则返回`false`。
- `boolean remove(Object o)`：从集合中删除元素`o`。需满足条件：`o == null ? e == null : o.equals(e)`。如果存在多个满足条件的元素，则只会删除先找到的那个。

### 批量操作

- `boolean containsAll(Collection<?> c)`：如果集合中包含指定集合`c`中的所有元素，则返回`true`。
- `boolean addAll(Collection<? extends E> c)`：将指定集合`c`中的所有元素添加至当前集合中。如果在操作过程中修改了指定的集合`c`，则此操作的行为是不确定的。
- `boolean removeAll(Collection<?> c)`：删除所有包含在指定集合`c`中的元素。此调用返回后，当前集合中将不包含与指定集合相同的元素。
- `boolean retainAll(Collection<?> c)`：仅保留此集合中包含在指定集合`c`中的元素。换句话说，此操作完成后，当前集合与指定集合中的元素完全相同。
- `void clear()`：删除此集合中的全部元素。

### 比较和哈希
该接口重新声明了`Object#equals`和`Object#hashCode`方法，强制子类必须实现这两个方法。

`Collection`接口要求`hashCode`方法返回该集合的哈希码；`equals`方法比较指定的对象与该集合是否相等。

### `JDK8`新增默认方法

`JDK8`的`StreamAPI`提供的两个方法。在此顶层接口中定义默认实现，表示所有的集合都可以使用流操作。

#### 获取串行流

```java
    default Stream<E> stream() {
        return StreamSupport.stream(spliterator(), false);
    }
```

#### 获取并行流

```java
    default Stream<E> parallelStream() {
        return StreamSupport.stream(spliterator(), true);
    }
```