package transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.DBUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 5.9.1 用JDBC对事务编程
 *
 * 说明：执行这个类的main方法之前，需要先执行ExecSQL的main方法，
 * 执行建表语句，并插入数据
 * Created by shucheng on 2020/9/29 12:41
 */
public class JDBCTransaction {

    private static Logger LOGGER = LoggerFactory.getLogger(JDBCTransaction.class);

    public static void main(String[] args) {
        Connection conn = null;
        Statement stat = null;

        try {
            conn = DBUtil.getConnection();

            conn.setAutoCommit(false);
            stat = conn.createStatement();

            stat.executeUpdate("INSERT INTO Authors VALUES ('t001', 'name001', 'fname001')");
            stat.executeUpdate("INSERT INTO Authors VALUES ('t002', 'name002', 'fname002')");

            int i = 1;
            if (i == 1) {
                throw new NullPointerException();
            }
            stat.executeUpdate("INSERT INTO Authors VALUES ('t003', 'name003', 'fname003')");

            conn.commit();
            LOGGER.info("事务正常提交");
        } catch (Exception ex) {
            if (conn != null) {
                try {
                    LOGGER.error("事务回滚", ex);
                    conn.rollback();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
        } finally {
            if (stat != null) {
                try {
                    stat.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
        }
    }
}