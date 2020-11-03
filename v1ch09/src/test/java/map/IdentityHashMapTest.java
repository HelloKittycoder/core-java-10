package map;

import org.junit.Test;

import java.util.HashMap;
import java.util.IdentityHashMap;

/**
 * 9.3.7 标识散列映射
 * 参考链接：https://blog.csdn.net/f641385712/article/details/81880711
 *
 * 简单解释下测试结果：HashMap为1；IdentityHashMap为3
 *
 * String重写了hashCode和equals方法，equals比较的是String中的字符内容，hashCode是根据字符内容计算出来的
 * 可以推断出，两个String（如：abcd和abcd）内容相同，equals为true，hashCode为true（也就是equals为true->hashCode相同）
 * 反过来不行，比如：两个String（ab和bC），hashCode相同，但是equals为false
 *
 * 下面的两个测试中放的是new String("a")，这里key的内存地址不同，但是hashCode为true，equals为true
 * 说明：
 * 对于HashMap，key都是一样的（判定标准：hashCode为true，equals为true，就是直接覆盖掉该key对应的value），最终只会保留最后一次放入的value；
 * 这里只是覆盖value，并不覆盖key（可以通过打断点查看map.keySet().toArray()[0]==a1，当test方法中执行3次put操作时始终都是true，说明key始终保留的是对a1的引用）
 *
 * 对于IdentityHashMap，key比较的是内存地址（源码中使用的方法是System.identityHashCode），显然这三者的内存地址不同，所以size为3
 *
 * Created by shucheng on 2020/10/27 16:59
 */
public class IdentityHashMapTest {

    // 找出所有的两个英文字母的字符串中和指定hashCode相等的字符串
    @Test
    public void testStr() {

        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        char[] chArr = new char[2];
        for (int i = 0; i < str.length(); i++) {
            char c1 = str.charAt(i);
            for (int j = 0; j < str.length(); j++) {
                char c2 = str.charAt(j);

                chArr[0] = c1;
                chArr[1] = c2;
                String string = new String(chArr);
                if (string.hashCode() == 3105) {
                    System.out.println(string);
                }
            }
        }
    }

    @Test
    public void test() {
        HashMap<String, String> map = new HashMap<>();

        String a1 = new String("a");
        String a2 = new String("a");
        String a3 = new String("a");

        map.put(a1, "1");
        map.put(a2, "2");
        map.put(a3, "3");
        System.out.println(map.size()); // 1
    }

    @Test
    public void test2() {
        IdentityHashMap<String, String> map = new IdentityHashMap<>();

        String a1 = new String("a");
        String a2 = new String("a");
        String a3 = new String("a");

        map.put(a1, "1");
        map.put(a2, "2");
        map.put(a3, "3");
        System.out.println(map.size()); // 3
    }
}