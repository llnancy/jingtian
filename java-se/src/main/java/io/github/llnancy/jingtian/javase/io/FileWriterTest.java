package io.github.llnancy.jingtian.javase.io;

import java.io.FileWriter;

/**
 * FileWriter Test
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/5/20
 */
public class FileWriterTest {

    public static void main(String[] args) throws Exception {
        String path = FileWriterTest.class.getResource("").getPath();
        path = path.replace("target/classes", "src/main/java") + "fw.txt";

        FileWriter fw = new FileWriter(path);
        fw.write("IO流-FileWriter");
        fw.close();
    }
}
