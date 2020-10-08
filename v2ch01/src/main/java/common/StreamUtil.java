package common;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by shucheng on 2020/9/10 19:57
 */
public class StreamUtil {

    // 打印流中的前10个元素
    public static <T> void show(String title, Stream<T> stream) {
        final int SIZE = 10;
        List<T> firstElements = stream.limit(SIZE + 1).collect(Collectors.toList());
        System.out.print(title + ": ");
        for (int i = 0; i < firstElements.size(); i++) {
            if (i > 0) System.out.print(", ");
            if (i < SIZE) System.out.print(firstElements.get(i));
            else System.out.print("...");
        }
        System.out.println();
    }

    // 将字符串中每个字符添加到流中
    public static Stream<String> letters(String s) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            result.add(s.substring(i, i + 1));
        }
        return result.stream();
    }

    // 将文件读到String中
    public static String readFileContent(String filePath) throws IOException {
        return readFileContent(filePath, StandardCharsets.UTF_8);
    }

    public static String readFileContent(String filePath, Charset charset) throws IOException {
        Path path = Paths.get(filePath);
        return new String(Files.readAllBytes(path), charset);
    }
}
