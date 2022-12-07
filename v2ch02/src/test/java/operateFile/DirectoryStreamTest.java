package operateFile;

import common.FileUtil;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * 2.5.7 使用目录流
 * Created by shucheng on 2020/9/24 17:53
 */
public class DirectoryStreamTest {

    @Test
    public void test() throws IOException {
        String resourcePath = FileUtil.getResourcePath("/");
        Path path = Paths.get(resourcePath);
        // 对path路径（target/test-classes）下的一级目录进行遍历（不会递归子目录）
        try (DirectoryStream<Path> entries = Files.newDirectoryStream(path)) {
            for (Path entry : entries) {
                System.out.println(entry);
            }
        }
    }

    @Test
    public void test2() throws IOException {
        String resourcePath = FileUtil.getResourcePath("objectStream");
        Path path = Paths.get(resourcePath);
        // 过滤path路径（target/test-classes，不会递归子目录）下后缀为"*.class"的文件
        try (DirectoryStream<Path> entries = Files.newDirectoryStream(path, "*.class")) {
            for (Path entry : entries) {
                System.out.println(entry);
            }
        }
    }

    @Test
    public void test3() throws IOException {
        String resourcePath = FileUtil.getResourcePath("/");
        Path path = Paths.get(resourcePath);
        // 打印给定目录（target/test-classes，包含该目录）下的所有子目录
        Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                System.out.println(dir);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                return FileVisitResult.SKIP_SUBTREE;
            }
        });
    }

    @Test
    public void test4() throws IOException {
        // 在target/testRecursiveDeleteDir目录下创建测试用的目录，用来测试目录树的删除
        String resourcePath = FileUtil.getResourcePath("../testRecursiveDeleteDir");
        Path root = Paths.get(resourcePath);

        Path testDir = root.resolve("a/b/c/d/e");
        Files.createDirectories(testDir);

        writeContentToFile(root.resolve("a/a.txt"), "a.txt");
        writeContentToFile(root.resolve("a/b/b.txt"), "b.txt");
        writeContentToFile(root.resolve("a/b/c/c.txt"), "c.txt");
        writeContentToFile(root.resolve("a/b/c/d/d.txt"), "d.txt");
        writeContentToFile(root.resolve("a/b/c/d/e/e.txt"), "e.txt");

        // 删除目录树（target/testRecursiveDeleteDir目录及其所有子目录）
        Files.walkFileTree(root, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                // System.out.println("visitFile--" + file);
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                if (exc != null) throw exc;
                // System.out.println("postVisitDirectory--" + dir);
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    private void writeContentToFile(Path file, String content) throws IOException {
        if (!Files.exists(file)) {
            Files.createFile(file);
        }
        if (content == null || content.equals("")) {
            throw new RuntimeException("content不能为空");
        }
        Files.write(file, content.getBytes());
    }
}