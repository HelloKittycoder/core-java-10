package autobox;

import org.junit.Test;
import org.omg.CORBA.IntHolder;

import java.util.ArrayList;

/**
 * 5.4 对象包装器与自动装箱
 *
 * Created by shucheng on 2020/10/9 17:01
 */
public class AutoBoxTest {

    // 装箱与拆箱
    @Test
    public void test() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(3); // 等效于 list.add(Integer.valueOf(3)); “装箱”（boxing）

        int n = list.get(0); // 等效于 list.get(0).intValue(); “拆箱”（unboxing）
        System.out.println(n);

        System.out.println(list);
    }

    /**
     * 测试值传递（P186）
     *
     * 简单说下：
     * triple写int不能改掉x的值，是因为java方法都是值传递
     * triple写Integer不能改掉x的值，是因为Integer对象是不可变的（包含在包装器中的内容不会改变）
     */
    @Test
    public void test2() {
        int a = 2;
        triple(a); // triple方法无论写int还是Integer，都没有办法修改x的值
        System.out.println(a);
    }

    public static void triple(int x) {
        x = 3 * x;
    }

    @Test
    public void test3() {
        IntHolder a = new IntHolder(2);
        triple(a);
        System.out.println(a.value);
    }

    public static void triple(IntHolder x) {
        x.value = 3 * x.value;
    }
}