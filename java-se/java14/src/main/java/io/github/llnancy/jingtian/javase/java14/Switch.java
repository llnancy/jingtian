package io.github.llnancy.jingtian.javase.java14;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

/**
 * switch expressions
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK14 2023/7/19
 */
public class Switch {

    public static void main(String[] args) {
        DayOfWeek day = LocalDateTime.now().getDayOfWeek();

        switch (day) {
            case MONDAY, FRIDAY, SUNDAY -> System.out.println(6);
            case TUESDAY -> System.out.println(7);
            case THURSDAY, SATURDAY -> System.out.println(8);
            case WEDNESDAY -> System.out.println(9);
        }

        int numLetter = switch (day) {
            case MONDAY, FRIDAY, SUNDAY -> 6;
            case TUESDAY -> 7;
            case THURSDAY, SATURDAY -> 8;
            case WEDNESDAY -> 9;
            default -> throw new IllegalStateException("Wat: " + day);
        };

        int res = switch (day) {
            case MONDAY:
                yield 1;
            case TUESDAY:
                yield 2;
            default:
                yield day.getValue();
        };
    }
}
