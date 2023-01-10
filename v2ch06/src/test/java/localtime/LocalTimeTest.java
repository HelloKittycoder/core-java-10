package localtime;

import org.junit.Test;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

/**
 * 6.4 本地时间
 * Created by shucheng on 2023/1/10 12:34
 */
public class LocalTimeTest {

    @Test
    public void test() {
        LocalTime rightNow = LocalTime.now(); // 当前时间
        System.out.println(rightNow);

        LocalTime bedtime = LocalTime.of(22, 30);
        // 计算bedtime往后推8小时的时间
        LocalTime wakeupTime = bedtime.plusHours(8);// wake up is 6:30:00
        System.out.println(wakeupTime);

        System.out.println(bedtime.minusHours(8)); // 14:30:00

        System.out.println(bedtime.plusMinutes(10)); // 22:40:00
        System.out.println(bedtime.minusMinutes(10)); // 22:20:00

        LocalTime plus2Hours = bedtime.plus(Duration.of(-2, ChronoUnit.HOURS));
        System.out.println(plus2Hours); // 20:30:00

        // 将bedtime的秒设置为35
        System.out.println(bedtime.withSecond(35)); // 22:30:35

        // 打印0点到oneClock对应的时间，过了多少秒
        LocalTime oneClock = LocalTime.of(1, 0);
        System.out.println(oneClock.toSecondOfDay()); // 3600

        // 打印1:00是否比22:30早
        System.out.println(oneClock.isBefore(bedtime)); // true
    }
}
