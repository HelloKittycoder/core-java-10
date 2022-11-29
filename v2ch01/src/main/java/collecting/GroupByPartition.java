package collecting;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 1.10 群组和分区（译成“分组和分区”可能会更好点）
 * Created by shucheng on 2020/9/14 22:51
 */
public class GroupByPartition {

    public static void main(String[] args) {
        Stream<Locale> locales = Stream.of(Locale.getAvailableLocales());
        // 获取给定国家代码对应的所有语言代码
        Map<String, List<Locale>> countryToLocales = locales.collect(Collectors.groupingBy(Locale::getCountry));
        System.out.println("countryToLocales: " + countryToLocales);

        locales = Stream.of(Locale.getAvailableLocales());
        // 将使用英语和所有其他语言的分成两类
        Map<Boolean, List<Locale>> englishAndOtherLocales = locales.collect(
                Collectors.partitioningBy(l -> l.getLanguage().equals("en")));
        List<Locale> englishLocales = englishAndOtherLocales.get(true);
        System.out.println(englishLocales);

        /**
         * 以下是基于CollectingIntoMaps中处理Person流中，一个key对多个value的改进写法
         * 下面是工作中常用的使用场景：
         */
        Map<Integer, List<Person>> idToPerson = people2().collect(Collectors.groupingBy(Person::getId));
        System.out.println(idToPerson);

        Map<Integer, List<String>> idToName = people2().collect(
                Collectors.groupingBy(Person::getId, Collectors.mapping(Person::getName, Collectors.toList())));
        System.out.println(idToName);
    }

    public static Stream<Person> people2() {
        return Stream.of(new Person(1001, "Peter"), new Person(1001, "Peter2"), new Person(1002, "Paul"),
                new Person(1003, "Mary"));
    }

    public static class Person {
        private int id;
        private String name;

        public Person(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
