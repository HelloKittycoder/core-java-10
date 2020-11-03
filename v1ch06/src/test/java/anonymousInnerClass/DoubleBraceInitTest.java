package anonymousInnerClass;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 双括号初始化（double brace initialization）
 * Created by shucheng on 2020/10/13 11:27
 */
public class DoubleBraceInitTest {

    @Test
    public void test() {
        ArrayList<String> friends = new ArrayList<>();
        friends.add("Harry");
        friends.add("tony");
        invite(friends);
    }

    @Test
    public void test2() {
        ArrayList<String> friends = new ArrayList<String>(){{
            add("Harry");
            add("tony");
        }};
        invite(friends);
    }

    public void invite(List<String> list) {
        System.out.println("调用invite方法，参数为" + list);
    }
}