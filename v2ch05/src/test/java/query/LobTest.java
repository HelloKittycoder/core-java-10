package query;

import org.junit.Ignore;
import org.junit.Test;
import util.DBUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;

/**
 * 5.5.2 读写LOB字段
 * 参考链接：https://www.cnblogs.com/langtianya/p/3157048.html
 * Created by shucheng on 2020/9/28 15:38
 */
// @Ignore表示忽略当前类中的所有单元测试，如果需要做单元测试，把@Ignore注释掉即可
@Ignore
public class LobTest {

    // 测试Clob字段
    // http://db.apache.org/derby/docs/10.9/ref/rrefclob.html
    @Test
    public void testClob() throws IOException, SQLException {
        Connection conn = DBUtil.getConnection();

        Statement s = conn.createStatement();
        s.executeUpdate("CREATE TABLE documents(id INT, text CLOB)");

        // - first, create an input stream
        Path path = Paths.get("../gutenberg/alice30.txt");
        InputStream fis = Files.newInputStream(path);

        PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO documents VALUES (?, ?)");
        ps.setInt(1, 1477);

        // 将输入参数设置为输入流
        ps.setAsciiStream(2, fis);
        ps.execute();

        // -- reading the columns
        ResultSet rs = s.executeQuery("SELECT text FROM documents WHERE id = 1477");

        while (rs.next()) {
            Clob aclob = rs.getClob(1);
            InputStream ip = aclob.getAsciiStream();

            for (int c = ip.read(); c != -1; c = ip.read()) {
                System.out.print((char)c);
            }
            ip.close();
        }

        rs.close();

        s.executeUpdate("DROP TABLE documents");

        s.close();
        ps.close();
        conn.close();
        fis.close();
    }

    // 测试Blob字段
    // http://db.apache.org/derby/docs/10.9/ref/rrefblob.html
    @Test
    public void testBlob() throws IOException, SQLException {
        Connection conn = DBUtil.getConnection();

        Statement s = conn.createStatement();
        s.executeUpdate("CREATE TABLE images (id INT, img BLOB)");

        // - first, create an input stream
        InputStream fis = ClassLoader.getSystemResourceAsStream("images/MissingPage.png");

        PreparedStatement ps = conn.prepareStatement("INSERT INTO images VALUES (?, ?)");
        ps.setInt(1, 1477);

        // 将输入参数设置为输入流
        ps.setBinaryStream(2, fis);
        ps.execute();

        // -- reading the columns
        ResultSet rs = s.executeQuery("SELECT  img FROM images WHERE id = 1477");
        byte[] buff = new byte[1024];

        while (rs.next()) {
            Blob ablob = rs.getBlob(1);
            // 生成的文件在 target/test-classes/query/newimage.png 中
            File newfile = new File(getImagePath("query/newimage.png"));

            InputStream is = ablob.getBinaryStream();
            FileOutputStream fos = new FileOutputStream(newfile);

            for (int b = is.read(buff); b != -1; b = is.read(buff)) {
                fos.write(buff, 0, b);
            }
            fos.close();
            is.close();
        }

        rs.close();

        s.executeUpdate("DROP TABLE images");

        s.close();
        ps.close();
        conn.close();
        fis.close();
    }

    // "query/newimage.png"
    public static String getImagePath(String path) {
        URL uri = ClassLoader.getSystemResource("");
        try {
            return uri.toURI().resolve(path).getPath();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}