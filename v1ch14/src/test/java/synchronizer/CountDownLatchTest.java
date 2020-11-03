package synchronizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 14.10.2 倒计时门栓
 * 参考链接：https://www.cnblogs.com/54chensongxia/p/12721925.html
 * Created by shucheng on 2020/11/3 21:23
 */
public class CountDownLatchTest {

    public static final int PERSON_COUNT = 5;
    public static final CountDownLatch c = new CountDownLatch(PERSON_COUNT);
    public static final Logger LOGGER = LoggerFactory.getLogger(CountDownLatchTest.class);

    public static void main(String[] args) throws InterruptedException {
        LOGGER.info("I am master, waiting guests...");
        for (int i = 0; i < PERSON_COUNT; i++) {
            final int personNo = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    LOGGER.info("I am person [" + personNo + "]");
                    try {
                        // TimeUnit.SECONDS.sleep(1);
                        // 这里让第i位客人等i秒，方便看出CountDownLatch的count变化情况，不然可能连续打出5个0
                        TimeUnit.SECONDS.sleep(personNo);
                        c.countDown();
                        LOGGER.debug("CountDownLatch count {}--PersonNo {}", c.getCount(), personNo);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        c.await();
        LOGGER.info("all guests arrived, begin dinner...");
    }
}
