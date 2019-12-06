package com.sunchaser.javase.test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author sunchaser
 * @date 2019/12/5
 * @description
 * @since 1.0
 */
public class Test {
    public static void main(String[] args) {
        List<Boolean> booleans = Arrays.asList(true, true, false, true, false);
        long count = booleans.stream()
                .filter(Predicate.isEqual(true))
                .count();
        System.out.println(count);
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        LocalDate of = LocalDate.of(2019, 12, 6);
        LocalDate now = LocalDate.now();
        System.out.println(now.toEpochDay() - of.toEpochDay());
    }
}
