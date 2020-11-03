package views;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 9.4.3 不可修改的视图
 * Created by shucheng on 10/27/2020 10:14 PM
 */
public class UnmodifiableViewTest {

    public static void main(String[] args) throws ReflectiveOperationException {
        List<String> list = new ArrayList<>();
        list.add("11");
        list.add("22");
        list.add("33");
        List<String> unmodifiableList = Collections.unmodifiableList(list);
        System.out.println(unmodifiableList);
        // 这里无法直接调用add方法，若调用的话，会报错
        // unmodifiableList.add("kk");

        // 这里可以通过反射获取到原始的list，然后进行操作
        Field listField = unmodifiableList.getClass().getSuperclass().getDeclaredField("list");
        listField.setAccessible(true);
        List<String> o = (List<String>) listField.get(unmodifiableList);
        System.out.println(o == list);
        o.set(0, "kk");
        System.out.println(o);
    }
}
