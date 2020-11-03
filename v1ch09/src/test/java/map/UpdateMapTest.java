package map;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 9.3.2 更新映射项
 * Created by shucheng on 10/26/2020 9:46 PM
 */
public class UpdateMapTest {

    // 统计文章中每个单词出现的频次
    public static void main(String[] args) throws IOException {
        // 参考了卷2的CountLongWords（1.1 从迭代到流的操作）
        Path path = Paths.get("gutenberg/alice30.txt");
        String contents = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);

        List<String> words = Arrays.asList(contents.split("\\PL+"));

        Map<String, Integer> counts = new HashMap<>();
        for (String word : words) {
            // 写法1：
            // counts.put(word, counts.getOrDefault(word, 0) + 1);

            // 写法2：
            /*counts.putIfAbsent(word, 0);
            counts.put(word, counts.get(word) + 1);*/

            // 写法3：
            counts.merge(word, 1, Integer::sum);
        }
        System.out.println("统计完毕");
    }
}
