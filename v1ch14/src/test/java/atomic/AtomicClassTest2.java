package atomic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 14.5.10 原子性
 * 这个是网上找的例子，下面测试使用AtomicLong是线程安全的
 * 参考链接：https://www.cnblogs.com/JuncaiF/p/11285395.html
 * Created by shucheng on 2020/10/30 18:31
 */
public class AtomicClassTest2 {

    private static Logger LOGGER = LoggerFactory.getLogger(AtomicClassTest2.class);

    public static void main(String[] args) {
        AtomicClassTest2 test = new AtomicClassTest2();
        Count count = test.new Count();
        ExecutorService service = Executors.newCachedThreadPool();
        for (int i = 0; i < 100; i++) {
            service.execute(() -> count.increase());
        }
        service.shutdown();
        // 表示主线程等待各子线程都运行完毕后再执行后面的代码
        try {
            service.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LOGGER.info("End count: {}", count.getCount());
    }

    class Count {
        private AtomicLong count = new AtomicLong(0);
        public void increase() {
            LOGGER.info("count {}", count.incrementAndGet());
        }

        public Long getCount() {
            return count.get();
        }
    }
}