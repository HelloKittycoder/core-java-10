package serviceLoader;

import java.io.UnsupportedEncodingException;
import java.util.ServiceLoader;

/**
 * 13.3 服务加载（P596）
 * Created by shucheng on 2020/10/29 16:46
 */
public class ServiceLoaderTest {

    public static ServiceLoader<Cipher> cipherLoader = ServiceLoader.load(Cipher.class);

    public static void main(String[] args) throws UnsupportedEncodingException {
        Cipher cipher = getCipher(1);
        String message = "Meet me at the toga party.";
        byte[] bytes = cipher.encrypt(message.getBytes(), new byte[]{3});
        String encrypted = new String(bytes, "UTF-8");
        System.out.println(encrypted);
    }

    public static Cipher getCipher(int minStrength) {
        for (Cipher cipher : cipherLoader) {
            // .rm Implicitly calls iterator
            if (cipher.strength() >= minStrength) return cipher;
        }
        return null;
    }
}