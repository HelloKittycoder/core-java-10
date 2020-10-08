package operateFile;

import common.FileUtil;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 2.5.3 创建文件和目录
 * Created by shucheng on 2020/9/23 18:10
 */
public class CreateFileDirectoryTest {

    @Test
    public void test() throws IOException {
        // Files.createDirectory 创建新目录（如果目录已经存在了，那么这个调用就会抛异常）
        String resourcePath = FileUtil.getResourcePath("operateFile/testDir");
        Path path = Paths.get(resourcePath);
        if (!Files.exists(path)) {
            Files.createDirectory(path);
        }

        // Files.createDirectories 创建多层目录
        String resourcePath2 = FileUtil.getResourcePath("operateFile/aa/bb");
        Path path2 = Paths.get(resourcePath2);
        Files.createDirectories(path2);

        // Files.createFile 创建一个空文件（如果文件已经存在了，那么这个调用就会抛异常）
        String resourcePath3 = FileUtil.getResourcePath("operateFile/aa/testFile.txt");
        Path path3 = Paths.get(resourcePath3);
        if (!Files.exists(path3)) {
            Files.createFile(path3);
        }

        // 创建临时文件
        String tempFileDirResourcePath = FileUtil.getResourcePath("operateFile/tempFileDir");
        Path tempFileDir = Paths.get(tempFileDirResourcePath);
        if (!Files.exists(tempFileDir)) {
            Files.createDirectory(tempFileDir);
        }
        // Files.createTempFile 返回创建好的临时文件对象
        Files.createTempFile(tempFileDir, "aa", ".txt");

        // 创建临时目录
        String tempDirResourcePath = FileUtil.getResourcePath("operateFile");
        Path tempDir = Paths.get(tempDirResourcePath);
        Files.createTempDirectory(tempDir, "aa");
    }
}