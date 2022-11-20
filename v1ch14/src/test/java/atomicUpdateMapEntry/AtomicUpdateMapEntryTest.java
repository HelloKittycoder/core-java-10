package atomicUpdateMapEntry;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

/**
 * 14.7.2 映射条目的原子更新
 *
 * 这里多线程情况下，反而比单线程慢了（多线程 54ms；单线程 22ms），原因后续再看下
 * Created by shucheng on 2020/10/31 22:17
 */
public class AtomicUpdateMapEntryTest {

    public static void main(String[] args) throws IOException, InterruptedException {
        // 参考了卷2的 CountLongWords（1.1 从迭代到流的操作）
        Path path = Paths.get("gutenberg/alice30.txt");
        String contents = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);

        List<String> words = Arrays.asList(contents.split("\\PL+"));

        Map<String, LongAdder> counts = new ConcurrentHashMap<>();

        int threadNum = 10;
        // 根据线程的数量划分区间，调用统计方法
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < threadNum; i++) {
            int[] intervalArr = calculateInterval(words.size(), threadNum, i);
            // 有关并发问题做了一些相关的验证，见ConcurrentHashMapTest，MultiThreadTest
            Thread thread = new Thread(() -> {
                putMap(intervalArr[0], intervalArr[1], words, counts);
            });
            thread.start();
            thread.join();
        }
        System.out.println("多线程花了" + (System.currentTimeMillis() - startTime) + "ms");

        // 对ConcurrentHashMap的结果按照字母顺序排序
        Map<String, Integer> treeMap = new TreeMap<>();
        for (Map.Entry<String, LongAdder> entry : counts.entrySet()) {
            treeMap.put(entry.getKey(), entry.getValue().intValue());
        }
        // 比较单线程和多线程的计算结果是否一致，实际情况是一致的
        // 这里为什么没有用counts和counts2进行比较？因为counts里的value存放的是LongAdder，而count2的value存放的是int
        Map<String, Integer> counts2 = test1(words);
        System.out.println(treeMap.equals(counts2)); // 关于map的equals使用见MapEqualTest
    }

    public static Map<String, Integer> test1(List<String> words) throws IOException {
        /*Path path = Paths.get("gutenberg/alice30.txt");
        String contents = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);

        List<String> words = Arrays.asList(contents.split("\\PL+"));*/

        Map<String, Integer> counts = new HashMap<>();
        long startTime = System.currentTimeMillis();
        for (String word : words) {
            // 写法1：
            // counts.put(word, counts.getOrDefault(word, 0) + 1);

            // 写法2：
            /*counts.putIfAbsent(word, 0);
            counts.put(word, counts.get(word) + 1);*/

            // 写法3：
            counts.merge(word, 1, Integer::sum);
        }
        System.out.println("单线程花了" + (System.currentTimeMillis() - startTime) + "ms");
        return counts;
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

    /**
     * 打印所有划分好的区间
     * 如：printInterval(100, 10)，每个区间长度为10
     * 会将0-99的索引范围划分为10个区间：[0,9],[10,19],[20,29],...,[90,99]
     * printInterval(101, 10)，每个区间长度为11
     * 会将0-99的索引范围划分为10个区间：[0,10],[11,21],[22,32],...,[99,100]
     * @param listSize
     * @param threadNum
     */
    public static void printInterval(int listSize, int threadNum) {
        // 比如：101，10应该划分为11个区间
        int interval = (int) Math.ceil(listSize / (threadNum * 1.0));
        for (int i = 0; i < threadNum; i++) {
            int start = i * interval;
            int end = i * interval + interval - 1;
            if (i == threadNum - 1) {
                end = end > listSize - 1 ? listSize - 1 :end;
            }
            System.out.println(start + " " + end);
        }
    }
}
