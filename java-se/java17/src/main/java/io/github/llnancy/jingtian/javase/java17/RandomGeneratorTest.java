package io.github.llnancy.jingtian.javase.java17;

import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;

/**
 * random generator
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/7/20
 */
public class RandomGeneratorTest {

    public static void main(String[] args) {
        RandomGeneratorFactory.all()
                .forEach(factory -> {
                    System.out.println(factory.group() + ":" + factory.name());
                });

        RandomGeneratorFactory<RandomGenerator> random = RandomGeneratorFactory.of("L32X64MixRandom");
        RandomGenerator generator = random.create();
        for (int i = 0; i < 5; i++) {
            System.out.println(generator.nextInt(10));
        }
    }
}
