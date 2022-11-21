package forkJoin;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.function.DoublePredicate;

/**
 * fork-join和其他处理方式的性能比较
 * 普通for循环最快，然后是foreach循环，接着是forkJoinPool，最后是Arrays.stream
 *
 * 普通for循环、foreach循环差距不大；
 * forkJoinPool比普通for循环、foreach循环慢很多；
 * Arrays.stream最慢，性能很差，用parallel也很慢
 *
 * forkJoinPool要在处理数据量很大的情况下才能体现优势
 *
 * Created by shucheng on 2022/11/22 6:32
 */
public class ForkJoinTest2 {

    public static void main(String[] args) {
        int SIZE = 20_000_000;
        double[] numbers = new double[SIZE];
        for (int i = 0; i < SIZE; i++) numbers[i] = Math.random();

        // 17ms
        elapsed(() -> {
            int count = 0;
            for (int i = 0; i < SIZE; i++) {
                if (numbers[i] > 0.5) {
                    count++;
                }
            }
            System.out.println(count);
        }, "普通for循环");

        // 18ms
        elapsed(() -> {
            int count = 0;
            for (double d : numbers) {
                if (d > 0.5) {
                    count++;
                }
            }
            System.out.println(count);
        }, "foreach循环");

        // 74ms
        elapsed(() -> {
            long count = Arrays.stream(numbers).filter(num -> num > 0.5).count();
            System.out.println(count);
        }, "Arrays.stream过滤");

        // 25ms
        elapsed(() -> {
            ForkJoinPool forkJoinPool = new ForkJoinPool();
            Counter counter = new Counter(numbers, 0, SIZE, num -> num > 0.5);
            Integer result = forkJoinPool.invoke(counter);
            System.out.println(result);
        }, "forkJoinPool");
    }

    public static void elapsed(Runnable runnable, String description) {
        long start = System.currentTimeMillis();
        runnable.run();
        long elapsed = System.currentTimeMillis() - start;
        System.out.printf("【%s】耗时：%d%n", description, elapsed);
    }

    static class Counter extends RecursiveTask<Integer> {

        public static final int THRESHOLD = 1000;
        private double[] values;
        private int from;
        private int to;
        private DoublePredicate predicate;

        public Counter(double[] values, int from, int to, DoublePredicate predicate) {
            this.values = values;
            this.from = from;
            this.to = to;
            this.predicate = predicate;
        }

        @Override
        protected Integer compute() {
            if (to - from < THRESHOLD) {
                int count = 0;
                for (int i = from; i < to; i++) {
                    if (predicate.test(values[i])) {
                        count++;
                    }
                }
                return count;
            } else {
                int mid = (from + to) / 2;
                Counter first = new Counter(values, from, mid, predicate);
                Counter second = new Counter(values, mid, to, predicate);
                invokeAll(first, second);
                return first.join() + second.join();
            }
        }
    }
}


