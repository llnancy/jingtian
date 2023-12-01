package io.github.llnancy.jingtian.javase.java9;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * try-with-resources
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK9 2022/2/11
 */
public class TryWithResources {

    /**
     * Java7 之前
     */
    private static void writeObjectBeforeJava7(Object obj, String path) throws IOException {
        FileOutputStream fos = null;
        ObjectOutputStream ops = null;
        try {
            fos = new FileOutputStream(path);
            ops = new ObjectOutputStream(fos);
            ops.writeObject(obj);
        } finally {
            try {
                if (ops != null) {
                    ops.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Java7 & 8
     */
    private static void writeObjectInJava7(Object obj, String path) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(path);
             ObjectOutputStream ops = new ObjectOutputStream(fos)) {
            ops.writeObject(obj);
        }
    }

    /**
     * Java9
     */
    private static void writeObjectInJava9(Object obj, String path) throws IOException {
        FileOutputStream fos = new FileOutputStream(path);
        ObjectOutputStream ops = new ObjectOutputStream(fos);
        try (fos; ops) {
            // fos 和 ops 都被声明为了 final，无法再被赋值。
            // fos = new FileOutputStream(path);
            ops.writeObject(obj);
        }
    }
}
