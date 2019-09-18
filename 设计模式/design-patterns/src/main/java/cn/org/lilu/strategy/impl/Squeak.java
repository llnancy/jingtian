package cn.org.lilu.strategy.impl;

import cn.org.lilu.strategy.QuackBehavior;

/**
 * @Auther: Java成魔之路
 * @Date: 2019/9/12
 * @Description: 吱吱叫的行为
 */
public class Squeak implements QuackBehavior {
    @Override
    public void quack() {
        System.out.println("Squeak 吱吱叫");
    }
}
