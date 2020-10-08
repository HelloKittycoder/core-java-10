package streams;

import java.io.IOException;
import java.util.stream.Stream;

import static common.StreamUtil.*;

/**
 * 1.4 抽取子流和连接流
 * Created by shucheng on 2020/9/10 13:12
 */
public class LimitStreamTest {

    public static void main(String[] args) throws IOException {
        Stream<Double> randoms = Stream.generate(Math::random).limit(100);
        show("randoms", randoms);

        String contents = readFileContent("gutenberg/alice30.txt");

        Stream<String> words1 = Stream.of(contents.split("\\PL+"));
        show("words1", words1);

        // 丢弃第1个元素（它是空字符串，可以去掉）
        Stream<String> words2 = Stream.of(contents.split("\\PL+")).skip(1);
        show("words2", words2);

        // 合并流（第一个流不应该是无限的，否则第二个流永远都不会得到处理的机会）
        Stream<String> combined = Stream.concat(letters("Hello"), letters("World"));
        show("combined", combined);
    }
}
