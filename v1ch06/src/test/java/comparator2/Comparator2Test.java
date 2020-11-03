package comparator2;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 6.3.8 再谈Comparator
 * Created by shucheng on 2020/10/10 18:20
 */
public class Comparator2Test {

    // 准备数据
    public List<Person> generateData(boolean needNull) {
        List<Person> list = new ArrayList<>();
        list.add(new Person("Steven", "K_ing"));
        list.add(new Person("Neena", "Kochhar"));
        list.add(new Person("Heena", "Kochhar"));
        list.add(new Person("Lex", "De Haan"));
        list.add(new Person("Alexander", "Hunold"));
        list.add(new Person("Bruce", "Ernst"));
        list.add(new Person("David", "Austin"));
        list.add(new Person("Valli", "Pataballa"));
        if (needNull) {
            list.add(new Person("Liz", null));
        }
        return list;
    }

    /**
     * 对lastName进行升序排序（测试不带null值的排序）
     */
    @Test
    public void test() {
        List<Person> list = generateData(false);
        Collections.sort(list, Comparator.comparing(Person::getLastName));
        System.out.println(list);
    }

    /**
     * 对lastName进行降序排序（测试不带null值的排序）
     */
    @Test
    public void test2() {
        List<Person> list = generateData(false);
        Collections.sort(list, Comparator.comparing(Person::getLastName).reversed());
        System.out.println(list);
    }

    /**
     * 对lastName进行升序排序（测试带null值的排序）
     */
    @Test
    public void test3() {
        List<Person> list = generateData(true);
        Collections.sort(list, Comparator.comparing(Person::getLastName, Comparator.nullsFirst(Comparator.naturalOrder())));
        System.out.println(list);
    }

    /**
     * 对多个字段排序（数据不带null，对lastName和firstName先后都升序排列）
     */
    @Test
    public void test4() {
        List<Person> list = generateData(false);
        Collections.sort(list, Comparator.comparing(Person::getLastName)
                .thenComparing(Person::getFirstName));
        System.out.println(list);
    }
}