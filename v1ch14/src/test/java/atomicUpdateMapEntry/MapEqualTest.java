package atomicUpdateMapEntry;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Map的equals方法
 * 只要两个map中相同的key有相同的value，这两个map的equals为true
 * Created by shucheng on 2020/11/1 20:34
 */
public class MapEqualTest {

    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();
        map.put("aa", 11);
        map.put("bb", 2);
        map.put("cc", 3);

        Map<String, Integer> map2 = new ConcurrentHashMap<>();
        map2.put("aa", 11);
        map2.put("bb", 2);
        map2.put("cc", 3);

        System.out.println(map.equals(map2)); // true
    }
}
