package com.sunchaser.sparrow.thinkinginjava.chapter05;

/**
 * 练习21：创建一个enum，它包含纸币中最小面值的6种类型，通过values()循环并打印每一个值及其ordinal()。
 * 练习22：在前面的例子中，为enum写一个switch语句，对于每一个case，输出该特定货币的描述。
 * @author sunchaser
 * @since JDK8 2020/2/2
 */
public class Exercise21_22 {
    public static void main(String[] args) {
        for (Money money : Money.values()) {
            System.out.println("money=" + money + ",ordinal=" + money.ordinal());
            describe(money);
        }
    }

    public static void describe(Money money) {
        switch (money) {
            case ONE:
                System.out.println("一元");
                break;
            case FIVE:
                System.out.println("五元");
                break;
            case TEN:
                System.out.println("十元");
                break;
            case TWENTY:
                System.out.println("二十元");
                break;
            case FIFTY:
                System.out.println("五十元");
                break;
            case HUNDRED:
                System.out.println("一百元");
                break;
            default:
                System.out.println("身无分文");
                break;
        }
    }

    /**
     * 一元，
     * 五元，
     * 十元，
     * 二十元，
     * 五十元，
     * 一百元
     */
    private enum Money {
        ONE,
        FIVE,
        TEN,
        TWENTY,
        FIFTY,
        HUNDRED,
        ;
    }
}
