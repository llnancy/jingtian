package io.github.llnancy.jingtian.javase.java10;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

/**
 * Reader 类和 InputStream 类中新增 transferTo 方法
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK10 2022/2/15
 */
public class TransferTo {

    public static void main(String[] args) {
        copyFileBeforeJava10();

        copyFileInJava10();
    }

    private static final String FILE = Objects.requireNonNull(TransferTo.class.getResource("/")).getFile();

    private static void copyFileInJava10() {
        try {
            // 复制 source.txt 文件到 target2.txt
            FileReader fr = new FileReader(FILE + "source.txt");
            FileWriter fw = new FileWriter(FILE + "target2.txt");

            fr.transferTo(fw);

            fr.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void copyFileBeforeJava10() {
        try {
            // 复制 source.txt 文件到 target1.txt
            FileReader fr = new FileReader(FILE + "source.txt");
            FileWriter fw = new FileWriter(FILE + "target1.txt");
            char[] chs = new char[1024 * 8];
            int len;
            while ((len = fr.read(chs)) != -1) {
                fw.write(chs, 0, len);
            }
            fr.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
