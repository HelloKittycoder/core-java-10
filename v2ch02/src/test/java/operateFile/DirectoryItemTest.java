package operateFile;

import common.util.FileUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * 2.5.6 访问目录中的项
 * Created by shucheng on 2020/9/23 23:00
 */
public class DirectoryItemTest {

    private static Logger LOGGER = LoggerFactory.getLogger(DirectoryItemTest.class);

    @Test
    public void test() throws URISyntaxException, IOException {
        URL resource = Thread.currentThread().getContextClassLoader().getResource("");
        Path path = Paths.get(resource.toURI());
        System.out.println(path);

        // 因为读取目录涉及需要关闭的系统资源，所以应该使用try块
        // 注意：Files.list方法不会进入子目录
        try (Stream<Path> entries = Files.list(path)) {
            entries.forEach(System.out::println);
        }

        // Files.walk会处理目录中的所有子目录
        LOGGER.info("开始调用Files.walk方法");
        try (Stream<Path> entries = Files.walk(path)) {
            entries.forEach(System.out::println);
        }

        // 目录复制
        Path path1 = Paths.get(FileUtil.getResourcePath("operateFile"));
        // Path path2 = Paths.get(FileUtil.getResourcePath("operateFile2"));
        // 为什么不从target/test-classes/operateFile复制到target/test-classes/operateFile2？
        // 因为第二次运行mvn test时无法正常加载类
        // copyDirectory(path1, path2);

        Path path2 = Paths.get(Thread.currentThread().getContextClassLoader().getResource("").toURI()); // target/test-classes/
        // 将target/test-classes/operateFile的内容复制到target/mytemp/operateFile2
        copyDirectory(path1, path2.resolve("../mytemp/operateFile2"));
    }

    /**
     * 目录复制
     * @param source 原始目录
     * @param target 目标目录
     */
    public static void copyDirectory(Path source, Path target) throws IOException {
        /**
         * 说明：下面的代码使用了Files.walk将一个目录复制到另一个目录
         * Files.walk不能方便地删除目录树，因为你需要在删除父目录之前必须先删除子目录，
         * 而Files.walk是先遍历父目录，然后遍历子目录，这二者方向刚好相反
         */
        LOGGER.info("开始复制文件夹{}", source);
        // 这里如果目标文件夹已存在，则直接退出方法
        if (Files.exists(target)) {
            return;
        // 否则，就事先创建目标文件夹的所有父级文件夹
        } else {
            Files.createDirectories(target.getParent());
        }
        try (Stream<Path> entries = Files.walk(source)) {
            entries.forEach(p -> {
                LOGGER.info("path=={}", p);
                try {
                    // 计算p在target中应该存放的位置
                    Path q = target.resolve(source.relativize(p));
                    // 如果p是目录，则把q当成目录来创建；如果p是文件，则把p直接复制到q所在的位置
                    if (Files.isDirectory(p)) {
                        Files.createDirectory(q);
                    } else {
                        Files.copy(p, q);
                    }
                } catch (IOException ex) {
                    throw new UncheckedIOException(ex);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER.info("结束复制文件夹{}", source);
    }
}
