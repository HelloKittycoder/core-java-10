package classLoader;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 9.1.4 编写你自己的类加载器
 * 这个只是照着书里的代码敲了一遍，没有图9-3（P405）的效果
 * This program demonstrates a custom class loader that decrypts class files
 * Created by shucheng on 2020/10/3 20:46
 */
public class ClassLoaderTest {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame frame = new ClassLoaderFrame();
            frame.setTitle("ClassLoaderTest");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }

    /**
     * This frame contains two text fields for the name of the class to load and the decryption key.
     * Created by shucheng on 2020/10/3 20:48
     */
    public static class ClassLoaderFrame extends JFrame {

        private JTextField keyField = new JTextField("3", 4);
        private JTextField nameField = new JTextField("Calculator", 30);
        public static final int DEFAULT_WIDTH = 300;
        public static final int DEFAULT_HEIGHT = 200;

        public ClassLoaderFrame() {
            setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
            setLayout(new GridBagLayout());
            add(new JLabel("Class"), new GBC(0, 0).setAnchor(GBC.EAST));
            add(nameField, new GBC(1, 0).setWeight(100, 0).setAnchor(GBC.WEST));
            add(new JLabel("Key"), new GBC(0, 1).setAnchor(GBC.EAST));
            JButton loadButton = new JButton("Load");
            add(loadButton, new GBC(0, 2, 2, 1));
            loadButton.addActionListener(event -> runClass(nameField.getText(), keyField.getText()));
            pack();
        }

        /**
         * Runs the main method of a given class.
         * @param name the class name
         * @param key the decryption key for the class files
         */
        public void runClass(String name, String key) {
            try {
                ClassLoader loader = new CryptoClassLoader(Integer.parseInt(key));
                Class<?> c = loader.loadClass(name);
                Method m = c.getMethod("main", String[].class);
                m.invoke(null, (Object) new String[]{});
            } catch (Throwable e) {
                JOptionPane.showMessageDialog(this, e);
            }
        }
    }

    /**
     * This class loader loads encrypted class files.
     * Created by shucheng on 2020/10/3 21:28
     */
    public static class CryptoClassLoader extends ClassLoader {

        private int key;

        /**
         * Constructs a crypto class loader.
         * @param k
         */
        public CryptoClassLoader(int k) {
            key = k;
        }

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            try {
                byte[] classBytes = loadClassBytes(name);
                Class<?> cl = defineClass(name, classBytes, 0, classBytes.length);
                if (cl == null) throw new ClassNotFoundException(name);
                return cl;
            } catch (IOException e) {
                throw new ClassNotFoundException(name);
            }
        }

        /**
         * Loads and decrypt the class file bytes.
         * @param name the class name
         * @return an array with the class file bytes
         * @throws IOException
         */
        private byte[] loadClassBytes(String name) throws IOException {
            String cname = name.replace('.', '/') + ".caesar";
            URL resource = ClassLoader.getSystemResource(cname);
            Path path = null;
            try {
                path = Paths.get(resource.toURI());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            byte[] bytes = Files.readAllBytes(path);
            for (int i = 0; i < bytes.length; i++) {
                bytes[i] = (byte) (bytes[i] - key);
            }
            return bytes;
        }
    }
}
