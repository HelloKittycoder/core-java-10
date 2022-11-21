package threadPool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * 14.9.2 预定执行
 * Created by shucheng on 2020/11/2 22:36
 */
public class ScheduledExecutorServiceTest {

    public static final Logger LOGGER = LoggerFactory.getLogger(ScheduledExecutorServiceTest.class);

    public static void main(String[] args) {
        testSchedule();
        // testScheduleAtFixedRate();
    }

    /**
     * schedule(Runnable task, long time, TimeUnit unit)
     * 预定在指定的时间之后执行任务
     */
    public static void testSchedule() {
        LOGGER.info("程序启动");
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);
        // 延时1s后执行Runnable中的内容
        ScheduledFuture<?> future = executorService.schedule(() -> {
            LOGGER.info("aaa");
        }, 1, TimeUnit.SECONDS);
    }

    /**
     * scheduleAtFixedRate(Runnable task, long initialDelay, long period, TimeUnit unit)
     * 预定在初始的延迟结束后，周期性地运行给定的任务，周期长度是period
     */
    public static void testScheduleAtFixedRate() {
        LOGGER.info("程序启动");
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(5);
        // 延时1s后执行Runnable中的内容，每隔1s执行一次
        executorService.scheduleAtFixedRate(() -> {
            LOGGER.info("aaa");
        }, 1, 1, TimeUnit.SECONDS);
        executorService.scheduleAtFixedRate(()-> {
            LOGGER.info("bbb");
        }, 1, 1, TimeUnit.SECONDS);
    }
}
