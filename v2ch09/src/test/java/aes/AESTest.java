package aes;

import util.Util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.*;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.SecureRandom;

/**
 * 9.5.2 密钥生成
 * This program tests the AES cipher. Usage:
 * java aes.AESTest -genkey keyfile
 * java aes.AESTest -encrypt plaintext encrypted keyfile
 * java aes.AESTest -decrypt encrypted decrypted keyfile
 *
 * 使用示例：
 * 进入target/test-classes目录，执行以下命令
 * # 生成密钥文件
 * java aes.AESTest -genkey keyfile.txt
 * # 使用给定的密钥对文件进行加密
 * java aes.AESTest -encrypt "F:\mytest\aaa.pdf" "F:\mytest\aaa_encrypted.pdf" keyfile.txt
 * # 使用给定的密钥对加密后的文件解密
 * java aes.AESTest -decrypt "F:\mytest\aaa_encrypted.pdf" "F:\mytest\aaa_decrypted.pdf" keyfile.txt
 *
 * Created by shucheng on 2020/10/8 15:00
 */
public class AESTest {

    public static void main(String[] args) throws IOException, GeneralSecurityException, ClassNotFoundException {
        if (args[0].equals("-genkey")) {
            KeyGenerator kengen = KeyGenerator.getInstance("AES");
            SecureRandom random = new SecureRandom();
            kengen.init(random);
            SecretKey key = kengen.generateKey();
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(args[1]))) {
                out.writeObject(key);
            }
        } else {
            int mode;
            if (args[0].equals("-encrypt")) mode = Cipher.ENCRYPT_MODE;
            else mode = Cipher.DECRYPT_MODE;

            try (ObjectInputStream keyIn = new ObjectInputStream(new FileInputStream(args[3]));
                InputStream in = new FileInputStream(args[1]);
                OutputStream out = new FileOutputStream(args[2])) {
                Key key = (Key) keyIn.readObject();
                Cipher cipher = Cipher.getInstance("AES");
                cipher.init(mode, key);
                Util.crypt(in, out, cipher);
            }
        }
    }
}
