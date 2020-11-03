package views;

import java.util.*;

/**
 * 9.4.1 轻量级集合包装器
 * Created by shucheng on 2020/10/27 18:16
 */
public class CollectionWrapper {

    public static void main(String[] args) {
        // 这里返回的对象不是ArrayList
        List<String> names = Arrays.asList("Amy", "Bob", "Carl");
        System.out.println(names);

        /**
         * nCopies方法将返回一个实现了List接口的不可修改的对象，并给人一种包含n个元素，每个元素都像是一个anObject错觉
         * 说明：nCopies返回的List，实际只存了1份，只会在真正调用（使用for循环、toString方法）时会变成n份
         */
        List<String> settings = Collections.nCopies(10, "DEFAULT");
        System.out.println(settings);

        /**
         * 返回了一个不可修改的单元素集
         */
        Set<String> singleton = Collections.singleton("11");
        // 下面的add方法无法正常调用
        // singleton.add("22");
        System.out.println(singleton);

        // 返回一个仅包含指定对象的不可变列表
        List<String> singletonList = Collections.singletonList("E");
        System.out.println(singletonList);

        // 返回一个不可修改的空集
        Set<String> emptySet = Collections.emptySet();
        System.out.println(emptySet);

        // 返回一个不可修改的空List
        List<String> emptyList = Collections.emptyList();
        System.out.println(emptyList);

        // 返回一个不可修改的空Map
        Map<String, String> emptyMap = Collections.emptyMap();
        System.out.println(emptyMap);
    }
}