package streams;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static common.StreamUtil.readFileContent;

/**
 * 1.6 简单约简
 * Created by shucheng on 2020/9/10 20:32
 */
public class SimpleReduce {

    public static void main(String[] args) throws IOException {
        String contents = readFileContent("gutenberg/alice30.txt");
        List<String> words = Arrays.asList(contents.split("\\PL+"));
        Optional<String> largest = words.stream().max(String::compareToIgnoreCase);
        System.out.println("largest: " + largest.orElse(""));

        Optional<String> startsWithQ = words.stream().filter(s -> s.startsWith("Q")).findFirst();
        System.out.println("startsWithQ: " + startsWithQ.orElse(""));

        Optional<String> startsWithQ2 = words.stream().parallel().filter(s -> s.startsWith("Q")).findFirst();
        System.out.println("startsWithQ2: " + startsWithQ2.orElse(""));

        boolean aWordStartsWithQ = words.stream().parallel().anyMatch(s -> s.startsWith("Q"));
        System.out.println("aWordStartsWithQ: " + aWordStartsWithQ);
    }
}
