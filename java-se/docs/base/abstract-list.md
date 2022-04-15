## 简介
`AbstractList`类是一个抽象类，它继承了`AbstractCollection`抽象类并实现了`List`接口。`List`接口定义了一种有序集合，可以精确控制每个元素的插入位置；而`AbstractCollection`抽象类提供了两个抽象方法，基于这两个抽象方法提供出了集合的最基本实现。

所以，`AbstractList`抽象类提供的是有序集合的骨架实现，用以最大程度地减少支持随机访问数据（例如数组）的实现类所需的工作。而对于顺序访问数据（例如链表），应该优先使用`AbstractSequentialList`抽象类。

## 特性
该抽象类提供了有序集合的骨架实现。让实现类支持随机快速访问。

如果要实现不可修改的有序集合，只需继承该类并为`get`和`size`方法提供实现；如果要实现可修改的有序集合，在实现不可修改的有序集合基础之上，还要另外重写此类的两个重载的`set`方法（否则将抛出`UnsupportedOperationException`异常），如果集合的大小是可变的，还要再另外重写两个重载的`add`方法和一个`remove`方法。

此外，实现类同样应该遵循`Collection`接口中提出的规范：必须提供两个标准构造器：`void`无参构造器和带`Collection`类型的单个参数的构造器。

与其它抽象集合实现不同，此类的实现类不是必须要提供迭代器实现；此类的迭代器和列表迭代器是在“随机访问”方法之上由此类自身进行实现的。

“随机访问”方法（索引访问）有：

- `get(int)`
- `set(int, Object)`
- `set(int, E)`
- `add(int Object)`
- `add(int, E)`
- `remove(int)`

## 源码详解

### 构造器
只有一个使用`protected`修饰符修饰的无参构造器。说明该抽象类不允许外部直接实例化使用，因为它只是一个骨架实现，并不提供有序集合的完整功能。

```java
protected AbstractList() {
}
```

### 成员变量

```java
protected transient int modCount = 0;
```

只有一个成员变量，表示该列表被结构修改的次数。

结构修改是指：更改列表大小或以其它方式干扰列表。

此字段由`iterator`和`listIterator`方法返回的迭代器使用。如果在迭代过程中修改了该字段的值，则迭代器将在`next`、`remove`和`previous`方法中抛出`ConcurrentModificationException`异常。这里就是`Java`普通集合的`fail-fast`机制。否则将出现不确定的行为。

子类对该字段的使用是可选的，当子类需要提供具有`fail-fast`机制的迭代器（或列表迭代器）时，只需在其`add`和`remove`方法中修改该字段的值，一次调用`add`或`remove`方法必须修改该值且不超过`1`。否则迭代器将抛出`ConcurrentModificationException`异常；当子类不需要提供具有`fail-fast`机制的迭代器时，可直接忽略该字段。

### 抽象方法

该类中只包含一个抽象方法，用于支持随机快速访问（根据索引取值）。

```java
    abstract public E get(int index);
```

### 已实现方法

所有的实现方法都是基于该类的抽象方法或其它已实现方法，大部分实现是基于迭代器的。部分方法中直接抛出`UnsupportedOperationException`异常，这样的实现表示该方法需要子类进行重写实现。总的来看，该类提供了有序集合的模板实现。下面我们来具体的分析每个方法。

#### 快速访问

##### 添加指定元素至列表末尾

```java
    public boolean add(E e) {
        add(size(), e);
        return true;
    }
```

调用了重载的`add`方法，传入`size()`方法的返回值和指定元素，然后返回`true`。

只要重载的`add`方法中不出现异常，则该`add`方法始终返回`true`。

需要注意的是，支持此操作的列表可能会对可以添加至列表的元素进行限制。例如，某些列表拒绝添加`null`元素，而另一些对可能添加的元素进行类型限制。所以实现类应在其文档中明确指出对可以添加的元素有哪些限制。

##### 在指定索引位置添加指定元素

```java
    public void add(int index, E element) {
        throw new UnsupportedOperationException();
    }
```

该方法中直接抛出`UnsupportedOperationException`异常，是必须要子类重写进行具体实现的。

##### 用指定元素来覆盖指定索引位置的值

```java
    public E set(int index, E element) {
        throw new UnsupportedOperationException();
    }
```

该方法中直接抛出`UnsupportedOperationException`异常，是必须要子类重写进行具体实现的。

##### 添加指定元素至指定索引位置

```java
    public void add(int index, E element) {
        throw new UnsupportedOperationException();
    }
```

该方法中直接抛出`UnsupportedOperationException`异常，是必须要子类重写进行具体实现的。

##### 删除指定索引位置的元素

```java
    public E remove(int index) {
        throw new UnsupportedOperationException();
    }
```

该方法中直接抛出`UnsupportedOperationException`异常，是必须要子类重写进行具体实现的。

#### 查询操作

##### 查询指定元素在列表中第一次出现的索引位置

```java
    public int indexOf(Object o) {
        ListIterator<E> it = listIterator();
        if (o==null) {
            while (it.hasNext())
                if (it.next()==null)
                    return it.previousIndex();
        } else {
            while (it.hasNext())
                if (o.equals(it.next()))
                    return it.previousIndex();
        }
        return -1;
    }
```

该方法的实现为：首先调用该类的`listIterator()`方法获取一个指向列表开头的列表迭代器，然后，从前往后遍历列表，直到第一次找到指定的元素或到达列表的末尾（返回`-1`）。

##### 查询指定元素在列表中最后一次出现的索引位置

```java
    public int lastIndexOf(Object o) {
        ListIterator<E> it = listIterator(size());
        if (o==null) {
            while (it.hasPrevious())
                if (it.previous()==null)
                    return it.nextIndex();
        } else {
            while (it.hasPrevious())
                if (o.equals(it.previous()))
                    return it.nextIndex();
        }
        return -1;
    }
```

该方法的实现为：首先调用该类的`listIterator()`方法传入`size()`方法的返回值（该列表的大小）获取一个指向列表末尾的列表迭代器，然后，从后往前遍历列表，直到第一次找到指定的元素或到达列表的开头（返回`-1`）。

#### 批量操作

##### 清空列表

```java
    public void clear() {
        removeRange(0, size());
    }
```

删除此列表中的所有元素，内部调用的是`removeRange`方法。子类必须重写本类的`remove`或`removeRange`方法，否则将抛出`UnsupportedOperationException`异常。

我们来看下`removeRange`方法的实现：

```java
    protected void removeRange(int fromIndex, int toIndex) {
        ListIterator<E> it = listIterator(fromIndex);
        for (int i=0, n=toIndex-fromIndex; i<n; i++) {
            it.next();
            it.remove();
        }
    }
```

从该列表中删除索引介于`fromIndex`和`toIndex`之间的元素，将`toIndex`索引后的所有元素左移。当`fromIndex`等于`toIndex`时，此方法不会做任何操作。

入参有两个：

- `fromIndex`：要删除的第一个元素的索引；
- `toIndex`：要删除的最后一个元素之后的索引。

首先调用`listIterator(fromIndex)`方法获取位于`fromIndex`之前的列表迭代器，然后迭代索引区间为`[0,toIndex-fromIndex)`的元素，在每次迭代过程中，调用`ListIterator.next`方法获取下一个元素，然后调用`ListIterator.remove`方法删除当前迭代索引元素，直到删除了整个范围内的元素为止。

如果`ListIterator.remove`方法的时间复杂度为`O(n)`，那么该方法的时间复杂度为`O(n^2)`。

##### 将指定集合从指定索引位置开始插入

```java
    public boolean addAll(int index, Collection<? extends E> c) {
        rangeCheckForAdd(index);
        boolean modified = false;
        for (E e : c) {
            add(index++, e);
            modified = true;
        }
        return modified;
    }
```

将指定集合`c`从索引为`index`的位置开始插入，需要子类实现`add(int,E)`方法，否则将抛出`UnsupportedOperationException`异常。

首先调用`rangeCheckForAdd(index)`方法校验索引范围（区间为`[0,size())`），然后迭代指定集合`c`，使用`add(int,E)`方法进行插入。

一般来说，子类会选择重写该方法来提高批量插入效率。

#### 迭代器

##### 普通迭代器

```java
    public Iterator<E> iterator() {
        return new Itr();
    }
```

直接创建内部类`Itr`的实例对象返回。我们来看下这个内部类`Itr`：

```java
private class Itr implements Iterator<E> {
        /**
         * Index of element to be returned by subsequent call to next.
         */
        int cursor = 0;

        /**
         * Index of element returned by most recent call to next or
         * previous.  Reset to -1 if this element is deleted by a call
         * to remove.
         */
        int lastRet = -1;

        /**
         * The modCount value that the iterator believes that the backing
         * List should have.  If this expectation is violated, the iterator
         * has detected concurrent modification.
         */
        int expectedModCount = modCount;

        public boolean hasNext() {
            return cursor != size();
        }

        public E next() {
            checkForComodification();
            try {
                int i = cursor;
                E next = get(i);
                lastRet = i;
                cursor = i + 1;
                return next;
            } catch (IndexOutOfBoundsException e) {
                checkForComodification();
                throw new NoSuchElementException();
            }
        }

        public void remove() {
            if (lastRet < 0)
                throw new IllegalStateException();
            checkForComodification();

            try {
                AbstractList.this.remove(lastRet);
                if (lastRet < cursor)
                    cursor--;
                lastRet = -1;
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException e) {
                throw new ConcurrentModificationException();
            }
        }

        final void checkForComodification() {
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
        }
    }
```

有三个成员变量：

- `cursor`：游标，初始化为`0`，后续调用该内部类的`next`方法返回的元素索引。
- `lastRet`：初始化为`-1`，最近一次调用`next`或`previous`方法返回的元素索引。如果一次调用中删除了当前指向的元素，则将其重置为`-1`。
- `expectedModCount`：期望修改次数，初始化为外部类`AbstractList`的成员变量`modCount`，迭代器认为后续迭代过程中列表应该具有的`modCount`值，如果期望值与实际`modCount`值不相等，则出现了并发修改的情况。

有三个公共方法：

1、判断是否还有下一个元素：

```java
    public boolean hasNext() {
        return cursor != size();
    }
```

判断当前游标是否等于列表大小。`size()`方法为父类`AbstractCollection`中的抽象`size()`方法。

2、获取当前游标索引指向的元素：

```java
    public E next() {
        checkForComodification();
        try {
            int i = cursor;
            E next = get(i);
            lastRet = i;
            cursor = i + 1;
            return next;
        } catch (IndexOutOfBoundsException e) {
            checkForComodification();
            throw new NoSuchElementException();
        }
    }
```

首先调用当前内部类的`checkForComodification()`方法校验期望修改次数`expectedModCount`是否等于实际修改次数`modCount`。如果不相等，则已出现并发修改，抛出`ConcurrentModificationException`异常。

然后调用外部类`AbstractList`的抽象方法`get(int)`根据当前游标索引取值，将当前游标值赋给成员变量`lastRet`，当前游标右移一位，最后返回取到的元素。

先校验`modCount`的值，如果在校验通过且代码执行到`get(i)`之前时有其它线程修改了当前列表的大小，则可能会出现索引越界`IndexOutOfBoundsException`的异常，此时将其捕获，在`catch`块中再次进行`modCount`校验，如果校验不通过，则会直接抛出`ConcurrentModificationException`异常；但可能在这次校验之前又有其它线程将当前列表还原了，此时校验又会通过，所以在校验之后主动抛出`NoSuchElementException`异常。

3、删除最近一次通过`next`方法获取到的元素：

```java
    public void remove() {
        if (lastRet < 0)
            throw new IllegalStateException();
        checkForComodification();

        try {
            AbstractList.this.remove(lastRet);
            if (lastRet < cursor)
                cursor--;
            lastRet = -1;
            expectedModCount = modCount;
        } catch (IndexOutOfBoundsException e) {
            throw new ConcurrentModificationException();
        }
    }
```

首先如果`lastRet`小于`0`，则该迭代器的`next`方法未先调用，抛出`IllegalStateException`异常。

然后检查`expectedModCount`与`modCount`是否相等，不相等则出现并发修改，抛出`ConcurrentModificationException`异常。

接下来调用外部类`AbstractList`的`remove`方法，删除上次通过`next`方法获取到的元素。所以`AbstractList`的子类必须重写`AbstractList`类的`remove`方法。

删除元素之后，将游标左移一位，此时指向的是删除的元素之后的第一个元素。然后重置`lastRet=-1`，重新将修改过后的`modCount`赋给`expectedModCount`。

> 解释：
>
> 例如列表`A,B,C`。
>
> 初始状态：初始游标`cursor`为`0`指向元素`A`，`lastRet=-1`；
>
> 第一次调用`next`方法：游标`cursor`右移一位为`1`指向元素`B`，`lastRet=0`；
>
> 此时调用`AbstractList`的`remove`方法：删除索引为`lastRet=0`的元素`A`，`lastRet=0 < cursor=1`，游标左移一位为`0`指向元素`B`，重置`lastRet=-1`并将`modCount`重新赋值给`expectedModCount`。
>
> 由于调用过外部类`AbstractList`的`remove`方法，此时的`modCount`值已经修改。

##### 列表迭代器

```java
    public ListIterator<E> listIterator() {
        return listIterator(0);
    }
```

直接调用`listIterator(0)`方法返回，我们来看下该方法：

```java
    public ListIterator<E> listIterator(final int index) {
        rangeCheckForAdd(index);

        return new ListItr(index);
    }
```

首先校验传入索引`index`范围，然后直接一个返回`ListItr`的实例对象。我们来看下`ListItr`的实现：

```java
private class ListItr extends Itr implements ListIterator<E> {
        ListItr(int index) {
            cursor = index;
        }

        public boolean hasPrevious() {
            return cursor != 0;
        }

        public E previous() {
            checkForComodification();
            try {
                int i = cursor - 1;
                E previous = get(i);
                lastRet = cursor = i;
                return previous;
            } catch (IndexOutOfBoundsException e) {
                checkForComodification();
                throw new NoSuchElementException();
            }
        }

        public int nextIndex() {
            return cursor;
        }

        public int previousIndex() {
            return cursor-1;
        }

        public void set(E e) {
            if (lastRet < 0)
                throw new IllegalStateException();
            checkForComodification();

            try {
                AbstractList.this.set(lastRet, e);
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        public void add(E e) {
            checkForComodification();

            try {
                int i = cursor;
                AbstractList.this.add(i, e);
                lastRet = -1;
                cursor = i + 1;
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }
    }
```

该类继承了内部类`Itr`并实现了`ListIterator`接口。

继承得到了`Itr`类的三个属性：`cursor`、`lastRet`和`expectedModCount`。

只有一个带参构造器：

```java
    ListItr(int index) {
        cursor = index;
    }
```

让游标指向传入的索引`index`。

重写了`ListIterator`接口的六个方法，我们来依次看下实现：

1、判断是否有前一个元素

```java
    public boolean hasPrevious() {
        return cursor != 0;
    }
```

直接判断当前游标`cursor`是否指向`0`，为`0`表示指向列表开始位置，无前一个元素。

2、获取前一个元素

```java
    public E previous() {
        checkForComodification();
        try {
            int i = cursor - 1;
            E previous = get(i);
            lastRet = cursor = i;
            return previous;
        } catch (IndexOutOfBoundsException e) {
            checkForComodification();
            throw new NoSuchElementException();
        }
    }
```

获取当前游标指向索引的前一位`i`，获取其元素值`previous`，将索引`i`赋值给游标`cursor`和最后返回元素索引`lastRet`，最后返回元素`previous`。校验逻辑同`Itr`迭代器的`next`方法。

3、获取下一个即将迭代元素索引

```java
    public int nextIndex() {
        return cursor;
    }
```

直接返回游标`cursor`。

4、获取即将迭代元素的前一个元素的索引

```java
    public int previousIndex() {
        return cursor-1;
    }
```

直接返回游标`cursor`减一。

5、用指定元素替换最近一次迭代返回的元素

```java
    public void set(E e) {
        if (lastRet < 0)
            throw new IllegalStateException();
        checkForComodification();

        try {
            AbstractList.this.set(lastRet, e);
            expectedModCount = modCount;
        } catch (IndexOutOfBoundsException ex) {
            throw new ConcurrentModificationException();
        }
    }
```

仅能在上次调用`next`或`previous`方法之后没有调用迭代器的`remove`或`add`方法的情况下才能调用。

调用了外部抽象类`AbstractList`的`set`方法将`lastRet`索引指向的元素替换成指定元素`e`，然后将实际`modCount`赋给期望修改次数`expectedModCount`。校验逻辑同`Itr`迭代器的`remove`方法。

6、插入指定元素至列表

```java
    public void add(E e) {
        checkForComodification();

        try {
            int i = cursor;
            AbstractList.this.add(i, e);
            lastRet = -1;
            cursor = i + 1;
            expectedModCount = modCount;
        } catch (IndexOutOfBoundsException ex) {
            throw new ConcurrentModificationException();
        }
    }
```

将指定元素`e`插入列表，该元素的插入位置为：`next`方法返回的元素（如果有元素）之前和`previous`方法返回的元素（如果有元素）之后。如果列表中本来不包含任何元素，则指定元素`e`将成为列表中的唯一元素。调用此方法后，`nextIndex`和`previousIndex`方法的调用返回值将增加一。

首先获取当前游标指向索引`i`，然后调用外部类`AbstractList`的`add`方法将指定元素`e`插入指定索引`i`位置。将`lastRet`重置为`-1`，游标`cursor`增一，即下次调用`next`方法将返回指定元素`e`之后的元素，而调用`previous`方法将返回指定元素`e`。最后将实际`modCount`赋给期望修改次数`expectedModCount`。校验逻辑同`set`方法。

#### 比较和哈希

##### `equals`比较

```java
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof List))
            return false;

        ListIterator<E> e1 = listIterator();
        ListIterator<?> e2 = ((List<?>) o).listIterator();
        while (e1.hasNext() && e2.hasNext()) {
            E o1 = e1.next();
            Object o2 = e2.next();
            if (!(o1==null ? o2==null : o1.equals(o2)))
                return false;
        }
        return !(e1.hasNext() || e2.hasNext());
    }
```

比较指定对象与此对象是否相等。当且仅当指定对象也是一个列表，且两个列表具有相同的大小，并且两个列表中所有对应的元素也均相等时，返回`true`。判断两个元素相等的条件为：当且仅当`e1 == null ? e2 == null : e1.equals(e2)`时，两个元素认为是相等的。

换句话说，如果两个列表包含相同顺序的相同元素，则将它们定义为相等。

此实现首先检查指定的对象是否为当前列表（`==`判断地址是否相等），如果是，则返回`true`；如果不是，将检查指定对象是否为`List`类型，如果不是则返回`false`；如果是，则迭代两个列表，比较相对应的元素对，如果有任何比较返回`false`，则该方法返回`false`。如果比较完成后任意一个迭代器还有元素，则返回`false`（因为两个列表长度不相等），否则迭代完成后将返回`true`。

##### `hashCode`

```java
    public int hashCode() {
        int hashCode = 1;
        for (E e : this)
            hashCode = 31*hashCode + (e==null ? 0 : e.hashCode());
        return hashCode;
    }
```

此实现完全使用`List#hashCode`方法文档中定义的列表哈希函数的代码。