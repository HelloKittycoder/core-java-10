package exceptional;

import java.util.Date;
import java.util.EmptyStackException;
import java.util.Stack;

/**
 * 7.3 使用异常机制的技巧
 * 异常处理不能代替简单的测试（P283）
 *
 * Created by shucheng on 2020/10/14 17:37
 */
public class ExceptionalTest {

    public static void main(String[] args) {
        int i = 0;
        int ntry = 10000000;
        Stack<String> s = new Stack<>();
        long s1;
        long s2;

        // test a stack for epmtiness ntry times
        System.out.println("Testing for empty stack");
        s1 = new Date().getTime();
        for (i = 0; i < ntry; i++)
            if (!s.empty()) s.pop();
        s2 = new Date().getTime();
        System.out.println((s2 - s1) + " milliseconds"); // 223ms

        // pop an empty stack ntry times and catch the resulting exception
        System.out.println("Catching EmptyStackException");
        s1 = new Date().getTime();
        for (i = 0; i < ntry; i++) {
            try {
                s.pop();
            } catch (EmptyStackException e) {}
        }
        s2 = new Date().getTime();
        System.out.println((s2 - s1) + " milliseconds"); // 5804ms
    }
}