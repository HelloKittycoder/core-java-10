package atomicUpdateMapEntry;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

/**
 * 这个是对AtomicUpdateMapEntryTest中多线程处理的改进，前面用的是手动计算区间，然后在各自区间上求和
 * 这里是直接对ConcurrentHashMap的value做累加操作，不过多个线程取list中的数据借助了BlockingQueue
 * （多线程 73ms；单线程 19ms）这里还是多线程比单线程慢了，原因后续再看下
 *
 * 下面的写法参考了BlockingQueueTest（14.6 阻塞队列）
 * Created by shucheng on 2022/11/20 20:45
 */
public class AtomicUpdateMapEntryTest2 {

    private static BlockingQueue<String> queue = new ArrayBlockingQueue<>(100);
    public static final String DUMMY = new String("");

    public static void main(String[] args) throws IOException, InterruptedException {
        // 参考了卷2的 CountLongWords（1.1 从迭代到流的操作）
        Path path = Paths.get("gutenberg/alice30.txt");
        String contents = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);

        List<String> words = Arrays.asList(contents.split("\\PL+"));
        Map<String, LongAdder> counts = new ConcurrentHashMap<>();

        int threadNum = 10;
        long startTime = System.currentTimeMillis();
        Runnable producer = () -> {
            try {
                for (String word : words) {
                    queue.put(word);
                }
                queue.put(DUMMY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        new Thread(producer).start();

        for (int i = 0; i < threadNum; i++) {
            Runnable consumer = () -> {
                try {
                    boolean done = false;
                    while (!done) {
                        String word = queue.take();
                        if (word == DUMMY) {
                            queue.put(word);
                            done = true;
                        } else {
                            counts.putIfAbsent(word, new LongAdder());
                            counts.get(word).increment();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };

            Thread thread = new Thread(consumer);
            thread.start();
            thread.join();
        }
        System.out.println("多线程花了" + (System.currentTimeMillis() - startTime) + "ms");

        // 对ConcurrentHashMap的结果按照字母顺序排序
        Map<String, Integer> treeMap = new TreeMap<>();
        for (Map.Entry<String, LongAdder> entry : counts.entrySet()) {
            treeMap.put(entry.getKey(), entry.getValue().intValue());
        }

        Map<String, Integer> counts2 = test1(words);
        System.out.println(treeMap.equals(counts2)); // 关于map的equals使用见MapEqualTest
    }

    public static Map<String, Integer> test1(List<String> words) throws IOException {
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
}
