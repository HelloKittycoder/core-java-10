package streams;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static common.StreamUtil.readFileContent;
import static common.StreamUtil.show;

/**
 * 1.5 其他的流转换
 * Created by shucheng on 2020/9/10 20:20
 */
public class OtherStreamConvert {

    public static void main(String[] args) throws IOException {
        // 去重
        Stream<String> uniqueWords = Stream.of("merrily", "merrily", "merrily", "gently").distinct();
        show("uniqueWords", uniqueWords);

        String contents = readFileContent("gutenberg/alice30.txt");
        List<String> words = Arrays.asList(contents.split("\\PL+"));

        Stream<String> longestFirst = words.stream().sorted(Comparator.comparing(String::length).reversed());
        show("longestFirst", longestFirst);

        Object[] powers = Stream.iterate(1.0, p -> p * 2)
                .peek(e -> System.out.println("Fetching " + e))
                .limit(20).toArray();
        System.out.println(Arrays.asList(powers));
    }
}
