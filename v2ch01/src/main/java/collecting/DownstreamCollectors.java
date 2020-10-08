package collecting;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

/**
 * 1.11 下游收集器
 * Created by shucheng on 2020/9/14 23:21
 */
public class DownstreamCollectors {

    public static void main(String[] args) throws Exception {
        Stream<Locale> locales = Stream.of(Locale.getAvailableLocales());
        // 获取给定国家代码对应的所有语言代码
        Map<String, Set<Locale>> countryToLocaleSet = locales.collect(groupingBy(Locale::getCountry, toSet()));
        System.out.println("countryToLocaleSet: " + countryToLocaleSet);

        locales = Stream.of(Locale.getAvailableLocales());
        // 获取给定国家代码对应的所有语言代码的数量
        Map<String, Long> countryToLocaleCounts = locales.collect(groupingBy(Locale::getCountry, counting()));
        System.out.println("countryToLocaleCounts: " + countryToLocaleCounts);

        Stream<City> cities = readCities("cities.txt");
        // 获取给定州下所有城市的人口总数
        Map<String, Integer> stateToCityPopulation = cities.collect(groupingBy(City::getState, summingInt(City::getPopulation)));
        System.out.println("stateToCityPopulation: " + stateToCityPopulation);

        cities = readCities("cities.txt");
        // 获取给定州下，所有城市中名字最长的那个城市
        Map<String, Optional<String>> stateToLongestCityName = cities.collect(groupingBy(City::getState,
                mapping(City::getName, maxBy(Comparator.comparing(String::length)))
        ));
        System.out.println("stateToLongestCityName: " + stateToLongestCityName);

        locales = Stream.of(Locale.getAvailableLocales());
        // 获取给定国家使用的所有语言（有多种语言的话，放到Set中）
        Map<String, Set<String>> countryToLanguages = locales.collect(groupingBy(Locale::getDisplayCountry, mapping(Locale::getDisplayLanguage, toSet())));
        System.out.println("countryToLanguages: " + countryToLanguages);

        cities = readCities("cities.txt");
        // 获取给定州下的城市人口统计数据
        Map<String, IntSummaryStatistics> stateToCityPopulationSummary = cities.collect(groupingBy(City::getState,
                summarizingInt(City::getPopulation)));
        System.out.println(stateToCityPopulationSummary.get("NY"));

        // 获取给定州下的所有城市名称，用逗号分隔（使用reducing或joining写都可以）
        cities = readCities("cities.txt");
        Map<String, String> stateToCityNames1 = cities.collect(groupingBy(City::getState,
                reducing("", City::getName, (s, t) -> s.length() == 0 ? t : s + ", " + t)));
        System.out.println("stateToCityNames1: " + stateToCityNames1);

        cities = readCities("cities.txt");
        Map<String, String> stateToCityNames2 = cities.collect(groupingBy(City::getState, mapping(City::getName,
                joining(", "))));
        System.out.println("stateToCityNames2: " + stateToCityNames2);
    }

    public static Stream<City> readCities(String filename) throws Exception {
        // 获取资源文件路径
        // URI uri = Thread.currentThread().getContextClassLoader().getResource("cities.txt").toURI();
        URI uri = Thread.currentThread().getContextClassLoader().getResource(filename).toURI();
        return Files.lines(Paths.get(uri)).map(l -> l.split(", "))
            .map(a -> new City(a[0], a[1], Integer.parseInt(a[2])));
    }

    public static class City {
        private String name;
        private String state;
        private int population;

        public City(String name, String state, int population) {
            this.name = name;
            this.state = state;
            this.population = population;
        }

        public String getName() {
            return name;
        }

        public String getState() {
            return state;
        }

        public int getPopulation() {
            return population;
        }
    }
}
