package streams;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static common.StreamUtil.*;

/**
 * 1.3 filter、map和flatMap方法
 * Created by shucheng on 2020/9/10 12:24
 */
public class FilterMapTest {

    public static void main(String[] args) throws IOException {
        String contents = readFileContent("gutenberg/alice30.txt");

        List<String> words = Arrays.asList(contents.split("\\PL+"));
        Stream<String> longWords = words.stream().filter(w -> w.length() > 12);
        show("longWords", longWords);

        Stream<String> lowercaseWords = words.stream().map(String::toLowerCase);
        // Stream<String> lowercaseWords = words.stream().map(s -> s.toLowerCase());
        show("lowercaseWords", lowercaseWords);

        Stream<String> firstLetters = words.stream().map(s -> s.length() > 0 ? s.substring(0, 1) : "");
        show("firstLetters", firstLetters);

        Stream<String> boat = letters("boat");
        System.out.println(boat.collect(Collectors.toList()));

        List<String> myWords = Arrays.asList("your", "boat");
        // 一个包含流的流（两层流） [...["y","o","u","r"],["b","o","a","t"],...]
        Stream<Stream<String>> streamStream = myWords.stream().map(w -> letters(w));
        show("streamStream", streamStream);

        // 被转换成一层流 [..."y","o","u","r","b","o","a","t",...]
        Stream<String> stringStream = myWords.stream().flatMap(w -> letters(w));
        show("stringStream", stringStream);
    }

}
