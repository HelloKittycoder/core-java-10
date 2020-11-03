package daemon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 14.4.2 守护线程
 * 从下面的例子可以看出，t1、t2（都是用户线程）已经完全执行完了，t3（守护线程）只执行了一部分，程序就结束了
 * 这说明：只要用户线程执行完毕，不管守护线程是否执行完，都会立即终止
 *
 * Created by shucheng on 2020/10/29 22:20
 */
public class DaemonThreadTest {

    public static final Logger LOGGER = LoggerFactory.getLogger(DaemonThreadTest.class);

    public static void main(String[] args) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 20; i++) {
                    LOGGER.info("number=={}", i);
                }
            }
        };

        Thread t1 = new Thread(r);
        t1.setName("userThread_1");

        Thread t2 = new Thread(r);
        t2.setName("userThread_2");

        Thread t3 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                LOGGER.info("number=={}", i);
            }
        });
        t3.setName("daemonThread");
        t3.setDaemon(true);

        t1.start();
        t2.start();
        t3.start();
    }
}
