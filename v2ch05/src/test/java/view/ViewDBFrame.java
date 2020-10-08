package view;

import util.DBUtil;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.Properties;

/**
 * The frame that holds the data panel and the navigation buttons
 * Created by shucheng on 2020/9/29 10:01
 */
public class ViewDBFrame extends JFrame {

    private JButton previousButton;
    private JButton nextButton;
    private JButton deleteButton;
    private JButton saveButton;
    private DataPanel dataPanel;
    private Component scrollPanel;
    private JComboBox<String> tableNames;
    private Properties props;
    private CachedRowSet crs;
    private Connection conn;

    public static void main(String[] args) throws IOException {
        StringWriter sw = new StringWriter();
        sw.write("111");
        sw.write("222");
        sw.write("kkk");
        sw.append("\r\nxixixi");
        System.out.println(sw);
        sw.close();
    }

    public ViewDBFrame() {
        tableNames = new JComboBox<>();

        try {
            readDatabaseProperties();
            conn = getConnection();
            DatabaseMetaData meta = conn.getMetaData();
            try (ResultSet mrs = meta.getTables(null, null, null, new String[]{"TABLE"})) {
                while (mrs.next())
                    tableNames.addItem(mrs.getString(3));
            }
        } catch (SQLException ex) {
            for (Throwable t : ex)
                t.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        tableNames.addActionListener(event -> showTable((String) tableNames.getSelectedItem(), conn));
        add(tableNames, BorderLayout.NORTH);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    if (conn != null) conn.close();
                } catch (SQLException ex) {
                    for (Throwable t : ex)
                        t.printStackTrace();
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        add(buttonPanel, BorderLayout.SOUTH);

        previousButton = new JButton("Previous" );
        previousButton.addActionListener(event -> showPreviousRow());
        buttonPanel.add(previousButton);

        nextButton = new JButton("Next");
        nextButton.addActionListener(event -> showNextRow());
        buttonPanel.add(nextButton);

        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(event -> deleteRow());
        buttonPanel.add(deleteButton);

        saveButton = new JButton("Save");
        saveButton.addActionListener(event -> saveChanges());
        buttonPanel.add(saveButton);
        if (tableNames.getItemCount() > 0)
            showTable(tableNames.getItemAt(0), conn);
    }

    /**
     * Prepares the text fields for showing a new table, and shows the first row.
     * @param tableName the name of the table to display
     * @param conn the database connection
     */
    public void showTable(String tableName, Connection conn) {
        try (Statement stat = conn.createStatement();
            ResultSet result = stat.executeQuery("SELECT * FROM " + tableName)) {
            // get result set
            // copy into cached row set
            RowSetFactory factory = RowSetProvider.newFactory();
            crs = factory.createCachedRowSet();
            crs.setTableName(tableName);
            crs.populate(result);

            if (scrollPanel != null) remove(scrollPanel);
            dataPanel = new DataPanel(crs);
            scrollPanel = new JScrollPane(dataPanel);
            add(scrollPanel, BorderLayout.CENTER);
            pack();
            showNextRow();
        } catch (SQLException ex) {
            for (Throwable t : ex)
                t.printStackTrace();
        }
    }

    /**
     * Moves to the previous table row.
     */
    public void showPreviousRow() {
        try {
            if (crs == null || crs.isFirst()) return;
            crs.previous();
            dataPanel.showRow(crs);
        } catch (SQLException ex) {
            for (Throwable t : ex)
                t.printStackTrace();
        }
    }

    /**
     * Moves to the next table row.
     */
    public void showNextRow() {
        try {
            if (crs == null || crs.isLast()) return;
            crs.next();
            dataPanel.showRow(crs);
        } catch (SQLException ex) {
            for (Throwable t : ex)
                t.printStackTrace();
        }
    }

    /**
     * Deletes current table row.
     */
    public void deleteRow() {
        if (crs == null) return;
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                crs.deleteRow();
                crs.acceptChanges(conn);
                if (crs.isAfterLast())
                    if (!crs.last()) crs = null;
                return null;
            }

            @Override
            protected void done() {
                dataPanel.showRow(crs);
            }
        }.execute();
    }

    /**
     * Saves all changes.
     */
    public void saveChanges() {
        if (crs == null) return;
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                dataPanel.setRow(crs);
                crs.acceptChanges(conn);
                return null;
            }
        }.execute();
    }

    private void readDatabaseProperties() throws IOException {
        props = new Properties();
        Path databaseProperties = DBUtil.getPathFromResourcePath("database.properties");
        try (InputStream in = Files.newInputStream(databaseProperties)) {
            props.load(in);
        }
        String drivers = props.getProperty("jdbc.drivers");
        if (drivers != null) System.setProperty("jdbc.drivers", drivers );
    }

    /**
     * Gets a connection from the properties specified in the database.properties
     * @return the database connection
     */
    private Connection getConnection() throws SQLException {
        String url = props.getProperty("jdbc.url");
        String username = props.getProperty("jdbc.username");
        String password = props.getProperty("jdbc.password");

        return DriverManager.getConnection(url, username, password);
    }
}