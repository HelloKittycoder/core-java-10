package views;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 9.4.4 同步视图
 *
 * 参考链接：https://stackoverflow.com/questions/18542037/how-to-prove-that-hashmap-in-java-is-not-thread-safe
 * https://blog.csdn.net/programmer_at/article/details/78934278
 *
 * Created by shucheng on 10/27/2020 11:04 PM
 */
public class SynchronizedViewTest {

    // 验证下多线程下同步视图能否保证代码的线程安全
    public static void main(String[] args) throws InterruptedException {
        // HashMap是线程不安全的，synchronizedMap和ConcurrentHashMap是线程安全的
        // Map<TempValue, TempValue> myMap = new HashMap<>();
        // Map<TempValue, TempValue> myMap = Collections.synchronizedMap(new HashMap<>());
        Map<TempValue, TempValue> myMap = new ConcurrentHashMap<>();
        List<Thread> listOfThreads = new ArrayList<>();

        // Create 10 Threads
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(() -> {

                // Let Each thread insert 3000 Items
                for (int j = 0; j < 3000; j++) {
                    TempValue key = new TempValue();
                    myMap.put(key, key);
                }
            });
            thread.start();
            listOfThreads.add(thread);
        }

        for (Thread thread : listOfThreads) {
            thread.join();
        }
        System.out.println("Count should be 30000, actual is: " + myMap.size());
    }
}

class TempValue {
    int value = 3;

    @Override
    public int hashCode() {
        return 1; // All objects of this class will have same hashcode.
    }
}
