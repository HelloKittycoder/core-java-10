package streams;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static common.StreamUtil.readFileContent;

/**
 * 1.13 基本类型流
 * Created by shucheng on 2020/9/15 20:07
 */
public class PrimitiveTypeStreams {

    public static void main(String[] args) throws IOException {
        IntStream is1 = IntStream.generate(() -> (int) (Math.random() * 100));
        show("is1", is1);
        IntStream is2 = IntStream.range(5, 10); // [5,10)的整数
        show("is2", is2);
        IntStream is3 = IntStream.rangeClosed(5, 10); // [5,10]的整数
        show("is3", is3);

        // 有一个字符串流，并想将其长度处理为整数
        String contents = readFileContent("gutenberg/alice30.txt");
        Stream<String> words = Stream.of(contents.split("\\PL+"));
        IntStream is4 = words.mapToInt(String::length);
        show("is4", is4);

        String sentence = "\uD835\uDD46 is the set of octonions.";
        System.out.println(sentence);
        // 将字符串转换为整数流
        IntStream codes = sentence.codePoints();
        // 将整数流转换为字符串流，并打印出来
        // %x/%X：表示以16进制输出整数
        System.out.println(codes.mapToObj(c -> String.format("%X", c)).collect(
                Collectors.joining()));
        // System.out.println(String.format("%X", 10)); // 10的16进制表示是A

        Stream<Integer> integers = IntStream.range(0, 100).boxed(); // boxed：将基本类型流转换为对象流
        IntStream is5 = integers.mapToInt(Integer::intValue); // mapToXX：将对象流转换为基本类型流
        show("is5", is5);
    }

    public static void show(String title, IntStream stream) {
        final int SIZE = 10;
        int[] firstElements = stream.limit(SIZE + 1).toArray();
        System.out.print(title + ": ");
        for (int i = 0; i < firstElements.length; i++) {
            if (i > 0) System.out.print(", ");
            if (i < SIZE) System.out.print(firstElements[i]);
            else System.out.print("...");
        }
        System.out.println();
    }
}
