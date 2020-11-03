package constructGenericArray;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 8.6.6 不能构造泛型数组（P323）
 * Created by shucheng on 2020/10/15 10:14
 */
public class ConstructGenericArrayTest {

    @Test(expected = ClassCastException.class)
    public void test() {
        String[] ss = ArrayAlg.minmax("Tom", "Dick", "Harry");
        System.out.println(ss);
    }

    @Test
    public void test2() {
        String[] ss = ArrayAlg.minmax2(String[]::new, "Tom", "Dick", "Harry");
        System.out.println(ss);
    }

    @Test
    public void test3() {
        String[] ss = ArrayAlg.minmax3("Tom", "Dick", "Harry");
        System.out.println(ss);
    }

    @Test
    public void test4() {
        List<String> list = new ArrayList<>();
        list.add("Tom");
        list.add("Dick");
        list.add("Harry");
        String[] ss = ArrayAlg.minmax4(list);
        System.out.println(ss);
        // Comparable[].class.isAssignableFrom(String[].class) 结果为true
        /*Comparable[] aa = ss;
        System.out.println(aa);*/
    }
}