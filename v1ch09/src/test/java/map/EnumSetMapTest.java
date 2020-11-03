package map;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;

/**
 * 9.3.6 枚举集与映射
 *
 * 以下只是简单使用EnumSet、EnumMap的api，没有去深入
 * 参考链接：https://www.cnblogs.com/csuwater/p/5405735.html
 * Created by shucheng on 2020/10/27 16:19
 */
public class EnumSetMapTest {

    public static void main(String[] args) {
        // EnumSet的使用
        EnumSet<Weekday> weekdayEnumSet = EnumSet.allOf(Weekday.class);
        // System.out.println(weekdayEnumSet);
        // 遍历数据
        for (Weekday weekday : weekdayEnumSet) {
            System.out.println(weekday);
        }
        // 判断Set中是否包含某个key
        System.out.println(weekdayEnumSet.contains(Weekday.TUESDAY));

        // EnumMap的使用
        EnumMap<Weekday, String> weekdayEnumMap = new EnumMap<>(Weekday.class);
        weekdayEnumMap.put(Weekday.MONDAY, "周一");
        weekdayEnumMap.put(Weekday.TUESDAY, "周二");

        // 遍历数据
        for (Map.Entry<Weekday, String> entry : weekdayEnumMap.entrySet()) {
            System.out.println(entry.getKey() + "->" + entry.getValue());
        }
        // 根据key获取value
        System.out.println(weekdayEnumMap.get(Weekday.MONDAY));
    }
}

enum Weekday {
    MONDAY, TUESDAY, WEDNESDAY, THUSDAY, FRIDAY, SATURDAY, SUNDAY
}