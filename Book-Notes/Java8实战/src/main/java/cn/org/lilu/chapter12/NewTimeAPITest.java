package cn.org.lilu.chapter12;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.*;
import java.time.temporal.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: lilu
 * @Date: 2019/8/26
 * @Description: 新的时间API
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class NewTimeAPITest {

    /**
     * LocalDate：年月日
     */
    @Test
    public void testLocalDate() {
        // 静态工厂方法of创建一个LocalDate实例
        LocalDate date = LocalDate.of(2019,8,26);
        // 年份
        int year = date.getYear();
        // 月份
        Month month = date.getMonth();
        // 这个月第几天
        int dayOfMonth = date.getDayOfMonth();
        // 这周星期几
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        // 今年第几天
        int dayOfYear = date.getDayOfYear();
        // 这个月的长度（有几天）
        int lengthOfMonth = date.lengthOfMonth();
        // 是否闰年
        boolean leapYear = date.isLeapYear();
        System.out.println(year); // 2019
        System.out.println(month); // AUGUST
        System.out.println(dayOfMonth); // 26
        System.out.println(dayOfWeek); // MONDAY
        System.out.println(dayOfYear); // 238
        System.out.println(lengthOfMonth); // 31
        System.out.println(leapYear); // false
    }

    /**
     * 通过传递一个TemporalField参数给get方法可以拿到同样的信息
     * TemporalField是一个接口，它定义了如何访问temporal对象某个字段的值。
     * ChronoField枚举实现了这个接口。
     */
    @Test
    public void testChronoField() {
        LocalDate today = LocalDate.now();
        // 年份
        int year = today.get(ChronoField.YEAR);
        // 月份
        int month = today.get(ChronoField.MONTH_OF_YEAR);
        // 这个月的第几天
        int dayOfMonth = today.get(ChronoField.DAY_OF_MONTH);
        // 这周的星期几
        int dayOfWeek = today.get(ChronoField.DAY_OF_WEEK);
        // 今年的第几天
        int dayOfYear = today.get(ChronoField.DAY_OF_YEAR);
        System.out.println(year); // 2019
        System.out.println(month); // 8
        System.out.println(dayOfMonth); // 26
        System.out.println(dayOfWeek); // 1
        System.out.println(dayOfYear); // 238
    }

    /**
     * LocalTime：时分秒
     * 一天中的时间，比如：13:45:20，可以使用LocalTime类表示
     * 可以使用of重载的两个工厂方法创建LocalTime实例
     * 第一个重载方法接收小时和分钟
     * 第二个重载方法同时还接收秒
     */
    @Test
    public void testLocalTime() {
        LocalTime localTime = LocalTime.of(13, 45, 20);
        int hour = localTime.getHour();
        int minute = localTime.getMinute();
        int second = localTime.getSecond();
        System.out.println(hour);
        System.out.println(minute);
        System.out.println(second);
    }

    /**
     * LocalDate和LocalTime都可以通过解析代表它们的字符串创建。使用静态方法parse。
     * ，一旦传递的字符串参数无法被解析为合法的LocalDate或LocalTime对象，
     * 这两个parse方法都会抛出一个继承自RuntimeException的DateTimeParseException异常。
     */
    @Test
    public void testParse() {
        // 月份小于10必须在前面补0，否则抛出异常
        LocalDate localDate = LocalDate.parse("2019-08-26");
        LocalTime localTime = LocalTime.parse("13:45:20");
        System.out.println(localDate);
        System.out.println(localTime);
    }

    /**
     * LocalDateTime：年月日时分秒
     */
    @Test
    public void testLocalDateTime() {
        LocalDateTime localDateTime1 = LocalDateTime.of(2019, Month.AUGUST, 26, 10, 47, 20);
        LocalDate localDate = LocalDate.parse("2019-08-26");
        LocalTime localTime = LocalTime.parse("13:45:20");
        // 由LocalDate和LocalTime组合出LocalDateTime
        LocalDateTime localDateTime2 = LocalDateTime.of(localDate,localTime);
        LocalDateTime localDateTime3 = localDate.atTime(10,51,32);
        LocalDateTime localDateTime4 = localDate.atTime(localTime);
        LocalDateTime localDateTime5 = localTime.atDate(localDate);
        // 由LocalDateTime提取出LocalDate和LocalTime
        LocalDate localDateFromLocalDateTime = localDateTime2.toLocalDate();
        LocalTime localTimeFromLocalDateTime = localDateTime2.toLocalTime();

        System.out.println(localDateTime1);
        System.out.println(localDateTime2);
        System.out.println(localDateTime3);
        System.out.println(localDateTime4);
        System.out.println(localDateTime5);
        System.out.println(localDateFromLocalDateTime);
        System.out.println(localTimeFromLocalDateTime);
    }

    /**
     * Instant：从UNIX元年时间开始到现在所经过的秒数对时间进行建模。包含的是由秒及纳秒组成的数字。
     *
     * 静态工厂方法：ofEpochSecond包含两个重载版本
     * // 传入一个代表秒数的值创建一个Instant实例
     * Instant ofEpochSecond(long epochSecond)
     * // 第一个参数：代表秒数的值，第二个参数：纳秒数，对第一个参数传入的秒数进行调整，确保保存的纳秒分片在0到999 999 999之间。
     * Instant ofEpochSecond(long epochSecond, long nanoAdjustment)
     *
     * 静态工厂方法：now
     */
    @Test
    public void testInstant() {
        Instant instant1 = Instant.ofEpochSecond(3);
        Instant instant2 = Instant.ofEpochSecond(3, 0);
        Instant instant3 = Instant.ofEpochSecond(2, 1_000_000_000);
        Instant instant4 = Instant.ofEpochSecond(4, -1_000_000_000);
        System.out.println(instant1);
        System.out.println(instant2);
        System.out.println(instant3);
        System.out.println(instant4);

        Instant now = Instant.now();
        // java.time.temporal.UnsupportedTemporalTypeException: Unsupported field: DayOfMonth
        int day = now.get(ChronoField.DAY_OF_MONTH);
        System.out.println(day);
    }

    /**
     * 机器的日期和时间格式
     * @throws InterruptedException
     */
    @Test
    public void testDuration() throws InterruptedException {
        LocalTime localTime1 = LocalTime.parse("13:45:20");
        LocalTime localTime2 = LocalTime.parse("13:45:30");
        LocalDateTime localDateTime1 = LocalDateTime.now();
        Thread.sleep(100);
        LocalDateTime localDateTime2 = LocalDateTime.now();
        Instant instant1 = Instant.ofEpochSecond(3);
        Instant instant2 = Instant.ofEpochSecond(6);
        System.out.println(Duration.between(localTime1,localTime2)); // PT10S
        System.out.println(Duration.between(localDateTime1,localDateTime2)); // PT0.1S
        System.out.println(Duration.between(instant1,instant2)); // PT3S

        // 计算两个LocalDate之间的时长
        Period periodBetween = Period.between(LocalDate.of(2019, 8, 26), LocalDate.of(2019, 8, 27));
        System.out.println(periodBetween); // P1D

        // Duration和Period的静态工厂方法直接创建实例
        Duration durationOfMinutes = Duration.ofMinutes(3);
        Duration durationOf = Duration.of(3, ChronoUnit.MINUTES);
        Period periodOfDays = Period.ofDays(10);
        Period periodOfWeeks = Period.ofWeeks(3);
        Period periodOf = Period.of(2, 6, 1);
        System.out.println(durationOfMinutes); // PT3M
        System.out.println(durationOf); // PT3M
        System.out.println(periodOfDays); // P10D
        System.out.println(periodOfWeeks); // P21D
        System.out.println(periodOf); // P2Y6M1D
    }

    /**
     * 操纵、解析和格式化日期
     *
     * LocalDate类为final类，不可变，每次操作后都返回一个新的LocalDate对象
     */
    @Test
    public void testUpdateTime() {
        LocalDate date1 = LocalDate.of(2019, 8, 26);
        LocalDate date2 = date1.withYear(2020);
        LocalDate date3 = date2.withDayOfMonth(25);
        LocalDate date4 = date3.with(ChronoField.MONTH_OF_YEAR, 9);
        System.out.println(date1); // 2019-08-26
        System.out.println(date2); // 2020-08-26
        System.out.println(date3); // 2020-08-25
        System.out.println(date4); // 2020-09-25

        LocalDate date5 = LocalDate.of(2019,8,26);
        LocalDate date6 = date5.plusWeeks(1); // 加一周
        LocalDate date7 = date6.minusYears(3); // 减三年
        LocalDate date8 = date7.plus(6, ChronoUnit.MONTHS); // 加六月
        System.out.println(date5); // 2019-08-26
        System.out.println(date6); // 2019-09-02
        System.out.println(date7); // 2016-09-02
        System.out.println(date8); // 2017-03-02
    }

    /**
     * 日期调整器
     */
    @Test
    public void testTemporalAdjuster() {
        LocalDate date1 = LocalDate.of(2019,8,26);
        // dayOfWeekInMonth(int ordinal, DayOfWeek dayOfWeek)
        // dayOfWeek表示星期几
        // 如果ordinal为0，则表示本日期所在的月的上一个月的最后一个星期几
        // 如果ordinal为正数，则以本日期所在的月从前向后数，第ordinal个星期几
        // 如果ordinal为负数，则以本日期所在的月从后往前数，第-ordinal个星期几
        LocalDate date2 = date1.with(TemporalAdjusters.dayOfWeekInMonth(1, DayOfWeek.FRIDAY));
        System.out.println("date2=" + date2);

        // TemporalAdjuster firstDayOfMonth()：创建一个新的日期，它的值为当月的第一天
        LocalDate date3 = date1.with(TemporalAdjusters.firstDayOfMonth());
        System.out.println("date3=" + date3);

        // TemporalAdjuster firstDayOfNextMonth()：创建一个新的日期，它的值为下月的第一天
        LocalDate date4 = date1.with(TemporalAdjusters.firstDayOfNextMonth());
        System.out.println("date4=" + date4);

        // TemporalAdjuster firstDayOfNextYear()：创建一个新的日期，它的值为明年的第一天
        LocalDate date5 = date1.with(TemporalAdjusters.firstDayOfNextYear());
        System.out.println("date5=" + date5);

        // TemporalAdjuster firstDayOfYear()：创建一个新的日期，它的值为今年的第一天
        LocalDate date6 = date1.with(TemporalAdjusters.firstDayOfYear());
        System.out.println("date6=" + date6);

        // TemporalAdjuster firstInMonth(DayOfWeek dayOfWeek)：创建一个新的日期，它的值为同一个月中，第一个符合星期几要求的日期（这个月的第一个星期几）
        LocalDate date7 = date1.with(TemporalAdjusters.firstInMonth(DayOfWeek.FRIDAY));
        System.out.println("date7=" + date7);

        // TemporalAdjuster lastDayOfMonth()：创建一个新的日期，它的值为这个月的最后一天
        LocalDate date8 = date1.with(TemporalAdjusters.lastDayOfMonth());
        System.out.println("date8=" + date8);

        // TemporalAdjuster lastDayOfYear()：创建一个新的日期，它的值为今年的最后一天
        LocalDate date9 = date1.with(TemporalAdjusters.lastDayOfYear());
        System.out.println("date9=" + date9);

        // TemporalAdjuster lastInMonth(DayOfWeek dayOfWeek)：创建一个新的日期，它的值为同一个月中，最后一个符合星期几要求的日期
        LocalDate date10 = date1.with(TemporalAdjusters.lastInMonth(DayOfWeek.FRIDAY));
        System.out.println("date10=" + date10);

        // TemporalAdjuster next(DayOfWeek dayOfWeek)：创建一个新的日期，并将其值设定为指定日期之后第一个符合指定星期几的日期
        LocalDate date11 = date1.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        System.out.println("date11=" + date11);

        // TemporalAdjuster nextOrSame(DayOfWeek dayOfWeek)：
        // 创建一个新的日期，并将其值设定为指定日期之后第一个符合指定星期几的日期；
        // 如果指定日期已符合要求，则直接返回该日期
        LocalDate date12 = date1.with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY));
        System.out.println("date12=" + date12);

        // TemporalAdjuster previous(DayOfWeek dayOfWeek)：创建一个新的日期，并将其值设定为指定日期之前第一个符合指定星期几的日期
        LocalDate date13= date1.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
        System.out.println("date13=" + date13);

        // TemporalAdjuster previousOrSame(DayOfWeek dayOfWeek)：
        // 创建一个新的日期，并将其值设定为指定日期之前第一个符合指定星期几的日期；
        // 如果指定日期已符合要求，则直接返回该日期
        LocalDate date14 = date1.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        System.out.println("date14=" + date14);

        // TemporalAdjuster ofDateAdjuster(UnaryOperator<LocalDate> dateBasedAdjuster)

    }

    /**
     * 定制TemporalAdjuster
     *
     * 计算下一个工作日
     */
    class NextWorkingDay implements TemporalAdjuster {

        /**
         * 周一到周五为工作日
         * 如果是周日到周四，则返回下一天
         * 如果是周五、周六、返回下周周一
         * @param temporal
         * @return
         */
        @Override
        public Temporal adjustInto(Temporal temporal) {
            DayOfWeek dayOfWeek = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));
            int dayToAdd = 1;
            if (dayOfWeek == DayOfWeek.FRIDAY) dayToAdd = 3;
            else if (dayOfWeek == DayOfWeek.SATURDAY) dayToAdd = 2;
            return temporal.plus(dayToAdd,ChronoUnit.DAYS);
        }
    }
}
