package map;

import java.util.HashMap;
import java.util.Map;

/**
 * 9.3.1 基本映射操作（P372）
 * This program demonstrates the use of a map with key type String and value type Employee.
 *
 * P374中提到put方法中，键可以为null，但值不能为null。经测试HashMap的key、value都能为null，这处估计有问题
 *
 * Created by shucheng on 10/26/2020 9:26 PM
 */
public class MapTest {

    public static void main(String[] args) {
        Map<String, Employee> staff = new HashMap<>();
        staff.put("144-25-5464", new Employee("Amy Lee"));
        staff.put("567-24-2546", new Employee("Harry Hacker"));
        staff.put("157-62-7935", new Employee("Gary Cooper"));
        staff.put("456-62-5527", new Employee("Francesca Cruz"));

        // print all entris
        System.out.println(staff);

        // remove an entry
        staff.remove("567-24-2546");

        // replace an entry
        staff.put("456-62-5527", new Employee("Francesca Miller"));

        // look up a value
        System.out.println(staff.get("157-62-7935"));

        // iterate through all entries
        staff.forEach((k, v) -> System.out.println("key=" + k + ", value=" + v));
    }
}
