package stackTrace;

/**
 * 7.2.6 分析堆栈轨迹元素
 * A program that displays a trace feature of a recursive method call.
 *
 * Created by shucheng on 2020/10/14 16:21
 */
public class StackTraceTest {

    public static void main(String[] args) {
        factorial(3);
    }

    /**
     * Computes the factorial of a number
     * @param n non-negative integer
     * @return n! = 1 * 2 * ... * n
     */
    public static int factorial(int n) {
        System.out.println("factorial(" + n + "):");
        Throwable t = new Throwable();
        StackTraceElement[] frames = t.getStackTrace();
        for (StackTraceElement f : frames)
            System.out.println(f);
        int r;
        if (n <= 1) r = 1;
        else r = n * factorial(n - 1);
        System.out.println("return " + r);
        return r;
    }
}