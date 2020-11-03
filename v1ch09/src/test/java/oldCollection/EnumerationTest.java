package oldCollection;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

/**
 * 9.6.2 枚举（P397）
 * Created by shucheng on 2020/10/28 23:10
 */
public class EnumerationTest {

    @Test
    public void test3() {
        List<String> list = new ArrayList<>();
        list.add("11");
        list.add("33");
        list.add("22");

        Enumeration<String> enumeration = Collections.enumeration(list);
        while (enumeration.hasMoreElements()) {
            String s = enumeration.nextElement();
            System.out.println(s);
        }
    }
}
