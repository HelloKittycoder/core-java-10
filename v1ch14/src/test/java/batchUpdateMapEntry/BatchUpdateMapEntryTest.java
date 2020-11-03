package batchUpdateMapEntry;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

/**
 * 14.7.3 对并发散列映射的批操作（P677）
 * Created by shucheng on 2020/11/1 21:26
 */
public class BatchUpdateMapEntryTest {

    public static void main(String[] args) throws IOException, InterruptedException {
        // 参考了卷2的 CountLongWords（1.1 从迭代到流的操作）
        Path path = Paths.get("gutenberg/alice30.txt");
        String contents = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);

        List<String> words = Arrays.asList(contents.split("\\PL+"));

        ConcurrentHashMap<String, LongAdder> counts = new ConcurrentHashMap<>();
        List<Thread> listOfThreads = new ArrayList<>();

        int threadNum = 10;
        // 根据线程的数量划分区间，调用统计方法
        for (int i = 0; i < threadNum; i++) {
            int[] intervalArr = calculateInterval(words.size(), threadNum, i);
            // 有关并发问题做了一些相关的验证，见ConcurrentHashMapTest，MultiThreadTest
            Thread thread = new Thread(() -> {
                putMap(intervalArr[0], intervalArr[1], words, counts);
            });
            thread.start();
            listOfThreads.add(thread);
        }

        for (Thread thread : listOfThreads) {
            thread.join();
        }
        // 找出第一个出现次数超过1000次的单词
        String result = counts.search(1, (k, v) -> v.intValue() > 1000 ? k : null);
        System.out.println(result); // 刚好找到the

        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("aa", 11);
        map.put("bb", 22);
        map.put("cc", 33);
        // 循环打印所有的映射条目
        // map.forEach(1, (k, v) -> System.out.println(k + "->" + v));
        map.forEach(1, (k, v) -> k + "->" + v, System.out::println);

        // 只打印counts中有大值的条目
        counts.forEach(1, (k, v) -> v.intValue() > 1000 ? k + "->" + v : null,
                System.out::println);

        // 计算所有value的总和
        Integer sum = map.reduceValues(1, Integer::sum); // 66
        System.out.println(sum);

        // 计算最长的键的长度
        Integer maxLength = counts.reduceKeys(1,
                String::length, Integer::max);
        // 找出最长的键
        ConcurrentHashMap.KeySetView<String, LongAdder> set = counts.keySet();
        int initLength = 0;
        String longestKey = "";
        for (String str : set) {
            if (str.length() > initLength) {
                initLength = str.length();
                longestKey = str;
            }
        }
        System.out.println(longestKey + "=====" + initLength + "-" + maxLength); // initLength和maxLength是一样的

        // 统计多少个条目的值>1000
        Long acount = counts.reduceValues(1, v -> v.longValue() > 1000 ? 1L : null, Long::sum);
        System.out.println(acount); // 只有一个，就是the
    }

    // 这个参考了卷1的 UpdateMapTest（9.3.2 更新映射项）
    public static void  putMap(int start, int end, List<String> list, Map<String, LongAdder> map) {
        // System.out.println("start--" + start + "====end--" + end);
        for (int i = start; i <= end; i++) {
            String word = list.get(i);
            map.putIfAbsent(word, new LongAdder());
            map.get(word).increment();
        }
    }

    /**
     * 返回划分好的第i个区间
     * 如：calculateInterval(100, 10, 1)返回[10,19]
     * calculateInterval(101, 10, 1)返回[11,21]
     * @param listSize
     * @param threadNum
     * @param index
     * @return
     */
    public static int[] calculateInterval(int listSize, int threadNum, int index) {
        int interval = (int) Math.ceil(listSize / (threadNum * 1.0));
        int start = index * interval;
        int end = index * interval + interval - 1;
        if (index == threadNum - 1) {
            end = end > listSize - 1 ? listSize - 1 :end;
        }
        return new int[]{start, end};
    }
}
