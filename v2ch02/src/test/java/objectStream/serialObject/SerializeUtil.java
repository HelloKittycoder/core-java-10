package objectStream.serialObject;

import java.io.*;

/**
 * Created by shucheng on 2020/9/22 21:24
 */
public class SerializeUtil {

    // 对象的深拷贝
    public static Object deepCopy(Object object) {
        try {
            // save the object to a byte array
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            try (ObjectOutputStream out = new ObjectOutputStream(bout)) {
                out.writeObject(object);
            }

            // read a clone of the object from the byte array
            try (InputStream bin = new ByteArrayInputStream(bout.toByteArray())) {
                ObjectInputStream in = new ObjectInputStream(bin);
                return in.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
