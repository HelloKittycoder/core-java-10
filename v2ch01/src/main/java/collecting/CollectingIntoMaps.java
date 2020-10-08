package collecting;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 1.9 收集到映射表中
 * Created by shucheng on 2020/9/11 13:06
 */
public class CollectingIntoMaps {

    public static void main(String[] args) {
        Map<Integer, String> idToName = people().collect(Collectors.toMap(Person::getId, Person::getName));
        System.out.println("idToName: " + idToName);

        Map<Integer, Person> idToPerson = people().collect(Collectors.toMap(Person::getId, Function.identity()));
        System.out.println("idToPerson: " + idToPerson.getClass().getName() + idToPerson);

        idToPerson = people().collect(Collectors.toMap(Person::getId, Function.identity(),
                (existingValue, newValue) -> {
                    throw new IllegalStateException();
                    // System.out.println(existingValue + "==" + newValue);
                    // return existingValue;
                }, TreeMap::new));
        System.out.println("idToPerson: " + idToPerson.getClass().getName() + idToPerson);

        Stream<Locale> locales = Stream.of(Locale.getAvailableLocales());
        Map<String, String> languageNames = locales.collect(
                Collectors.toMap(Locale::getDisplayLanguage, l -> l.getDisplayLanguage(l),
                (existingValue, newValue) -> existingValue));
        System.out.println("languageNames: " + languageNames);

        locales = Stream.of(Locale.getAvailableLocales());
        // 如果出现相同key下有多个value的情况，则把这多个value放到一个Set中
        Map<String, Set<String>> countryLanguageSets = locales.collect(Collectors.toMap(Locale::getDisplayCountry,
                l -> Collections.singleton(l.getDisplayLanguage()),
                (a, b) -> {
                    Set<String> union = new HashSet<>(a);
                    union.addAll(b);
                    return union;
                }));
        System.out.println("countryLanguageSets: " + countryLanguageSets);
    }

    public static Stream<Person> people() {
        // Person p = new Person(1001, "Peter");
        // 验证带有相同key时，对应多个实体类时的处理策略，取新的还是保持原有的或者其他情况
        /*Stream.of(new Person(1001, "Peter"), new Person(1001, "Peter2"), new Person(1002, "Paul"),
                new Person(1003, "Mary"));*/
        return Stream.of(new Person(1001, "Peter"), new Person(1002, "Paul"),
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
