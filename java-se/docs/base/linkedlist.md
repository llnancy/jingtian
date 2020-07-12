## 前言

`LinkedList`的底层实现是双向链表，在学习其源码之前，我们首先要搞懂链表的原理。

## 单链表简介

单链表中的数据是以节点的形式来表示的，每个节点存储了当前节点的元素值（数据域）和下一个节点（后继节点）的地址值（指针域）。每个节点的内存空间可以是不连续的，它通过每个节点的指针域将各个节点连接起来。

每个节点只有一个指针域的链表称为单链表。如果想要遍历单链表，则必须从头节点开始迭代。

## 双向链表简介

双向链表与单链表相比，它的每个节点在单链表的基础上还存储了上一个节点（前驱节点）的地址值。也就是说，我们从任一节点开始，都可以遍历整个链表。

## 循环链表简介

单链表和双链表都可以构造成循环链表，只需将最后一个节点的后继结点指针域指向头节点，形成一个环。因此，从循环链表的任意一个节点开始，都可以遍历整个链表。

## `LinkedList`简介

`LinkedList`类是基于双向链表实现的，它继承了`AbstractSequentialList`顺序访问集合抽象模板类，实现了有序集合接口`List`和双端队列接口`Deque`。所以，`LinkedList`即可以作为集合使用，同时可以用作双端队列，还可以用作栈。这是一个非常优秀的实现类。

## `LinkedList`特性

由于`LinkedList`类继承自`AbstractSequentialList`抽象类，而`AbstractSequentialList`抽象类又继承自`AbstractList`抽象类。于是`LinkedList`类得到了`AbstractList`抽象类的`modCount`字段。基于该字段实现了`fail-fast`机制。

## 源码详解

### 成员变量

```java

    /**
     * 定义集合的大小
     */
    transient int size = 0;

    /**
     * 指向双向链表第一个节点的引用
     */
    transient Node<E> first;

    /**
     * 指向双向链表最后一个节点的引用
     */
    transient Node<E> last;

    ...

    /**
     * 序列化ID
     */
    private static final long serialVersionUID = 876323262645176354L;
```

### `Node`节点类

```java
    private static class Node<E> {
        // 数据域
        E item;
        // 后继节点
        Node<E> next;
        // 前驱节点
        Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
```

### 构造器

遵循了`Collection`接口的规范：提供了两个标准构造器：`void`无参构造器和带`Collection`类型的单个参数构造器。

```java
    /**
     * 无参构造器
     */
    public LinkedList() {
    }

    /**
     * 带参构造器：构造一个包含指定集合中元素的链表，其顺序为指定集合迭代器返回的顺序
     */
    public LinkedList(Collection<? extends E> c) {
        this();
        addAll(c);
    }
```

无参构造器中没有任何操作。

带参构造器中首先调用了无参构造器，然后调用`addAll(c)`方法构造链表。

```java
    public boolean addAll(Collection<? extends E> c) {
        return addAll(size, c);
    }
```

该`addAll(c)`方法是将指定集合`c`添加至此列表末尾，由于是在构造器中调用，所以`size`为初始值`0`。

我们来看下重载的`addAll(size,c)`方法：

```java
    public boolean addAll(int index, Collection<? extends E> c) {
        // 校验索引是否越界
        checkPositionIndex(index);

        // 集合转Object数组
        Object[] a = c.toArray();
        // 计算数组长度
        int numNew = a.length;
        if (numNew == 0)
            return false;
        // pred： 插入位置的前驱节点（索引：index - 1）
        // succ： 插入位置的后继节点（索引：index + 1）
        Node<E> pred, succ;
        if (index == size) {
            // 插入位置为末尾
            // 后继节点为null
            succ = null;
            // 前驱节点为 last
            pred = last;
        } else {
            // 插入位置不在末尾，在链表中间
            // 后继节点为 node(index)
            succ = node(index);
            // 前驱节点为后继节点succ的前驱节点
            pred = succ.prev;
        }

        // 迭代Object数组a
        for (Object o : a) {
            // 强制类型转换
            @SuppressWarnings("unchecked") E e = (E) o;
            // 创建一个新的节点newNode：其前驱节点为pred，节点数据域为此次迭代元素e，后继节点为null
            Node<E> newNode = new Node<>(pred, e, null);
            if (pred == null)
                // 前驱节点为null：链表初始为空链表；新节点newNode为头节点
                first = newNode;
            else
                // 前驱节点不为null：将pred的后继节点指向newNode
                pred.next = newNode;
            // 将前驱节点置为newNode，以便下次迭代链接节点。
            pred = newNode;
        }
        // 迭代完成
        if (succ == null) {
            // 后继节点为null：初始插入位置为链表末尾：将last置为最后一次迭代的pred，即为链表末尾元素。
            last = pred;
        } else {
            // 后继节点不为null：从链表中间进行插入：将最后一次迭代的pred元素的后继节点指向succ。
            pred.next = succ;
            // 将succ的前驱节点指向pred。
            succ.prev = pred;
        }
        // 集合大小增加
        size += numNew;
        // 修改次数增加
        modCount++;
        return true;
    }
```

该方法是将指定集合插入至指定索引位置，指定索引位置的元素（如果有的话）和后续所有元素都将右移。

分为两种情况：

1、 从链表尾部插入

![从链表尾部插入](https://cdn.jsdelivr.net/gh/sunchaser-lilu/sunchaser-cdn@master/images/jdk/linkedlistaddfromlast.gif)

初始条件为：

- 初始链表为：`[L,I,N]`
- 指定索引位置：`index：3`
- 待插入元素集合：`collection：[K,E,D]`
- 插入位置的前驱节点：`pred：N`
- 插入位置的后继节点：`succ：null`

插入过程：

迭代开始：

- 待插入集合转数组：`a：[K,E,D]`
- 第一次迭代：创建新节点`newNode`，值为`K`，前驱节点`pred`为`N`，后继节点`succ`为`null`，将N的后继节点指向`K`，将前驱节点`pred`引用`newNode:K`。
- 第二次迭代：创建新节点`newNode`，值为`E`，前驱节点`pred`为`K`，后继节点`succ`为`null`，将`K`的后继节点指向`E`， 将前驱节点`pred`引用`newNode:E`。
- 第三次迭代：创建新节点`newNode`，值为`D`，前驱节点`pred`为`E`，后继节点`succ`为`null`，将`E`的后继节点指向`D`，将前驱节点`pred`引用`newNode:D`。

迭代完成。

此时后继节点`succ`仍指向`null`，前驱节点`pred`引用`newNode:D`，为链表末尾节点，将`last`引用置为`pred`。

集合大小增加了数组`a`的长度个；修改次数`modCount`增加。

2、 从链表中间插入

![从链表中间插入](https://cdn.jsdelivr.net/gh/sunchaser-lilu/sunchaser-cdn@master/images/jdk/linkedlistaddfrommid.gif)

初始条件为：

- 初始链表为：`[L,I,N,D]`
- 指定索引位置：`index：3`
- 待插入元素集合：`collection：[K,E]`
- 插入位置的前驱节点：`pred：N`
- 插入位置的后继节点：`succ：D`

插入过程：

迭代开始：

- 待插入集合转数组：`a：[K,E]`
- 第一次迭代：创建新节点`newNode`，值为`K`，前驱节点`pred`为`N`，后继节点`succ`为`D`，将N的后继节点指向`K`，将前驱节点`pred`引用`newNode:K`。
- 第二次迭代：创建新节点`newNode`，值为`E`，前驱节点`pred`为`K`，后继节点`succ`为`D`，将`K`的后继节点指向`E`， 将前驱节点`pred`引用`newNode:E`。

迭代完成。

此时后继节点`succ`仍指向`D`，前驱节点`pred`引用`newNode:E`，`last`引用仍指向`D`。

将`pred`的后继节点指向`D`，将`D`的前驱节点指向`E`。

集合大小增加了数组`a`的长度个；修改次数`modCount`增加。

### 内部方法

`LinkedList`类中提供了一些默认或私有方法用来将一个指定元素连接至整个链表。

#### <span id="linkFirst">连接指定元素作为头节点</span>

```java
    private void linkFirst(E e) {
        // 获取链表头节点f
        final Node<E> f = first;
        // 创建一个新的节点，其数据域为e，后继节点为链表头节点f。
        final Node<E> newNode = new Node<>(null, e, f);
        // 链表头节点引用指向新节点
        first = newNode;
        if (f == null)
            // 旧的头节点为null：原链表为空链表。将尾节点引用last也指向新节点。
            last = newNode;
        else
            // 旧的头节点不为null：新节点将作为整个链表的新的头节点。将旧的头节点的前驱节点指向新节点。
            f.prev = newNode;
        // 集合大小增加
        size++;
        // 修改次数增加
        modCount++;
    }
```

首先通过头节点引用`first`获取当前链表头节点`f`，然后创建一个新的`Node`节点`newNode`，其数据域为指定元素`e`，后继节点为当前链表头节点`f`；

然后将头节点引用`first`指向`newNode`，判断旧的头节点`f`是否为`null`，如果是，则原链表为空链表，将尾节点引用`last`指向`newNode`；否则，将旧的头节点`f`的前驱节点指向`newNode`。

#### <span id="linkLast">连接指定元素作为尾节点</span>

```java
    void linkLast(E e) {
        // 获取链表尾节点
        final Node<E> l = last;
        // 创建一个新的节点，其数据域为e，前驱节点为链表尾节点l
        final Node<E> newNode = new Node<>(l, e, null);
        // 链表尾节点引用执行新节点
        last = newNode;
        if (l == null)
            // 旧的尾节点为null：原链表为空链表。将头节点引用也指向新节点。
            first = newNode;
        else
            // 旧的尾节点不为null：新节点将作为整个链表的新的尾节点。将旧的尾节点的后继节点指向新节点。
            l.next = newNode;
        // 集合大小增加
        size++;
        // 修改次数增加
        modCount++;
    }
```

首先通过尾节点引用`last`获取当前链表尾节点`l`，然后创建一个新的`Node`节点`newNode`，其数据域为指定元素`e`，前驱节点为当前链表尾节点`l`；

然后将尾节点引用`last`指向`newNode`，判断旧的尾节点`l`是否为`null`，如果是，则原链表为空链表，将头节点引用`first`指向`newNode`；否则，将旧的尾节点`l`的后继节点指向`newNode`。

#### <span id="linkBefore">在指定节点之前连接指定元素</span>

```java
    /**
     * 约定指定节点succ不为null
     */
    void linkBefore(E e, Node<E> succ) {
        // assert succ != null;
        // 获取指定节点的前驱节点
        final Node<E> pred = succ.prev;
        // 创建一个新的节点，其数据域为e，前驱节点为指定节点的前驱节点，后继节点为指定节点。
        final Node<E> newNode = new Node<>(pred, e, succ);
        // 指定节点的前驱节点指向新节点
        succ.prev = newNode;
        if (pred == null)
            // 指定节点的旧的前驱节点为null：指定节点为原链表的头节点。新节点将作为新的头节点。
            first = newNode;
        else
            // 指定节点的旧的前驱节点不为null：将指定节点的旧的前驱节点的后继节点指向新节点。
            pred.next = newNode;
        // 集合大小增加
        size++;
        // 修改次数增加
        modCount++;
    }
```

约定指定节点`succ`非空，否则将导致`NPE`问题。

首先获取指定节点的前驱节点`pred`，然后创建一个新的`Node`节点`newNode`，其前驱节点为`pred`，数据域为指定元素`e`，后继节点为指定节点`succ`。

将指定节点`succ`的前驱节点指向`newNode`，判断指定节点`succ`的旧的前驱节点`pred`是否为`null`，如果是，则指定节点`succ`为原链表的头节点，将头节点引用`first`指向`newNode`；否则，将旧的前驱节点`pred`的后继节点指向`newNode`。

#### 取消头节点的连接

```java
    /**
     * 约定指定节点f为链表头节点且不为null
     */
    private E unlinkFirst(Node<E> f) {
        // assert f == first && f != null;
        // 获取头节点的元素值
        final E element = f.item;
        // 获取头节点的后继节点：即将成为新的头节点。
        final Node<E> next = f.next;
        // 将原头节点的数据域和后继节点置为null。解除对其它对象的引用，便于GC清理。
        f.item = null;
        f.next = null; // help GC
        // 将头节点引用指向next
        first = next;
        if (next == null)
            // next为null：原链表只有一个节点。将尾节点引用置为null。
            last = null;
        else
            // next不为null：将next的前驱节点置为null。取消对原头节点的引用，便于GC清理。
            next.prev = null;
        // 集合大小减少
        size--;
        // 修改次数增加
        modCount++;
        // 返回被剔除的节点元素值。
        return element;
    }
```

约定指定节点为链表的头节点且非空，否则将导致`NPE`问题。

获取头节点的数据域值`element`和后继节点`next`，将头节点的数据域和后继节点置为`null`。解除对原对象的引用，便于`GC`清理。

然后将头节点引用`first`指向`next`，判断`next`是否为`null`，如果是，则原链表只有一个节点，将尾节点引用`last`置为`null`；否则，将`next`节点的前驱节点置为`null`，取消对原链表头节点的引用。

#### <span id="unlinkLast">取消尾节点的连接</span>

```java
    /**
     * 约定指定节点l为链表尾节点且不为null
     */
    private E unlinkLast(Node<E> l) {
        // assert l == last && l != null;
        // 获取尾节点的元素值
        final E element = l.item;
        // 获取尾节点的前驱节点：即将成为新的尾节点。
        final Node<E> prev = l.prev;
        // 将原尾节点的数据域和前驱节点置为null。解除对其它对象的引用，便于GC清理。
        l.item = null;
        l.prev = null; // help GC
        // 将尾节点引用指向prev
        last = prev;
        if (prev == null)
            // prev为null：原链表只有一个节点。将头节点引用置为null。
            first = null;
        else
            // prev不为null：将prev的后继节点置为null。取消对原尾节点的引用，便于GC清理。
            prev.next = null;
        // 集合大小减少
        size--;
        // 修改次数增加
        modCount++;
        // 返回被剔除的节点元素值
        return element;
    }
```

约定指定节点为链表的尾节点且非空，否则将导致`NPE`问题。

获取尾节点的数据域值`element`和前驱节点`prev`，将尾节点的数据域和前驱节点置为`null`。解除对原对象的引用，便于`GC`清理。

然后将尾节点引用`last`指向`prev`，判断`prev`是否为`null`，如果是，则原链表只有一个节点，将头节点引用`first`置为`null`；否则，将`prev`节点的后继节点置为`null`，取消对原链表尾节点的引用。

#### <span id="unlink">取消指定节点的连接</span>

```java
    /**
     * 约定指定节点x不为null
     */
    E unlink(Node<E> x) {
        // assert x != null;
        // 获取指定节点x的数据域元素
        final E element = x.item;
        // 获取指定节点x的后继节点
        final Node<E> next = x.next;
        // 获取指定节点x的前驱节点
        final Node<E> prev = x.prev;

        if (prev == null) {
            // 指定节点的前驱节点为null：指定节点为原链表头节点。指定节点的后继节点将作为新的头节点。
            first = next;
        } else {
            // 否则，将指定节点的前驱节点的后继节点指向指定节点的后继节点。
            prev.next = next;
            // 指定节点的前驱节点置为null
            x.prev = null;
        }

        if (next == null) {
            // 指定节点的后继节点为null：指定节点为原链表尾节点。指定节点的前驱节点将作为新的尾节点。
            last = prev;
        } else {
            // 否则，将指定节点的后继节点的前驱节点指向指定节点的前驱节点。
            next.prev = prev;
            // 指定节点的后继节点置为null
            x.next = null;
        }

        // 指定节点的数据域元素置为null
        x.item = null;
        // 集合大小减少
        size--;
        // 修改次数增加
        modCount++;
        // 返回被剔除的节点元素值
        return element;
    }
```

约定指定节点`x`非空，否则将导致`NPE`问题。

分别获取指定节点`x`的数据域元素`element`、后继节点`next`和前驱节点`prev`。

如果前驱节点`prev`为`null`，则在原链表中，指定节点`x`为头节点，指定节点的后继节点`next`将作为新的头节点：将`first`引用指向后继节点`next`；否则将前驱节点`prev`的后继节点指向`next`，将指定元素`x`的前驱节点置为`null`。

如果后继节点`next`为`null`，则在原链表中，指定节点`x`为尾节点，指定节点的前驱节点`prev`将作为新的尾节点：将`last`引用指向前驱节点`prev`；否则将后继节点`next`的前驱节点指向`prev`，将指定元素`x`的后继节点置为`null`。

最后将指定节点`x`的数据域置为`null`，解除对象引用，便于`GC`清理。

#### <span id="node">获取指定索引位置的`Node`节点</span>

```java
    /**
     * 约定指定索引合法
     */
    Node<E> node(int index) {
        // assert isElementIndex(index);
        // 二分法
        if (index < (size >> 1)) {
            // 指定索引在[0,size/2)之间
            // 获取头节点x
            Node<E> x = first;
            // 从0迭代到index，每次迭代将x赋值为x的后继节点
            for (int i = 0; i < index; i++)
                x = x.next;
            // 迭代完成，x即为指定索引位置的节点
            return x;
        } else {
            // 指定索引在[size/2,size]之间
            // 获取尾节点 
            Node<E> x = last;
            // 从size-1迭代到index，每次迭代将x赋值为x的前驱节点
            for (int i = size - 1; i > index; i--)
                x = x.prev;
            // 迭代完成，x即为指定索引位置的节点
            return x;
        }
    }
```

约定指定索引不会产生越界。

采用二分法的思想优化时间复杂度，如果指定索引`index`在`[0,size/2)`范围内，则从`0`迭代至`index - 1`，迭代之前获取头节点`x`，每次迭代将`x`赋值为`x`的后继节点，迭代完成时，`x`即为指定索引`index`位置的元素；如果指定索引`index`在[size/2,size)之间，则从`size - 1`迭代至`index + 1`，迭代之前获取尾节点`x`，每次迭代将`x`赋值为`x`的前驱节点，迭代完成时，`x`即为指定索引`index`位置的元素。

### 集合方法

`LinkedList`继承了`AbstractSequentialList`有序集合顺序访问抽象模板类。对集合方法进行了实现，可以作为集合使用。

#### <span id="add">添加指定元素至集合末尾</span>

```java
    public boolean add(E e) {
        // 调用内部方法linkLast(e)
        linkLast(e);
        return true;
    }
```

该方法的最初来源是`Collection`接口中定义的`add(E e)`方法。

> `AbstractSequentialList`抽象类继承了`AbstractList`抽象类，在`AbstractList`抽象类中已经实现了`add(E e)`方法。它只要求子类去实现`add(int index,E e)`方法即可实现`add(E e)`。在`LinkedList`类中已经提供了`add(int index,E e)`方法的实现，它为什么还要重写`add(E e)`方法呢？
> 
> 理解：`LinkedList`作为一个优秀的实现类，它继承/实现了多个接口。在编码开发时，我们经常面向接口编程，例如：`List<String> list = new LinkedList<>()`。`LinkedList`类的对象会向上转型为`List`类。对于实例对象`list`来说，此时它是一个`List`，它所具有的方法是`List`接口中定义的方法，而不是`LinkedList`类中的方法，所以当我们调用`list.add(E e)`时，实际上调用的是`List`类中定义的`add(E e)`方法，而它的实现是在`LinkedList`类中。
> 
> 也就是说，当我们想把`LinkedList`作为`List`集合使用时，我们可以写成：`List<String> list = new LinkedList<>()`。而当我们想把`LinkedList`当做`Queue`队列使用时，我们可以写成`Queue<String> queue = new LinkedList<>()`。它可以向上转型成任意的父接口（或父接口的父接口），向上转型后，实例对象只拥有转型后的接口中所定义的方法。所以，`LinkedList`类中对所有父接口中定义的方法都进行了实现，以便向上转型使用。
> 
> 这体现了`Java`的多态机制。

直接调用了内部方法[`linkLast(e)`](#linkLast)，在不出现异常的情况下固定返回`true`。

#### 在指定索引位置添加指定元素

```java
    public void add(int index, E element) {
        // 校验索引是否越界
        checkPositionIndex(index);

        if (index == size)
            // 指定索引在集合末尾：调用内部方法连接指定元素至链表末尾
            linkLast(element);
        else
            // 指定索引在集合中间：调用内部方法连接指定元素至指定索引位置的节点之前。
            linkBefore(element, node(index));
    }
```

首先校验指定索引是否越界，然后判断指定索引是否在集合末尾，如果是，则指定元素为链表的新尾节点，调用内部方法[`linkLast(e)`](#linkLast)，将指定元素连接至链表末尾；否则，调用内部方法[`linkBefore(e,node)`](#linkBefore)将指定元素连接至指定索引位置的节点之前。

#### 清空所有元素

```java
    public void clear() {
        // Clearing all of the links between nodes is "unnecessary", but:
        // - helps a generational GC if the discarded nodes inhabit
        //   more than one generation
        // - is sure to free memory even if there is a reachable Iterator
        for (Node<E> x = first; x != null; ) {
            // 迭代：初始x为头节点；迭代结束的条件为：x为null。
            // 获取x的后继节点
            Node<E> next = x.next;
            // 当前x的数据域、后继节点和前驱节点都置为null
            x.item = null;
            x.next = null;
            x.prev = null;
            // x赋值为其后继节点，进行下一次迭代。
            x = next;
        }
        // 清除头尾节点引用
        first = last = null;
        // 重置集合大小
        size = 0;
        // 修改次数增加
        modCount++;
    }
```

迭代链表，`for`循环中获取原链表头节点`x`，迭代过程中对`x`进行重新赋值，迭代结束的条件为`x`为`null`。

每次迭代将当前迭代的节点`x`的数据域、后继节点和前驱节点都置为`null`，然后将`x`赋值为`x`的后继节点，进行下一次迭代，直至`x`为尾节点。

迭代完成后清除头尾节点`first`和`last`的引用，重置集合大小为`0`。

#### 获取指定元素在集合中第一次出现的索引位置

```java
    public int indexOf(Object o) {
        // 索引记录
        int index = 0;
        if (o == null) {
            // 指定元素为null
            for (Node<E> x = first; x != null; x = x.next) {
                // 迭代：获取链表头节点x，迭代结束的条件为x为null，每次迭代完成将x赋值为x的后继节点
                // 判断节点x的数据域是否为null：是则找到了指定元素的第一次出现，返回索引记录index；否则，索引增一。
                if (x.item == null)
                    return index;
                index++;
            }
        } else {
            // 指定元素不为null，采用equals方法比较是否相等
            for (Node<E> x = first; x != null; x = x.next) {
                // 迭代的条件同指定元素为null的情况
                if (o.equals(x.item))
                    return index;
                index++;
            }
        }
        // 迭代完成仍未返回：集合中不存在指定元素，返回-1。
        return -1;
    }
```

首先创建一个索引记录`index`初始为`0`，然后迭代链表，分为指定元素为`null`和非`null`两种情况，为`null`则采用`==`运算符比较；非`null`则采用`equals`方法比较是否相等。从链表头节点开始迭代，一旦找到相等的元素，则返回索引`index`，否则索引增一。如果迭代完成仍未找到指定元素，则返回`-1`。

#### 获取指定元素在集合中最后一次出现的索引位置

```java
    public int lastIndexOf(Object o) {
        // 索引记录初始为集合大小size
        int index = size;
        if (o == null) {
            // 指定元素为null，采用==运算符比较
            for (Node<E> x = last; x != null; x = x.prev) {
                // 迭代：获取链表尾节点x，迭代结束的条件为x为null，每次迭代后将x赋值为x的前驱节点
                // 首先索引记录减一
                index--;
                // 判断节点x的数据域是否为null：是则找到了指定元素的最后一次出现，返回索引记录index；否则，进行下一次迭代。
                if (x.item == null)
                    return index;
            }
        } else {
            // 指定元素不为null，采用equals方法比较是否相等
            for (Node<E> x = last; x != null; x = x.prev) {
                // 迭代的条件同指定元素为null的情况
                index--;
                if (o.equals(x.item))
                    return index;
            }
        }
        // 迭代完成仍未返回：集合中不存在指定元素，返回-1。
        return -1;
    }
```

首先创建一个索引记录`index`初始为集合大小`size`，然后迭代链表，分为指定元素为`null`和非`null`两种情况，为`null`则采用`==`运算符比较；非`null`则采用`equals`方法比较是否相等。从链表尾节点开始迭代，迭代一开始就将索引记录减一，然后进行相等比较，一旦找到相等的元素，则返回索引`index`，否则进行下一次迭代。如果迭代完成仍未找到指定元素，则返回`-1`。

#### 判断指定元素是否在集合中至少出现一次

```java
    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }
```

实际上调用的是`indexOf`方法寻找指定元素的索引位置，如果返回`-1`则集合中不存在该指定元素，否则表示指定元素在集合中至少出现过一次。

#### 获取指定索引位置的元素

```java
    public E get(int index) {
        // 校验索引是否越界
        checkElementIndex(index);
        // 调用内部方法node(index)获取指定索引位置的节点，取出数据域item返回。
        return node(index).item;
    }
```

首先校验指定索引是否越界，然后调用内部方法[`node(index)`](#node)获取指定索引位置的节点，取出其数据域`item`返回。

#### <span id="remove">剔除指定索引位置的元素</span>

```java
    public E remove(int index) {
        // 校验指定索引是否越界
        checkElementIndex(index);
        // 调用内部方法node获取指定索引位置节点，再调用内部方法unlink取消此节点的连接
        return unlink(node(index));
    }
```

首先校验指定索引是否越界，然后调用内部方法[`node(index)`](#node)获取指定索引位置的节点，最后调用内部方法[`unlink(node)`](#unlink)取消此节点的连接。

#### <span id="remove">剔除指定元素的第一次出现</span>

```java
    public boolean remove(Object o) {
        if (o == null) {
            // 指定元素为null：从头节点x开始迭代，迭代结束的条件为x为null，每次迭代将x赋值为x的后继节点。
            for (Node<E> x = first; x != null; x = x.next) {
                if (x.item == null) {
                    // 如果找到了null元素，则调用内部方法unlink剔除x元素的连接。
                    unlink(x);
                    return true;
                }
            }
        } else {
            // 指定元素非null
            for (Node<E> x = first; x != null; x = x.next) {
                // 迭代的条件同指定元素为null的情况，使用equals比较当前迭代元素x与指定元素是否相等。
                if (o.equals(x.item)) {
                    unlink(x);
                    return true;
                }
            }
        }
        // 迭代完成都未返回，则集合中无该元素，返回false。
        return false;
    }
```

迭代链表，分为指定元素为`null`和非`null`两种情况，为`null`采用`==`运算符进行比较，非`null`则采用`equals`方法比较是否相等。从链表头节点开始迭代，一旦找到相等的元素，则调用内部方法[`unlink(node)`](#unlink)取消该节点的连接，返回`true`。如果迭代完成时都未找到相等的元素，则返回`false`。

#### 覆盖指定索引位置的元素

```java
    public E set(int index, E element) {
        // 校验指定索引是否越界
        checkElementIndex(index);
        // 获取指定索引位置节点x
        Node<E> x = node(index);
        // 取出x的数据域
        E oldVal = x.item;
        // 给x的数据域赋值为指定元素
        x.item = element;
        // 返回x节点旧的数据域
        return oldVal;
    }
```

首先校验指定索引是否越界，然后调用内部方法[`node(index)`](#node)获取指定索引位置的节点`x`，取出节点`x`的数据域，然后为`x`的数据域重新赋值为指定元素，最后返回节点`x`旧的数据域。

#### 获取集合中元素个数

```java
    public int size() {
        return size;
    }
```

直接返回`size`成员变量。

#### 转换成`Object`数组

```java
    public Object[] toArray() {
        // 定义size长度的数组
        Object[] result = new Object[size];
        int i = 0;
        // 从头节点x开始迭代，迭代结束的条件为x=null，每次迭代完成将x赋值为x的后继节点
        for (Node<E> x = first; x != null; x = x.next)
            // 填充数组
            result[i++] = x.item;
        // 返回数组
        return result;
    }
```

首先定义一个`size`长度的`Object`数组，然后从链表头节点开始迭代，每次迭代向`Object`数组中填充一个元素，迭代完成时数组中的元素即为链表中的全部元素，最后返回数组。

#### 转换成指定类型数组

```java
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        // 指定数组长度是否小于链表长度
        if (a.length < size)
            // 小于：重新分配一个size长度数组
            a = (T[])java.lang.reflect.Array.newInstance(
                                a.getClass().getComponentType(), size);
        int i = 0;
        // 返回数组容器
        Object[] result = a;
        // 迭代链表，填充元素。
        for (Node<E> x = first; x != null; x = x.next)
            result[i++] = x.item;
        // 迭代完成，如果指定数组的长度大于链表长度，则将返回数组容器中size索引位置的元素置为null
        // null元素之前为原链表元素，null元素之后为原指定数组元素。
        if (a.length > size)
            a[size] = null;
        // 数组返回。此时数组中元素类型已由Object强转成泛型T
        return a;
    }
```

入参为泛型数组`a`，判断入参数组长度是否小于链表长度，小于则重新分配一个链表长度`size`大小的数组空间。迭代链表，将元素填充至数组，迭代完成时，如果是重新分配的数组空间，则数组中的元素恰好全部为链表中的元素；否则数组中的元素多于链表中的元素，将`size`索引位置的元素置为`null`，此时，`null`元素之前为原链表元素，之后为原入参数组元素。最后返回将`Object`数组强转成泛型数组。

此方法可用于将集合中的元素类型转换为指定类型并转换成数组。

### 双端队列方法

`LinkedList`实现了`Deque`双端队列接口，提供了双端队列的基本实现。

#### 入队列

##### 入队首

```java
    public void addFirst(E e) {
        linkFirst(e);
    }
```

直接调用内部方法[`linkFirst(e)`](#linkFirst)连接指定元素`e`作为新的头节点。

```java
    public boolean offerFirst(E e) {
        addFirst(e);
        return true;
    }
```

同样是调用了内部方法[`linkFirst(e)`](#linkFirst)连接指定元素`e`作为新的头节点。然后返回特殊值`true`。

##### 入队尾

```java
    public void addLast(E e) {
        linkLast(e);
    }
```

直接调用内部方法[`linkLast(e)`](#linkLast)连接指定元素`e`作为新的尾节点。

```java
    public boolean offerLast(E e) {
        addLast(e);
        return true;
    }
```

同样是调用了内部方法[`linkLast(e)`](#linkLast)连接指定元素`e`作为新的尾节点。然后返回特殊值`true`。

#### 出队列

##### 从队首出队列

```java
    public E removeFirst() {
        final Node<E> f = first;
        if (f == null)
            throw new NoSuchElementException();
        return unlinkFirst(f);
    }
```

首先获取链表头节点，如果头节点为`null`，则为空队列，抛出`NoSuchElementException`异常。

否则调用内部方法[`unlinkFirst(e)`](#unlinkFirst)剔除头节点的连接。

```java
    public E pollFirst() {
        final Node<E> f = first;
        return (f == null) ? null : unlinkFirst(f);
    }
```

同样是先获取链表头节点，如果头节点为`null`，则为空队列，返回`null`，不会抛出异常；否则调用内部方法[`unlinkFirst(e)`](#unlinkFirst)剔除头节点的连接。

此实现符合`Deque`接口中的定义：当队列为空队列时，`removeFirst()`方法抛出异常，而`pollFirst()`方法返回特殊值`null`。

##### 从队尾出队列

```java
    public E removeLast() {
        final Node<E> l = last;
        if (l == null)
            throw new NoSuchElementException();
        return unlinkLast(l);
    }
```

首先获取链表尾节点，如果尾节点为`null`，则为空队列，抛出`NoSuchElementException`异常。

否则调用内部方法[`unlinkLast(e)`](#unlinkLast)剔除尾节点的连接。

```java
    public E pollLast() {
        final Node<E> l = last;
        return (l == null) ? null : unlinkLast(l);
    }
```

同样是先获取链表尾节点，如果尾节点为`null`，则为空队列，返回`null`，不会抛出异常；否则调用内部方法[`unlinkLast(e)`](#unlinkLast)剔除尾节点的连接。

此实现也符合`Deque`接口中的定义：当队列为空队列时，`removeLast()`方法抛出异常，而`pollLast()`方法返回特殊值`null`。

##### <span id="getFirst">查询队首元素</span>

```java
    public E getFirst() {
        final Node<E> f = first;
        if (f == null)
            throw new NoSuchElementException();
        return f.item;
    }
```

首先获取链表头节点，如果头节点为`null`，则为空队列，抛出`NoSuchElementException`异常。否则返回头节点的数据域。

```java
    public E peekFirst() {
        final Node<E> f = first;
        return (f == null) ? null : f.item;
     }
```

同样是先获取链表头节点，如果头节点为`null`，则为空队列，返回`null`，不会抛出异常；否则返回头节点的数据域。

此实现也符合`Deque`接口中的定义：当队列为空队列时，`getFirst()`方法抛出异常，而`peekFirst()`方法返回特殊值`null`。

##### 查询队尾元素

```java
    public E getLast() {
        final Node<E> l = last;
        if (l == null)
            throw new NoSuchElementException();
        return l.item;
    }
```

首先获取链表尾节点，如果尾节点为`null`，则为空队列，抛出`NoSuchElementException`异常。否则返回尾节点的数据域。

```java
    public E peekLast() {
        final Node<E> l = last;
        return (l == null) ? null : l.item;
    }
```

同样是先获取链表尾节点，如果尾节点为`null`，则为空队列，返回`null`，不会抛出异常；否则返回尾节点的数据域。

此实现也符合`Deque`接口中的定义：当队列为空队列时，`getLast()`方法抛出异常，而`peekLast()`方法返回特殊值`null`。

##### 删除第一次出现的指定元素

```java
    public boolean removeFirstOccurrence(Object o) {
        return remove(o);
    }
```

直接调用集合方法[`remove(o)`](#remove)剔除指定元素的第一次出现。

##### 删除最后一次出现的指定元素

```java
    public boolean removeLastOccurrence(Object o) {
        if (o == null) {
            // 指定元素为null
            // 从尾节点开始迭代链表，一旦找到数据域为null的节点，则调用内部方法unlink剔除该节点的连接。
            for (Node<E> x = last; x != null; x = x.prev) {
                if (x.item == null) {
                    unlink(x);
                    return true;
                }
            }
        } else {
            // 指定元素非null，使用equals比较元素是否相等
            // 迭代的条件同指定元素为null的情况
            for (Node<E> x = last; x != null; x = x.prev) {
                if (o.equals(x.item)) {
                    unlink(x);
                    return true;
                }
            }
        }
        // 迭代完成仍未找到，返回false。
        return false;
    }
```

分为指定元素为`null`和非`null`两种情况，为`null`采用`==`运算符比较，非`null`采用`equals`方法比较。从尾节点开始迭代，一旦找到数据域与指定元素相等的节点，则调用内部方法[`unlink`](#unlink)剔除该节点的连接。

### 普通队列方法

`Deque`双端队列接口中同样声明了普通队列`Queue`接口中定义的一系列方法。`LinkedList`类也对其进行了实现。

`Queue`接口中定义的方法如下：

action | throws exception | returns special value
:--- | :---| :---
insert | add(e) | offer(e)
remove | remove() | poll()
get head | element() | peek()

它有三组共六个方法，在`Queue`接口的声明中约定了每组方法中有一个是失败时抛出指定异常，另一个是返回特殊值`null`或`false`。

`LinkedList`类却没有完全遵守`Queue`接口的约定，所有方法在执行失败时均未抛出指定异常。这是为什么呢？

在我看来，`LinkedList`的实现初衷是作为`List`集合使用（这点可从类名体现出来，它是一种`List`）。由于其底层数据结构是双向链表，它可以用来实现队列等其它数据结构，所以设计者让`LinkedList`类实现了队列接口。

> 是因为双向链表这种数据结构可以用来实现队列，所以才实现队列接口。而不是因为实现了队列接口，才决定使用双向链表这种数据结构。

#### 从队尾入队列

第一个方法是`add(e)`，实际上这也是在`List`接口中定义的方法，它的实现既符合列表的定义，又符合队列的定义。

详情见集合方法中的[`add(e)`](#add)。

第二个方法是`offer(e)`：

```java
    public boolean offer(E e) {
        return add(e);
    }
```

直接调用了[`add(e)`](#add)方法。

#### 从队首出队列

第一个方法是`remove()`，该方法同样也定义在了`List`接口中，详情见集合方法中的[`remove`](#remove)。

第二个方法是`poll()`：

```java
    public E poll() {
        final Node<E> f = first;
        return (f == null) ? null : unlinkFirst(f);
    }
```

首先获取链表头节点，如果头节点为`null`，则为空队列，返回特殊值`null`；否则调用内部方法[`unlinkFirst(f)`](#unlinkFirst)剔除头节点的连接。这符合`Queue`接口中`poll`方法的定义。

#### 查询队首元素

第一个方法是`element()`：

```java
    public E element() {
        return getFirst();
    }
```

直接调用双端队列的[`getFirst()`](#getFirst)方法查询队首元素。

第二个方法是`peek()`：

```java
    public E peek() {
        final Node<E> f = first;
        return (f == null) ? null : f.item;
    }
```

首先获取链表头节点，如果头节点为`null`，则为空队列，返回特殊值`null`；否则返回头节点的数据域。这符合`Queue`接口中`peek`方法的定义。

### 迭代器

`LinkedList`类只提供了列表迭代器的实现，基类`AbstractSequentialList`类中提供的普通迭代器实现也是列表迭代器。

```java
    public ListIterator<E> listIterator(int index) {
        // 校验索引是否越界
        checkPositionIndex(index);
        // 返回从index位置开始的迭代器对象
        return new ListItr(index);
    }
```

迭代器`ListItr`实现：

```java
    private class ListItr implements ListIterator<E> {

        /**
         * 最后一次通过迭代器返回的节点对象
         */
        private Node<E> lastReturned;

        /**
         * 当前迭代器所持有的节点对象，它将作为迭代的依据，调用previous方法将返回其前驱节点；调用next方法将返回其后继节点。
         */
        private Node<E> next;

        /**
         * 当前迭代器所持有的节点对象的索引
         */
        private int nextIndex;

        /**
         * fail-fast机制的期望修改次数，初始化为AbstractList#modCount
         */
        private int expectedModCount = modCount;

        /**
         * 带参构造器，构造指定索引位置的列表迭代器。由外部校验索引合法。
         */
        ListItr(int index) {
            // assert isPositionIndex(index);
            // index = size 是合法的，便于从后向前迭代。此时next为尾节点last的后继节点null，可以调用previous方法迭代尾节点。
            next = (index == size) ? null : node(index);
            nextIndex = index;
        }

        /**
         * 判断是否迭代到了列表末尾
         */
        public boolean hasNext() {
            return nextIndex < size;
        }

        /**
         * 获取当前nextIndex索引位置的节点元素，且索引右移一位。
         */
        public E next() {
            // fail-fast
            checkForComodification();
            if (!hasNext())
                throw new NoSuchElementException();
            // 记录最后一次通过迭代器返回的节点对象
            lastReturned = next;
            // 当前迭代器所持有的节点对象 赋值 为其后继节点
            next = next.next;
            // 索引右移
            nextIndex++;
            // 返回元素值
            return lastReturned.item;
        }

        /**
         * 判断是否迭代到了列表开头
         */
        public boolean hasPrevious() {
            return nextIndex > 0;
        }

        /**
         * 获取当前nextIndex索引位置的节点元素，且索引左移一位。
         */
        public E previous() {
            // fail-fast机制
            checkForComodification();
            if (!hasPrevious())
                throw new NoSuchElementException();
            // 如果next为null，则初始化该迭代器对象时，构造方法中传入的index=size。
            // 表示从列表尾部开始迭代，将next赋值为尾节点last；否则赋值为其前驱节点。
            lastReturned = next = (next == null) ? last : next.prev;
            nextIndex--;
            return lastReturned.item;
        }

        /**
         * 返回当前迭代索引，其值由构造器初始化。
         */
        public int nextIndex() {
            return nextIndex;
        }

        /**
         * 返回当前迭代索引前一位
         */
        public int previousIndex() {
            return nextIndex - 1;
        }

        /**
         * 安全地从列表中删除上一次迭代方法（next/previous）返回的节点。
         * 迭代过程中只能调用该方法修改列表，否则将引发fail-fast机制。
         */
        public void remove() {
            // fail-fast机制
            checkForComodification();
            // 迭代状态校验：调用此remove方法之前必须先调用迭代方法。
            if (lastReturned == null)
                throw new IllegalStateException();

            // 获取上次迭代返回节点的后继节点
            Node<E> lastNext = lastReturned.next;
            // 剔除lastReturned节点的连接（会导致AbstractList#modCount增加）
            unlink(lastReturned);
            // 从后往前（previous）迭代时，next = lastReturned成立
            if (next == lastReturned)
                // 当前迭代器所持有的节点对象赋值为上次迭代返回节点的后继节点
                next = lastNext;
            else
                // 从前往后（next）迭代时，进入else分支，迭代索引减一。
                nextIndex--;
            // 重置迭代器迭代状态，下次调用迭代方法之前不可再调用remove方法。
            lastReturned = null;
            // 期望修改次数增加
            expectedModCount++;
        }

        /**
         * 覆盖上次迭代方法返回的节点数据域
         */
        public void set(E e) {
            // 迭代状态校验
            if (lastReturned == null)
                throw new IllegalStateException();
            // fail-fast机制
            checkForComodification();
            // 覆盖
            lastReturned.item = e;
        }

        /**
         * 在下次调用迭代方法将返回的节点之前连接指定节点e
         */
        public void add(E e) {
            checkForComodification();
            lastReturned = null;
            if (next == null)
                linkLast(e);
            else
                linkBefore(e, next);
            nextIndex++;
            expectedModCount++;
        }

        /**
         * JDK8 新增
         */
        public void forEachRemaining(Consumer<? super E> action) {
            Objects.requireNonNull(action);
            while (modCount == expectedModCount && nextIndex < size) {
                action.accept(next.item);
                lastReturned = next;
                next = next.next;
                nextIndex++;
            }
            checkForComodification();
        }

        /**
         * fail-fast机制：校验实际修改次数是否等于期望修改次数。不等则立即抛出ConcurrentModificationException异常。
         */
        final void checkForComodification() {
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
        }
    }
```

### 其它方法

#### 克隆

该类提供了深拷贝方法（从`LinkedList`维度来说可以称为深拷贝）。由于其内部持有的是对象引用，列表中的元素并没有进行“深拷贝”。

```java
    /**
     * 克隆实现
     */
    public Object clone() {
        // 获取一个浅拷贝对象
        LinkedList<E> clone = superClone();
        // 初始化状态
        // Put clone into "virgin" state
        clone.first = clone.last = null;
        clone.size = 0;
        clone.modCount = 0;
        // 值填充
        // Initialize clone with our elements
        for (Node<E> x = first; x != null; x = x.next)
            clone.add(x.item);

        return clone;
    }

    /**
     * 私有方法：调用Object#clone浅拷贝
     */
    private LinkedList<E> superClone() {
        try {
            return (LinkedList<E>) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }
```