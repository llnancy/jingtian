package com.sunchaser.sparrow.javase.io;

import java.io.FileReader;

/**
 * FileReader Test
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/5/20
 */
public class FileReaderTest {

    public static void main(String[] args) throws Exception {
        // 获取当前类同级目录下的io.txt文件路径
        String path = FileReaderTest.class.getResource("").getPath();
        path = path.replace("target/classes","src/main/java") + "fr.txt";

        FileReader fr = new FileReader(path);
        int c;
        while ((c = fr.read()) != -1) {
            System.out.print((char) c);
        }
        fr.close();
    }
}
