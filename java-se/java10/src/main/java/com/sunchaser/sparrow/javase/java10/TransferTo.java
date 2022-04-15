package com.sunchaser.sparrow.javase.java10;

import java.io.*;
import java.util.Objects;

/**
 * Reader类和InputStream类中新增transferTo方法
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
            // 复制source.txt文件到target2.txt
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
            // 复制source.txt文件到target1.txt
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
