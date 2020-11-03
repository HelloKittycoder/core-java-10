package atomicUpdateMapEntry;

/**
 * 下面是运行多个线程，各自打印不同范围的数字
 * 首先说明下，下面的代码不存在线程安全问题
 * 因为printNumber使用的是方法局部变量（方法声明上是int，如果是引用类型，且多个线程用的是同一个引用，可能会有并发问题），
 * 各个线程之间并没有共享变量
 *
 * 在AtomicUpdateMapEntryTest中putMap方法上使用了map，多个线程在操作这个map，但这里不存在线程安全问题，
 * 因为这个map实际类型是ConcurrentHashMap，不同线程操作的是map上的不同的部分
 *
 * 参考链接：https://blog.csdn.net/qq_40294980/article/details/82286878
 * Created by shucheng on 2020/10/31 22:43
 */
public class MultiThreadTest {

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            printNumber(1, 5);
        });
        Thread t2 = new Thread(() -> {
            printNumber(11, 15);
        });
        Thread t3 = new Thread(() -> {
            printNumber(21, 25);
        });

        t1.start();
        t2.start();
        t3.start();
    }

    public static void printNumber(int start, int end) {
        for (int i = start; i < end; i++) {
            System.out.println(Thread.currentThread().getName() + ":" + i);
        }
    }
}
