package resultset;

import util.DBUtil;

import java.io.IOException;
import java.sql.*;

/**
 * 5.6.2 可更新的结果集
 *
 * 说明：执行这个类的main方法之前，需要先执行ExecSQL的main方法，
 * 执行建表语句，并插入数据
 *
 * Created by shucheng on 2020/9/29 9:28
 */
public class UpdatableResultSet {

    public static void main(String[] args) throws IOException, SQLException {
        Connection conn = DBUtil.getConnection();
        DatabaseMetaData metaData = conn.getMetaData();

        // 判断是否支持“滚动遍历结果集，同时能编辑数据”
        boolean b = metaData.supportsResultSetConcurrency(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE); // derby支持
        if (b) {
            try (Statement stat = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stat.executeQuery("SELECT * FROM Books")) {

                // 给所有行的price增加1000
                while (rs.next()) {
                    double increase = 1000;
                    double price = rs.getDouble("Price");
                    rs.updateDouble("Price", price + increase);
                    rs.updateRow();
                }
            }
        }

        conn.close();
    }
}