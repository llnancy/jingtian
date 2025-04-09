package io.github.llnancy.middleware.flink;

import java.util.ArrayList;
import java.util.List;

public class WordCount {

    public String word;

    public Integer count;

    public WordCount() {
    }

    public WordCount(String word, Integer count) {
        this.word = word;
        this.count = count;
    }

    public static List<WordCount> list() {
        List<WordCount> wordCounts = new ArrayList<>();
        wordCounts.add(new WordCount("a", 1));
        wordCounts.add(new WordCount("b", 1));
        wordCounts.add(new WordCount("c", 1));
        wordCounts.add(new WordCount("d", 1));
        wordCounts.add(new WordCount("e", 1));
        return wordCounts;
    }

    @Override
    public String toString() {
        return "WordCount{" +
                "word='" + word + '\'' +
                ", count=" + count +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WordCount wordCount = (WordCount) o;
        return word.equals(wordCount.word) && count.equals(wordCount.count);
    }

    @Override
    public int hashCode() {
        int result = word.hashCode();
        result = 31 * result + count.hashCode();
        return result;
    }
}