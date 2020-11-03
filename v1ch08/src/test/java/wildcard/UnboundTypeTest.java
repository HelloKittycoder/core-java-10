package wildcard;

/**
 * 8.8.3 无限定通配符
 * 这一节看着内容比较少，但里面有些地方还是需要理解下
 * Created by shucheng on 10/20/2020 8:50 PM
 */
public class UnboundTypeTest {

    private static Pair<?> pair = new Pair<>();

    public static boolean hasNulls(Pair<?> p) {
        return p.getFirst() == null || p.getSecond() == null;
    }

    public static void main(String[] args) {
        /**
         * 这里只能赋值给Object
         * 下面的getFirst方法实际是：public ? getFirst()
         */
        Object first = pair.getFirst();
        /**
         * 下面setFirst方法没有办法调用，最多可以调 pair.setFirst(null);
         * setFirst方法实际是：public void setFirst(? first)
         */
        // pair.setFirst();
        pair.setFirst(null);
    }
}
