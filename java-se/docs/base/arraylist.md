## 简介
`ArrayList`底层数据结构是动态数组，与普通的数组相比，它的容量可以动态增长。在添加的元素数量达到一定值时，会触发自动扩容机制，保证集合的可用性。

它继承了`AbstractList`抽象类，并实现了`List`、[`RandomAccess`](/2020/04/26/javase/base/randomaccess/)、[`Cloneable`](/2020/04/26/javase/base/cloneable/) 和 [`java.io.Serializable`](/2020/03/21/javase/base/serializable/)接口。

> [`AbstractList`抽象类已经实现了`List`接口，为什么`ArrayList`还要去实现`List`接口？](https://github.com/sunchaser-lilu/gold-road-to-Java/tree/master/java-se/src/main/java/com/sunchaser/javase/collect/reimpllist)

由于底层由数组存储元素，其取指定下标位置元素、在集合尾部插入元素和求数组长度的时间复杂度为`O(1)`；而在指定索引位置插入和删除元素的时间复杂度为`O(n)`。

## <span id="useAnalysis">使用分析</span>

在开发中，我们会经常使用`ArrayList`来存储对象，例如对数据库批量插入/修改的入参实体集合、数据库的列表查询结果集转换成前端视图模型对象等。一般来说，我们的使用形式如下：

```java
// 无参构造
List<String> list = new ArrayList<>();
// 连续添加10个元素
list.add("a");
list.add("b");
list.add("c");
list.add("d");
list.add("e");
list.add("f");
list.add("g");
list.add("h");
list.add("i");
list.add("j");
// 添加第11个元素
list.add("k");
// do other
```

这样使用似乎没有任何问题，但这却不是一个匠心程序员该写出来的代码。

为什么这么说呢？我们知道`ArrayList`底层是动态数组，那这个数组什么时候进行动态呢？换句话说，数组什么时候会进行扩容？又扩容到多少呢？

以上代码执行完后到底有没有进行扩容？这些都是问题，让我们带着这些问题来看下面的源码。

## 源码详解

### 成员变量

```java
    /**
     * 序列化ID
     */
    private static final long serialVersionUID = 8683452581122892189L;

    /**
     * 默认初始容量
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * 用于空ArrayList实例的共享空数组实例
     */
    private static final Object[] EMPTY_ELEMENTDATA = {};

    /**
     * 共享的空数组实例，用于默认大小的空实例。
     * 将该成员变量与EMPTY_ELEMENTDATA区分开来，以了解添加第一个元素时需要填充多少。
     */
    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};

    /**
     * 存储ArrayList的元素的数组缓冲区。ArrayList的容量是此数组缓冲区的长度。
     * 添加第一个元素时，任何具有elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA的空ArrayList都将扩展为DEFAULT_CAPACITY。
     * 非私有成员以简化嵌套类访问。
     */
    transient Object[] elementData;

    /**
     * ArrayList的大小（它包含的元素个数）。
     */
    private int size;

    /**
     * 分配的最大数组大小。某些VM在数组中保留一些标头字。
     * 尝试分配更大的数组可能会导致OutOfMemoryError：请求的数组大小超出VM限制。
     */
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
```

### 构造器

#### 无参构造

构造一个初始容量为`10`的空列表。将`DEFAULTCAPACITY_EMPTY_ELEMENTDATA`空列表赋给存储元素的`elementData`数组缓冲区。

```java
    public ArrayList() {
        this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    }
```

#### 有参构造

`1`、 构造一个具有指定初始容量的空列表。入参`initialCapacity`为列表的指定初始容量，如果为负数则抛出`IllegalArgumentException`异常。

```java
    public ArrayList(int initialCapacity) {
        if (initialCapacity > 0) {
            // 创建initialCapacity大小的Object类数组。
            this.elementData = new Object[initialCapacity];
        } else if (initialCapacity == 0) {
            // 指定容量为0，赋值静态空数组成员变量。
            this.elementData = EMPTY_ELEMENTDATA;
        } else {
            // 负数抛出异常。
            throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
        }
    }
```

`2`、 构造一个包含指定集合元素的列表，其顺序由集合的迭代器返回。入参`c`集合中的元素将被放入此列表，如果集合为`null`则抛出`NullPointerException`异常。

```java
    public ArrayList(Collection<? extends E> c) {
        // 集合转数组赋值给elementData
        elementData = c.toArray();
        // 集合元素个数不为0。
        if ((size = elementData.length) != 0) {
            // c.toArray方法返回的可能不是Object[]数组，此处判断如果不为Object[]类型则转换成Object[]类型。
            if (elementData.getClass() != Object[].class)
                elementData = Arrays.copyOf(elementData, size, Object[].class);
        } else {
            // 赋值静态空数组成员变量。
            this.elementData = EMPTY_ELEMENTDATA;
        }
    }
```

### 数组拷贝方法

由于`ArrayList`底层使用数组进行元素存储，其很多实现都是对数组进行直接操作。所以在看其它方法之前，很有必要先弄懂一些数组的方法。

`java.util.Arrays`是`JDK`提供的一个数组工具类，在`ArrayList`中大量使用了它的一个静态方法：

```java
    /**
     * 数组拷贝
     * @param original 待拷贝数组
     * @param newLength 拷贝后新数组的长度
     */
    public static <T> T[] copyOf(T[] original, int newLength) {
        return (T[]) copyOf(original, newLength, original.getClass());
    }

    /**
     * @param original 待拷贝数组
     * @param newLength 拷贝后新数组的长度
     * @param newType 数组元素类型
     */
    public static <T,U> T[] copyOf(U[] original, int newLength, Class<? extends T[]> newType) {
        @SuppressWarnings("unchecked")
        // 传入的newType类型是否是Object类型，是则创建Object数组，否则创建指定类型数组。
        T[] copy = ((Object)newType == (Object)Object[].class)
            ? (T[]) new Object[newLength]
            : (T[]) Array.newInstance(newType.getComponentType(), newLength);
        // native方法
        System.arraycopy(original, 0, copy, 0,
                         Math.min(original.length, newLength));
        // 返回创建的新长度的数组
        return copy;
    }

    /**
     * 将src数组中srcPos索引及其之后的length个元素分别拷贝至dest数组中destPos索引及其之后的length个位置上。
     *
     * @param src 源数组
     * @param srcPos 源数组拷贝开始位置索引
     * @param dest 目标数组
     * @param destPos 目标数组拷贝开始位置索引
     * @param length 拷贝的长度
     */
    public static native void arraycopy(Object src,  int  srcPos,
                                        Object dest, int destPos,
                                        int length);
```

### 插入元素
`ArrayList`提供了两个重载的`add`插入方法。第一个是将指定元素添加至列表末尾；第二个是将指定元素添加至指定位置；同时还提供了两个重载的`addAll`方法，用来批量插入。

#### 插入至列表末尾

源码如下：

```java
    public boolean add(E e) {
        ensureCapacityInternal(size + 1);  // Increments modCount!!
        elementData[size++] = e;
        return true;
    }
```

看到这里我们可以对[`使用分析`](#useAnalysis)中的示例代码进行分析了：

首先使用`new`关键字调用无参构造器初始化一个`ArrayList`集合对象，我们知道其实质是将`DEFAULTCAPACITY_EMPTY_ELEMENTDATA`空列表赋给存储元素的`elementData`数组缓冲区。创建出的`list`对象的`size`成员变量初始化为`0`，然后调用`list`对象的`add`方法添加元素。

第一次调用：`list.add("a");`

在`add`方法中首先调用`ensureCapacityInternal`方法，确保内部容量，然后将传入的元素赋值给`elementData`数据域末尾。最后返回`true`。

确保内部容量`ensureCapacityInternal`方法源码如下：

```java
    private void ensureCapacityInternal(int minCapacity) {
        ensureExplicitCapacity(calculateCapacity(elementData, minCapacity));
    }
```

第一次调用`add`方法时：`elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA`（构造器中初始化），`minCapacity = size + 1`（其值为`1`）。

调用`calculateCapacity(elementData, minCapacity)`方法计算容量：

```java
    private static int calculateCapacity(Object[] elementData, int minCapacity) {
        // new ArrayList<>() 时，初始化为 elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA
        if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
            return Math.max(DEFAULT_CAPACITY, minCapacity);
        }
        return minCapacity;
    }
```

此时`elementData`等于`DEFAULTCAPACITY_EMPTY_ELEMENTDATA`，返回`DEFAULT_CAPACITY`（其值为`10`）和`minCapacity`（其值为`1`）中较大的值，即返回`10`。

随即调用了`ensureExplicitCapacity(10)`方法：

```java
    private void ensureExplicitCapacity(int minCapacity) {
        modCount++;

        // overflow-conscious code
        if (minCapacity - elementData.length > 0)
            grow(minCapacity);
    }
```

首先增加当前集合的修改次数。然后判断计算出的容量是否超出了当前`elementData`数组长度，如果超过则进行`grow`扩容。

在第一次调用`add`方法时显然超过了，进行扩容：

```java
    private void grow(int minCapacity) {
        // overflow-conscious code
        // 旧的容量
        int oldCapacity = elementData.length;
        // 新容量为原来的1.5倍
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        if (newCapacity - minCapacity < 0)
            // 新容量小于传入的计算出的容量，则新容量为传入容量。
            newCapacity = minCapacity;
        if (newCapacity - MAX_ARRAY_SIZE > 0)
            // 新容量超过了最大值，则计算最大容量
            newCapacity = hugeCapacity(minCapacity);
        // minCapacity is usually close to size, so this is a win:
        // 数组内容拷贝
        elementData = Arrays.copyOf(elementData, newCapacity);
    }
```

扩容流程为：

- 获取旧容量`oldCapacity`；
- 计算新容量，利用位运算（速度远超整除运算）得到旧容量的一半再加上旧容量，即扩容`1.5`倍；
- 检查新容量是否小于传入的计算容量`minCapacity`，如果小于，则将传入的容量作为新容量；
- 检查得到的新容量是否大于`ArrayList`定义的所能容纳的最大容量：`Integer,MAX_VALUE - 8`；
- 如果大于，则调用`hugeCapacity(minCapacity)`计算最大容量：如果`minCapacity`大于`Integer.MAX_VALUE - 8`，则最大容量为`Integer.MAX_VALUE`，否则为`Integer.MAX_VALUE - 8`。
- 最后调用`Arrays.copyOf(elementData, newCapacity)`进行数组拷贝，得到一个以新容量为长度的数组对象并赋值给`elementData`。

第一次调用`add`方法时，旧容量`oldCapacity = 0`，通过位运算计算出的新容量也为`0`，所以最后新容量`newCapacity = minCapacity`，等于`10`。

> 所以我们使用`new`关键字调用无参构造器创建`ArrayList`对象时，实际上只初始化了一个空数组，在第一次调用`add`方法时才会进行空数组的扩容。

扩容完成后，`elementData`数组容量充足，可以往其末尾添加元素：

```java
    elementData[size++] = e;
```

像[`使用分析`](#useAnalysis)中的示例代码一样，我们不断地向其中添加元素，当添加的元素个数不超过`10`时，`ensureExplicitCapacity(int minCapacity)`方法判断`minCapacity - elementData.length`始终小于`0`（经过第一次扩容后`elementData.length`的值为`10`），不会进行`grow`扩容; 而当添加至第`11`个元素`k`时，情况发生变化：

- `calculateCapacity(elementData, minCapacity)`方法直接返回`minCapacity = 11`；
- 然后调用`ensureExplicitCapacity(int minCapacity)`方法，此时`elementData`数组长度为`10`，`minCapacity - elementData.length`大于`0`，将再次进行`grow`扩容。

而扩容的流程我们知道，会调用`Arrays.copyOf(elementData, newCapacity)`方法进行数组拷贝，会有性能损耗。

所以，具有匠心的代码应该像下面这样：

```java
// 指定初始容量的构造
List<String> list = new ArrayList<>(11);
// 连续添加10个元素
list.add("a");
list.add("b");
list.add("c");
list.add("d");
list.add("e");
list.add("f");
list.add("g");
list.add("h");
list.add("i");
list.add("j");
// 添加第11个元素
list.add("k");
// do other
```

调用指定初始容量的构造器，在创建`list`对象时就会对`elementData`数组进行初始化，而不是在第一次调用`add`方法时。

所以如果能提前预估到集合容量，尽量提前指定容量，避免频繁的扩容带来的性能损耗。

> 根据使用场景，如果集合的数据量不好预估，且只会对其进行增删操作，则不建议使用`ArrayList`集合，而是建议使用`LinkedList`集合。

#### 插入至指定位置

源码如下：

```java
    /**
     * @param index 待插入元素的指定的位置的索引
     * @param element 待插入的元素
     */
    public void add(int index, E element) {
        // 校验索引是否越界
        rangeCheckForAdd(index);
        // 确保容量，修改modCount值
        ensureCapacityInternal(size + 1);  // Increments modCount!!
        // 数组拷贝：将[index,size)索引区间的元素整体向后移动一个单位，将集合的`index`位置留空。
        System.arraycopy(elementData, index, elementData, index + 1,
                         size - index);
        // 给index索引位置赋值为待插入元素
        elementData[index] = element;
        // 集合大小增加
        size++;
    }
```

#### 将一个集合全部元素插入至当前集合末尾

源码如下：

```java
    public boolean addAll(Collection<? extends E> c) {
        // 集合转数组
        Object[] a = c.toArray();
        // 待添加元素个数
        int numNew = a.length;
        // 确保容量
        ensureCapacityInternal(size + numNew);  // Increments modCount
        // 数组拷贝
        System.arraycopy(a, 0, elementData, size, numNew);
        // 集合大小增加
        size += numNew;
        // 传入集合无元素则返回false，否则返回true。
        return numNew != 0;
    }
```

首先将传入的集合`c`转为`Object[]`对象数组，调用`Collection`的`toArray()`方法，不管是哪种集合的实现，最终都会返回一个数组。以下是`ArrayList`类的实现：

```java
    public Object[] toArray() {
        return Arrays.copyOf(elementData, size);
    }
```

调用工具类的方法`Arrays.copyOf`方法进行数组拷贝，返回一个`Object[]`数组。

#### 从当前集合指定索引位置开始，将一个集合全部元素插入

源码如下：

```java
    /**
     *
     * @param index 指定索引
     * @param c 待插入集合
     */
    public boolean addAll(int index, Collection<? extends E> c) {
        // 校验索引是否越界
        rangeCheckForAdd(index);
        // 集合转Object数组
        Object[] a = c.toArray();
        // 待插入集合元素个数
        int numNew = a.length;
        // 确保容量
        ensureCapacityInternal(size + numNew);  // Increments modCount
        // 需要移动的元素个数
        int numMoved = size - index;
        if (numMoved > 0)
            // 不是从末尾添加，则将[index,size)索引上的元素整体向后移动numMoved个单位。
            System.arraycopy(elementData, index, elementData, index + numNew,
                             numMoved);
        // Object数组拷贝至elementData
        System.arraycopy(a, 0, elementData, index, numNew);
        // 集合大小增加
        size += numNew;
        // 传入集合无元素则返回false，否则返回true。
        return numNew != 0;
    }
```

### 删除元素
删除集合中的元素有多种情况：删除指定索引位置元素/删除指定元素/删除指定索引范围内的元素/删除全部元素/指定条件删除（`Java8`新增）等。

#### 删除指定索引位置元素

源码如下：

```java
    /**
     * 删除指定索引位置的元素
     * @param index 指定索引
     */
    public E remove(int index) {
        // 校验索引是否越界
        rangeCheck(index);
        // 修改次数增加
        modCount++;
        // 获取旧的index索引位置元素值
        E oldValue = elementData(index);
        // 计算需要移动的元素个数
        int numMoved = size - index - 1;
        if (numMoved > 0)
            // 移动元素：[index+1,size)索引区间的元素整体向前移动一个单位。
            System.arraycopy(elementData, index+1, elementData, index,
                             numMoved);
        // 清除末尾索引原有的引用，减小集合大小
        elementData[--size] = null; // clear to let GC do its work
        // 返回被删除的元素值
        return oldValue;
    }
```

> 为什么判断`size - index - 1 > 0`？
>
> 答：集合大小`size`是从`1`开始计算，而数组下标索引`index`是从`0`开始计算。

#### 删除指定元素

由于`ArrayList`集合中的元素可以重复，指定的元素可能在集合中出现多次，所以该方法删除的是指定元素在集合中第一次出现位置的元素。

源码如下：

```java
    /**
     * @param o 指定元素
     */
    public boolean remove(Object o) {
        if (o == null) {
            // 指定元素为null：==运算符比较
            for (int index = 0; index < size; index++)
                if (elementData[index] == null) {
                    fastRemove(index);
                    return true;
                }
        } else {
            // 非null：equals方法比较
            for (int index = 0; index < size; index++)
                if (o.equals(elementData[index])) {
                    fastRemove(index);
                    return true;
                }
        }
        return false;
    }
```

> `fori`循环是从索引为`0`开始遍历，所以删除的是具有最低索引的元素。

我们来看下`fastRemove(index);`的实现：

```java
    /**
     * @param index 指定索引
     */
    private void fastRemove(int index) {
        // 修改次数增加
        modCount++;
        // 计算需要移动的元素个数
        int numMoved = size - index - 1;
        if (numMoved > 0)
            // 移动元素：[index+1,size)索引区间元素整体向前移动一位。
            System.arraycopy(elementData, index+1, elementData, index,
                             numMoved);
        // 清除末尾索引原有的引用，减小集合大小
        elementData[--size] = null; // clear to let GC do its work
    }
```

#### 删除指定索引范围内的元素

该方法为`ArrayList`类中受保护的方法，外部无法直接进行调用。由`JDK`集合框架内部进行使用。

源码如下：

```java
    /**
     * 约定 fromIndex 小于 toIndex，否则，进行元素移动时会出现索引越界。
     * @param fromIndex 开始索引
     * @param toIndex 结束索引
     */
    protected void removeRange(int fromIndex, int toIndex) {
        // 修改次数增加
        modCount++;
        // 计算需要移动的元素个数
        int numMoved = size - toIndex;
        // 移动元素：[toIndex,size)索引区间元素整体向前移动toIndex - fromIndex个单位。
        System.arraycopy(elementData, toIndex, elementData, fromIndex,
                         numMoved);

        // clear to let GC do its work
        // 计算新的数组大小
        int newSize = size - (toIndex-fromIndex);
        for (int i = newSize; i < size; i++) {
            // 清除删除的索引位置原有的引用
            elementData[i] = null;
        }
        // 指定新的数组大小
        size = newSize;
    }
```

#### 删除全部元素

有两种情况：一种是删除当前集合全部元素，方法为`clear()`；另一种是从当前集合中删除指定集合中包含的所有元素，方法为`removeAll(Collection<?> c)`。

`clear()`方法源码如下：

```java
    public void clear() {
        // 修改次数增加
        modCount++;

        // clear to let GC do its work
        // 清除所有索引位置的引用对象
        for (int i = 0; i < size; i++)
            elementData[i] = null;
        // 集合大小置0
        size = 0;
    }
```

`removeAll(Collection<?> c)`方法源码如下：

```java
    public boolean removeAll(Collection<?> c) {
        // 非空
        Objects.requireNonNull(c);
        return batchRemove(c, false);
    }
```

首先，调用`Java8`提供的`Objects.requireNonNull(c);`方法对传入的集合`c`进行非空校验。

然后，调用私有方法`batchRemove(c, false)`，传入集合`c`和布尔值`false`：

```java
    private boolean batchRemove(Collection<?> c, boolean complement) {
        final Object[] elementData = this.elementData;
        int r = 0, w = 0;
        boolean modified = false;
        try {
            for (; r < size; r++)
                if (c.contains(elementData[r]) == complement)
                    elementData[w++] = elementData[r];
        } finally {
            // Preserve behavioral compatibility with AbstractCollection,
            // even if c.contains() throws.
            if (r != size) {
                System.arraycopy(elementData, r,
                                 elementData, w,
                                 size - r);
                w += size - r;
            }
            if (w != size) {
                // clear to let GC do its work
                for (int i = w; i < size; i++)
                    elementData[i] = null;
                modCount += size - w;
                size = w;
                modified = true;
            }
        }
        return modified;
    }
```

我们来写一个简单的`demo`来看下其过程：

```java
    ......

        List<Integer> removeList = new ArrayList<>();
        removeList.add(1);
        removeList.add(2);
        removeList.add(3);
        removeList.add(4);
        removeList.add(5);
        removeList.add(6);
        List<Integer> beRemovedList = new ArrayList<>();
        beRemovedList.add(3);
        beRemovedList.add(4);
        beRemovedList.add(6);
        System.out.println(removeList);
        System.out.println(beRemovedList);
        removeList.removeAll(beRemovedList);
        System.out.println(removeList);

    ......
```

创建一个`removeList`集合并初始化六个元素，再创建一个待删除的`beRemovedList`集合并初始化三个元素（在`removeList`中）。

下面我们来分析`removeAll`方法的具体执行流程：

当我们调用`removeList.removeAll(beRemovedList);`时，会先对`beRemovedList`进行非空校验，然后调用`batchRemove`方法：

1、使用局部最终变量`elementData`指向当前集合（`removeList`）的引用：

```java
final Object[] elementData = this.elementData;
```

2、定义两个索引并初始化为`0`，以及定义一个布尔值用来记录当前集合是否被修改：

```java
int r = 0, w = 0;
boolean modified = false;
```

3、从`r`到`size`进行遍历，判断传入的集合`c`是否包含`r`位置的元素。

```java
    for (; r < size; r++)
        if (c.contains(elementData[r]) == complement)
            elementData[w++] = elementData[r];
```

我们对着我们的`demo`程序进行分析，`c = {3, 4, 6}`，`elementData = {1, 2, 3, 4, 5, 6}`，`size=6`，`complement = false`。

每次循环`r`的值增一，循环结束的条件为`r < size`不成立，即当`r = size`时循环结束。

- 第一次循环：`r = 0`，`elementData[r] = 1`，`c.contains(1) = false`。`if`条件成立，`w = 0`。将`elementData[r]`赋值给`elementData[w++]`：即将当前不在`c`集合中的元素赋值到集合的第`0`位置，随后`w`增一。此时`elementData`的第`0`位置元素为：`1`。
- 第二次循环：`r = 1`，`elementData[r] = 2`，`c.contains(2) = false`。`if`条件成立，`w = 1`。将`elementData[r]`赋值给`elementData[w++]`：即将当前不在`c`集合中的元素赋值到集合的第`1`位置，随后`w`增一。此时`elementData`的第`1`位置元素为：`2`。
- 第三次循环：`r = 2`，`elementData[r] = 3`，`c.contains(3) = true`。`if`条件不成立，`w`的值为`2`，不做任何操作。
- 第四次循环：`r = 3`，`elementData[r] = 4`，`c.contains(4) = true`。`if`条件不成立，`w`的值为`2`，不做任何操作。
- 第五次循环：`r = 4`，`elementData[r] = 5`，`c.contains(5) = false`。`if`条件成立，`w = 2`，将`elementData[r]`赋值给`elementData[w++]`：即将当前不在`c`集合中的元素赋值到集合的第`2`位置，随后`w`增一。此时`elementData`的第`2`位置元素为：`5`。
- 第六次循环：`r = 5`，`elementData[r] = 6`，`c.contains(6) = true`。`if`条件不成立，`w`的值为`3`，不做任何操作。

循环结束，`w = 3`，`elementData = {1, 2, 5, ......}`，`r = 6`。

接下来我们来看下`finally`块中的代码：

```java
    // Preserve behavioral compatibility with AbstractCollection,
    // even if c.contains() throws.
    if (r != size) {
        System.arraycopy(elementData, r,elementData, w,size - r);
        w += size - r;
    }
    if (w != size) {
        // clear to let GC do its work
        for (int i = w; i < size; i++)
            elementData[i] = null;
        modCount += size - w;
        size = w;
        modified = true;
    }
```

对于我们的`demo`程序：

此时`r = size`，第一个`if`块不进入。

此时`(w = 3) != (size = 6)`，进入第二个`if`块，将索引从`w`到`size - 1`位置的元素置为`null`，释放对原来元素的引用。

接下来是一些收尾工作：

- 记录修改次数，修改（移除）了`size - w`个元素；
- 将集合大小设为`w`：为在`for`循环中给`elementData`域赋值的元素个数。
- 设置修改标记为`true`，此处是`c`集合中的元素全部从集合中删除。

最后，`batchRemove`方法返回`modified`布尔值：表示是否当前集合中删除了指定集合`c`中包含的所有元素。

### 修改元素

修改方法只有一个：修改指定索引位置的元素为新的元素。

源码如下：

```java
    /**
     * @param index 指定索引
     * @param element 指定的新元素
     */
    public E set(int index, E element) {
        // 校验索引是否越界
        rangeCheck(index);
        // 获取index索引位置的旧元素
        E oldValue = elementData(index);
        // 赋值为新的指定元素
        elementData[index] = element;
        // 返回旧元素
        return oldValue;
    }
```

### 查询元素

由于`ArrayList`底层由`elementData`数组存储元素，所以支持按数组下标访问：即随机快速访问。其查询的时间复杂度为`O(1)`，这也是为什么`ArrayList`实现`RandomAccess`的原因：标记该类支持随机快速访问。

```java
    public E get(int index) {
        // 校验索引是否越界
        rangeCheck(index);
        // 按数组下标取值
        return elementData(index);
    }

    E elementData(int index) {
        return (E) elementData[index];
    }
```

### 其它方法

#### `clone`方法

[传送门](/2020/04/26/javase/base/cloneable)

#### `size`方法

获取集合大小：返回成员变量`size`。

#### `isEmpty`方法

判断集合是否为空集合：返回`size == 0`得到的值。

#### <span id="indexOf">`indexOf`方法</span>

返回指定元素在当前集合中第一次出现的位置索引。如果当前集合中不包含此元素，返回`-1`。

```java
    public int indexOf(Object o) {
        if (o == null) {
            // 指定元素为null：使用==运算符比较
            for (int i = 0; i < size; i++)
                if (elementData[i]==null)
                    return i;
        } else {
            // 非null：使用equals方法比较
            for (int i = 0; i < size; i++)
                if (o.equals(elementData[i]))
                    return i;
        }
        // 未找到，返回-1。
        return -1;
    }
```

从前往后遍历，`null`值使用`==`运算符进行比较，其它对象使用`equals`方法比较。

#### `lastIndexOf`方法

返回指定元素在当前集合中最后一次出现的位置索引。如果当前集合中不包含此元素，返回`-1`。

```java
    public int lastIndexOf(Object o) {
        if (o == null) {
            for (int i = size-1; i >= 0; i--)
                if (elementData[i]==null)
                    return i;
        } else {
            for (int i = size-1; i >= 0; i--)
                if (o.equals(elementData[i]))
                    return i;
        }
        return -1;
    }
```

从后往前遍历，`null`值使用`==`运算符进行比较，其它对象使用`equals`方法比较。

#### `contains`方法

判断指定元素是否在集合中。调用的是[`indexOf(o)`](#indexOf)方法，判断其返回值是否大于等于`0`，等于`-1`说明不在集合中。

#### `iterator`方法

该方法是迭代器设计模式的体现。使用了`new`关键字创建了一个私有内部类`Itr`对象。

```java
    public Iterator<E> iterator() {
        return new Itr();
    }

    private class Itr implements Iterator<E> {
        int cursor;       // index of next element to return
        int lastRet = -1; // index of last element returned; -1 if no such
        int expectedModCount = modCount;

        Itr() {}
        ......
    }
```

私有内部类`Itr`实现了`java.util.Iterator`迭代器接口。其成员变量有三个：

- `cursor`：游标（下一个要返回元素的索引）。
- `lastRet`：初始化为`-1`，最后一个被返回元素的索引；如果集合中本来没有任何元素则返回`-1`。
- `expectedModCount`：期望的修改次数。初始化为当前集合的`modCount`。

只有一个无参构造函数。

我们知道使用迭代器对`ArrayList`进行遍历的写法如下：

```java
    List<Integer> iteratorList = new ArrayList<>();
    iteratorList.add(1);
    iteratorList.add(2);
    iteratorList.add(3);
    iteratorList.add(4);
    iteratorList.add(5);
    iteratorList.add(6);
    Iterator<Integer> iterator = iteratorList.iterator();
    while (iterator.hasNext()) {
        Integer next = iterator.next();
        System.out.println(next);
    }
```

其中关键的两个方法为：`hasNext()`和`next()`。接下来我们着重来看下这两个方法。

首先调用`iteratorList`对象的`iterator()`方法得到一个迭代器对象，经过上面的分析我们可知这实际是一个`Itr`对象。

其`hasNext()`方法源码如下：

```java
    public boolean hasNext() {
        return cursor != size;
    }
```

返回当前游标`cursor`是否不等于集合大小`size`。如果不等于`size`，说明还有下一个元素。可继续迭代。否则迭代完成。

`next()`方法源码如下：

```java
    public E next() {
        // fail-fast机制
        checkForComodification();
        // 获取当前游标
        int i = cursor;
        // 当前游标超过集合大小则抛出异常
        if (i >= size)
            throw new NoSuchElementException();
        // 获取存储元素的数组对象
        Object[] elementData = ArrayList.this.elementData;
        if (i >= elementData.length)
            // 当前游标超出数组长度：与hasNext方法中的判断出现了矛盾。并发修改：fail-fast机制
            throw new ConcurrentModificationException();
        // 校验通过，游标加一
        cursor = i + 1;
        // 最后一个被返回元素的索引赋值为旧的游标i，返回旧的游标对应的元素。
        return (E) elementData[lastRet = i];
    }
```

> 在`Itr`类中使用`this`指代的是当前`Itr`对象，使用`ArrayList.this`指代的是集合对象。

> 为什么`ArrayList`使用迭代器遍历没有普通`fori`循环遍历效率高？
>
> 答：经过以上代码的分析，原因显而易见：使用迭代器遍历，首先需要使用`new`关键字创建一个`Itr`对象，创建对象需要耗时（一次）；其次，中间有多次条件判断并且有局部变量产生，以及一个加`1`操作，这也需要耗费时间（多次：每次调用`next`方法）。

## `Demo`实战

```java
package com.sunchaser.javase.collect;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author sunchaser
 * @since JDK8 2020/5/2
 * 
 */
public class ArrayListTest {
    public static void main(String[] args) {
        // 插入元素
        ArrayList<String> addList = new ArrayList<>();

        // 集合尾部插入
        addList.add("测试1");
        System.out.println(addList);

        // 集合指定索引位置插入
        addList.add(1,"指定位置1");
        System.out.println(addList);

        // 集合指定索引位置插入：索引位置无元素且不是尾部：索引越界。
        // addList.add(10,"指定位置2");

        // 待插入集合初始化
        ArrayList<String> toBeAddList = new ArrayList<>();
        toBeAddList.add("测试2");
        toBeAddList.add("测试3");
        toBeAddList.add("测试4");

        // 待指定索引位置插入集合初始化
        ArrayList<String> toBeAddIndexList = new ArrayList<>();
        toBeAddIndexList.add("测试5");
        toBeAddIndexList.add("测试6");

        // 将一个集合全部元素插入至当前集合末尾
        addList.addAll(toBeAddList);
        System.out.println(addList);

        // 从当前集合指定索引位置开始，将一个集合全部元素插入
        addList.addAll(1,toBeAddIndexList);
        System.out.println(addList);

        // 删除元素
        List<Integer> removeList = new ArrayList<>();
        removeList.add(1);
        removeList.add(2);
        removeList.add(6);
        removeList.add(3);
        removeList.add(4);
        removeList.add(5);
        removeList.add(6);
        removeList.add(4);
        System.out.println(removeList);

        // 删除指定索引位置元素
        removeList.remove(1);
        System.out.println(removeList);

        // 删除指定元素在集合中第一次出现位置的元素
        removeList.remove(new Integer(6));
        System.out.println(removeList);

        // 待删除元素集合
        List<Integer> beRemovedList = new ArrayList<>();
        beRemovedList.add(2);
        beRemovedList.add(3);
        beRemovedList.add(6);
        System.out.println(removeList);
        System.out.println(beRemovedList);

        // 从当前集合中删除指定集合中包含的所有元素
        boolean b = removeList.removeAll(beRemovedList);
        System.out.println(b);
        System.out.println(removeList);

        // 删除全部元素
        removeList.clear();
        System.out.println(removeList);

        // 修改元素集合初始化
        ArrayList<Integer> operatorList = new ArrayList<>();
        operatorList.add(1);
        operatorList.add(2);
        operatorList.add(3);
        operatorList.add(2);
        operatorList.add(1);
        System.out.println(operatorList);

        // 修改元素，将索引为1的元素修改为6
        operatorList.set(1,6);
        System.out.println(operatorList);

        // 查询元素
        Integer integer = operatorList.get(1);
        System.out.println(integer);

        // 克隆
        Object clone = operatorList.clone();
        System.out.println(clone);

        // size
        System.out.println(operatorList.size());

        // isEmpty
        System.out.println(operatorList.isEmpty());

        // indexOf
        System.out.println(operatorList.indexOf(1));

        // lastIndexOf
        System.out.println(operatorList.lastIndexOf(1));

        // contains
        System.out.println(operatorList.contains(3));
        System.out.println(operatorList.contains(4));

        // 迭代器设计模式
        List<Integer> iteratorList = new ArrayList<>();
        iteratorList.add(1);
        iteratorList.add(2);
        iteratorList.add(3);
        iteratorList.add(4);
        iteratorList.add(5);
        iteratorList.add(6);
        Iterator<Integer> iterator = iteratorList.iterator();
        while (iterator.hasNext()) {
            Integer next = iterator.next();
            System.out.println(next);
        }

        // 普通fori循环遍历
        for (int i = 0,size = iteratorList.size(); i < size; i++) {
            System.out.println(iteratorList.get(i));
        }

        // forEach遍历，底层实现为迭代器
        for (Integer i : iteratorList) {
            System.out.println(i);
        }
    }
}
```

## 总结

`ArrayList`集合是我们在工作中用到的最多的集合，我们必须熟练掌握其特性。

通过上面的源码分析可知，`ArrayList`集合查找效率非常高。顺序添加元素至末尾效率也很高，但需要确保不扩容，否则进行数组拷贝很耗时。所以我们在创建`ArrayList`对象时，如果可以预估到集合中元素个数，最好指定初始容量，以避免在插入时扩容带来的性能损耗。

本文`Demo`实战代码见：[传送门](https://github.com/sunchaser-lilu/gold-road-to-Java/blob/master/java-se/src/main/java/com/sunchaser/javase/collect/ArrayListTest.java)