package checkedException;

import java.io.File;
import java.util.Scanner;

/**
 * 8.6.9 不能抛出或捕获泛型类的实例
 * Created by shucheng on 2020/10/15 11:39
 */
public class CheckedExceptionTest {

    public static void main(String[] args) {
        new Block() {
            @Override
            public void body() throws Exception {
                Scanner in = new Scanner(new File("ququx"), "UTF-8");
                while (in.hasNext()) {
                    System.out.println(in.next());
                }
            }
        }.toThread().start();
    }
}