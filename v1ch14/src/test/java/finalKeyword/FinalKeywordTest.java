package finalKeyword;

/**
 * 14.5.9 final变量
 * 尝试了半天，没法在代码层面把不用final时的情况给复现出来，有空再看看
 * 没加final的情况下，我也没访问到部分创建的对象
 *
 * 参考链接：https://www.cnblogs.com/jxldjsn/p/6115764.html
 * Created by shucheng on 2020/10/30 16:21
 */
public class FinalKeywordTest {
    static volatile AClass aClass;

    public static void main(String[] args) {

        Thread t = new Thread(() -> {
            createObject();
        });

        t.start();
        /*try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        for (int i = 0; i < 10; i++) {
            Runnable r = () -> {
                while (true) {
                    while (getObject() != null) {
                        if (getObject().str == null || "".equals(getObject().str)) {
                            System.out.println("str的值为===" + getObject().str);
                        }
                    }
                }
            };
            Thread t2 = new Thread(r);
            t2.start();
        }
    }

    public static void createObject() {
        aClass = new AClass();
    }

    public static AClass getObject() {
        return aClass;
    }
}

class AClass {

    String str = test();

    public String test() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "aa";
    }
}