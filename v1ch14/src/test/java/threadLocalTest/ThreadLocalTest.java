package threadLocalTest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 14.5.12 线程局部变量
 * 不使用ThreadLocal时，直接用SimpleDateFormat会造成结果混乱
 * Created by shucheng on 2022/11/15 21:22
 */
public class ThreadLocalTest {

    private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    private static Map<String, Date> dateMap = new LinkedHashMap<>();
    static {
        try {
            for (int i = 1; i <= 31; i++) {
                String date = "2022-10-" + (i < 10 ? "0" + i : i);
                dateMap.put(date, df.parse(date));
            }
            for (int i = 1; i <= 30; i++) {
                String date = "2022-11-" + (i < 10 ? "0" + i : i);
                dateMap.put(date, df.parse(date));
            }
            for (int i = 1; i <= 31; i++) {
                String date = "2022-12-" + (i < 10 ? "0" + i : i);
                dateMap.put(date, df.parse(date));
            }
            /*dateMap.put("2022-11-01", df.parse("2022-11-01"));
            dateMap.put("2022-11-11", df.parse("2022-11-11"));*/
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newCachedThreadPool();
        for (Map.Entry<String, Date> entry : dateMap.entrySet()) {
            service.submit(() -> {
                String original = entry.getKey();
                String result = df.format(entry.getValue());
                System.out.println(Thread.currentThread().getName() + "==>original: " + original + "," + "result: " + result);
            });
        }
        service.shutdown();
        service.awaitTermination(1, TimeUnit.DAYS);
    }
}
