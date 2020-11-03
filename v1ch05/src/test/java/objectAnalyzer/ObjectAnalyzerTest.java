package objectAnalyzer;

import java.util.ArrayList;

/**
 * 5.7.4 在运行时使用反射分析对象
 * Created by shucheng on 2020/10/10 10:53
 */
public class ObjectAnalyzerTest {

    public static void main(String[] args) {
        /*String str = "test string";
        System.out.println(new ObjectAnalyzer().toString(str));*/

        /*int[] arr = {1, 2, 3, 4, 5};
        System.out.println(new ObjectAnalyzer().toString(arr));*/

        ArrayList<Integer> squares = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            squares.add(i * i);
        }
        System.out.println(new ObjectAnalyzer().toString(squares));
    }
}