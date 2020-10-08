package streams;

import common.StreamUtil;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static common.StreamUtil.readFileContent;

/**
 * 流的创建
 * Created by shucheng on 2020/9/10 8:26
 */
public class CreatingStreams {

    public static void main(String[] args) throws IOException {
        String filePath = "gutenberg/alice30.txt";
        String contents = readFileContent(filePath);

        Stream<String> words = Stream.of(contents.split("\\PL+"));
        StreamUtil.show("words", words);

        Stream<String> song = Stream.of("gently", "down", "the", "stream");
        StreamUtil.show("song", song);

        Stream<String> silence = Stream.empty();
        StreamUtil.show("silence", silence);

        Stream<String> echos = Stream.generate(() -> "Echo");
        StreamUtil.show("echos", echos);

        Stream<Double> randoms = Stream.generate(Math::random);
        StreamUtil.show("randoms", randoms);

        Stream<BigInteger> integers = Stream.iterate(BigInteger.ONE, n -> n.add(BigInteger.ONE));
        StreamUtil.show("integers", integers);

        Stream<String> wordsAnotherWay = Pattern.compile("\\PL+").splitAsStream(contents);
        StreamUtil.show("wordsAnotherWay", wordsAnotherWay);

        try (Stream<String> lines = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
            StreamUtil.show("lines", lines);
        }
    }
}
