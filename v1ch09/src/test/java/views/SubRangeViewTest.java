package views;

import java.util.*;

/**
 * 9.4.2 子范围
 * Created by shucheng on 10/27/2020 9:23 PM
 */
public class SubRangeViewTest {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(i);
        }
        // 取出列表的第10-19个元素（从0开始计算）
        System.out.println(list.subList(10, 20));

        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        SortedSet<String> sortedSet = new TreeSet<>();
        for (int i = 0; i < 26; i++) {
            char c = str.charAt(i);
            String s = new String(new char[] {c});
            sortedSet.add(s);
        }
        // 取出Set中从D（含）到P（不含）的所有元素
        System.out.println(sortedSet.subSet("D", "P"));

        SortedMap<String, Integer> sortedMap = new TreeMap<>();
        for (int i = 0; i < 26; i++) {
            char c = str.charAt(i);
            String s = new String(new char[] {c});
            sortedMap.put(s, i + 1);
        }
        // 返回映射视图，该映射包含键落在D（含）到P（不含）范围内的所有元素
        System.out.println(sortedMap.subMap("D", "P"));
    }
}
