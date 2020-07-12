## 简介

`AbstractSequentialList`类是一个抽象类，它继承了`AbstractList`抽象类。同样提供了有序集合的骨架实现，但是它是最大程度地减少了支持顺序访问数据的实现类所需的工作。如果需要随机访问数据，优先使用`AbstractList`抽象类。

从某种意义上说，该类与`AbstractList`抽象类相反，该类实现了随机访问方法，而不是像`AbstractList`抽象类一样抛出异常。

## 特性
该抽象类提供了有序集合的骨架实现。让实现类支持顺序访问。

如果要实现集合，只需继承该类并实现该类的`listIterator()`抽象方法和`AbstractCollection#size()`方法；

如果要实现不可修改的集合，只需继承该类并实现列表迭代器`ListIterator`的`hasNext`/`next`/`hasPrevious`/`previous`/`index`方法。

如果要实现可修改的集合，在实现不可修改集合的基础上，需要另外实现列表迭代器的`set`方法；对于可变大小的集合，还需要实现列表迭代器的`remove`和`add`方法。

此外，实现类同样应该遵循`Collection`接口中提出的规范：必须提供两个标准构造器：`void`无参构造器和带`Collection`类型的单个参数的构造器。

## 源码详解

### 构造器

```java
    protected AbstractSequentialList() {
    }
```

只有一个使用`protected`修饰符修饰的无参构造器。说明该抽象类不允许外部直接实例化使用，因为它只是一个骨架实现，并不提供顺序访问集合的完整功能。

### 抽象方法

```java
    public abstract ListIterator<E> listIterator(int index);
```

按正确的顺序返回此列表的列表迭代器，其开始索引为入参`index`。该类中的其它方法都是基于此抽象方法返回的列表迭代器实现的。

### 已实现方法

所有的已实现方法都传入了索引值，对于客户端而言，看似是随机访问，但内部实现却是顺序访问。

#### 获取指定索引位置的元素

```java
    public E get(int index) {
        try {
            return listIterator(index).next();
        } catch (NoSuchElementException exc) {
            throw new IndexOutOfBoundsException("Index: "+index);
        }
    }
```

获取`index`索引开始的列表迭代器后调用其`next`方法获取`index`索引位置元素返回。

#### 用指定元素替换指定索引位置的元素

```java
    public E set(int index, E element) {
        try {
            ListIterator<E> e = listIterator(index);
            E oldVal = e.next();
            e.set(element);
            return oldVal;
        } catch (NoSuchElementException exc) {
            throw new IndexOutOfBoundsException("Index: "+index);
        }
    }
```

获取`index`索引开始的列表迭代器后调用其`next`方法获取`index`索引位置的旧值，然后调用列表迭代器的`set`方法，覆盖`next`方法最后返回的`index`索引位置的元素，最后返回旧值。

#### 在指定索引位置插入指定元素

```java
    public void add(int index, E element) {
        try {
            listIterator(index).add(element);
        } catch (NoSuchElementException exc) {
            throw new IndexOutOfBoundsException("Index: "+index);
        }
    }
```

此实现会将当前位于`index`位置的元素（如果有的话）和后续所有元素右移一位（索引增一）。

首先获取`index`索引开始的列表迭代器，然后调用列表迭代器的`add`方法插入指定的元素。

所以列表迭代器必须实现`add`方法，否则该方法将抛出`UnsupportedOperationException`异常。

#### 删除指定索引位置的元素

```java
    public E remove(int index) {
        try {
            ListIterator<E> e = listIterator(index);
            E outCast = e.next();
            e.remove();
            return outCast;
        } catch (NoSuchElementException exc) {
            throw new IndexOutOfBoundsException("Index: "+index);
        }
    }
```

此实现会将`index`索引位置后续所有元素左移一位（索引减一）。

首先获取`index`索引开始的列表迭代器，然后获取`index`索引位置的旧元素，再调用列表迭代器的`remove`方法删除元素，最后返回旧元素。

所以列表迭代器必须实现`remove`方法，否则该方法将抛出`UnsupportedOperationException`异常。

#### 在指定索引位置插入指定集合

```java
    public boolean addAll(int index, Collection<? extends E> c) {
        try {
            boolean modified = false;
            ListIterator<E> e1 = listIterator(index);
            Iterator<? extends E> e2 = c.iterator();
            while (e2.hasNext()) {
                e1.add(e2.next());
                modified = true;
            }
            return modified;
        } catch (NoSuchElementException exc) {
            throw new IndexOutOfBoundsException("Index: "+index);
        }
    }
```

此实现会将当前位于`index`索引位置及其后续所有元素右移，指定集合中的元素将以其迭代器返回的顺序显示在当前集合中。

分别获取当前集合的列表迭代器和指定集合的普通迭代器，迭代指定集合，依次将元素`add`至当前集合中。

所以列表迭代器必须实现`add`方法，否则该方法将抛出`UnsupportedOperationException`异常。

#### 获取普通迭代器

```java
    public Iterator<E> iterator() {
        return listIterator();
    }
```

直接调用当前类的抽象方法`listIterator()`，返回列表迭代器。

## 总结

该抽象类提供了顺序访问集合的骨架实现，是`LinkedList`集合的直接父类。所有实现依托于该类的`listIterator()`抽象方法。