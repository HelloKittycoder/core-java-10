package resultset;

import util.DBUtil;

import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

/**
 * 5.6.1 可滚动的结果集
 *
 * 说明：执行这个类的main方法之前，需要先执行ExecSQL的main方法，
 * 执行建表语句，并插入数据
 *
 * Created by shucheng on 2020/9/28 22:41
 */
public class ScrollResultSet {

    public static void main(String[] args) throws IOException, SQLException {
        Connection conn = DBUtil.getConnection();
        DatabaseMetaData metaData = conn.getMetaData();

        // 判断是否支持“滚动遍历结果集、但同时不能编辑数据”
        boolean b = metaData.supportsResultSetConcurrency(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY); // derby支持
        if (b) {
            try (Statement stat = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
                 ResultSet rs = stat.executeQuery("SELECT * FROM AUTHORS")) {

                boolean done = false;
                Scanner in = new Scanner(System.in);
                int lastRow = 0;
                while (!done) {;
                    // 这里R2，将游标指向2，显示的是从第2条至最后一条数据
                    // N，如果上一次是R2，这次就将游标指向3，显示从第3条至最后一条数据
                    // P，如果上一次是N，这一次就将游标指向2，显示从第2条至最后一条数据
                    System.out.println("Please input rnum(e.g.'R2') or 'N)ext', 'P)revious', 'Q)uit'");
                    String input = in.nextLine();
                    if (input != null) {
                        boolean result = false;
                        if (input.startsWith("R")) { // 指定行
                            // 获取行号
                            int sel = Integer.parseInt(input.substring(1));
                            result = rs.absolute(sel);
                            lastRow = rs.getRow();
                        } else if (input.startsWith("N")) { // 下一条
                            rs.absolute(lastRow);
                            result = rs.relative(1);
                            lastRow = rs.getRow();
                        } else if (input.startsWith("P")) { // 上一条
                            rs.absolute(lastRow);
                            result = rs.previous();
                            lastRow = rs.getRow();
                        } else if (input.startsWith("Q")) { // 退出
                            done = true;
                        }
                        if (result) {
                            DBUtil.showResultSet(rs);
                        }
                    }
                }
                in.close();
            }
        }

        conn.close();
    }
}
