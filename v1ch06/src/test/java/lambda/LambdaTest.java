package lambda;

import javax.swing.*;
import java.util.Arrays;
import java.util.Date;

/**
 * 6.3.2 lambda表达式的语法
 * This program demonstrates the use of lambda expressions.
 *
 * Created by shucheng on 2020/10/10 17:14
 */
public class LambdaTest {

    public static void main(String[] args) {

        String[] planets = {"Mercury", "Venus", "Earth", "Mars",
            "Jupiter", "Saturn", "Uranus", "Neptune"};
        System.out.println(Arrays.toString(planets));
        System.out.println("Sorted in dictionary order:");
        Arrays.sort(planets);
        System.out.println(Arrays.toString(planets));
        System.out.println("Sorted by length:");
        Arrays.sort(planets, (first, second) -> first.length() - second.length());
        System.out.println(Arrays.toString(planets));

        Timer t = new Timer(1000, event -> {
            System.out.println("The time is " + new Date());
        });
        t.start();

        // keep program running util user selects "Ok"
        JOptionPane.showMessageDialog(null, "Quit program?");
        System.exit(0);
    }
}