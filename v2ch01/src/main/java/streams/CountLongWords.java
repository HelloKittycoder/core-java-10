package streams;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static common.StreamUtil.readFileContent;

/**
 * 从迭代到流的操作
 * Created by shucheng on 2020/9/9 23:16
 */
public class CountLongWords {

    public static void main(String[] args) throws IOException {
        // 将文件读到String中
        String contents = readFileContent("gutenberg/alice30.txt");
        // 使用非字母对contents进行分割
        // 说明： \pL表示字母，\P表示非字母（P表示取补集）
        List<String> words = Arrays.asList(contents.split("\\PL+"));

        long count = 0;
        for (String w : words) {
            if (w.length() > 12) count++;
        }
        System.out.println(count);

        count = words.stream().filter(w -> w.length() > 12).count();
        System.out.println(count);

        count = words.parallelStream().filter(w -> w.length() > 12).count();
        System.out.println(count);
    }
}
