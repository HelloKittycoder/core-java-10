package collecting;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static common.StreamUtil.readFileContent;

/**
 * 1.12 约简操作
 * Created by shucheng on 2020/9/15 9:33
 */
public class ReduceTest {

    public static void main(String[] args) throws IOException {

        // 计算 1+2+3+...+10
        // List<Integer> numbers = Stream.iterate(1, n -> n + 1).limit(10).collect(Collectors.toList());
        Stream<Integer> integerStream = Stream.iterate(1, n -> n + 1).limit(10);
        Optional<Integer> sum = integerStream.reduce((x, y) -> x + y);
        System.out.println(sum);

        integerStream = Stream.iterate(1, n -> n + 1).limit(10);
        sum = integerStream.reduce(Integer::sum);
        System.out.println(sum);

        // 计算alice30.txt中的所有单词的字母数量之和
        String contents = readFileContent("gutenberg/alice30.txt");
        List<String> wordList = Arrays.asList(contents.split("\\PL+"));
        int result = wordList.stream().reduce(0, (total, word) -> total + word.length(),
                (total1, total2) -> total1 + total2);
        System.out.println(result);

        // 验证上面的结果是否正确，两种方法结果是一致的
        /*int wordNum = 0;
        for (String s : wordList) {
            wordNum += s.length();
        }
        System.out.println(wordNum);*/
    }
}
