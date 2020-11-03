package views;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 9.4.5 受查视图
 * Created by shucheng on 2020/10/28 18:30
 */
public class CheckViewTest {

    // 不使用受查视图的情况
    @Test(expected = ClassCastException.class)
    public void test() {
        ArrayList<String> strings = new ArrayList<>();
        ArrayList rawList = strings;
        // 在进行add操作时不会报错，在调用get转换成String类型时会报ClassCastException
        rawList.add(new Date());
        String str = strings.get(0);
        System.out.println(str);
    }

    // 使用受查视图的情况
    @Test(expected = ClassCastException.class)
    public void test2() {
        ArrayList<String> strings = new ArrayList<>();
        List<String> safeStrings = Collections.checkedList(strings, String.class);
        List rawList = safeStrings;
        // 在进行add操作时会报错（好处是把报错的时机前置了，有助于排查错误）
        rawList.add(new Date());
    }
}