package operateFile;

import common.FileUtil;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * 2.5.8 ZIP文件系统 P112
 * Created by shucheng on 2020/9/24 18:19
 */
public class ZipFileSystemTest {

    @Test
    public void test() throws IOException {
        String testFileSystem = FileUtil.getResourcePath("testFileSystem.zip");
        Path path = Paths.get(testFileSystem);
        FileSystem fs = FileSystems.newFileSystem(path, null);
        // 列出testFileSystem.zip中的所有文件
        Files.walkFileTree(fs.getPath("/"), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                System.out.println(file);
                return FileVisitResult.CONTINUE;
            }
        });
    }
}