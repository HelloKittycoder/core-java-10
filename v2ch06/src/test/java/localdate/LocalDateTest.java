package localdate;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;

/**
 * 6.2 本地时间
 * Created by shucheng on 2023/1/9 21:57
 */
public class LocalDateTest {

    public static void main(String[] args) {
        LocalDate today = LocalDate.now();// Today's date
        System.out.println("today: " + today);

        // lambda演算的发明者Alonzo Church在这一天诞生
        LocalDate alonzosBirthday = LocalDate.of(1903, 6, 14);
        alonzosBirthday = LocalDate.of(1903, Month.JUNE, 14);
        // Uses the Month enumeration
        System.out.println("alonzosBirthday: " + alonzosBirthday);

        // 程序员日是每年的第256天
        LocalDate programmersDay = LocalDate.of(2018, 1, 1).plusDays(255);
        // September 13, but in a leap year it would be September 12
        System.out.println("programmersDay: " + programmersDay);

        LocalDate independenceDay = LocalDate.of(2018, Month.JULY, 4);
        LocalDate christmas = LocalDate.of(2018, Month.DECEMBER, 25);

        /**
         * 计算2018-7-4到2018-12-25（前闭后开），中间间隔多长时间 Period
         *
         * Instant（精确到纳秒）的间隔-->Duration
         * LocalDate（精确到日）的间隔-->Period
         */
        System.out.println("Util christmas: " + independenceDay.until(christmas)); // 5个月21天
        System.out.println("Util christmas: " + independenceDay.until(christmas, ChronoUnit.DAYS));

        // 下面的两个日期调用plusMonths、minusMonths后会返回该月有效的最后一天
        System.out.println(LocalDate.of(2016, 1, 31).plusMonths(1)); // 2016-02-29
        System.out.println(LocalDate.of(2016, 3, 31).minusMonths(1)); // 2016-02-29

        DayOfWeek startOfLastMillennium = LocalDate.of(1900, 1, 1).getDayOfWeek();
        System.out.println("startOfLastMillennium: " + startOfLastMillennium);
        System.out.println(startOfLastMillennium.getValue());
        System.out.println(DayOfWeek.SATURDAY.plus(3)); // TUESDAY
    }
}
