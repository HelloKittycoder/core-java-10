package permissions;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * 9.2.5 实现权限类
 * This class demonstrates the custom WordCheckPermission
 * Created by shucheng on 2020/10/7 15:28
 */
public class PermissionTest {

    public static void main(String[] args) {
        URL policyFile = ClassLoader.getSystemResource("permissions/PermissionTest.policy");
        String policyFilePath = policyFile.getPath();
        System.setProperty("java.security.policy", policyFilePath);
        System.setSecurityManager(new SecurityManager());
        EventQueue.invokeLater(() -> {
            JFrame frame = new PermissionTestFrame();
            frame.setTitle("PermissionTest");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }

    /**
     * This frame contains a text field for inserting words into a text area that is protected from
     * "bad words".
     */
    public static class PermissionTestFrame extends JFrame {
        private JTextField textField;
        private WordCheckTextArea textArea;
        public static final int TEXT_ROWS = 20;
        public static final int TEXT_COLUMNS = 60;

        public PermissionTestFrame() {
            textField = new JTextField(20);
            JPanel panel = new JPanel();
            panel.add(textField);
            JButton openButton = new JButton("insert");
            panel.add(openButton);
            openButton.addActionListener(event -> insertWords(textField.getText()));

            add(panel, BorderLayout.NORTH);

            textArea = new WordCheckTextArea();
            textArea.setRows(TEXT_ROWS);
            textArea.setColumns(TEXT_COLUMNS);
            add(new JScrollPane(textArea), BorderLayout.CENTER);
            pack();
        }

        /**
         * Tries to insert words into the text area. Displays a dialog if the attempt fails.
         * @param words the words to insert
         */
        public void insertWords(String words) {
            try {
                textArea.append(words + "\n");
            } catch (SecurityException ex) {
                JOptionPane.showMessageDialog(this, "I am sorry, but I cannot do that.");
                ex.printStackTrace();
            }
        }
    }

    /**
     * A text area whose append method makes a security check to see that no bad words are added.
     */
    public static class WordCheckTextArea extends JTextArea {
        @Override
        public void append(String text) {
            WordCheckPermission p = new WordCheckPermission(text, "insert");
            SecurityManager manager = System.getSecurityManager();
            if (manager != null) manager.checkPermission(p);
            super.append(text);
        }
    }
}
