package parallel;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import static common.StreamUtil.readFileContent;
import static java.util.stream.Collectors.*;

/**
 * 1.14 并行流
 * Created by shucheng on 2020/9/15 22:26
 */
public class ParallelStreams {

    public static void main(String[] args) throws IOException {
        String contents = readFileContent("gutenberg/alice30.txt");
        List<String> wordList = Arrays.asList(contents.split("\\PL+"));

        // 对字符串流中的所有短单词计数
        // A.Very bad code ahead（有问题的代码）
        int[] shortWords = new int[10];
        wordList.parallelStream().forEach(s -> {
            if (s.length() < 10) shortWords[s.length()]++;
        });
        System.out.println(Arrays.toString(shortWords));

        // Try again--the result will likely be different(and also wrong)
        Arrays.fill(shortWords, 0);
        wordList.parallelStream().forEach(s -> {
            if (s.length() < 10) shortWords[s.length()]++;
        });
        System.out.println(Arrays.toString(shortWords));

        // B.Remedy: Group and count
        Map<Integer, Long> shortWordCounts = wordList.parallelStream()
            .filter(s -> s.length() < 10)
            .collect(groupingBy(String::length, counting()));
        System.out.println(shortWordCounts);

        /**
         * C.Downstream order not deterministic（运行两次会发现List<String>里的顺序不一样，
         * 但整体包含的内容都一样）
         */
        Map<Integer, List<String>> result = wordList.parallelStream().collect(
                groupingByConcurrent(String::length));
        System.out.println(result.get(14));

        result = wordList.parallelStream().collect(
                groupingByConcurrent(String::length));
        System.out.println(result.get(14));

        // D.这里会发现key在0-9的部分和B的结果是一致的
        Map<Integer, Long> wordCounts = wordList.parallelStream().collect(
                groupingByConcurrent(String::length, counting()));
        System.out.println(wordCounts);
    }
}
