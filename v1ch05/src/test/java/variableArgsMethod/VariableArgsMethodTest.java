package variableArgsMethod;

/**
 * 5.5 参数数量可变的方法
 * Created by shucheng on 2020/10/9 17:31
 */
public class VariableArgsMethodTest {

    // 说明：String[] args 也能写成 String... args
    public static void main(String[] args) {
        double m = max(3.1, 40.4, -5);
        System.out.println(m);
    }

    public static double max(double... values) {
        double largest = Double.NEGATIVE_INFINITY;
        for (double v : values) if (v > largest) largest = v;
        return largest;
    }
}