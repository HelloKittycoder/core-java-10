package synchronizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Exchanger;

/**
 * 14.10.4 交换器
 * 两个线程进行数据交换用的。比如：有两个线程t1、t2，当t1调用了exchange后，
 * 它就会等t2执行exchange，当t2执行过exchange以后，t1、t2就会分别再执行
 * 它们exchange位置之后的代码
 *
 * 参考链接：https://www.cnblogs.com/senlinyang/p/7856332.html
 * Created by shucheng on 2020/11/3 22:44
 */
public class ExchangerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(Exchanger.class);
    private static final Exchanger<String> exchanger = new Exchanger<>();

    public static void main(String[] args) {
        new Thread(() -> {
            String strA = "a record data";
            try {
                exchanger.exchange(strA);
                /*String strB = exchanger.exchange(strA);
                LOGGER.info("strA交换数据===={}", strB);*/
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            String strB = "b record data";
            try {
                String strA = exchanger.exchange(strB);
                LOGGER.info("strB交换数据===={}", strA);
                LOGGER.info("strA.equals(strB)-->{}", strA.equals(strB));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();;
    }
}
