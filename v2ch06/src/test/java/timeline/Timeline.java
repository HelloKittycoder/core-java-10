package timeline;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 6.1 时间线
 * Created by shucheng on 2023/1/9 21:38
 */
public class Timeline {

    public static void main(String[] args) {
        Instant start = Instant.now();
        runAlgorithm();
        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start, end);
        long millis = timeElapsed.toMillis();
        System.out.printf("%d milliseconds%n", millis);

        Instant start2 = Instant.now();
        runAlgorithm2();
        Instant end2 = Instant.now();
        Duration timeElapsed2 = Duration.between(start2, end2);
        System.out.printf("%d milliseconds%n", timeElapsed2.toMillis());
        /**
         * lsc注：
         * a比b快10倍，书上写的是multipliedBy(10)，这个是有问题的
         *
         * 理由：
         * a比b快10倍，即b比a慢10倍，b比a多出来的时间比a的10倍还多
         * b-a>10a => 11a<b => 11a-b<0
         */
        boolean overTenTimesFaster = timeElapsed.multipliedBy(11)
                .minus(timeElapsed2).isNegative();
        System.out.printf("The first algorithm is %smore than ten times faster",
                overTenTimesFaster ? "" : "not ");
    }

    public static void runAlgorithm() {
        int size = 10;
        List<Integer> list = new Random().ints().map(i -> i % 100).limit(size)
                .boxed().collect(Collectors.toList());
        Collections.sort(list);
        System.out.println(list);
    }

    public static void runAlgorithm2() {
        int size = 10;
        List<Integer> list = new Random().ints().map(i -> i % 100).limit(size)
                .boxed().collect(Collectors.toList());
        while (!IntStream.range(1, list.size()).allMatch(
                i -> list.get(i - 1).compareTo(list.get(i)) <= 0))
            Collections.shuffle(list);
        System.out.println(list);
    }
}
