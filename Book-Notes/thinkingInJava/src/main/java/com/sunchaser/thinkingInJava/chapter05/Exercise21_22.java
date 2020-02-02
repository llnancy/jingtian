package com.sunchaser.thinkingInJava.chapter05;

/**
 * @author sunchaser
 * @date 2020/2/2
 * @description
 * @since 1.0
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
