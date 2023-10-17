package io.github.llnancy.jingtian.javase.java13;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

/**
 * switch expressions
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK13 2023/7/18
 */
public class Switch {

    public static void main(String[] args) {
        DayOfWeek day = LocalDateTime.now().getDayOfWeek();

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
