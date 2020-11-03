package uncaughtException;

/**
 * 14.4.3 未捕获异常
 * 参考链接：https://www.yiibai.com/java/lang/thread_setuncaughtexceptionhandler.html
 * Created by shucheng on 2020/10/29 22:39
 */
public class UncaughtExceptionTest {

    /**
     * 感觉用不用UncaughtExceptionHandler没啥差别，不用的情况下，
     * 错误一样能显示出来，程序也能完整运行
     *
     * 暂时还不能理解UncaughtExceptionHandler的用途
     * @param args
     */
    public static void main(String[] args) {
        /*Thread t = new Thread(new AdminRunnable());
        t.start();
        System.out.println("111");
        System.out.println("222");*/

        Thread t = new Thread(new AdminRunnable());
        t.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println(t + " throws exception: " + e);
            }
        });
        t.start();
        System.out.println("111");
        System.out.println("222");
    }
}

class AdminRunnable implements Runnable {

    @Override
    public void run() {
        throw new RuntimeException();
    }
}