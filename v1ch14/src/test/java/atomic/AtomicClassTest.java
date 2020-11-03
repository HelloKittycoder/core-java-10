package atomic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 14.5.10 原子性
 * 这个是网上找的例子，下面测试直接使用Long时的并发问题
 * 参考链接：https://www.cnblogs.com/JuncaiF/p/11285395.html
 * Created by shucheng on 2020/10/30 17:43
 */
public class AtomicClassTest {

    private static Logger LOGGER = LoggerFactory.getLogger(AtomicClassTest.class);

    public static void main(String[] args) {
        AtomicClassTest test = new AtomicClassTest();
        Count count = test.new Count();
        ExecutorService service = Executors.newCachedThreadPool();
        for (int i = 0; i < 100; i++) {
            service.execute(() -> count.increase());
        }
        service.shutdown();
        try {
            service.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LOGGER.info("End count: {}", count.getCount());
    }

    class Count {
        private Long count = 0L;
        public void increase() {
            LOGGER.info("count {}", ++count);
        }

        public Long getCount() {
            return count;
        }
    }
}