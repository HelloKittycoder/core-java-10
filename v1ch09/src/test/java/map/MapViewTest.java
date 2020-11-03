package map;

import org.junit.Test;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 9.3.3 映射视图
 * Created by shucheng on 10/26/2020 10:14 PM
 */
public class MapViewTest {

    private Map<String, String> getMap() {
        Map<String, String> map = new HashMap<>();
        map.put("aa", "11");
        map.put("bb", "22");
        map.put("cc", "33");
        map.put("dd", "22");
        return map;
    }

    @Test
    public void test() {
        Map<String, String> map = getMap();

        // 获取键集
        Set<String> keySet = map.keySet();
        System.out.println(keySet);
        // 注：这里可以对键集进行remove操作（会删掉关联的value），但是不能进行add操作
        /*keySet.remove("cc");
        keySet.add("ee");*/

        // 获取值集合
        Collection<String> values = map.values();
        System.out.println(values);
        // 注：这里可以对值集合进行remove操作（会删掉关联的key），但是不能进行add操作
        // remove时如果有多个value，只会删其中一个
        /*values.remove("22");
        values.add("44");*/

        // 获取键/值对集
        Set<Map.Entry<String, String>> entries = map.entrySet();
        System.out.println(entries);
    }

    // 遍历map（一般选用2或3）
    @Test
    public void test2() {
        Map<String, String> map = getMap();
        System.out.println("===========1.通过keySet遍历===========");
        for (String key : map.keySet()) {
            System.out.println(key + "->" + map.get(key));
        }

        System.out.println("===========2.通过entrySet遍历===========");
        for (Map.Entry<String, String> entry: map.entrySet()) {
            System.out.println(entry.getKey() + "->" + entry.getValue());
        }

        System.out.println("===========3.通过forEach遍历===========");
        map.forEach((k, v) -> System.out.println(k + "->" + v));
    }
}
