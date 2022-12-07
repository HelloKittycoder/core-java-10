package operateFile;

import common.FileUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

/**
 * 2.5.2 读写文件
 * Created by shucheng on 2020/9/23 8:23
 */
public class FilesTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void test() throws IOException {
        Charset utf8 = StandardCharsets.UTF_8;

        // 读取文件的所有内容
        Path path = Paths.get("../gutenberg/alice30.txt");
        // System.out.println(System.getProperty("user.dir"));
        // System.out.println("path========" + path.toAbsolutePath());
        String content = new String(Files.readAllBytes(path), utf8);

        // 将文件当作行序列读入
        List<String> lines = Files.readAllLines(path, utf8);

        Path filePath = FileUtil.createFileAndWriteContent("operateFile/test.txt",
                "abcdefghijklmn");
        // 写出一个字符串到文件中
        Files.write(filePath, "111".getBytes(utf8));

        // 向指定文件追加内容
        Files.write(filePath, "张三".getBytes(utf8), StandardOpenOption.APPEND);
        Files.write(filePath, System.lineSeparator().getBytes(), StandardOpenOption.APPEND);

        // 将行的集合写出到文件中
        List<String> lines2 = Arrays.asList("第1行", "第2行", "第3行");
        Files.write(filePath, lines2, StandardOpenOption.APPEND);

        /**
         * 以上简便方法适用于处理中等长度的文本文件，如果要处理的文件长度比较大，
         * 或者是二进制文件，那么还是使用所熟知的输入/输出流或者读入器/写出器
         */
        String resourcePath = FileUtil.getResourcePath("operateFile/filesTest.txt");
        Path resource = Paths.get(resourcePath);

        OutputStream outputStream = Files.newOutputStream(resource);
        outputStream.write("测试".getBytes(utf8));
        outputStream.close();

        InputStream inputStream = Files.newInputStream(resource);
        byte[] bytes = new byte[10];
        int readLength = inputStream.read(bytes);
        logger.info(new String(bytes, 0, readLength, utf8));
        inputStream.close();

        BufferedWriter bufferedWriter = Files.newBufferedWriter(resource, StandardOpenOption.APPEND);
        bufferedWriter.newLine();
        bufferedWriter.write("写入BufferedWriter数据");
        bufferedWriter.close();

        BufferedReader bufferedReader = Files.newBufferedReader(resource);
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            logger.info(line);
        }
        bufferedReader.close();
    }
}
