package cn.org.lilu.chapter5;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Auther: Java成魔之路
 * @Date: 2019/8/17
 * @Description: Stream API练习
 * (1) 找出2011年发生的所有交易，并按交易额排序（从低到高）。
 * (2) 交易员都在哪些不同的城市工作过？
 * (3) 查找所有来自于剑桥（Cambridge）的交易员，并按姓名排序。
 * (4) 返回所有交易员的姓名字符串，按字母顺序排序。
 * (5) 有没有交易员是在米兰工作的？
 * (6) 打印生活在剑桥的交易员的所有交易额。
 * (7) 所有交易中，最高的交易额是多少？
 * (8) 找到交易额最小的交易。
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class StreamAPIPractice {

    /**
     * 初始化交易员和交易订单数据
     */

    Trader raoul = new Trader("Raoul", "Cambridge");
    Trader mario = new Trader("Mario","Milan");
    Trader alan = new Trader("Alan","Cambridge");
    Trader brian = new Trader("Brian","Cambridge");

    List<Transaction> transactions = Arrays.asList(
            new Transaction(brian, 2011, 300),
            new Transaction(raoul, 2012, 1000),
            new Transaction(raoul, 2011, 400),
            new Transaction(mario, 2012, 710),
            new Transaction(mario, 2012, 700),
            new Transaction(alan, 2012, 950)
    );

    /**
     * 找出2011年发生的所有交易，并按交易额排序（从低到高）。
     */
    @Test
    public void test1() {
        List<Transaction> transactionList = transactions.stream()
                .filter(e -> e.getYear() == 2011)
                .sorted(Comparator.comparing(Transaction::getValue))
                .collect(Collectors.toList());
        System.out.println(transactionList);
    }

    /**
     * 交易员都在哪些不同的城市工作过？
     */
    @Test
    public void test2() {
        List<String> cityList = transactions.stream()
                .map(e -> e.getTrader().getCity())
                .distinct()
                .collect(Collectors.toList());
        System.out.println(cityList);
    }

    /**
     * 查找所有来自于剑桥（Cambridge）的交易员，并按姓名排序。
     */
    @Test
    public void test3() {
        List<Trader> cambridgeList = transactions.stream()
                .map(Transaction::getTrader)
                .filter(e -> "Cambridge".equals(e.getCity()))
                .distinct()
                .sorted(Comparator.comparing(Trader::getName))
                .collect(Collectors.toList());
        System.out.println(cambridgeList);
    }

    /**
     * 返回所有交易员的姓名相拼接的字符串，按字母顺序排序。
     */
    @Test
    public void test4() {
        // 每次reduce内部迭代过程中都使用加号+拼接字符串，每次拼接需要生成一个String对象，效率不高。
        String nameString = transactions.stream()
                .map(e -> e.getTrader().getName())
                .distinct()
                .sorted()
                .reduce("",(x,y) -> x + y);

        // 使用joining()，内部使用StringBuilder实现，提高字符串拼接效率。
        String nameJoinString = transactions.stream()
                .map(e -> e.getTrader().getName())
                .distinct()
                .sorted()
                .collect(Collectors.joining());
        System.out.println(nameString);
        System.out.println(nameJoinString);
    }

    /**
     * 有没有交易员是在米兰工作的？
     */
    @Test
    public void test5() {
        boolean anyInMilan = transactions.stream()
                .anyMatch(e -> "Milan".equals(e.getTrader().getCity()));
        System.out.println(anyInMilan);
    }

    /**
     * 打印生活在剑桥的交易员的所有交易额。
     * 疑问：一个交易员可能有多笔交易，书中是分开每笔打印的。
     */
    @Test
    public void test6() {
        transactions.stream()
                .filter(e -> "Cambridge".equals(e.getTrader().getCity()))
                .map(Transaction::getValue)
                .forEach(System.out::println);
    }

    /**
     * 所有交易中，最高的交易额是多少？
     */
    @Test
    public void test7() {
        transactions.stream()
                .map(Transaction::getValue)
                .reduce(Integer::max)
                .ifPresent(System.out::println);
    }

    /**
     * 找到交易额小的交易。
     */
    @Test
    public void test8() {
        // 书中没有的解法：按交易额定制排序后取出第一个交易
        Optional<Transaction> minValueTransaction = transactions.stream()
                .sorted(Comparator.comparing(Transaction::getValue))
                .findFirst();
        // 使用reduce()迭代出交易额最小的订单
        Optional<Transaction> minValueTransactionByReduce = transactions.stream()
                .reduce((x, y) -> x.getValue() < y.getValue() ? x : y);
        // 使用流的min()方法取出交易额最小的交易
        Optional<Transaction> minValueTransactionByMin = transactions.stream()
                .min(Comparator.comparing(Transaction::getValue));
        minValueTransaction.ifPresent(System.out::println);
        minValueTransactionByReduce.ifPresent(System.out::println);
        minValueTransactionByMin.ifPresent(System.out::println);
    }
}
