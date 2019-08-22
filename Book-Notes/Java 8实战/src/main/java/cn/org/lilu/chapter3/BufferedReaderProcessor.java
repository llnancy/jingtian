package cn.org.lilu.chapter3;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * @Auther: lilu
 * @Date: 2019/8/12
 * @Description: 函数式接口
 */
@FunctionalInterface
public interface BufferedReaderProcessor {
    String process(BufferedReader b) throws IOException;
}
