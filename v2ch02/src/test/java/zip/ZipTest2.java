package zip;

import common.util.FileUtil;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * 压缩多个文件文件到zip包中，查看zip包中的内容
 * Created by shucheng on 2020/9/18 8:22
 */
public class ZipTest2 {

    public static void main(String[] args) throws IOException {
        File file1 = createFile("aaa.txt");
        File file2 = createFile("bbb.txt");
        File file3 = createFile("ccc.txt");

        // 创建一个zip文件
        String zipFilePath = FileUtil.getResourcePath("test.zip");
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zipFilePath))) {
            compressFile(zout, file1);
            compressFile(zout, file2);
            compressFile(zout, file3);
        }

        readZipFileList(zipFilePath);
    }

    // 将一个文件放到压缩包中
    public static void compressFile(ZipOutputStream zout, File file) {
        String fileName = file.getName();
        ZipEntry ze = new ZipEntry(fileName);
        try {
            zout.putNextEntry(ze);

            FileInputStream fis = new FileInputStream(file);
            int len;
            byte[] data = new byte[1024];
            while ((len = fis.read(data, 0 , data.length)) != -1) {
                zout.write(data, 0, len);
            }

            fis.close();
            zout.closeEntry();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 读取压缩包中的文件列表
    public static void readZipFileList(String filePath) throws IOException {
        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(filePath))) {
            ZipEntry ze;
            while ((ze = zin.getNextEntry()) != null) {
                String name = ze.getName();
                System.out.println("解压缩文件：" + name);
                zin.closeEntry();
            }
        }
    }

    // 在资源目录（target/classes）下创建文件，并写入内容（就是文件名）
    public static File createFile(String fileName) throws IOException {
        String resourcePath = FileUtil.getResourcePath(fileName);
        File file = new File(resourcePath);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(fileName.getBytes());
        fos.close();
        file.deleteOnExit();
        return file;
    }
}
