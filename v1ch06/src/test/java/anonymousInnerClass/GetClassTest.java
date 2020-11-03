package anonymousInnerClass;

import org.junit.Test;

/**
 * 获取当前类的类名
 * Created by shucheng on 2020/10/13 12:18
 */
public class GetClassTest {

    @Test
    public void test() {
        System.out.println("invoke test() in " + getClass());
    }

    @Test
    public void test2() {
        staticMethod();
    }

    // 这里主要看下在静态方法中如何获取当前class
    public static void staticMethod() {
        System.out.println("invoke staticMethod() in " + new Object(){}.getClass().getEnclosingClass());
    }
}