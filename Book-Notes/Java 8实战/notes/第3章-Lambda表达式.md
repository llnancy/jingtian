### 第三章：Lambda表达式
#### 1 函数式接口
函数式接口就是只定义一个抽象方法的接口。
接口现在还可以拥有默认方法（即在类没有对方法进行实现时，其主体为方法提供默认实现的方法）。
哪怕有很多默认方法，只要接口只定义了一个抽象方法，它就仍然是一个函数式接口。

函数式接口的抽象方法的签名称为函数描述符。

#### 1.1 Java8 内置四大核心函数式接口
java.util.function包下：
1. 消费型接口：Consumer<T> {void accept(T t);}
2. 供给型接口：Supplier<T> {T get();}
3. 函数型接口：Function<T,R> {R apply(T t);}
4. 断言型接口：Predicate<T> {boolean test(T t);}

#### 1.2 原始类型特化函数式接口
为了避免装箱操作，对Predicate<T>和Function<T, R>等通用函数式接口的原始类型特化：IntPredicate、IntToLongFunction等。 

#### 2 方法引用
如果一个Lambda代表的只是“直接调用这个方法”，那好还是用名称来调用它，而不是去描述如何调用它。

##### 2.1 如何构建方法引用
方法引用主要有三类。 

(1) 指向静态方法的方法引用（例如Integer的parseInt方法，写作Integer::parseInt）。

(2) 指向任意类型实例方法的方法引用（例如String 的 length 方法，写作 String::length）。
 
(3) 指向现有对象的实例方法的方法引用（假设你有一个局部变量expensiveTransaction用于存放Transaction类型的对象，它支持实例方法getValue，那么你就可以写expensive- Transaction::getValue）。

##### 2.2 可以将Lambda表达式重构为等价的方法引用
(1) Lambda：(args) -> ClassName.staticMethod(args)

等价的方法引用为：ClassName::staticMethod。

(2) Lambda：(arg0,test) -> arg0.instanceMethod(test)

等价的方法引用为：ClassName::instanceMethod，其中arg0的类型是ClassName。

(3) Lambda：(args) -> expr.instanceMethod(args)

等价的方法引用为：expr::instanceMethod。

### 本章小结
1. Lambda表达式可以理解为一种匿名函数：它没有名称，但有参数列表、函数主体、返回 类型，可能还有一个可以抛出的异常的列表。
2. Lambda表达式让你可以简洁地传递代码。函数式接口就是仅仅声明了一个抽象方法的接口。只有在接受函数式接口的地方才可以使用Lambda表达式。
3. Java 8自带一些常用的函数式接口，放在java.util.function包里，包括Predicate<T>、Function<T,R>、Supplier<T>、Consumer<T>等。
4. 为了避免装箱操作，对Predicate<T>和Function<T, R>等通用函数式接口的原始类型特化：IntPredicate、IntToLongFunction等。
5. 方法引用让你重复使用现有的方法实现并直接传递它们。