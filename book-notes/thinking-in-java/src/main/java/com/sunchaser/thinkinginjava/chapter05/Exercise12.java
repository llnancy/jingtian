package com.sunchaser.thinkinginjava.chapter05;

import static com.sunchaser.thinkinginjava.chapter05.TankStatusEnum.*;

/**
 * @author sunchaser
 * @date 2020/1/13
 * @description
 * 练习12：编写名为Tank的类，此类的状态可以是“满的”或“空的”。其终结条件是：对象被清理时必须处于空状态。
 * 请编写finalize()以检验终结条件是否成立。在main()方法中测试Tank可能发生的几种使用方式。
 * @since 1.0
 */
public class Exercise12 {
    public static void main(String[] args) {
        Tank tank = new Tank(FULL.getStatus());
        tank.free();
        System.gc();

        new Tank(FULL.getStatus()).free();
        System.gc();

        new Tank(FULL.getStatus());
        System.gc();

        new Tank(FULL.getStatus());
        System.gc();
    }
}

/**
 * Tank类，包含一个状态字段，可以是“满的”和“空的”状态。
 */
class Tank {
    private String status;

    public Tank(String status) {
        this.status = status;
    }

    void free() {
        this.status = EMPTY.getStatus();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        if (FULL.getStatus().equals(status)) {
            System.out.println("Error: Full");
        } else if (EMPTY.getStatus().equals(status)){
            System.out.println("Success: empty");
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Tank{");
        sb.append("status='").append(status).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

/**
 * Tank状态枚举
 */
enum TankStatusEnum {
    FULL("满的"),
    EMPTY("空的"),
    ;
    private String status;

    TankStatusEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TankStatusEnum{");
        sb.append("status='").append(status).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
