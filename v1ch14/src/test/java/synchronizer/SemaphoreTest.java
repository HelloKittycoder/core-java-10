package synchronizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 14.10.1 信号量
 * 参考链接：https://www.cnblogs.com/xd502djj/p/13574810.html
 * Created by shucheng on 2020/11/3 21:11
 */
public class SemaphoreTest {

    // 定义一个信号量Semaphore
    static Semaphore sp = new Semaphore(3);

    public static void main(String[] args) {
        // 定义10个学生去打饭
        for (int i = 0; i < 10; i++) {
            new Student(sp, "学生" + i).start();
        }
    }
}

class Student extends Thread {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private Semaphore sp;
    private String name;

    public Student(Semaphore sp, String name) {
        this.sp = sp;
        this.name = name;
    }

    @Override
    public void run() {
        try {
            logger.debug(name + "进入了餐厅");
            sp.acquire();
            logger.info(name + "拿到了打饭的许可");
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            logger.info(name + "打好了饭，释放这个窗口");
            sp.release();
        }
    }
}
