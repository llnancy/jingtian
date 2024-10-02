package io.github.llnancy.jingtian.designpattern.gof.strategy.impl;

import io.github.llnancy.jingtian.designpattern.gof.strategy.QuackBehavior;

/**
 * 不会叫的行为
 * @author sunchaser
 * @since JDK8 2019/9/12
 */
public class MuteQuack implements QuackBehavior {
    @Override
    public void quack() {
        System.out.println("Silence 不会叫");
    }
}
