package classLoader2;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by shucheng on 2020/10/6 21:07
 */
public class CryptClassLoader extends ClassLoader {

    private int key;

    public CryptClassLoader(int key) {
        this.key = key;
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

    private byte[] loadClassBytes(String name) throws IOException {
        String cname = name.replace('.', '/') + ".secret";
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
