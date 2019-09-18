package cn.org.lilu.chapter3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @Auther: Java成魔之路
 * @Date: 2019/8/12
 * @Description:
 */
public class LambdaTest {

    /**
     * 定义一个行为
     * @param p
     * @return
     * @throws IOException
     */
    public static String processFile(BufferedReaderProcessor p) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("data.txt"))) {
            return p.process(br);
        }
    }

    /**
     * 局部变量则保存在栈上。
     * 如果Lambda可以直接访问局部变量，而且Lambda是在一个线程中使用的，
     * 则使用Lambda的线程，可能会在分配该变量的线程将这个变量收回之后，去访问该变量。
     * 因此，Java在访问自由局部变量时，实际上是在访问它的副本，而不是访问原始变量。
     */
    public static void lambdaLocalVariable() {
        // 局部变量i保存在栈中
        int i = 1;
        new Thread(() -> {
            // 访问的是局部变量i的副本
            // 此处执行的是run方法，栈中又会生成一个栈帧
            Runnable r = () -> System.out.println(i);
        });
    }

    public static void main(String[] args) throws Exception {
        // 不同方式处理文件
        String oneLine = processFile((b) -> b.readLine());
        String twoLines = processFile((b) -> b.readLine() + b.readLine());
    }
}
