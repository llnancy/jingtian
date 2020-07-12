## 简介
`java.util.AbstractCollection`类是一个抽象类，它实现了`Collection`接口。从设计模式的角度来看，以`Abstract`开头的类应该是一个模板方法类。事实也是如此，它提供了对`Collection`接口的最基本实现。以最大程度地减少实现类所需的工作。

## 特性
该抽象类只提供了集合的最基本实现，并未对实现类的特性进行任何限制。

如果要实现不可修改的集合，只需继承该类并为`iterator`和`size`方法提供实现（由`iterator`方法返回的迭代器必须实现`hasNext`和`next`方法）；如果要实现可修改的集合，在实现不可修改集合的基础之上，还要另外重写此类的`add`方法（否则将抛出`UnsupportedOperationException`异常），同时由`iterator`方法返回的迭代器必须另外实现其`remove`方法。

此外，实现类同样应该遵循`Collection`接口中提出的规范：必须提供两个标准构造器：`void`无参构造器和带`Collection`类型的单个参数的构造器。

所有未进行实现的方法都被标记为抽象方法待子类实现。下面我们来看下在该抽象类中是如何对集合进行最基本实现的。

## 源码详解

### 构造器

只有一个使用`protected`修饰符修饰的无参构造器。说明该抽象类不允许外部直接实例化使用，因为它只是最基本的实现，并不具备集合的完整功能。

```java
protected AbstractCollection() {
}
```

### 成员变量

```java
private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
```

只有一个静态成员变量，它表明了要分配的数组的最大大小。如果超出此限制可能会导致`OutOfMemoryError`。

### 抽象方法

所有的抽象方法都是待具体子类去实现的方法。一共有两个：

- `public abstract Iterator<E> iterator();`：返回一个迭代器。
- `public abstract int size();`：计算集合大小。

### 已实现方法

所有实现方法都是最基本的实现，当子类有更好的实现方式时完全可以进行重写。

#### 查询操作

##### 判断集合是否为空

```java
    public boolean isEmpty() {
        return size() == 0;
    }
```

只需判断`size()`方法计算出的集合大小是否为`0`即可。由于`size()`方法是抽象方法，此方法相当于还是交由子类实现。

##### 判断集合中是否包含指定的元素

```java
    public boolean contains(Object o) {
        Iterator<E> it = iterator();
        if (o==null) {
            while (it.hasNext())
                if (it.next()==null)
                    return true;
        } else {
            while (it.hasNext())
                if (o.equals(it.next()))
                    return true;
        }
        return false;
    }
```

首先调用抽象的`iterator`方法获取一个迭代器对象，然后对集合进行迭代遍历。

分为两种情况，指定的元素为`null`和非`null`，如果为`null`，则使用`==`运算符比较；如果为非`null`，则使用`equals`方法进行比较。

迭代器对象的`hasNext`和`next`方法均由具体子类进行实现。

##### 将集合转换成对象数组

将该集合的迭代器返回的所有元素按相同顺序连续存储在数组中，数组索引从`0`开始。返回的数组长度等于集合元素的数量，即使在迭代过程中此集合的大小发生了改变（并发修改），最终返回的数组也与集合保持一致。

源码如下：

```java
    public Object[] toArray() {
        // Estimate size of array; be prepared to see more or fewer elements
        Object[] r = new Object[size()];
        Iterator<E> it = iterator();
        for (int i = 0; i < r.length; i++) {
            if (! it.hasNext()) // fewer elements than expected
                return Arrays.copyOf(r, i);
            r[i] = it.next();
        }
        return it.hasNext() ? finishToArray(r, it) : r;
    }
```

首先创建`size()`大小的`Object`数组，调用`iterator`方法得到集合的迭代器，然后对创建的`Object`数组进行下标索引遍历：

正常情况下，在`for`循环的遍历中，`it.hasNext()`方法将一直返回`true`，`if`条件判断将不会成立，循环完成后，迭代器的`hasNext`方法将返回`false`，最终将`r`数组返回；

而在并发修改的情况下，会出现迭代过程中集合元素减少的情况，这时`if(!it.hasNext())`判断成立，将返回`Arrays.copyOf(r,i)`：拷贝一个新数组，其长度为`i`；

同样地，并发修改会造成集合元素增加的情况，`for`循环遍历原集合长度`r.length`次后就已经结束了，再返回时进行了三目表达式判断，如果迭代器的`hasNext`方法返回`true`（还有下一个元素），则调用`finishToArray(r, it)`方法并返回。

下面我们来看下`finishToArray`方法的具体实现：

```java
    private static <T> T[] finishToArray(T[] r, Iterator<?> it) {
        int i = r.length;
        while (it.hasNext()) {
            int cap = r.length;
            if (i == cap) {
                int newCap = cap + (cap >> 1) + 1;
                // overflow-conscious code
                if (newCap - MAX_ARRAY_SIZE > 0)
                    newCap = hugeCapacity(cap + 1);
                r = Arrays.copyOf(r, newCap);
            }
            r[i++] = (T)it.next();
        }
        // trim if overallocated
        return (i == r.length) ? r : Arrays.copyOf(r, i);
    }
```

入参为原集合的对象数组（并发修改前的集合）和集合的迭代器。

首先记录原数组的长度为`i`；

然后开始迭代，再次记录一次原数组长度，用局部变量`cap`接收，在第一次迭代时`i == cap`成立，对`cap`进行扩容，`newCap = cap + (cap >> 1) + 1;`：扩容大约为原`cap`的`1.5`倍。

扩容后如果发现大于了定义的静态成员变量`MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8`时，则进行容量最大化处理：调用`hugeCapacity`方法。

`hugeCapacity`方法源码如下：

```java
    private static int hugeCapacity(int minCapacity) {
        if (minCapacity < 0) // overflow
            throw new OutOfMemoryError
                ("Required array size too large");
        return (minCapacity > MAX_ARRAY_SIZE) ?
            Integer.MAX_VALUE :
            MAX_ARRAY_SIZE;
    }
```

可看到返回的最大容量为`Integer.MAX_VALUE`。

接下来调用`r = Arrays.copyOf(r, newCap);`，将数组`r`中的元素拷贝至一个新的容量为`newCap`的数组中并赋值给`r`。

再接下来调用`r[i++] = (T)it.next();`将集合中的剩余元素（并发修改产生的，索引大于`i`）填充至新容量的`r`数组中。

然后进行下一次的迭代，如果`newCap`容量的数组仍不能完全填充集合中的剩余元素，则`i == cap`条件会再次成立，会再次进行扩容，直至完全填充。

填充完成后，`r`数组并不是恰好被填满，因为扩容是直接扩容`1.5`倍，很可能数组末尾没有任何元素，所以在`return`时利用三目运算符判断`i == r.length`是否成立，如果成立则表示恰好填满，直接返回`r`数组；否则调用`Arrays.copyOf(r, i)`将`r`数组拷贝至一个新的容量为`i`的数组中返回。

#### 修改操作

##### 添加元素至集合

此抽象类直接抛出`UnsupportedOperationException`异常。由具体子类去重写`add`方法来实现向集合中添加元素。

> 添加元素已经属于集合的个性化操作了，不同的集合保存元素的方式不同，添加的方式也不同，在抽象类中不应有任何具体实现。

```java
    public boolean add(E e) {
        throw new UnsupportedOperationException();
    }
```

##### 从集合中删除指定元素

此抽象类中没有任何存储集合元素的介质，唯一能接触到集合元素的是迭代器。

> 这里就体现出迭代器设计模式的好处了：不用去关心子类采用何种算法去实现迭代，只需要知道`hasNext`方法可判断集合中是否还有元素；`next`方法可获取到本次迭代的元素即可。

所以删除指定元素利用的是迭代器的删除方法。我们来看下具体代码实现：

```java
    public boolean remove(Object o) {
        Iterator<E> it = iterator();
        if (o==null) {
            while (it.hasNext()) {
                if (it.next()==null) {
                    it.remove();
                    return true;
                }
            }
        } else {
            while (it.hasNext()) {
                if (o.equals(it.next())) {
                    it.remove();
                    return true;
                }
            }
        }
        return false;
    }
```

分为指定元素为`null`和非`null`两种情况：`null`使用`==`运算符比较；非`null`使用`equals`方法比较。最终都会调用迭代器的`remove`方法删除指定元素。

所以实现类如果不重写该`remove(Object o)`方法，则必须在`iterator()`方法返回的迭代器中实现迭代器的`remove`方法。否则将抛出`UnsupportedOperationException`异常。

#### 批量操作

##### 判断是否包含指定集合中的全部元素

使用迭代器遍历指定集合中的所有元素，遍历过程中调用本抽象类中的`contains`方法判断是否包含，只要出现一个不包含的情况，返回`false`，全部包含返回`true`。

> 语法糖：JDK5引入的forEach循环实际调用的是迭代器，这是一个语法糖。

```java
    public boolean containsAll(Collection<?> c) {
        for (Object e : c)
            if (!contains(e))
                return false;
        return true;
    }
```

##### 添加指定集合至当前集合

使用迭代器迭代指定的集合，并将迭代器返回的每个对象添加至当前集合中。

由于当前集合的`add`方法中直接抛出`UnsupportedOperationException`异常，所以该方法依赖于具体子类的`add`方法实现。

```java
    public boolean addAll(Collection<? extends E> c) {
        boolean modified = false;
        for (E e : c)
            if (add(e))
                modified = true;
        return modified;
    }
```

##### 删除指定集合中的全部元素

使用迭代器遍历当前集合，如果迭代器返回的元素包含在指定集合中，则调用迭代器的`remove`方法将其从当前集合中删除。

所以，子类实现的`iterator`方法返回的迭代器必须包含`remove`方法实现。否则调用该方法将抛出`UnsupportedOperationException`异常。

```java
    public boolean removeAll(Collection<?> c) {
        Objects.requireNonNull(c);
        boolean modified = false;
        Iterator<?> it = iterator();
        while (it.hasNext()) {
            if (c.contains(it.next())) {
                it.remove();
                modified = true;
            }
        }
        return modified;
    }
```

##### 从当前集合中删除指定集合之外的全部元素

使用迭代器遍历当前集合，如果迭代器返回的元素不包含在指定集合中，则调用迭代器的`remove`方法将其重当前集合中删除。

所以，子类实现的`iterator`方法返回的迭代器必须包含`remove`方法实现。否则调用该方法将抛出`UnsupportedOperationException`异常。

```java
    public boolean retainAll(Collection<?> c) {
        Objects.requireNonNull(c);
        boolean modified = false;
        Iterator<E> it = iterator();
        while (it.hasNext()) {
            if (!c.contains(it.next())) {
                it.remove();
                modified = true;
            }
        }
        return modified;
    }
```

##### 清空集合

在此抽象类中，是使用迭代器遍历当前集合，调用迭代器的`remove`方法依次删除每个元素。

但大多数具体实现类会选择重写该方法以提高效率。

```java
    public void clear() {
        Iterator<E> it = iterator();
        while (it.hasNext()) {
            it.next();
            it.remove();
        }
    }
```

#### 转换成字符串

此方法是重写`Object#toString`方法，避免在控制台打印集合时打印出集合对象的地址值。其格式为：最外层用方括号`[]`括起来，方括号中间填充由迭代器返回的元素，顺序为迭代器返回的顺序。相邻元素中间以英文逗号`,`分隔。每一个元素通过`String.valueOf(Object)`方法转换成字符串。

```java
    public String toString() {
        Iterator<E> it = iterator();
        if (! it.hasNext())
            return "[]";

        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (;;) {
            E e = it.next();
            sb.append(e == this ? "(this Collection)" : e);
            if (! it.hasNext())
                return sb.append(']').toString();
            sb.append(',').append(' ');
        }
    }
```

此方法是大多数集合的`toString`方法，具体实现类（例如`ArrayList`）中并未重写该方法。

## 总结

该抽象类虽然提供了很多方法的实现，但所有的实现都是基于`iterator`和`size`这两个方法。从模板方法设计模式的角度看，该类定义了集合操作的算法骨架，具体实现还是得由子类去进行实现。