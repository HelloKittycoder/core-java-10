package rsa;

import util.Util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.*;
import java.security.*;

/**
 * 9.5.4 公共密钥密码
 * This program tests the RST cipher. Usage:
 * java rsa.RSATest -genkey public private
 * java rsa.RSATest -encrypt plaintext encrypted public
 * java rsa.RSATest -decrypt encrypted decrypted private
 *
 * 使用示例：
 * 进入target/test-classes目录，执行以下命令
 * # 生成公钥和私钥
 * java rsa.RSATest -genkey public_key.txt private_key.txt
 * # 用公钥对文件进行加密
 * java rsa.RSATest -encrypt "F:\mytest\aaa.pdf" "F:\mytest\aaa_encrypted.pdf" public_key.txt
 * # 用私钥对加密的文件进行解密
 * java rsa.RSATest -decrypt "F:\mytest\aaa_encrypted.pdf" "F:\mytest\aaa_decrypted.pdf" private_key.txt
 *
 * Created by shucheng on 2020/10/8 15:43
 */
public class RSATest {

    public static final int KEYSIZE = 512;

    public static void main(String[] args) throws IOException, GeneralSecurityException, ClassNotFoundException {
        if (args[0].equals("-genkey")) {
            KeyPairGenerator pairgen = KeyPairGenerator.getInstance("RSA");
            SecureRandom random = new SecureRandom();
            pairgen.initialize(KEYSIZE, random);
            KeyPair keyPair = pairgen.generateKeyPair();
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(args[1]))) {
                out.writeObject(keyPair.getPublic());
            }
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(args[2]))) {
                out.writeObject(keyPair.getPrivate());
            }
        } else if (args[0].equals("-encrypt")) {
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            SecureRandom random = new SecureRandom();
            keygen.init(random);
            SecretKey key = keygen.generateKey();

            // wrap with RSA public key
            try (ObjectInputStream keyIn = new ObjectInputStream(new FileInputStream(args[3]));
                DataOutputStream out = new DataOutputStream(new FileOutputStream(args[2]));
                InputStream in = new FileInputStream(args[1])) {
                Key publicKey = (Key) keyIn.readObject();
                Cipher cipher = Cipher.getInstance("RSA");
                cipher.init(Cipher.WRAP_MODE, publicKey);
                byte[] wrappedKey = cipher.wrap(key);
                out.writeInt(wrappedKey.length);
                out.write(wrappedKey);

                cipher = Cipher.getInstance("AES");
                cipher.init(Cipher.ENCRYPT_MODE, key);
                Util.crypt(in, out, cipher);
            }
        } else {
            try (DataInputStream in = new DataInputStream(new FileInputStream(args[1]));
                ObjectInputStream keyIn = new ObjectInputStream(new FileInputStream(args[3]));
                OutputStream out = new FileOutputStream(args[2])) {
                int length = in.readInt();
                byte[] wrappedKey = new byte[length];
                in.read(wrappedKey, 0, length);

                // unwrap with RSA private key
                Key privateKey = (Key) keyIn.readObject();

                Cipher cipher = Cipher.getInstance("RSA");
                cipher.init(Cipher.UNWRAP_MODE, privateKey);
                Key key = cipher.unwrap(wrappedKey, "AES", Cipher.SECRET_KEY);

                cipher = Cipher.getInstance("AES");
                cipher.init(Cipher.DECRYPT_MODE, key);

                Util.crypt(in, out, cipher);
            }
        }
    }
}
