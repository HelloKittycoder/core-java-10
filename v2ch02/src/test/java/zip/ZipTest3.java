package zip;

import common.util.FileUtil;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.function.BiConsumer;
import java.util.zip.*;

/**
 * 将目录压缩到zip包中，查看zip包中的内容
 * 参考链接：https://www.iteye.com/blog/lxzqz-2034189
 *
 * 下面的方法（uncompress、readFileList、readFileList2）里加Charset.forName("GBK")，
 * 是为了应对zip包内部的文件有中文时会报java.lang.IllegalArgumentException: MALFORMED的错误
 * Created by shucheng on 2020/9/18 10:03
 */
public class ZipTest3 {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZipTest3.class);

    public static void main(String[] args) throws IOException {
        createFile("aaa/1.txt");
        createFile("aaa/bbb/2.txt");

        File file = FileUtil.getResourceFile("aaa");

        LOGGER.info("Start---压缩文件aaa.zip");
        // compress1(file);
        compress(file, FileUtil.getResourcePath("compressedFile/aaa.zip"));
        // compress(file, "F:/aaa.zip");
        LOGGER.info("End---压缩文件aaa.zip");

        LOGGER.info("Start---解压文件aaa.zip");
        File zipFile = FileUtil.getResourceFile("compressedFile/aaa.zip");
        uncompress(zipFile, FileUtil.getResourcePath("decompressedFile"));
        LOGGER.info("End---解压文件aaa.zip");

        LOGGER.info("Start---读取aaa.zip文件列表");
        readFileList(FileUtil.getResourcePath("compressedFile/aaa.zip"));
        // readFileList2(FileUtil.getResourcePath("compressedFile/aaa.zip"));
        LOGGER.info("End---读取aaa.zip文件列表");

        LOGGER.info("Start---读取testFileSystem.zip文件列表");
        readZipFile(FileUtil.getResourcePath("testFileSystem.zip"), ".sql", (inputStream, entryName) -> {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            try {
                String s;
                System.out.println("读取文件==>" + entryName);
                while ((s = bufferedReader.readLine()) != null) {
                    System.out.println(s);
                }
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
            }
        });
        LOGGER.info("End---读取testFileSystem.zip文件列表");
    }

    // 简单读取指定目录下的zip压缩文件，这里没有递归去读
    @Test
    @Ignore
    public void testAaa() {
        File file = new File("C:\\Users\\shucheng.luo\\Downloads");
        File[] files = file.listFiles((dir, name) -> name.endsWith(".zip"));
        System.out.println(files);
        for (File f : files) {
            System.out.println(f.getAbsolutePath());
        }
    }

    /**
     * 读取压缩包中带指定后缀的文件
     * @param zipFilePath 压缩包路径
     * @param fileExt 需要指定的后缀
     * @param consumer 调用者对文件的处理
     */
    public static void readZipFile(String zipFilePath, String fileExt, BiConsumer<InputStream, String> consumer) {
        try {
            ZipFile zipFile = new ZipFile(zipFilePath, Charset.forName("GBK"));
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                String entryName = entry.getName();
                if (!entry.isDirectory() && entryName.endsWith(fileExt)) {
                    InputStream inputStream = zipFile.getInputStream(entry);
                    try {
                        // inputStream的具体逻辑交给外部调用者来处理
                        consumer.accept(inputStream, entryName);
                    } finally {
                        if (inputStream != null) {
                            inputStream.close();
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 将文件或目录压缩成zip文件（不用递归，手动穷举时的写法）
    public static void compress1(File file) {
        if (file.exists()) {
            try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream("aaa.zip"))) {
                if (file.isDirectory()) { // 如果是目录的话
                    compressFile(zout, FileUtil.getResourceFile("aaa/1.txt"), "aaa/");
                    compressFile(zout, FileUtil.getResourceFile("aaa/bbb/2.txt"), "aaa/bbb/");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            throw new RuntimeException("文件不存在");
        }
    }

    // 将文件或目录压缩成zip文件（使用递归的写法）
    public static void compress(File file, String outputFilePath) {
        if (file.exists()) {
            // 如果目标文件所在的文件夹不存在，则自动创建所在文件夹（递归创建）
            File outputFile = new File(outputFilePath);
            File parentDir = outputFile.getParentFile();
            if (!parentDir.exists()) {
                parentDir.mkdirs();
            }

            try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(outputFile))) {
                compress(zout, file, "");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            throw new RuntimeException("文件不存在");
        }
    }

    /**
     * 压缩文件是由一个个entry组成的
     * 这里没有把目录添加到压缩文件的entry里
     * 正常使用压缩软件压缩的文件，会把目录页加到entry里
     */
    private static void compress(ZipOutputStream zout, File file, String parent) {
        if (file.isDirectory()) {
            LOGGER.info("压缩目录：" + file.getAbsolutePath());
            compressDirectory(zout, file, parent);
        } else {
            LOGGER.info("压缩文件：" + file.getAbsolutePath());
            compressFile(zout, file, parent);
        }
    }

    private static void compressDirectory(ZipOutputStream zout, File file, String parent) {
        File[] files = file.listFiles();
        for (File f : files) {
            compress(zout, f, parent + file.getName() + "/");
        }
    }

    private static void compressFile(ZipOutputStream zout, File file, String parent) {
        String fileName = file.getName();
        ZipEntry ze = new ZipEntry(parent + fileName);
        try {
            zout.putNextEntry(ze);

            FileInputStream fis = new FileInputStream(file);
            int len;
            byte[] data = new byte[1024];
            while ((len = fis.read(data, 0, data.length)) != -1) {
                zout.write(data, 0, len);
            }

            fis.close();
            zout.closeEntry();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 将zip压缩包解压到指定目录
    public static void uncompress(File sourceFile, String outputDirectoryPath) {
        if (sourceFile.exists()) {
            // 如果设置的解压目录不存在，则自动创建目录
            File outputDirectory = new File(outputDirectoryPath);
            if (!outputDirectory.exists()) {
                outputDirectory.mkdirs();
            }

            /**
             * 解决报错：文件名中带中文时会出现java.lang.IllegalArgumentException: MALFORMED
             * 方法：给ZipInputStream指定字符集为GBK
             * 参考链接：https://cloud.tencent.com/developer/article/1130020
             */
            try (ZipInputStream zin = new ZipInputStream(new FileInputStream(sourceFile), Charset.forName("GBK"))) {
                uncompress(zin, outputDirectoryPath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            throw new RuntimeException("文件不存在");
        }
    }

    // 路径创建
    private static void uncompress(ZipInputStream zin, String path) {
        ZipEntry ze;
        try {
            while ((ze = zin.getNextEntry()) != null) {
                String name = ze.getName();
                File file = new File(path, name);
                if (ze.isDirectory()) { // 说明是目录
                    file.mkdirs();
                    LOGGER.info("entry创建目录：" + file.getAbsolutePath());
                } else { // 说明是文件（这个代码创建的压缩包只会进到else里面，如果是用压缩软件创建的，两种情况都会进入）
                    if (!file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                        LOGGER.info("file创建目录：" + file.getParentFile().getAbsolutePath());
                    }
                    uncompressFile(zin, file);
                    LOGGER.info("file解压文件：" + file.getAbsolutePath());
                }
                zin.closeEntry();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 解压文件
    private static void uncompressFile(ZipInputStream zin, File file) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            int len;
            byte[] data = new byte[1024];
            while ((len = zin.read(data, 0, data.length)) != -1) {
                fos.write(data, 0, len);
            }
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 读取zip压缩包的文件列表（读取速度快，推荐使用）
    public static void readFileList(String zipFilePath) {
        try {
            ZipFile zipFile = new ZipFile(zipFilePath, Charset.forName("GBK"));
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                LOGGER.info("{}-{}", entry.isDirectory() ? "目录" : "文件", entry.getName());
            }
            zipFile.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 不建议使用下面这种方式读取zip包文件列表，因为对于大文件来说，速度太慢了
    public static void readFileList2(String zipFilePath) {
        try {
            ZipInputStream zin = new ZipInputStream(new FileInputStream(zipFilePath), Charset.forName("GBK"));
            ZipEntry ze;
            while ((ze = zin.getNextEntry()) != null) {
                LOGGER.info("{}-{}", ze.isDirectory() ? "目录" : "文件", ze.getName());
            }
            zin.closeEntry();
            zin.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 在资源目录（target/classes）下创建文件，并写入内容（就是文件名），
     * 如果该文件的上层目录不存在，则自动创建
     * @param filePath 文件路径（相对于资源目录的）
     * @return
     * @throws IOException
     */
    public static File createFile(String filePath) throws IOException {
        File file = FileUtil.getResourceFile(filePath);

        File parentDir = file.getParentFile();
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }

        FileOutputStream fos = new FileOutputStream(file);
        fos.write(file.getName().getBytes());
        fos.close();
        return file;
    }
}