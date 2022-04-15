package com.sunchaser.sparrow.javase.java11;

import java.util.ArrayList;
import java.util.List;

/**
 * 添加VM参数：-XX:+UnlockExperimentalVMOptions -XX:+UseEpsilonGC
 *
 * 解锁实验性选项，使用Epsilon收集器
 *
 * 用途：
 * - 性能测试（过滤掉GC引起的性能假象）
 * - 内存压力测试
 * - 非常短的任务
 * - VM接口测试
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK11 2022/2/18
 */
public class EpsilonGC {
    public static void main(String[] args) {
        List<Garbage> list = new ArrayList<>();
        int count = 0;
        while (true) {
            list.add(new Garbage());
            if (count++ == 500) {
                list.clear();
            }
        }
    }
}

class Garbage {
    private double d1 = 1D;
    private double d2 = 1D;

    @Override
    protected void finalize() throws Throwable {
        System.out.println(this + " collecting");
    }
}
