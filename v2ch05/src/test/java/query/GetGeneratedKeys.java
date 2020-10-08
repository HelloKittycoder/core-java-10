package query;

import org.junit.Ignore;
import org.junit.Test;
import util.DBUtil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 5.5.5 获取自动生成的键
 * http://db.apache.org/derby/docs/10.9/ref/crefjavstateautogen.html
 * https://blog.csdn.net/weixin_34113237/article/details/91905817
 *
 * Created by shucheng on 2020/9/28 19:05
 */
@Ignore
public class GetGeneratedKeys {

    @Test
    public void test() throws IOException, SQLException {
        Connection conn = DBUtil.getConnection();

        Statement s = conn.createStatement();
        s.executeUpdate("CREATE TABLE TABLE1(C11 int, C12 int GENERATED ALWAYS AS IDENTITY)");

        s.execute("INSERT INTO TABLE1 (C11) VALUES (1)", Statement.RETURN_GENERATED_KEYS);
        ResultSet rs = s.getGeneratedKeys();
        if (rs.next()) {
            int key = rs.getInt(1);
            System.out.printf("自增后返回的主键为%d%n", key); // 返回值为1
        }

        s.executeUpdate("DROP TABLE TABLE1");
        rs.close();
        s.close();
        conn.close();
    }
}