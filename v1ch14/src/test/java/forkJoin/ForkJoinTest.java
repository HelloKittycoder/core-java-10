package forkJoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.function.DoublePredicate;

/**
 * 14.9.4 Fork-Join框架
 * This program demonstrates the fork-join framework
 * Created by shucheng on 2020/11/3 20:38
 */
public class ForkJoinTest {

    public static void main(String[] args) {
        final int SIZE = 10000000;
        double[] numbers = new double[SIZE];
        for (int i = 0; i < SIZE; i++) numbers[i] = Math.random();

        /*
        // 使用普通方法计算大于0.5的数字个数
        int sum = 0;
        for (int i = 0; i < SIZE; i++) {
            if (numbers[i] > 0.5) {
                sum++;
            }
        }
        System.out.println(sum);*/

        // 使用ForkJoin计算大于0.5的数字个数
        Counter counter = new Counter(numbers, 0, numbers.length, x -> x > 0.5);
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(counter);
        System.out.println(counter.join());
    }
}

class Counter extends RecursiveTask<Integer> {
    public static final int THRESHOLD = 1000;
    private double[] values;
    private int from;
    private int to;
    private DoublePredicate filter;

    public Counter(double[] values, int from, int to, DoublePredicate filter) {
        this.values = values;
        this.from = from;
        this.to = to;
        this.filter = filter;
    }

    @Override
    protected Integer compute() {
        if (to - from < THRESHOLD) {

            int count = 0;
            for (int i = from; i < to; i++) {
                if (filter.test(values[i])) count++;
            }
            return count;
        } else {
            int mid = (from + to) / 2;
            Counter first = new Counter(values, from, mid, filter);
            Counter second = new Counter(values, mid, to, filter);
            invokeAll(first, second);
            return first.join() + second.join();
        }
    }
}
