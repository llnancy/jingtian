调度中心启动时主要执行了`XxlJobScheduler.init()`方法，该方法中有六个初始化动作，这篇文章来分析第一个初始化动作：初始化国际化组件。其关键调用代码为：`initI18n();`

## 国际化简介
国际化又称为`i18n`，来源于国际化的英文单词`internationalization`从`i`到`n`之间有`18`个英文字母。

## 源码分析
我们来看一下`initI18n()`方法的代码：
```
    private void initI18n(){
        for (ExecutorBlockStrategyEnum item:ExecutorBlockStrategyEnum.values()) {
            item.setTitle(I18nUtil.getString("jobconf_block_".concat(item.name())));
        }
    }
```

循环迭代枚举`com.xxl.job.core.enums.ExecutorBlockStrategyEnum`，该枚举是执行器阻塞策略枚举。只有一个`title`属性，提供了三个枚举对象：

```
public enum ExecutorBlockStrategyEnum {

    SERIAL_EXECUTION("Serial execution"),
    /*CONCURRENT_EXECUTION("并行"),*/
    DISCARD_LATER("Discard Later"),
    COVER_EARLY("Cover Early");

    private String title;
......
......
```

循环中调用变异器`setTitle()`方法给每个枚举对象的`title`属性重新赋值。

工具类`I18nUtil`的`getString()`方法获取到以`jobconf_block_`开头，以枚举对象名结尾的属性值。

我们来看一下工具类`getString()`方法的实现：
```
    public static String getString(String key) {
        return loadI18nProp().getProperty(key);
    }
```

调用了静态成员方法`loadI18nProp()`得到一个`Properties`对象，再调用其`getProperty()`方法从资源文件中获取对应`key`的值。

来看一下`loadI18nProp()`方法的实现：

![I18nUtil.loadI18nProp](https://cdn.jsdelivr.net/gh/sunchaser-lilu/sunchaser-cdn@master/images/xxl-job/I18nUtil.loadI18nProp.png)

- 读取`application.properties`配置文件的`xxl.job.i18n`配置项的值;
- `build`组装，得到`i18n`文件路径。即`resources\i18n`目录下的`Resource Bundle 'message'`资源包文件;
- 使用`Spring`提供的`PropertiesLoaderUtils`工具类加载对应资源文件。

我们可以从这里知道调度中心`application.properties`的配置项`xxl.job.i18n`有两种配置，一是缺省不填，为中文；二是设置为`en`，即英文。

去`resources\i18n`目录看下资源包文件：`message.properties`和`message_en.properties`。搜索`jobconf_block_`，得到以下内容：

`message.properties`：

```
## job conf
jobconf_block_SERIAL_EXECUTION=单机串行
jobconf_block_DISCARD_LATER=丢弃后续调度
jobconf_block_COVER_EARLY=覆盖之前调度
```

`message_en.properties`：

```
## job conf
jobconf_block_SERIAL_EXECUTION=Serial execution
jobconf_block_DISCARD_LATER=Discard Later
jobconf_block_COVER_EARLY=Cover Early
```

可以发现键中`jobconf_block_`后接的内容即为`ExecutorBlockStrategyEnum`枚举的`name`值。一共有三个阻塞策略：单机串行、丢弃后续调度和覆盖之前调度。

`initI18n()`方法的迭代完成后，枚举的`title`属性值就变成了对应的国际化设置。

## 总结
这篇文章主要是分析了调度中心国际化组件的初始化原理。接下来我们将继续分析调度中心启动的其它初始化动作。