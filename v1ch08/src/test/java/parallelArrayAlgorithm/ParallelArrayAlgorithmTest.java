package parallelArrayAlgorithm;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;

/**
 * 14.7.6 并行数组算法（P679）
 * Created by shucheng on 2020/11/2 8:10
 */
public class ParallelArrayAlgorithmTest {

    public static void main(String[] args) throws IOException {
        // 参考了卷2的 CountLongWords（1.1 从迭代到流的操作）
        Path path = Paths.get("gutenberg/alice30.txt");
        String contents = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);

        String[] words = contents.split("[\\P{L}]+");
        String[] copyWords = Arrays.copyOf(words, words.length);
        /*String[] words2 = contents.split("\\PL+");
        System.out.println(Arrays.equals(words, words2)); // true*/

        Arrays.parallelSort(words); // 按字母顺序排序
        Arrays.parallelSort(copyWords, Comparator.comparing(String::length)); // 按单词长度排序

        // 使用给定的规则进行数组填充
        int[] values = new int[30];
        Arrays.parallelSetAll(values, i -> i % 10);
        int[] values2 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        Arrays.parallelPrefix(values2, (x, y) -> x * y);
    }
}
