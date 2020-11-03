package atomicUpdateMapEntry;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 如下代码的并发问题，是指run方法本身写法产生的并发，和数据结构本身的并发问题是两类问题
 * 这里简单说下：
 * 数据结构层面的：
 * ConcurrentHashMap这个数据结构是没有并发问题的（P675），这里是只单纯的进行put，get操作不会使ConcurrentHashMap的结构混乱；
 * HashMap就可能会出问题
 * 因为ConcurrentHashMap进行了分段操作（可以看下put方法里synchronized部分），而HashMap在put时没有相关的同步操作
 * 所以从数据结构层面将，多线程情况下需要使用ConcurrentHashMap来保证线程安全
 *
 * 自己写的代码层面的：
 * 以下面的代码为例，下面的本意是想100个线程运行后，test对应的值为100
 * run方法内部get为null后直接做put操作，因为有多个线程同时进来，并且在if判断处同时为null，所以就有可能多次put，
 * 这样导致计数变少，并没有100
 * 正确的写法是把put换成putIfAbsent，或者直接把if判断部分去掉，然后改成CACHE_MAP.putIfAbsent(KEY, new AtomicInteger());
 *
 * 参考链接：https://blog.csdn.net/u011983531/article/details/65936945
 * Created by shucheng on 2020/10/31 23:00
 */
public class ConcurrentHashMapTest {

    private static final ConcurrentMap<String, AtomicInteger> CACHE_MAP = new ConcurrentHashMap<>();
    private static final String KEY = "test";

    private static class TestThread implements Runnable{
        @Override
        public void run() {
            if (CACHE_MAP.get(KEY) == null) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                CACHE_MAP.put(KEY, new AtomicInteger());
            }
            CACHE_MAP.get(KEY).incrementAndGet();
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i ++) {
            new Thread(new TestThread()).start();
        }
        /*new Thread(new TestThread()).start();
        new Thread(new TestThread()).start();
        new Thread(new TestThread()).start();*/
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("次数:"+CACHE_MAP.get(KEY).get());
    }
}
