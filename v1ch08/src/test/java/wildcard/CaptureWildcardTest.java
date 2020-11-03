package wildcard;

import java.util.ArrayList;

/**
 * 8.8.4 通配符捕获
 * Created by shucheng on 10/20/2020 9:04 PM
 */
public class CaptureWildcardTest {

    public static <T> void swapHelper(Pair<T> p) {
        T t = p.getFirst();
        p.setFirst(p.getSecond());
        p.setSecond(t);
    }

    public static <T> void test(ArrayList<Pair<T>> pairs) {}

    public static void main(String[] args) {
        Pair<?> pair = new Pair<>();
        swapHelper(pair);

        ArrayList<Pair<?>> pairs = new ArrayList<>();
        /**
         * 下面这个调用是会报错的
         * ArrayList<Pair<T>>中的T永远不能捕获ArrayList<Pair<?>>中的通配符
         */
        // test(pairs);
    }
}
