package randomAccess;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created by shucheng on 2020/9/17 20:54
 */
public class DataIO {

    // 读取固定长度的字符，如果达不到size，则有多少读多少
    public static String readFixedString(int size, DataInput in)
            throws IOException {
        StringBuilder b = new StringBuilder(size);
        int i = 0;
        boolean more = true;
        while (more && i < size) {
            char ch = in.readChar();
            i++;
            if (ch == 0) more = false;
            else b.append(ch);
        }
        /**
         * 这段可不是随便写的，好好理解下Employee实体类中各字段
         * 占用的字节数量，画个图，就很容易理解了
         */
        in.skipBytes(2 * (size - i));
        return b.toString();
    }

    // 写出固定长度的字符，不足部分用0补齐
    public static void writeFixedString(String s, int size, DataOutput out)
            throws IOException {
        for (int i = 0; i < size; i++) {
            char ch = 0;
            if (i < s.length()) ch = s.charAt(i);
            out.writeChar(ch);
        }
    }
}
