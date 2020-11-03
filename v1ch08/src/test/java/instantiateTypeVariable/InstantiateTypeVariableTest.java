package instantiateTypeVariable;

import org.junit.Test;

/**
 * 8.6.5 不能实例化类型变量（P323）
 * Created by shucheng on 2020/10/15 9:43
 */
public class InstantiateTypeVariableTest {

    @Test
    public void test() {
        /*Pair<String> p = Pair.makePair(() -> {
            return new String();
        });*/
        Pair<String> p = Pair.makePair(String::new);
        System.out.println(p);
    }

    @Test
    public void test2() {
        Pair<String> p = Pair.makePair(String.class);
        System.out.println(p);
    }
}