package io.github.llnancy.jingtian.javase.java14;

/**
 * test records
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK14 2023/7/19
 */
public class RecordsTest {

    public static void main(String[] args) {
        Records records1 = new Records("id1", "name1");
        Records records2 = new Records("id2", "name2");
        String id = records1.id();
        String name = records1.name();
        // Records[id=id1, name=name1]
        System.out.println(records1);
        // Records[id=id2, name=name2]
        System.out.println(records2);
    }
}

record Records(String id, String name) {
}
