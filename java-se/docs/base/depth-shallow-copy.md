无论是`java.lang.Object#clone()`方法还是自己实现的克隆方法，都存在着浅拷贝和深拷贝的问题。

那么什么是浅拷贝和深拷贝呢？

举一个不太恰当的例子：大家都玩过英雄联盟这款游戏吧？英雄联盟中的孙悟空这个英雄的`W`技能是“真假猴王”：孙悟空进入隐形状态片刻，并留下一个替身，替身在片刻后会对其周围的敌人造成魔法伤害。如果玩家攻击替身，替身掉血但本身不掉血。这似乎可以理解成深拷贝。

## 浅拷贝和深拷贝简介
- 浅拷贝：只拷贝被克隆对象中按值传递的属性数据，不拷贝引用类型的属性数据。换言之，所有对其它对象的引用仍指向原来的对象，拷贝的是栈内的引用而不是堆内的对象。
- 深拷贝：除了浅拷贝需要克隆的值传递的属性数据之外，还会拷贝引用类型所指向的对象，即拷贝的引用会指向新的对象。换言之，深拷贝把待克隆的对象所引用的对象全都拷贝了一遍。

深拷贝要深入到多少层，这是一个需要根据实际情况来决定的问题。当拷贝至无引用对象的时候，就可称之为完全深拷贝。此外，深拷贝中还可能会出现循环引用的问题，需要仔细处理。

## `java.lang.Object#clone()`浅拷贝
为了证实该方法是浅拷贝，我们先来尝试设计一下英雄，首先创建一个`LOL`英雄接口，它包含两个方法：攻击和被攻击，攻击时法力值减少，被攻击时生命值减少。代码如下：

```java
package com.sunchaser.javase.base.cloneable;

/**
 * LOL英雄接口
 * @author sunchaser
 * @since JDK8 2020/4/7
 * 
 */
public interface LolHero {

    /**
     * 攻击
     * @param lolSkill 使用的技能
     */
    void attack(LolSkill lolSkill);

    /**
     * 受到其它英雄攻击
     */
    void beAttacked();
}
```

然后我们来定义技能类，技能包括按键`key`、技能名称和技能描述：

```java
package com.sunchaser.javase.base.cloneable;

/**
 * LOL英雄的技能类
 * @author sunchaser
 * @since JDK8 2020/4/7
 * 
 */
public class LolSkill {
    private String key;
    private String name;
    private String desc;

    public LolSkill() {
    }

    public LolSkill(String key, String name, String desc) {
        this.key = key;
        this.name = name;
        this.desc = desc;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("LolSkill{");
        sb.append("key='").append(key).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", desc='").append(desc).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
```

接下来我们来定义生命值和法力值类。

生命值类：
```java
package com.sunchaser.javase.base.cloneable;

/**
 * 英雄生命值类
 * @author sunchaser
 * @since JDK8 2020/4/7
 * 
 */
public class LifeValue {
    private Integer lifeValue;

    public LifeValue() {
    }

    public LifeValue(Integer lifeValue) {
        this.lifeValue = lifeValue;
    }

    public Integer getLifeValue() {
        return lifeValue;
    }

    public LifeValue setLifeValue(Integer lifeValue) {
        this.lifeValue = lifeValue;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("LifeValue{");
        sb.append("lifeValue=").append(lifeValue);
        sb.append('}');
        return sb.toString();
    }
}
```

法力值类：
```java
package com.sunchaser.javase.base.cloneable;

/**
 * 英雄法力值类
 * @author sunchaser
 * @since JDK8 2020/4/7
 * 
 */
public class Mana {
    private Integer mana;

    public Mana() {
    }

    public Mana(Integer mana) {
        this.mana = mana;
    }

    public Integer getMana() {
        return mana;
    }

    public Mana setMana(Integer mana) {
        this.mana = mana;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Mana{");
        sb.append("mana=").append(mana);
        sb.append('}');
        return sb.toString();
    }
}
```

最后我们来创建孙悟空英雄类，它实现了自定义的`LOL`英雄接口和`java.lang.Cloneable`接口，包含英雄名称、生命值、法力值和四个技能等属性，重写了`java.lang.Cloneable#clone()`方法，代码如下：

```java
package com.sunchaser.javase.base.cloneable;

/**
 * 英雄：孙悟空
 * @author sunchaser
 * @since JDK8 2020/4/7
 * 
 */
public class SunWuKong implements LolHero,Cloneable {

    /**
     * 名字
     */
    private String name = "齐天大圣-孙悟空";

    /**
     * 生命值
     */
    private LifeValue sunWuKongLifeValue;

    /**
     * 法力值
     */
    private Mana sunWuKongMana;

    /* skill */
    private LolSkill Q = new LolSkill("Q","粉碎打击","孙悟空的下次攻击造成额外物理伤害，获得距离加成，并暂时减少敌人的护甲。");
    private LolSkill W = new LolSkill("W","真假猴王","孙悟空进入隐形状态片刻，并留下一个替身，替身在片刻后会对其周围的敌人造成魔法伤害。");
    private LolSkill E = new LolSkill("E","腾云突击","孙悟空突进至目标敌人处，并变出最多2个分身，攻击附近目标。对每个击中的目标造成物理伤害。");
    private LolSkill R = new LolSkill("R","大闹天宫","孙悟空展开金箍棒，不停地旋转，对敌人造成伤害并将其击飞。在此期间，孙悟空速度持续增加。");

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public void attack(LolSkill lolSkill) {
        System.out.println("使用了" + lolSkill + "进行了攻击");
    }

    @Override
    public void beAttacked() {
        Integer oldSunWuKongLifeValue = this.sunWuKongLifeValue.getLifeValue() - 1;
        this.sunWuKongLifeValue.setLifeValue(oldSunWuKongLifeValue);
    }

    public String getName() {
        return name;
    }

    public LifeValue getSunWuKongLifeValue() {
        return sunWuKongLifeValue;
    }

    public SunWuKong setSunWuKongLifeValue(LifeValue sunWuKongLifeValue) {
        this.sunWuKongLifeValue = sunWuKongLifeValue;
        return this;
    }

    public Mana getSunWuKongMana() {
        return sunWuKongMana;
    }

    public SunWuKong setSunWuKongMana(Mana sunWuKongMana) {
        this.sunWuKongMana = sunWuKongMana;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SunWuKong{");
        sb.append("name='").append(name).append('\'');
        sb.append(", sunWuKongLifeValue=").append(sunWuKongLifeValue);
        sb.append(", sunWuKongMana=").append(sunWuKongMana);
        sb.append('}');
        return sb.toString();
    }
}
```

一切准备就绪，我们来测试一下：

```java
package com.sunchaser.javase.base.cloneable;

/**
 * 深浅拷贝测试
 * @author sunchaser
 * @since JDK8 2020/4/7
 * 
 */
public class DepthCopyTest {

    public static void main(String[] args) throws CloneNotSupportedException {
        // 创建一个孙悟空英雄对象，并赋予生命值1000，法力值2000
        SunWuKong sunWuKong = new SunWuKong();
        sunWuKong.setSunWuKongLifeValue(new LifeValue(1000))
                .setSunWuKongMana(new Mana(2000));
        // 克隆一个替身
        SunWuKong clone = (SunWuKong) sunWuKong.clone();
        System.out.println(sunWuKong);
        System.out.println(clone);
        System.out.println(sunWuKong == clone);
        // 替身受到攻击
        clone.beAttacked();
        System.out.println(sunWuKong);
        System.out.println(clone);
        System.out.println(sunWuKong == clone);
    }
}
```

观察控制台输出：

```
SunWuKong{name='齐天大圣-孙悟空', sunWuKongLifeValue=LifeValue{lifeValue=1000}, sunWuKongMana=Mana{mana=2000}}
SunWuKong{name='齐天大圣-孙悟空', sunWuKongLifeValue=LifeValue{lifeValue=1000}, sunWuKongMana=Mana{mana=2000}}
false
SunWuKong{name='齐天大圣-孙悟空', sunWuKongLifeValue=LifeValue{lifeValue=999}, sunWuKongMana=Mana{mana=2000}}
SunWuKong{name='齐天大圣-孙悟空', sunWuKongLifeValue=LifeValue{lifeValue=999}, sunWuKongMana=Mana{mana=2000}}
false
```

孙悟空“替身”受到攻击后，“真身”的生命值也减少了。可以看出真身和替身引用的是同一个生命值对象。足以证实`java.lang.Cloneable#clone()`方法是浅拷贝。

## 深拷贝的实现
深拷贝则不能简单的在重写的`clone`方法内直接调用`super.clone()`，需要将当前类的每一个引用对象都进行拷贝，如果引用对象还包含引用对象，则需进行多层拷贝。

当前类的每一个引用对象均需实现`java.lang.Cloneable`接口并重写其`clone`方法。

我们来看下`SunWuKong`类的引用对象：`LifeValue`和`Mana`。

`LifeValue`代码实现为：
```java
package com.sunchaser.javase.base.cloneable;

/**
 * 英雄生命值类
 * @author sunchaser
 * @since JDK8 2020/4/7
 * 
 */
public class LifeValue implements Cloneable {
    private Integer lifeValue;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    
    ......
}
```

`Mana`代码实现为：
```java
package com.sunchaser.javase.base.cloneable;

/**
 * 英雄法力值类
 * @author sunchaser
 * @since JDK8 2020/4/7
 * 
 */
public class Mana implements Cloneable {
    private Integer mana;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    
    ......
}
```

此时`SunWuKong`类的`clone`方法实现如下：

```java
package com.sunchaser.javase.base.cloneable;

/**
 * 英雄：孙悟空
 * @author sunchaser
 * @since JDK8 2020/4/7
 * 
 */
public class SunWuKong implements LolHero,Cloneable {

    /**
     * 名字
     */
    private String name = "齐天大圣-孙悟空";

    /**
     * 生命值
     */
    private LifeValue sunWuKongLifeValue;

    /**
     * 法力值
     */
    private Mana sunWuKongMana;
    
    ......

    @Override
    public Object clone() throws CloneNotSupportedException {
        SunWuKong clone = (SunWuKong) super.clone();
        LifeValue lifeValueClone = (LifeValue) this.sunWuKongLifeValue.clone();
        Mana manaClone = (Mana) this.sunWuKongMana.clone();
        clone.setSunWuKongLifeValue(lifeValueClone);
        clone.setSunWuKongMana(manaClone);
        return clone;
    }
    
    ......
}
```

我们再来看下输出结果：
```
SunWuKong{name='齐天大圣-孙悟空', sunWuKongLifeValue=LifeValue{lifeValue=1000}, sunWuKongMana=Mana{mana=2000}}
SunWuKong{name='齐天大圣-孙悟空', sunWuKongLifeValue=LifeValue{lifeValue=1000}, sunWuKongMana=Mana{mana=2000}}
false
SunWuKong{name='齐天大圣-孙悟空', sunWuKongLifeValue=LifeValue{lifeValue=1000}, sunWuKongMana=Mana{mana=2000}}
SunWuKong{name='齐天大圣-孙悟空', sunWuKongLifeValue=LifeValue{lifeValue=999}, sunWuKongMana=Mana{mana=2000}}
false
```

替身对象受到攻击后，原对象生命值不变，替身对象生命值减少。这就是深拷贝的实现。

## 总结
深拷贝要求被拷贝的类的每一个引用对象都实现`java.lang.Cloneable`接口并实现`clone`方法，所以当需要实现深拷贝时，需要进行对象功能的全面考虑，特别是当引用对象还包含引用对象的多层嵌套时，需要结合对象的功能进行考虑。

本篇文章所有源代码地址：[传送门](https://github.com/sunchaser-lilu/gold-road-to-Java/tree/master/java-se/src/main/java/com/sunchaser/javase/base/cloneable)