package temporaladjuster;

import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

/**
 * 6.3 日期调整器
 * TemporalAdjusters类的使用
 *
 * Created by shucheng on 2023/1/10 11:59
 */
public class TimeAdjusterTest {

    @Test
    public void test() {
        TemporalAdjuster NEXT_WORKDAY = w -> {
            LocalDate result = (LocalDate) w;
            do {
                result = result.plusDays(1);
            } while (result.getDayOfWeek().getValue() >= 6);
            return result;
        };
        /*// 上面还可以写成
        TemporalAdjuster NEXT_WORKDAY = TemporalAdjusters.ofDateAdjuster(w -> {
            LocalDate result = w;
            do {
                result = result.plusDays(1);
            } while (result.getDayOfWeek().getValue() >= 6);
            return result;
        });*/

        // 计算今天（2023-1-10）后的下一个工作日（这里的工作日指周一至周五） 2023-01-11
        // LocalDate today = LocalDate.now();
        LocalDate today = LocalDate.of(2023, 1, 10);
        LocalDate nextWorkday = today.with(NEXT_WORKDAY);
        System.out.println(nextWorkday);
    }

    @Test
    public void test2() {
        // LocalDate today = LocalDate.now();
        LocalDate today = LocalDate.of(2023, 1, 10);

        // 计算2023-1-10以后的下一个星期一 2023-1-16
        TemporalAdjuster NEXT_MONDAY = TemporalAdjusters.next(DayOfWeek.MONDAY);
        LocalDate nextMonday = today.with(NEXT_MONDAY);
        System.out.printf("nextMonday %s%n", nextMonday);

        // 计算2023-1-10以前的上一个星期一 2023-1-9
        TemporalAdjuster PREVIOUS_MONDAY = TemporalAdjusters.previous(DayOfWeek.MONDAY);
        LocalDate previousMonday = today.with(PREVIOUS_MONDAY);
        System.out.printf("previousMonday %s%n", previousMonday);

        // 计算2023-1-10所在月份（2023-1）的第4个星期一 2023-1-23
        TemporalAdjuster CURRENT_MONTH_FOURTH_MONDAY = TemporalAdjusters.dayOfWeekInMonth(4, DayOfWeek.MONDAY);
        LocalDate currentMonthFourthMonday = today.with(CURRENT_MONTH_FOURTH_MONDAY);
        System.out.printf("currentMonthFourthMonday %s%n", currentMonthFourthMonday);

        // 计算2023-1-10所在月份（2023-1）的最后一个星期五 2023-1-27
        TemporalAdjuster CURRENT_MONTH_LAST_FRIDAY = TemporalAdjusters.lastInMonth(DayOfWeek.FRIDAY);
        LocalDate currentMonthLastFriday = today.with(CURRENT_MONTH_LAST_FRIDAY);
        System.out.printf("currentMonthLastFriday %s%n", currentMonthLastFriday);

        // 计算2023-1-10所在月份（2023-1）的第1天 2023-1-1
        TemporalAdjuster FIRST_DAY_OF_MONTH = TemporalAdjusters.firstDayOfMonth();
        LocalDate firstDayOfMonth = today.with(FIRST_DAY_OF_MONTH);
        System.out.printf("firstDayOfMonth %s%n", firstDayOfMonth);

        // 计算2023-1-10所在月份（2023-1）的最后1天 2023-1-31
        TemporalAdjuster LAST_DAY_OF_MONTH = TemporalAdjusters.lastDayOfMonth();
        LocalDate lastDayOfMonth = today.with(LAST_DAY_OF_MONTH);
        System.out.printf("lastDayOfMonth %s%n", lastDayOfMonth);

        // 计算2023-1-10所在月份（2023-1）的下个月的第1天 2023-2-1
        TemporalAdjuster FIRST_DAY_OF_NEXT_MONTH = TemporalAdjusters.firstDayOfNextMonth();
        LocalDate firstDayOfNextMonth = today.with(FIRST_DAY_OF_NEXT_MONTH);
        System.out.printf("firstDayOfNextMonth %s%n", firstDayOfNextMonth);

        // 计算2023-1-10所在年（2023）的第1天 2023-1-1
        TemporalAdjuster FIRST_DAY_OF_YEAR = TemporalAdjusters.firstDayOfYear();
        LocalDate firstDayOfYear = today.with(FIRST_DAY_OF_YEAR);
        System.out.printf("firstDayOfYear %s%n", firstDayOfYear);

        // 计算2023-1-10所在年（2023）的最后1天 2023-12-31
        TemporalAdjuster LAST_DAY_OF_YEAR = TemporalAdjusters.lastDayOfYear();
        LocalDate lastDayOfYear = today.with(LAST_DAY_OF_YEAR);
        System.out.printf("lastDayOfYear %s%n", lastDayOfYear);
    }
}
