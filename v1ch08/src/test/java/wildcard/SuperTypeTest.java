package wildcard;

import java.util.ArrayList;
import java.util.function.Predicate;

/**
 * 8.8.2 通配符的超类型限定
 * Created by shucheng on 10/20/2020 8:45 PM
 */
public class SuperTypeTest {

    public static void main(String[] args) {
        ArrayList<Employee> staff = new ArrayList<>();
        Predicate<Object> oddHashCode = obj -> obj.hashCode() %2 != 0;
        staff.removeIf(oddHashCode);
    }
}
