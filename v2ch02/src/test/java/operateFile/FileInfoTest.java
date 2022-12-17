package operateFile;

import common.util.FileUtil;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 2.5.5 获取文件信息
 * 这块用的不多，里面的信息有很多内容在操作系统之间并不具备可移植性
 * Created by shucheng on 2020/9/23 22:51
 */
public class FileInfoTest {

    @Test
    public void test() throws IOException {
        String path = FileUtil.getResourcePath("operateFile/fileInfoTest");
        Path resourcePath = Paths.get(path);
        System.out.println(Files.exists(resourcePath));

        Path path2 = Paths.get("../gutenberg/alice30.txt");
        System.out.println(Files.exists(path2));
        System.out.println(Files.isHidden(path2));
        System.out.println(Files.size(path2)); // 返回文件的字节数

    }
}
