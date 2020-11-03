package iterator;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * 9.1.3 迭代器
 *
 * 有关forEach与forEachRemaining的区别详见
 * https://blog.csdn.net/qq_37499840/article/details/90477187
 *
 * Created by shucheng on 10/21/2020 10:36 PM
 */
public class IteratorTest {

    // 使用迭代器遍历元素
    @Test
    public void test() {
        Collection<String> collection = new ArrayList<>();
        collection.add("11");
        collection.add("22");
        collection.add("33");

        Iterator<String> iter = collection.iterator();
        System.out.println("使用Iterator来遍历");
        while (iter.hasNext()) {
            String element = iter.next();
            System.out.println(element);
        }

        System.out.println("使用foreach来遍历");
        for (String str : collection) {
            System.out.println(str);
        }
    }

    /**
     * 使用迭代器删除元素
     * 关键点：在使用Iterator时，要移除该元素前，必须先调用一次next越过该元素，
     * 然后再调用remove移除该元素
     * 如果不这样做，将会抛出一个IllegalStateExceptio异常
     */
    @Test
    public void test2() {
        // 使用迭代器删除第1个元素（P348）
        Collection<String> collection = new ArrayList<>();
        collection.add("11");
        collection.add("22");
        collection.add("33");
        System.out.println(collection); // [11, 22, 33]

        Iterator<String> iter = collection.iterator();
        iter.next();
        iter.remove();
        System.out.println(collection); // [22, 33]

        // 使用迭代器删除两个相邻的元素（P349）
        Collection<String> collection2 = new ArrayList<>();
        collection2.add("11");
        collection2.add("22");
        collection2.add("33");
        System.out.println(collection2); // [11, 22, 33]

        Iterator<String> iter2 = collection2.iterator();
        iter2.next();
        iter2.remove();
        iter2.next();
        iter2.remove();
        System.out.println(collection2); // [33]
    }
}
