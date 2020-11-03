package concurrentHashSet;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 14.7.4 并发集视图
 * 并发包里并没有ConcurrentHashSet类，但是可以使用ConcurrentHashMap的静态newKeySet方法生成一个Set<K>，
 * 这实际上是ConcurrentHashMap<K, Boolean>的一个包装器（所有值都为Boolean.TRUE）
 *
 * Created by shucheng on 2020/11/1 22:05
 */
public class ConcurrentHashSetTest {

    public static void main(String[] args) {
        // 方法1：使用ConcurrentHashMap的newKeySet方法生成一个Set<K>
        Set<String> words = ConcurrentHashMap.<String>newKeySet();
        words.add("C++");
        words.add("java");
        System.out.println(words);

        /**
         * 方法2：如果是用一个已有的ConcurrentHashMap来生成Set的话，需要提供一个默认的映射值，比如为1，
         * 这时写成map.keySet(1)，表示后续在map中添加的key都映射到1上
         *
         * 注意：这里不能写map.keySet()，如果这样写的话，返回的Set将只能进行删除操作，但无法进行add操作
         */
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap();
        map.put("aa", 1);
        Set<String> words2 = map.keySet(1);
        words2.add("Java");
        words2.add("C++");
        System.out.println(words2);
    }
}
