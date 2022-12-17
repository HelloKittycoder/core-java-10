package randomAccess;

import common.util.FileUtil;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

/**
 * 测试DataInput相关接口
 * 参考链接：https://www.cnblogs.com/z-qinfeng/p/11789568.html
 * Created by shucheng on 2020/9/17 8:25
 */
public class DataInputTest {

    @Before
    public void setUp() throws IOException {
        String resourcePath = FileUtil.getResourcePath("randomAccess/randomAccess.txt");
        File file = new File(resourcePath);
        if (!file.exists()) {
            file.createNewFile();
        }

        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write("abcdefghijklmn");
        fileWriter.close();
    }

    // randomAccess.txt的文件内容为abcdefghijklmn
    // 如果下面把5改成15，就会报EOF错误
    @Test
    public void testReadFully() throws IOException {
        RandomAccessFile dataInput = FileUtil.readResourceRandomFile("randomAccess/randomAccess.txt", "r");
        byte[] buf = new byte[5];
        dataInput.readFully(buf); // 从inputStream中读取5个字节，并存到buf中
        System.out.println(new String(buf)); // abcde
        dataInput.close();
    }

    @Test
    public void testSkipBytes() throws IOException {
        RandomAccessFile dataInput = FileUtil.readResourceRandomFile("randomAccess/randomAccess.txt", "r");
        dataInput.skipBytes(5);
        byte[] buf = new byte[5];
        dataInput.readFully(buf);
        System.out.println(new String(buf)); // fghij
        dataInput.close();
    }

    // readBoolean：从流中读取一个字节，如果这个字节是非零，返回true；否则返回false
    // 如果要显示Exception的话，把@Test里的expected去掉，然后在try块后面加上catch块即可
    @Test(expected = EOFException.class)
    public void testReadBoolean() throws IOException {
        RandomAccessFile dataInput = FileUtil.readResourceRandomFile("randomAccess/randomAccess.txt", "r");
        try {
            while (true) {
                System.out.println(dataInput.readBoolean());
            }
        } finally {
            dataInput.close();
        }
    }

    @Test
    public void testReadBoolean2() throws IOException {
        String resourcePath = FileUtil.getResourcePath("randomAccess/test1.txt");
        RandomAccessFile data = new RandomAccessFile(resourcePath, "rw");
        // 使用writeBoolean()方法将false写到test1.txt文件
        data.writeBoolean(false);
        // 再写个int
        data.writeInt(5);
        data.close();

        data = new RandomAccessFile(resourcePath, "r");
        // 使用readBoolean()读取test1.txt文件的内容
        System.out.println(data.readBoolean()); // false
        // 再读int
        System.out.println(data.readInt()); // 5
        data.close();
    }

    @Test
    public void testReadByte() throws IOException {
        RandomAccessFile dataInput = FileUtil.readResourceRandomFile("randomAccess/randomAccess.txt", "r");
        // 从dataInput中读取一个字节
        byte b = dataInput.readByte();
        System.out.println(b); // 97（a的unicode就是97）
    }

}
