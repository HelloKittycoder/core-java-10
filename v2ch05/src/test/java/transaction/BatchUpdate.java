package transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.DBUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 5.9.3 批量更新
 * 一个语句序列作为一批操作同时被收集和提交
 *
 * 说明：执行这个类的main方法之前，需要先执行ExecSQL的main方法，
 * 执行建表语句，并插入数据
 *
 * Created by shucheng on 2020/9/29 12:42
 */
public class BatchUpdate {

    private static Logger LOGGER = LoggerFactory.getLogger(BatchUpdate.class);
    // 有关String.format的使用：https://blog.csdn.net/lonely_fireworks/article/details/7962171
    public static final String SQL_TEMPLATE = "INSERT INTO Authors VALUES ('t%1$03d', 'name%1$03d', 'fname%1$03d')";

    /**
     * 说明：
     * 1.运行main方法后，如果要恢复原来的数据状态，就执行DELETE FROM AUTHORS WHERE AUTHOR_ID LIKE 't0%';
     * 2.下面的事务使用的是jdbc默认的自动提交
     * 这种情况下：如果批量更新中有一个出问题了，整个批量更新全部被撤销
     */
    public static void main(String[] args) {

        Connection conn = null;
        Statement stat = null;

        try {
            conn = DBUtil.getConnection();

            conn.setAutoCommit(false);
            stat = conn.createStatement();
            /**
             * String.format(SQL_TEMPLATE, i)的结果：
             * 当i=2时，为 INSERT INTO Authors VALUES ('t002', 'name002', 'fname002')
             * 当i=81时，为 INSERT INTO Authors VALUES ('t081', 'name081', 'fname081')
             */
            int i = 0;
            while (i < 100) {
                stat.addBatch(String.format(SQL_TEMPLATE, i));
                if (i == 50) throw new NullPointerException();
                i++;
            }
            stat.executeBatch();
            conn.commit();
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