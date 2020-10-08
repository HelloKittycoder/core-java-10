package zip;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * 压缩单个文件到zip包中，查看zip包中的内容
 * Created by shucheng on 2020/9/17 22:23
 */
public class ZipTest {

    public static void main(String[] args) throws IOException {

        File file = File.createTempFile("tmp", ".txt", new File("D:/"));
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(("hehehe====" + file.getName()).getBytes());
        fos.close();
        file.deleteOnExit();

        // 创建一个zip文件
        FileOutputStream fout = new FileOutputStream("test.zip");
        try (ZipOutputStream zout = new ZipOutputStream(fout);
             FileInputStream fis = new FileInputStream(file)) {
            ZipEntry ze = new ZipEntry(file.getName());
            zout.putNextEntry(ze);

            int len;
            byte[] data = new byte[1024];
            while ((len = fis.read(data, 0, data.length)) != -1) {
                zout.write(data, 0, len);
            }

            zout.closeEntry();
        }

        // 读取一个zip文件的内容
        try (ZipInputStream zin = new ZipInputStream(new FileInputStream("test.zip"))) {
            ZipEntry ze;
            while ((ze = zin.getNextEntry()) != null) {
                String name = ze.getName();
                System.out.println("解压缩文件：" + name);
                zin.closeEntry();
            }
        }
    }
}
