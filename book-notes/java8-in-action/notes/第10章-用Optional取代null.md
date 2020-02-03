## 第十章：用Optional取代null
`null`引用的想法最初由一位英国计算机科学家提出。他认为这是为”不存在的值“建模最容易的方式。设计初衷是通过编译器的自动检测机制，确保所有使用引用的地方都是绝对安全的。近些年出现的现代程序设计语言包括Java在内，都采用了相同的设计方式。

#### null 引用带来的问题
- 错误之源：程序员对对象的字段进行检查，判断字段的值是否为期望的格式，最终却发现访问的并不是一个对象，而是一个`null`引用--空指针，于是抛出一个`NullPointerException`异常。
- 代码膨胀：为了防止`null`，我们经常会写出这样类似的代码：`if (xxx != null) { // dosomething}`。这样你的代码就会充斥着深度嵌套的`null`检查，代码可读性极差。
- 毫无意义：`null`自身没有任何的语义，尤其是，它代表的是在静态类型语言中以一种错误的方式对缺失变量值的建模。
- 破坏哲学：Java一直试图避免让程序员意识到指针的存在，唯一的例外是：null指针。

#### 使用防御式编程思想减少NPE问题
`NullPointerException`简称NPE。

采用防御式编程--快速失败的思想可以有效减少NPE问题。

如下的代码是推荐的方式：
```
if (param1 == null) {
    return false;
}
if (param2 == null) {
    return false;
}
// do service
```

不要使用`try-catch`的方式来处理。例如：
```
try {
    param.function();
} catch (NullPointerException e) {
    ...
}
```

使用这种防御式编程的方法能有效解决问题，但仍然极易出错，一旦忘记检查那个可能为null的属性就会埋下一个隐藏的bug。

#### 优雅的解决方式
Java 8中引入了一个新的类`java.util.Optional<T>`来优雅的解决NPE问题。

假设现在有一个`Person`类，有一个成员变量：汽车`Car`类；汽车`Car`类中又有一个成员变量：保险公司`Insurance`类；保险公司`Insurance`类中有一个成员变量：`String`类型的公司名。你可能会按下面的方式设计。
`Person`类：

```
public class Person {
    private Car car;
    public Car getCar() {
        return car;
    }
}
```

`Car`类：
```
public class Car {
    private Insurance insurance;
    public Insurance getInsurance() {
        return insurance;
    }
}
```

`Insurance`类：
```
public class Insurance {
    private String name;
    public String getName() {
        return name;
    }
}
```
但是我们知道一个人可能有车也可能没有车，当一个人没车时需要将`null`引用赋值给`car`变量，于是我们应该将`car`变量声明为`Optional<Car>`类型；一辆车可能买了保险也可能没买保险，于是`Car`类中的`insurance`字段也应该声明为`Optional<Insurance>`类型；保险公司一定有一个公司名称，所以`Insurance`类中的`name`字段不用声明成`Optional`类型。新的类定义如下：
`Person`类：

```
import java.util.Optional;

public class Person {
    private Optional<Car> car;
    public Optional<Car> getCar() {
        return car;
    }
}
```

`Car`类：
```
import java.util.Optional;

public class Car {
    private Optional<Insurance> insurance;
    public Optional<Insurance> getInsurance() {
        return insurance;
    }
}
```

`Insurance`类：
```
public class Insurance {
    private String name;
    public String getName() {
        return name;
    }
}
```
当`car`变量存在时，`Optional`类只是对`Car`类简单封装。当变量不存在时，缺失的值会被建模成一个“空” 的`Optional`对象，由方法`Optional.empty()`返回。`Optional.empty()`方法是一个静态工厂 方法，它返回`Optional`类的特定单一实例，与`null`完全不一样。

##### 何时使用Optional类？
在实际业务编码中，我们只能靠自己对业务模型的理解进行判断，需要判断出一个`null`是否属于某个变量的有效范围。

值得注意的是：如果一个类包含了`Optional`成员变量，则该类无法进行序列化和反序列化。

原因是`Optional`类未实现序列化`Serializable`接口。所以如果你的类是类似`Dubbo`服务提供者返回的模型对象，则不能将类成员设计成`Optional`类型。

Java语言的架构师Brian Goetz曾经非常明确地陈述过，`Optional`的设计初衷仅仅是要支持能返回`Optional`对象的语法。由于`Optional`类设计时就没特别考虑将其作为类的字段使用，所以它并未实现`Serializable`接口。

#### 应用Optional的几种模式
##### 创建Optional对象
- 声明一个空的`Optional`：通过静态工厂方法`Optional.empty`，创建一个空的`Optional`对象：
```
Optional<Car> optCar = Optional.empty();
```

- 依据一个非空值创建`Optional`：使用静态工厂方法`Optional.of`，依据一个非空值创建一个`Optional`对象：

```
Optional<Car> optCar = Optional.of(car);
```

如果`car`变量是一个`null`值，则这段代码会立即抛出`NullPointerException`，而不是等到试图访问`car`的属性时才返回一个错误。

- 可接受`null`的`Optional`：使用静态工厂方法`Optional.ofNullalbe`，创建一个允许`null`值的`Optional`对象：
```
Optional<Car> optCar = Optional.ofNullable(car);
```
如果`car`变量是一个`null`值，那么得到的`Optional`对象就是个空对象。

##### 使用map从Optional对象中提取和转换值
`Optional`类的`map`方法和`Stream`类的`map`方法相差无几。`map`方法会将流中的每个元素通过提供的函数进行映射。可以把`Optional`对象看成一个特殊的集合，它最多包含一个元素。如果`Optional`包含一个值，那map方法就会把该值通过提供的函数映射成其它值，否则什么也不做。

##### 使用flatMap链接Optional对象
同样的，`Optional`类的`flatMap`方法和流的用法也类似。`flatMap`方法接收一个函数作为参数，这个函数的返回值是另一个流。这个函数会应用到流中的每一个元素上，于是每个元素被映射成一个小流，每个小流又形成一个大流作为`flatMap`方法的返回值。但是`flatMap`会将这个大流中的小流扁平化成一个单一的流。即流中不包含流，只包含各个元素。

##### 默认行为及解引用Optional对象
`Optional`类提供了多种方法读取`Optional`实例中的变量值。

- `get()`：如果变量存在，直接返回封装的变量值，否则抛出一个`NoSuchElementException`异常。
- `orElse(T other)`：允许在`Optional`对象不包含值时提供一个默认值。
- `orElseGet(Supplier<? extends T> other)`：是`orElse`方法的延时调用版，`Supplier`方法只有在`Optional`对象不含值时才执行调用。如果创建默认值是件耗时操作，考虑使用该方法；或者非常确定某个方法仅在`Optional`为空时才执行调用，也考虑使用该方法。
- `orElseThrow(Supplier<? extends X> exceptionSupplier)`：和`get`方法类似，如果取出的值为空都会抛出一个异常，使用`orElseThrow`可以定制希望抛出的异常类型。
- `ifPresent(Consumer<? extends T>)`：在变量值存在时执行一个传入的`Consumer`方法，否则不做任何处理。

##### 使用filter剔除特定的值
我们经常需要调用某个对象的方法，查看它的某些属性。比如，我们可能需要检查保险公司的名称是否为`CambridgeInsurance`，为了以一种安全的方式进行这些操作，我们首先需要确定引用指向的`Insurance`对象是否为`null`，之后再调用它的`getName`方法。示例如下：
```
Insurance insurance = ...;
if (insurance != null && "CambridgeInsurance".equals(insurance.getName())) {
    System.out.println("ok");
}
```
如果使用`Optional`对象的`filter`方法，这段代码可以重构如下：
```
Optional<Insurance> optInsurance = ...;
optInsurance.filter(ins -> "CambridgeInsurance".equals(ins.getName()))
			.ifPresent(x -> System.out.println("ok"));
```
如果`Optional`对象包含的值为空，它不做任何操作；反之，它对`Optional`对象包含的值添加谓词操作，如果该操作的结果为`true`，它不做任何改变，直接返回该`Optional`对象，否则就将该值过滤掉，将`Optional`的值置为空。

#### Optional类的方法

|  方法名 |  描述   |
| :---- | :---- |
| empty | 返回一个空的 Optional 实例  |
| filter | 如果值存在并且满足提供的谓词，就返回包含该值的 Optional 对象；否则返回一个空的 Optional 对象 |
| flatMap | 如果值存在，就对该值执行提供的mapping映射函数调用，返回一个 Optional 类型的值，否则就返回一个空的 Optional 对象  |
| get | 如果该值存在，将该值用 Optional 封装返回，否则抛出一个 NoSuchElementException 异常 |
| ifPresent | 如果值存在，就执行使用该值的方法调用，否则什么也不做 |
| isPresent | 如果值存在就返回 true，否则返回 false |
| map | 如果值存在，就对该值执行提供的 mapping映射函数调用 |
| of | 将指定值用 Optional 封装之后返回，如果该值为 null，则抛出一个 NullPointerException 异常 |
| ofNullable | 将指定值用 Optional 封装之后返回，如果该值为 null，则返回一个空的 Optional 对象 |
| orElse | 如果有值则将其返回，否则返回一个默认值 |
| orElseGet | 如果有值则将其返回，否则返回一个由指定的 Supplier 接口生成的值 |
| orElseThrow | 如果有值则将其返回，否则抛出一个由指定的 Supplier 接口生成的异常|

#### 基础类型的Optional对象，以及为什么应该避免使用它们
与`Stream`对象一样，`Optional`也提供了类似的基础类型：`OptionalInt`、`OptionalLong`以及`OptionalDouble`。

如果`Stream`对象包含了大量元素，出于性能的考量， 使用基础类型是不错的选择，但对于`Optional`对象而言，这个理由就不成立了，因为`Optional`对象多只包含一个值。

不推荐大家使用基础类型的`Optional`对象，因为基础类型的`Optional`不支持`map`、`flatMap`和`filter`等方法。而这些却是`Optional`类最有用的方法。

## 本章小结
- `null`引用在历史上被引入到程序设计语言中，目的是为了对不存在的值进行建模。
- Java 8中引入了一个新的类`java.util.Optional<T>`，对存在或缺失的变量值进行建模。
- 可以使用静态工厂方法`Optional.empty`、`Optional.of`以及`Optional.ofNullable`创建`Optional`对象。
- `Optional`类支持多种方法，比如`map`、`flatMap`、`filter`，它们在概念上与`Stream`类中对应的方法十分相似。
- 使用`Optional`类能更优雅有效地防止代码中出现不期而至的空指针异常。