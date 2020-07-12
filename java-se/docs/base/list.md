## 简介
`List`接口继承了`Collection`接口，更精确的定义了一种集合类型。

该接口定义的是一种有序集合，也称为序列。接口的使用者可以精确控制序列中每个元素的插入位置。可以通过其整数索引（序列中的位置）访问元素，并在序列中搜索元素。

与`Set`集合不同，序列通常允许重复的元素（`equals`比较相等）。并且允许多个`null`元素。

## 主要接口签名

对于`Iterator`、`add`、`remove`、`equals`和`hashCode`方法，序列在`Collection`接口的定义之外，又增加了一些其它定义。同时为了方便起见，序列中还包含了对继承方法的重新声明。

### `hashCode()`

`Collection`接口中的`hashCode`方法只是定义了返回值是该集合的哈希码（调用的是`Object`类的`hashCode`方法）。而`List`接口明确规定了集合`hashCode`的计算方式：

```java
int hashCode = 1;
for (E e : list)
    hashCode = 31*hashCode + (e==null ? 0 : e.hashCode());
```

计算每一个元素的`hashCode`并相加。

这确保了对于任意两个集合`list1`和`list2`，如果满足`list1.hashCode() == list2.hashCode()`，则`list1.equals(list2)`一定相等。这符合`Object#hashCode`的规定。

### `equals(Object o)`

`Collection`接口中的`equals`方法的定义是指定对象与当前集合是否相等，如果实现类希望不使用默认的`Object#equals`方法的`==`比较，那么需要重写该方法。

`List`接口中重写的`equals`方法规定了判断指定对象与集合是否相等的逻辑：当且仅当指定的对象也是一个`List`序列，具有相同的大小，并且两个序列中所有对应位置的元素都相等（相等的条件为：`e1 == null ? e2 == null : e1.equals(e2)`）。

换句话说，如果两个序列中相同索引位置的元素都相等，那么这两个序列被定义为相等。

这个定义确保了`equals`方法可以在`List`接口的不同实现类中正常运行。

### `add(E e)`

`Collection`接口中的`add`方法定义了可以向集合中添加元素。而`List`接口中定义的`add`方法规定了元素添加的位置：添加到原集合末尾。

### `add(int index, E element)`

`List`接口中新增的接口签名，在指定索引位置插入指定元素。如果指定索引位置有元素，该元素及其之后的所有元素整体向后移动一位。

### `addAll(Collection<? extends E> c)`

同样地，`Collection`接口中的`add`方法只是定义了可以向当前集合中插入另一个集合。而`List`接口明确规定了插入的位置为原集合末尾。

### `addAll(int index, Collection<? extends E> c)`

`List`接口中新增的接口签名，在指定索引位置插入指定集合。如果指定索引位置有元素，该元素及其之后的所有元素整体向后移动指定集合大小位。

### `remove(int index)`

`List`接口中新增的接口签名，删除序列中指定索引位置的元素。如果指定索引位置后还有其它元素，则之后的所有元素整体向前移动一位，最后返回被移除的那个元素。

### `Iterator`

`List`接口提供了一个称为`ListIterator`的特殊迭代器，除了允许`Iterator`接口提供的常规操作外，它还允许元素的插入和替换以及双向访问。提供了一种方式来获取从序列的指定位置开始的序列迭代器。

定义了以下两个方法获取序列迭代器：

```java
ListIterator<E> listIterator();

ListIterator<E> listIterator(int index);
```

其中重载的带一个参数的`listIterator(int index)`方法从指定索引位置开始返回序列迭代器，它表示在首次调用`ListIterator#next`方法时将返回指定索引位置的元素。首次调用`ListIterator#previous`方法时将返回指定索引减一位置的元素。

## 其它特性

`List`接口定义了四个方法用于对序列元素进行位置（索引）访问。索引从`0`开始。在某些实现类中，例如`LinkedList`，这些方法的实现可能在时间上与索引的值成比例增长。因此，如果在编码过程中不知道`List`的实现类（例如使用反射等）具体是哪个类，则遍历序列可能比通过索引进行索引更加高效。

- `void add(int index, E element);`：在指定索引位置添加指定元素。
- `E get(int index);`：获取指定索引位置的元素。
- `E remove(int index);`：移除指定索引位置的元素。
- `E set(int index, E element);`：给指定索引位置的元素赋值。

另外，`List`接口还定义了两个用来搜索指定对象的方法，从性能来看，应谨慎使用这些方法。在许多实现类中，它们的执行效率非常低下。

- `int indexOf(Object o);`：返回指定元素第一次出现在该序列中的索引位置。如果不存在则返回`-1`。
- `int lastIndexOf(Object o);`：返回指定元素最后一次出现在该序列中的索引位置。如果不存在则返回`-1`。

## 总结

`List`接口是对`Collection`接口的进一步定义，明确了一种集合的类型：序列。

接口中的方法一部分是对父接口方法的重新声明，另一部分是为了对序列这种集合建模而增加的接口。

我们学习该接口是为了“从上往下”地对集合框架的特性进行认知。