package test;

import util.DBUtil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 5.3.5 连接到数据库
 * Created by shucheng on 2020/9/27 15:49
 */
public class TestDB {

    public static void main(String[] args) throws IOException {
        try {
            runTest();
        } catch (SQLException ex) {
            for (Throwable t : ex) {
                t.printStackTrace();
            }
        }
    }

    /**
     * Runs a test by creating a table, adding a value, showing the table contents, and removing
     * the table.
     * @throws IOException
     * @throws SQLException
     */
    public static void runTest() throws IOException, SQLException {
        try (Connection conn  = DBUtil.getConnection();
            Statement stat = conn.createStatement()) {
            stat.executeUpdate("CREATE TABLE Greetings (Message CHAR(20))");
            stat.executeUpdate("INSERT INTO Greetings VALUES('Hello, World!')");

            try (ResultSet result = stat.executeQuery("SELECT * FROM Greetings")) {
                if (result.next()) {
                    System.out.println(result.getString(1));
                }
                stat.executeUpdate("DROP TABLE Greetings");
            }
        }
    }
}