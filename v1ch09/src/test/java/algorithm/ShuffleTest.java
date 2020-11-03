package algorithm;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 9.5.1 排序与混排
 * This program demonstrate the random shuffle and sort algorithms.
 *
 * Created by shucheng on 10/28/2020 7:58 PM
 */
public class ShuffleTest {

    @Test
    public void testShuffle() {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 49; i++) {
            numbers.add(i);
        }

        // 将集合中的数据顺序打乱
        Collections.shuffle(numbers);
        List<Integer> winningCombination = numbers.subList(0, 6);
        Collections.sort(winningCombination);
        System.out.println(winningCombination);
    }

    @Test
    public void testMax() {
        List<Integer> list = new ArrayList<>();
        list.add(8);
        list.add(3);
        list.add(2);
        list.add(5);
        list.add(1);
        // 找出集合中的最大元素
        Integer max = Collections.max(list);
        System.out.println(max);
    }
}
