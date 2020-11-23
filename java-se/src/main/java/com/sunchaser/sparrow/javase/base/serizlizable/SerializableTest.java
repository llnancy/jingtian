package com.sunchaser.sparrow.javase.base.serizlizable;

import java.io.*;

/**
 * 序列化测试
 *
 * @author sunchaser
 * @since JDK8 2020/3/19
 */
public class SerializableTest {
    public static void main(String[] args) {
        // 获取当前类所在包中的serializableClass.txt文件路径
        String path = SerializableTest.class.getResource("").getPath();
        path = path.replace("target/classes","src/main/java") + "serializableClass.txt";
        SerializableClass sc = new SerializableClass().setName("序列化").setAge(10);
//        writeObject(sc,path);
        readObject(path);
    }

    private static void writeObject(SerializableClass sc,String path) {
        FileOutputStream fos = null;
        ObjectOutputStream ops = null;
        try {
            fos = new FileOutputStream(path);
            ops = new ObjectOutputStream(fos);
            ops.writeObject(sc);
        } catch (Exception e) {
            e.printStackTrace();
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

    private static void readObject(String path) {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(path);
            ois = new ObjectInputStream(fis);
            SerializableClass sc = (SerializableClass) ois.readObject();
            System.out.println(sc);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
