package priority;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 14.4.1 线程优先级
 * 这里好像设置后效果不明显，不知道是哪里写的有问题
 * Created by shucheng on 2020/10/29 21:29
 */
public class ThreadPriorityTest {

    // public static final Logger LOGGER = LoggerFactory.getLogger(ThreadPriorityTest.class);

    public static void main(String[] args) {

        Runnable r = () -> {

            System.out.println(Thread.currentThread().getName() + " priority="
                    + Thread.currentThread().getPriority());
            System.out.println(Thread.currentThread().getName() + " Run begin");

            for (int i = 0; i < 20; i++) {
                System.out.println(Thread.currentThread().getName() + "::" + i);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + " Run end");
        };

        /*Runnable r = () -> {
            LOGGER.info("priority={}", Thread.currentThread().getPriority());
            LOGGER.info("{} Run begin", Thread.currentThread().getName());

            for (int i = 0; i < 20; i++) {
                LOGGER.info("{} :: {}", Thread.currentThread().getName(), i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            LOGGER.info("{} Run end", Thread.currentThread().getName());
        };*/

        Thread p4 = new Thread(r);
        Thread p6 = new Thread(r);
        p4.setName("P4");
        p6.setName("P6");
        p4.setPriority(1);
        p6.setPriority(10);

        p4.start();
        p6.start();
    }
}
