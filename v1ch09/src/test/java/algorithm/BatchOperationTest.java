package algorithm;

import org.junit.Test;

import java.util.*;

/**
 * 9.5.4 批操作（P394）
 * Created by shucheng on 2020/10/28 22:39
 */
public class BatchOperationTest {

    @Test
    public void test() {
        Set<String> s1 = new HashSet<>();
        s1.add("11");
        s1.add("22");
        s1.add("33");


        Set<String> s2 = new HashSet<>();
        s2.add("11");
        s2.add("22");
        s2.add("aa");

        s1.removeAll(s2);
        System.out.println(s1); // [33]
    }

    @Test
    public void test2() {
        Set<String> s1 = new HashSet<>();
        s1.add("11");
        s1.add("22");
        s1.add("33");


        Set<String> s2 = new HashSet<>();
        s2.add("11");
        s2.add("22");
        s2.add("aa");

        s1.retainAll(s2);
        System.out.println(s1); // [11, 22]
    }

    @Test
    public void test3() {
        Map<String, String> map = new HashMap<>();
        map.put("aa", "11");
        map.put("bb", "22");
        map.put("cc", "33");
        System.out.println(map);

        Set<String> needToRemoveKeys = new HashSet<>();
        needToRemoveKeys.add("aa");
        needToRemoveKeys.add("bb");

        // 通过操作map对应的keySet来删掉map的键值对
        Set<String> strings = map.keySet();
        strings.removeAll(needToRemoveKeys);
        System.out.println(map);
    }

    @Test
    public void test4() {
        List<String> list = new ArrayList<>();
        list.add("00");
        list.add("11");
        list.add("22");
        list.add("33");
        list.add("44");

        List<String> relocated = new ArrayList<>();
        relocated.addAll(list.subList(0, 4));
        System.out.println(relocated); // [00, 11, 22, 33]

        System.out.println(list); // list->[00, 11, 22, 33, 44]
        list.subList(0, 4).clear();
        // 通过list.subList(0, 4)来做clear操作只能影响到原有的list，不会影响新的relocated
        // list->[44], relocated->[00, 11, 22, 33]
        System.out.println("list->" + list + "===relocated->" + relocated);
    }
}
