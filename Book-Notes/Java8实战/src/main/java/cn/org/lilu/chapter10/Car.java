package cn.org.lilu.chapter10;

import java.util.Optional;

/**
 * @Auther: lilu
 * @Date: 2019/8/25
 * @Description: 车 类
 */
public class Car {
    private Optional<Insurance> insurance;

    public Optional<Insurance> getInsurance() {
        return insurance;
    }
}
