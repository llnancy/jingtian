package com.sunchaser.sparrow.springboot.frame;

/**
 * 空执行的函数式接口
 *
 * coat：外衣，图层，外套的意思。。。
 *
 * 不需要入参，不需要出参
 *
 * 入参用final变量传入lambda表达式
 * 出参用引用设置到外部
 *
 * @author sunchaserlilu@didiglobal.com
 * @since JDK8 2021/1/30
 */
@FunctionalInterface
public interface CoatInvoker {
    void invoke();
}
