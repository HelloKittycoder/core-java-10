package serviceLoader;

/**
 * Created by shucheng on 2020/10/29 16:47
 */
public interface Cipher {

    byte[] encrypt(byte[] source, byte[] key);
    byte[] decrypt(byte[] source, byte[] key);
    int strength();
}