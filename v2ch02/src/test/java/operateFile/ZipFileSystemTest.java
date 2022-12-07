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

    @Test
    public void test2() throws IOException {
        Path source = Paths.get(FileUtil.getResourcePath("testFileSystem.zip"));
        Path target = Paths.get(FileUtil.getResourcePath("operateFile/testZipFileSystem/decompressedZip"));
        // 将target/test-classes/testFileSystem.zip中的所有文件解压到target/test-classes/operateFile/testZipFileSystem/decompressedZip目录中
        unzip(source, target);
    }

    @Test
    public void test3() throws IOException {
        Path source = Paths.get(FileUtil.getResourcePath("spring-boot-starter-amqp-2.2.2.RELEASE-sources.jar"));
        Path target = Paths.get(FileUtil.getResourcePath("operateFile/testZipFileSystem/decompressedJar"));
        // 将target/test-classes/spring-boot-starter-amqp-2.2.2.RELEASE-sources.jar中的所有文件解压到target/test-classes/operateFile/testZipFileSystem/decompressedJar目录中
        unzip(source, target);
    }

    /**
     * 解压zip或jar包
     * @param source 要解压的源文件
     * @param target 解压到的目标目录
     * @throws IOException
     */
    private static void unzip(Path source, Path target) throws IOException {
        FileSystem fs = FileSystems.newFileSystem(source, null);

        // 确保要解压到的目标目录是存在的
        if (!Files.exists(target)) {
            Files.createDirectories(target);
        }
        // 执行解压操作
        Path root = fs.getPath("/");
        Files.walkFileTree(root, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                // 不写toString的话，这里会报java.nio.file.ProviderMismatchException这个错
                Path q = target.resolve(root.relativize(file).toString());
                Path parent = q.getParent();
                if (!Files.exists(parent)) {
                    Files.createDirectories(parent);
                }
                Files.copy(file, q);
                return FileVisitResult.CONTINUE;
            }
        });
    }
}