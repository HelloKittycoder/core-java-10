package transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.DBUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;

/**
 * 5.9.2 保存点
 * 在使用某些驱动程序时，使用保存点可以更细粒度地控制回滚操作。创建
 * 一个保存点意味着稍后只需返回到这个点，而非事务的开头。
 *
 * 说明：执行这个类的main方法之前，需要先执行ExecSQL的main方法，
 * 执行建表语句，并插入数据
 *
 * Created by shucheng on 2020/9/29 12:41
 */
public class SavePoint {

    private static Logger LOGGER = LoggerFactory.getLogger(SavePoint.class);

    public static void main(String[] args) {
        Connection conn = null;
        Statement stat = null;
        Savepoint svpt = null;

        try {
            conn = DBUtil.getConnection();

            conn.setAutoCommit(false);
            stat = conn.createStatement(); // start transaction; rollback() goes here

            stat.executeUpdate("INSERT INTO Authors VALUES ('t001', 'name001', 'fname001')");
            stat.executeUpdate("INSERT INTO Authors VALUES ('t002', 'name002', 'fname002')");
            svpt = conn.setSavepoint(); // set savepoint; rollback(svpt)

            int i = 1;
            if (i == 1) {
                throw new NullPointerException();
            }
            stat.executeUpdate("INSERT INTO Authors VALUES ('t003', 'name003', 'fname003')");
            conn.commit();
        } catch (Exception ex) {
            if (conn != null) {
                try {
                    LOGGER.error("事务回滚", ex);
                    conn.rollback(svpt);
                    // 当不再需要保存点时，必须释放它
                    conn.releaseSavepoint(svpt);
                    /**
                     * 下面这一行是需要的，不然会报错（java.sql.SQLException: 当事务处理仍处于活动状态时，无法关闭连接）
                     * 因为conn.setAutoCommit(false);时，事务不会自动提交，
                     * 执行过conn.rollback(svpt)操作后，事务还处在活动状态
                     *
                     * 这个要和conn.rollback()进行区分，conn.rollback()后不需要再执行conn.commit()，因为此时事务已经结束，
                     * 不再处于活动状态
                     */
                    conn.commit();
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