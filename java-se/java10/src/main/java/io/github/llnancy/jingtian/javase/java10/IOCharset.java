package io.github.llnancy.jingtian.javase.java10;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * IO 流类系添加 Charset 参数
 * <p>
 * PrintStream、PrintWriter、Scanner 新增带 Charset 类参数的构造器
 * <p>
 * ByteArrayOutPutStream 新增重载 toString(Charset) 方法，指定编码方式将缓冲区内容转化为字符串
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK10 2022/2/15
 */
public class IOCharset {

    public static void main(String[] args) {
        printStream();

        byteArrayOutputStream();
    }

    private static void byteArrayOutputStream() {
        try {
            String str = "ByteArrayOutPutStream的toString()方法";
            Charset gbk = Charset.forName("GBK");
            // getBytes 默认使用 UTF-8 编码将字符串转字节数组
            byte[] bytes = str.getBytes(gbk);
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            int b;
            while ((b = bais.read()) != -1) {
                baos.write(b);
            }

            System.out.println(baos.toString());
            System.out.println(baos.toString(gbk));

            baos.close();
            bais.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printStream() {
        try {
            PrintStream ps = new PrintStream("/Users/llnancy/workspace/llnancy-projects/jingtian/java-se/java10/src/main/resources/ps.txt", StandardCharsets.UTF_8);
            ps.println("io charset 参数");
            ps.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
