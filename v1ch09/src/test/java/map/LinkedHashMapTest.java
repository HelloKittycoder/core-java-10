package map;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 9.3.5 链接散列集与映射
 * Created by shucheng on 2020/10/27 16:55
 */
public class LinkedHashMapTest {

    public static void main(String[] args) {
        Map<String, Employee> staff = new LinkedHashMap<>();
        staff.put("144-25-5464", new Employee("Amy Lee"));
        staff.put("567-24-2546", new Employee("Harry Hacker"));
        staff.put("157-62-7935", new Employee("Gary Cooper"));
        staff.put("456-62-5527", new Employee("Francesca Cruz"));

        // print all entris
        System.out.println(staff);
    }
}