package collecting;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static common.StreamUtil.readFileContent;

/**
 * 1.8 收集结果
 * Created by shucheng on 2020/9/10 22:48
 */
public class CollectingResults {

    public static void main(String[] args) throws IOException {
        Iterator<Integer> iter = Stream.iterate(0, n -> n + 1).limit(10).iterator();
        while (iter.hasNext())
            System.out.println(iter.next());

        Object[] numbers = Stream.iterate(0, n -> n + 1).limit(10).toArray();
        System.out.println("Object array: " + numbers); // 注：这是一个Object[]数组

        try {
            Integer number = (Integer) numbers[0];
            System.out.println("number: " + number);
            System.out.println("The following statement throws a exception:");
            Integer[] numbers2 = (Integer[]) numbers;
        } catch (ClassCastException ex) {
            System.out.println(ex);
        }

        Integer[] numbers3 = Stream.iterate(0, n -> n + 1).limit(10)
                .toArray(Integer[]::new);
        System.out.println("Integer array: " + numbers3); // 注：这是一个Integer[]数组

        // 将list流转换成Set
        Set<String> noVowelSet = noVowels().collect(Collectors.toSet());
        show("noVowelSet", noVowelSet);

        // 将list流转换成TreeSet
        TreeSet<String> noVowelTreeSet = noVowels().collect(Collectors.toCollection(TreeSet::new));
        show("noVowelTreeSet", noVowelTreeSet);

        /* 将list流中的前10个元素连接起来：可通过下面这段进行验证
        List<String> strList = noVowels().limit(10).collect(Collectors.toList());
        System.out.println(strList);*/
        String result = noVowels().limit(10).collect(Collectors.joining());
        System.out.println("Joining: " + result);

        // 将list流中的前10个元素使用逗号连接起来
        result = noVowels().limit(10).collect(Collectors.joining(", "));
        System.out.println("Joining with commas: " + result);

        // 获取list流中有关字符串长度的一些统计信息（单词平均长度，单词最大长度）
        IntSummaryStatistics summary = noVowels().collect(Collectors.summarizingInt(String::length));
        // IntSummaryStatistics summary = noVowels().collect(Collectors.summarizingInt(s->s.length()));
        double averageWordLength = summary.getAverage();
        int maxWordLength = summary.getMax();
        System.out.println("Average word length: " + averageWordLength);
        System.out.println("Max word length: " + maxWordLength);

        // 循环打印list流中的前10个元素
        System.out.println("forEach: ");
        noVowels().limit(10).forEach(System.out::println);
        // noVowels().limit(10).forEach(s-> System.out.println(s));
    }

    public static <T> void show(String label, Set<T> set) {
        System.out.println(label + ": " + set.getClass().getName());
        System.out.println("["
            + set.stream().limit(10).map(Objects::toString).collect(Collectors.joining(", ")) + "]");
    }

    // 将整个文件中的所有单词里的元音字母去掉
    public static Stream<String> noVowels() throws IOException {
        String contents = readFileContent("gutenberg/alice30.txt");
        List<String> wordList = Arrays.asList(contents.split("\\PL+"));
        Stream<String> words = wordList.stream();
        return words.map(s -> s.replaceAll("[aeiouAEIOU]", ""));
    }
}
