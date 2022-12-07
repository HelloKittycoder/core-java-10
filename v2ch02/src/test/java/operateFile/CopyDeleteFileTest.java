package operateFile;

import common.FileUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * 2.5.4 复制、移动和删除文件
 * Created by shucheng on 2020/9/23 21:47
 */
public class CopyDeleteFileTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void test() throws IOException {
        Path file = createFile("operateFile/original/aaa.txt");
        logger.info("创建文件{}", file);
        Files.write(file, "111".getBytes(StandardCharsets.UTF_8));
        Path directory = createDirectory("operateFile/destination");
        logger.info("创建目录{}", directory);

        // 将文件"operateFile/original/aaa.txt"复制到"operateFile/destination/bbb.txt"
        Path targetFile = directory.resolve("bbb.txt");
        Files.copy(file, targetFile, StandardCopyOption.REPLACE_EXISTING);
        logger.info("将文件{}复制到{}", file, targetFile);

        // 将文件"operateFile/destination/bbb.txt"移动到"operateFile/original/"下，并重命名为"ccc.txt"
        Files.move(targetFile, file.resolveSibling("ccc.txt"), StandardCopyOption.REPLACE_EXISTING);
        logger.info("将文件{}移动到{}", targetFile, file.resolveSibling("ccc.txt"));

        // 删除文件"operateFile/original/ccc.txt"
        // delete：如果要删除的文件不存在时，会抛出异常；所以下面改用deleteIfExists，避免了异常的产生
        Files.deleteIfExists(file.resolveSibling("ccc.txt"));
        logger.info("删除文件{}", file);
    }

    /**
     * 根据给定的资源目录相对路径创建空文件
     * @param resourceFilePath
     * @return 返回新创建的文件或者已经有的文件
     * @throws IOException
     */
    public static Path createFile(String resourceFilePath) throws IOException {
        String resourcePath = FileUtil.getResourcePath(resourceFilePath);
        Path path = Paths.get(resourcePath);
        // 如果文件不存在
        if (!Files.exists(path)) {
            // 创建父级及以上所有层级的文件夹（只要有不存在的）
            Files.createDirectories(path.getParent());
            // 创建一个空文件
            return Files.createFile(path);
        }
        return path;
    }

    /**
     * 根据给定的资源目录相对路径创建目录
     * @param resourceDirPath
     * @return 返回新创建的目录或者已经有的目录
     * @throws IOException
     */
    public static Path createDirectory(String resourceDirPath) throws IOException {
        String resourcePath = FileUtil.getResourcePath(resourceDirPath);
        Path path = Paths.get(resourcePath);
        // 如果目录不存在
        if (!Files.exists(path)) {
            // 创建该目录
            return Files.createDirectory(path);
        }
        return path;
    }
}
