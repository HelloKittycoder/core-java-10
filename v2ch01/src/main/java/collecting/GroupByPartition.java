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
    }
}
