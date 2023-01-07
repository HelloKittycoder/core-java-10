package util;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

/**
 * 看到P278
 * Created by shucheng on 2020/9/28 8:07
 */
public class DBUtil {

    public static Path getPathFromResourcePath(String filePath) {
        URL resource = ClassLoader.getSystemResource(filePath);
        Path path = null;
        try {
            path = Paths.get(resource.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return path;
    }

    public static File getFileFromResourcePath(String filePath) {
        Path path = getPathFromResourcePath(filePath);
        return new File(path.toUri());
    }

    /**
     * Gets a connection from the properties specified in the file database.properties.
     * @return the database connection
     */
    public static Connection getConnection() throws SQLException, IOException {
        Properties props = new Properties();
        Path databaseProperties = getPathFromResourcePath("database.properties");
        try (InputStream in  = Files.newInputStream(databaseProperties)) {
            props.load(in);
        }

        String drivers = props.getProperty("jdbc.drivers");
        if (drivers != null) System.setProperty("jdbc.drivers", drivers);

        String url = props.getProperty("jdbc.url");
        String username = props.getProperty("jdbc.username");
        String password = props.getProperty("jdbc.password");

        return DriverManager.getConnection(url, username, password);
    }

    // 如果是以insert、delete、update、create开头的
    public static final Pattern SQL_START = Pattern.compile("^(insert|delete|update|create).*$", Pattern.CASE_INSENSITIVE);
    public static final Pattern SELECT = Pattern.compile("^select.*$", Pattern.CASE_INSENSITIVE);

    /**
     * 执行insert、delete、update、create等语句
     *
     * 参照ExecSQL#executeSql作了简单的改造
     * 使用：
     * executeSql("sql/Books.sql");
     * executeSql("sql/Authors.sql");
     * executeSql("sql/Publishers.sql");
     * executeSql("sql/BooksAuthors.sql");
     *
     * @param resourcePath
     * @throws IOException
     */
    public static void executeSql(String resourcePath) throws IOException {
        // "sql/Books.sql"
        InputStream is = ClassLoader.getSystemResourceAsStream(resourcePath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        // 读取sql内容
        String s;
        StringBuilder sb = null;

        // 将解析好的sql放到list中（这里考虑的情况比较简单，有很多不够完善的地方）
        List<String> sqlList = new ArrayList<>();
        while ((s = reader.readLine()) != null) {

            boolean isSqlStart = SQL_START.matcher(s).matches();
            String trimStr = s.trim();

            if (isSqlStart) { // sql的开头
                sb = new StringBuilder();
                sb.append(trimStr);
            } else if (!SELECT.matcher(s).matches()) { // sql中间或结束部分
                sb.append(System.lineSeparator()).append(s);
            }

            if (s.endsWith(";")) { // 该条sql解析完成
                sqlList.add(sb.substring(0, sb.length() - 1));
            }
        }
        reader.close();

        // 执行解析好的sql
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            for (String line : sqlList) {
                boolean isResult = stmt.execute(line);
                if (!isResult) {
                    int updateCount = stmt.getUpdateCount();
                    System.out.println(updateCount + " rows updated");
                }
            }
        } catch (SQLException ex) {
            for (Throwable e : ex)
                e.printStackTrace();
        }
    }

    /**
     * Prints a result set
     * @param result the result set to be printed
     */
    public static void showResultSet(ResultSet result) throws SQLException {
        int columnCount = printColumnTitle(result);
        while (result.next()) {
            printRowData(result, columnCount);
        }
    }

    /**
     * Prints a result set
     * @param result the result set to be printed
     */
    public static void showRow(ResultSet result) throws SQLException {
        int columnCount = printColumnTitle(result);
        printRowData(result, columnCount);
    }

    private static int printColumnTitle(ResultSet result) throws SQLException {
        ResultSetMetaData metaData = result.getMetaData();
        int columnCount = metaData.getColumnCount();

        for (int i = 1; i <= columnCount; i++) {
            if (i > 1) System.out.print(", ");
            System.out.print(metaData.getColumnLabel(i));
        }
        System.out.println();
        return columnCount;
    }

    private static void printRowData(ResultSet result, int columnCount) throws SQLException {
        for (int i = 1; i <= columnCount; i++) {
            if (i > 1) System.out.print(", ");
            System.out.print(result.getString(i));
        }
        System.out.println();
    }
}
