package cn.org.lilu.chapter12;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Auther: lilu
 * @Date: 2019/8/29
 * @Description: 传统时间格式转换器线程安全问题解决方案：使用ThreadLocal进行线程封闭
 */
public class TraditionalSimpleDateFormatThreadLocal {
    private static final ThreadLocal<DateFormat> threadLocal = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    public static Date convert(String source) throws Exception {
        return threadLocal.get().parse(source);
    }
}
