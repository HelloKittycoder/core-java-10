package exec;

import util.DBUtil;

import java.io.IOException;
import java.nio.file.Path;
import java.sql.*;
import java.util.Scanner;

/**
 * 5.4.4 组装数据库
 * Created by shucheng on 2020/9/27 17:18
 */
public class ExecSQL {

    public static void main(String[] args) throws Exception {
        executeSql("sql/Books.sql");
        executeSql("sql/Authors.sql");
        executeSql("sql/Publishers.sql");
        executeSql("sql/BooksAuthors.sql");
    }

    public static void executeSql(String filePath) throws IOException {
        Path resourcePath = DBUtil.getPathFromResourcePath(filePath);
        try (Scanner in = new Scanner(resourcePath, "UTF-8");
             Connection conn = DBUtil.getConnection();
             Statement stat = conn.createStatement()) {
            while (true) {
                if (!in.hasNextLine()) return;

                String line = in.nextLine().trim();
                if (line.equalsIgnoreCase("EXIT")) return;
                if (line.endsWith(";")) { // remove trailing semicolon
                    line = line.substring(0, line.length() - 1);
                }
                try {
                    boolean isResult = stat.execute(line);
                    if (isResult) {
                        try (ResultSet rs = stat.getResultSet()) {
                            DBUtil.showResultSet(rs);
                        }
                    } else {
                        int updateCount = stat.getUpdateCount();
                        System.out.println(updateCount + " rows updated");
                    }
                } catch (SQLException ex) {
                    for (Throwable e : ex)
                        e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            for (Throwable t : e)
                t.printStackTrace();
        }
    }

}