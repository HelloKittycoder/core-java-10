package common;

import java.io.*;
import java.net.URL;
import java.nio.file.Path;

/**
 * Created by shucheng on 2020/9/18 8:26
 */
public class FileUtil {
    /**
     * 获取相对于已有资源路径（如：target/classes）下的文件路径
     * （这里不要求资源文件存在）
     * @param filePath
     *  比如：filePath为randomAccess/test1.txt，最终的路径就是target/classes/randomAccess/test1.txt（target之前的路径被省略了）
     * @return
     */
    public static String getResourcePath(String filePath) {
        if (filePath == null || filePath == "") {
            throw new RuntimeException("文件路径为空");
        }
        if (filePath.startsWith("/")) {
            filePath = filePath.substring(1);
        }
        URL resource = Thread.currentThread().getContextClassLoader().getResource("");
        /**
         * 说明：下面为什么加substring(1)？
         * 通过 https://www.cnblogs.com/foolash/p/12180455.html 可以知道，因为resource.getPath()返回的路径r开头中含有/，
         * 导致Paths.get(r)报错java.nio.file.InvalidPathException: Illegal char <:>
         * 这里没有采用链接里提出的方法，因为我的方法返回的是String，而不是Path
         * 解决方法：去掉第1个/就行了
         *
         * 如何重现问题java.nio.file.InvalidPathException: Illegal char <:>？
         * URL resource = Thread.currentThread().getContextClassLoader().getResource("");
         * Path resourcePath = Paths.get(resource.getPath());
         */
        return resource.getPath().substring(1) + filePath;
    }

    /**
     * 根据给出的相对于资源路径的相对路径，返回对应的File（文件或目录）
     * @param filePath
     * @return
     */
    public static File getResourceFile(String filePath) {
        String resourcePath = getResourcePath(filePath);
        return new File(resourcePath);
    }

    // 读取资源文件（这里要求资源文件确实存在，不然readResourceFile里的uri为null）
    public static RandomAccessFile readResourceRandomFile(String filePath, String mode) throws FileNotFoundException {
        File file = readResourceFile(filePath);
        return new RandomAccessFile(file, mode);
    }

    public static File readResourceFile(String filePath) {
        // randomAccess/randomAccess.txt
        URL uri = Thread.currentThread().getContextClassLoader().getResource(filePath);
        return new File(uri.getPath());
    }

    // 根据给出的相对于资源路径的相对路径，创建文件，并写入内容
    public static Path createFileAndWriteContent(String resourceFilePath, String content) throws IOException {
        String resourcePath = FileUtil.getResourcePath(resourceFilePath);
        File file = new File(resourcePath);
        if (!file.exists()) {
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();
        }
        return file.toPath();
    }
}
