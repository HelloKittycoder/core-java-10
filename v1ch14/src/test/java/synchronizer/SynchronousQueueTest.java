package synchronizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 14.10.5 同步队列
 * 参考链接：https://www.cnblogs.com/shamo89/p/7363339.html
 *
 * 一个线程（t1）做了put操作后，必须有另一个线程（t2）来进行take操作，
 * 不然t1会被阻塞
 * Created by shucheng on 2020/11/3 22:56
 */
public class SynchronousQueueTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SynchronousQueueTest.class);
    private static SynchronousQueue<Integer> queue = new SynchronousQueue<>();

    public static void main(String[] args) {
        // 生产者
        Runnable producer = () -> {
            for (int i = 1; i <= 4; i++) {
                int producedElement = ThreadLocalRandom.current().nextInt();
                try {
                    LOGGER.info("1s后生产者设值：{}", producedElement);
                    Thread.sleep(1000);
                    queue.put(producedElement);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        // 消费者
        Runnable consumer = () -> {
            for (int i = 1; i <= 5; i++) {
                try {
                    LOGGER.info("0.5s后消费者取值");
                    Integer consumedElement = queue.take();
                    LOGGER.info("消费者取值：{}", consumedElement);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        new Thread(producer).start();
        new Thread(consumer).start();
    }
}
