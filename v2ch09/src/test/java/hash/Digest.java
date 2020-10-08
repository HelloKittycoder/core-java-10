package hash;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 9.4.1 消息摘要
 * This program computes the message digest of a file.
 *
 * Created by shucheng on 2020/10/8 13:01
 */
public class Digest {

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        String algname = "SHA-1";
        URL resource = ClassLoader.getSystemResource("hash/input.txt");
        Path filePath = Paths.get(URI.create(resource.toExternalForm()));
        String messageDigestStr = getMessageDigestStr(filePath, algname);
        // 12 5F 09 03 E7 31 30 19 2E A6 E7 E4 90 43 84 B4 38 99 8F 67
        System.out.println(messageDigestStr);
    }

    /**
     * 获取消息摘要
     * @param filePath 文件路径
     * @param algname 算法名称(SHA-1, SHA-256, or MD5)
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    public static String getMessageDigestStr(Path filePath, String algname) throws IOException, NoSuchAlgorithmException {
        MessageDigest alg = MessageDigest.getInstance(algname);
        byte[] input = Files.readAllBytes(filePath);
        byte[] hash = alg.digest(input);
        String d = "";
        for (int i = 0; i < hash.length; i++) {
            int v = hash[i] & 0xFF; // 这里是为了取低8位
            if (v < 16) d += "0";
            d += Integer.toString(v, 16).toUpperCase() + " ";
        }
        return d;
    }
}
