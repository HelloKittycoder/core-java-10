package map;

import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * 9.3.4 弱散列映射
 * https://www.cnblogs.com/skywang12345/p/3311092.html
 *
 * Created by shucheng on 10/27/2020 8:37 AM
 */
public class WeakHashMapTest {

    public static void main(String[] args) {
        // 初始化3个“弱键”
        String w1 = new String("one");
        String w2 = new String("two");
        String w3 = new String("three");

        // 新建WeakHashMap
        Map<String, String> wmap = new WeakHashMap<>();
        // 添加键值对
        wmap.put(w1, "w1");
        wmap.put(w2, "w2");
        wmap.put(w3, "w3");

        // 打印出wmap
        System.out.printf("wmap:%s\n", wmap);

        // containsKey(Object key)：是否包含键key
        System.out.printf("contains key two: %s\n", wmap.containsKey("two"));
        System.out.printf("contains key two: %s\n", wmap.containsKey("five"));

        // containsValue(Object value)：是否包含值value
        System.out.printf("contains value 0: %s\n", wmap.containsValue(new Integer(0)));

        // remove(Object key): 删除键key对应的键值对
        wmap.remove("three");
        System.out.printf("wmap: %s\n", wmap);

        // ---- 测试WeakHashMap的自动回收特性 ----
        // 将w1设置null
        // 这意味着“弱键”w1再没有被其他对象引用，调用gc时会回收WeakHashMap中与“w1”对应的键值对
        w1 = null;
        // 内存回收。这里，会回收WeakHashMap中与“w1”对应的键值对
        System.gc();

        // 遍历WeakHashMap
        Iterator<Map.Entry<String, String>> iter = wmap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, String> entry = iter.next();
            System.out.printf("next: %s - %s\n", entry.getKey(), entry.getValue());
        }
        // 打印WeakHashMap的实际大小
        System.out.printf("after gc WeakHashMap size:%s\n", wmap.size());
    }
}
