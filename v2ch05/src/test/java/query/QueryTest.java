package query;

import util.DBUtil;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 5.5.1 预备语句
 *
 * 说明：执行这个类的main方法之前，需要先执行ExecSQL的main方法，
 * 执行建表语句，并插入数据
 *
 * Created by shucheng on 2020/9/27 23:20
 */
public class QueryTest {
    public static final String allQuery = "SELECT Books.Price, Books.Title FROM Books";

    public static final String authorPublisherQuery = "SELECT Books.Price, Books.Title"
            + " FROM Books, BooksAuthors, Authors, Publishers"
            + " WHERE Authors.Author_Id = BooksAuthors.Author_id AND BooksAuthors.ISBN = Books.ISBN"
            + " AND Books.Publisher_Id = Publishers.Publisher_Id AND Authors.Name = ?"
            + " AND Publishers.Name = ?";

    public static final String authorQuery
            = "SELECT Books.Price, Books.Title FROM Books, BooksAuthors, Authors"
            + " WHERE Authors.Author_Id = BooksAuthors.Author_Id AND BooksAuthors.ISBN = Books.ISBN"
            + " AND Authors.Name = ?";

    public static final String publisherQuery
            = "SELECT Books.Price, Books.Title FROM Books, Publishers"
            + " WHERE Books.Publisher_Id = Publishers.Publisher_Id AND Publishers.Name = ?";

    public static final String priceUpdate = "UPDATE Books " + "SET Price = Price + ? "
            + " WHERE Books.Publisher_Id = (SELECT Publisher_Id FROM Publishers WHERE Name = ?)";

    private static Scanner in;
    private static ArrayList<String> authors = new ArrayList<>();
    private static ArrayList<String> publishers = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        try (Connection conn = DBUtil.getConnection()) {
            in = new Scanner(System.in);
            authors.add("Any");
            publishers.add("Any");
            try (Statement stat = conn.createStatement()){
                // Fill the authors array list
                String query = "SELECT Name FROM Authors";
                try (ResultSet rs = stat.executeQuery(query)) {
                    while (rs.next())
                        authors.add(rs.getString(1));
                }

                // Fill the publishers array list
                query = "SELECT Name FROM Publishers";
                try (ResultSet rs = stat.executeQuery(query)) {
                    while (rs.next())
                        publishers.add(rs.getString(1));
                }
            }

            boolean done = false;
            while (!done) {
                System.out.println("Q)uery C)hange prices E)xit: ");
                String input = in.next().toUpperCase();
                if (input.equals("Q"))
                    executeQuery(conn);
                else if (input.equals("C"))
                    changePrices(conn);
                else
                    done = true;
            }
        } catch (SQLException e) {
            for (Throwable t : e) {
                System.out.println(t.getMessage());
            }
        } finally {
            in.close();
        }
    }

    /**
     * Executes the selected query.
     * @param conn the database connection
     */
    private static void executeQuery(Connection conn) throws SQLException {
        String author = select("Authors:", authors);
        String publisher = select("Publishers:", publishers);
        PreparedStatement stat;
        // 查询条件：author、publisher
        if (!author.equals("Any") && !publisher.equals("Any")) {
            stat = conn.prepareStatement(authorPublisherQuery);
            stat.setString(1, author);
            stat.setString(2, publisher);
        // 查询条件：author
        } else if (!author.equals("Any") && publisher.equals("Any")) {
            stat = conn.prepareStatement(authorQuery);
            stat.setString(1, author);
        // 查询条件：publisher
        } else if (author.equals("Any") && !publisher.equals("Any")) {
            stat = conn.prepareStatement(publisherQuery);
            stat.setString(1, publisher);
        // 查询条件：无
        } else {
            stat = conn.prepareStatement(allQuery);
        }

        try (ResultSet rs = stat.executeQuery()) {
            while (rs.next())
                System.out.println(rs.getString(1) + ", " + rs.getString(2));
        }
    }

    /**
     * Executes an update statement to change prices.
     * @param conn the database connection
     */
    public static void changePrices(Connection conn) throws SQLException {
        String publisher = select("Publishers:", publishers.subList(1, publishers.size()));
        System.out.println("Change prices by: ");
        double priceChange = in.nextDouble();
        PreparedStatement stat = conn.prepareStatement(priceUpdate);
        stat.setDouble(1, priceChange);
        stat.setString(2, publisher);
        int r = stat.executeUpdate();
        System.out.println(r + " records updated.");
    }

    /**
     * Asks the user to select a string.
     * @param prompt the prompt to display
     * @param options the options from which the user can choose
     * @return the option that the user chose
     */
    public static String select(String prompt, List<String> options) {
        while (true) {
            System.out.println(prompt);
            for (int i = 0; i < options.size(); i++) {
                System.out.printf("%2d) %s%n", i + 1, options.get(i));
            }
            int sel = in.nextInt();
            if (sel > 0 && sel <= options.size()) {
                return options.get(sel - 1);
            }
        }
    }
}
