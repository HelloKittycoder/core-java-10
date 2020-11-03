package genericMethod;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * 9.1.4 泛型实用方法
 * Created by shucheng on 10/24/2020 10:14 PM
 */
public class GenericMethodTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    // 生成ArrayList
    private <T> List<T> generateList(T... arr) {
        List<T> result = new ArrayList<>();
        if (arr != null && arr.length > 0) {
            for (T t : arr) {
                result.add(t);
            }
        }
        return result;
    }

    // 测试ArrayList
    @Test
    public void test() {
        Collection<String> list = generateList("11", "22", "33");

        logger.info("list.contains(\"11\")->{}", list.contains("11")); // true
        logger.info("list.size()->{}", list.size()); // 3

        Collection<String> list2 = generateList("22", "33");
        // list2.add("44");
        // 说明：现在下面返回true；如果把“44”的注释取消掉，下面返回false
        logger.info("list.containsAll(list2)->{}", list.containsAll(list2)); // true

        Collection<String> list3 = generateList("11", "22", "33");
        logger.info("list.equals(list3)->{}", list.equals(list3)); // true

        Collection<String> list4 = generateList("aa", "bb", "cc");
        Collection<String> list5 = generateList("dd", "ee", "ff");
        // list4.addAll(list5)返回true，list4变成了[aa, bb, cc, dd, ee, ff]
        logger.info("list4.addAll(list5)->{} list4->{}", list4.addAll(list5), list4);

        list5.clear(); // list5变成了[]
        logger.info("list5.clear()后，list5变成了{}", list5);

        Collection<String> list6 = generateList("11", "22", "33", "44");
        list6.remove("44"); // true
        // list6变成了[11, 22, 33]
        logger.info("list6.remove(\"44\")->{} list6->{}", list6.remove("44"), list6);

        Collection<String> list7 = generateList("11", "22", "33", "44");
        Collection<String> list8 = generateList("33", "44");
        // list7.removeAll(list8)返回true，list7变成了[11, 22]
        logger.info("list7.removeAll(list8)->{} list7->{}", list7.removeAll(list8), list7);

        Collection<String> list9 = generateList("11", "22", "33", "44");
        Collection<String> list10 = generateList("33", "44", "55", "66");
        // list9.retainAll(list10)返回true，list9变成了[33, 44]，list10无变化
        logger.info("list9.retainAll(list10)->{} list9->{} list10->{}", list9.retainAll(list10), list9, list10);

        Collection<String> list11 = generateList("11", "22", "33", "44");
        Object[] objectArr = list11.toArray(); // 注：这里的数组类型是Object[]
        logger.info("list11.toArray()->{}", Arrays.toString(objectArr));

        String[] strArr = list11.toArray(new String[0]); // 注：这里的数组类型是String[]
        logger.info("list11.toArray(T[])->{}", Arrays.toString(strArr));
    }
}
