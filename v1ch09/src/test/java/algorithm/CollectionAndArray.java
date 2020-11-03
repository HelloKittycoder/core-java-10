package algorithm;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 9.5.5 集合与数组的转换
 *
 * 下面只写了Set和Array之间的相互转换，其他Collection的实现和Array之间的相互转换类似
 * Created by shucheng on 2020/10/28 22:55
 */
public class CollectionAndArray {

    // Array->Set
    @Test
    public void test() {
        String[] strArr = {"11", "22", "33"};
        Set<String> set = new HashSet<>(Arrays.asList(strArr));
        System.out.println(set);
    }

    // Set->Array
    @Test
    public void test2() {
        Set<String> set = new HashSet<>();
        set.add("11");
        set.add("22");
        set.add("33");
        // 注意：如果写set.toArray()得到将是Object[]，这个是不能转换为String[]的
        String[] strArr = set.toArray(new String[0]);
        System.out.println(Arrays.toString(strArr));
    }
}
